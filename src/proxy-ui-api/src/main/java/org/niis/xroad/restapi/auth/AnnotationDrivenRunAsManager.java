package org.niis.xroad.restapi.auth;

import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * dasdas
 */
public class AnnotationDrivenRunAsManager extends RunAsManagerImpl {

    @Override
    public Authentication buildRunAs(Authentication authentication, Object object,
            Collection<ConfigAttribute> attributes) {
        if (!(object instanceof ReflectiveMethodInvocation)
                    || ((ReflectiveMethodInvocation)object).getMethod().getAnnotation(RunAsRole.class) == null) {
            return super.buildRunAs(authentication, object, attributes);
        }

        String[] roleNames = ((ReflectiveMethodInvocation)object).getMethod().getAnnotation(RunAsRole.class).values();

        if (roleNames == null || roleNames.length == 0) {
            return null;
        }

        List<GrantedAuthority> newAuthorities = new ArrayList<GrantedAuthority>();
        for (String roleName: roleNames) {
            GrantedAuthority runAsAuthority = new SimpleGrantedAuthority(roleName);
            // Add existing authorities
            newAuthorities.addAll(authentication.getAuthorities());
            // Add the new run-as authority
            newAuthorities.add(runAsAuthority);
        }

        return new RunAsUserToken(getKey(), authentication.getPrincipal(), authentication.getCredentials(),
                newAuthorities, authentication.getClass());
    }
}
