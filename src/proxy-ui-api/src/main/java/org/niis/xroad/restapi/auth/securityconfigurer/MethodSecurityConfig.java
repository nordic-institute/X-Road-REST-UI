package org.niis.xroad.restapi.auth.securityconfigurer;

import org.niis.xroad.restapi.auth.AnnotationDrivenRunAsManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(proxyTargetClass = true, prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Override
    protected RunAsManager runAsManager() {
        RunAsManagerImpl runAsManager = new AnnotationDrivenRunAsManager();
        runAsManager.setKey("MyRunAsKey");
        return runAsManager;
    }
}