/**
 * The MIT License
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.niis.xroad.restapi.service;

import ee.ria.xroad.common.conf.serverconf.model.ClientType;
import ee.ria.xroad.common.conf.serverconf.model.LocalGroupType;
import ee.ria.xroad.common.identifier.ClientId;

import lombok.extern.slf4j.Slf4j;
import org.niis.xroad.restapi.converter.ClientConverter;
import org.niis.xroad.restapi.exceptions.NotFoundException;
import org.niis.xroad.restapi.repository.ClientRepository;
import org.niis.xroad.restapi.repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * groups service
 */
@Slf4j
@Service
@Transactional
@PreAuthorize("denyAll")
public class GroupsService {

    private final GroupsRepository groupsRepository;
    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;

    /**
     * GroupsService constructor
     * @param groupsRepository
     * @param clientConverter
     * @param clientRepository
     */
    @Autowired
    public GroupsService(GroupsRepository groupsRepository, ClientConverter clientConverter,
            ClientRepository clientRepository) {
        this.groupsRepository = groupsRepository;
        this.clientConverter = clientConverter;
        this.clientRepository = clientRepository;
    }

    /**
     * Return local group
     * @param clientId
     * @param groupCode
     * @return LocaGroupType
     */
    @PreAuthorize("hasAuthority('VIEW_CLIENT_LOCAL_GROUPS')")
    public LocalGroupType getLocalGroup(String groupCode, ClientId clientId) {
        return groupsRepository.getLocalGroupType(groupCode, clientId);
    }

    /**
     * Edit local group description
     * @param id
     * @param groupCode
     * @return LocaGroupType
     */
    @PreAuthorize("hasAuthority('EDIT_LOCAL_GROUP_DESC')")
    public LocalGroupType updateDescription(String id, String groupCode, String description) {
        LocalGroupType localGroupType = getLocalGroup(groupCode, clientConverter.convertId(id));
        if (localGroupType == null) {
            throw new NotFoundException("LocalGroup with id " + id + " not found");
        }
        localGroupType.setDescription(description);
        groupsRepository.saveOrUpdate(localGroupType);
        return localGroupType;
    }

    /**
     * Adds a local group to a client
     * @param id
     * @param localGroupType
     */
    @PreAuthorize("hasAuthority('ADD_LOCAL_GROUP')")
    public void addLocalGroup(ClientId id, LocalGroupType localGroupType) {
        ClientType clientType = clientRepository.getClient(id);
        if (clientType == null) {
            throw new NotFoundException(("client with id " + id + " not found"));
        }
        clientType.getLocalGroup().add(localGroupType);
        clientRepository.saveOrUpdate(clientType);
    }
}