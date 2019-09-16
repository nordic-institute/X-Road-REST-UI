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
package org.niis.xroad.restapi.converter;

import ee.ria.xroad.common.identifier.LocalGroupId;
import ee.ria.xroad.common.identifier.XRoadId;
import ee.ria.xroad.common.identifier.XRoadObjectType;

import org.niis.xroad.restapi.exceptions.BadRequestException;
import org.niis.xroad.restapi.openapi.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Convert Subject related data between openapi and service domain classes
 */
@Component
public class SubjectConverter {
    private ClientConverter clientConverter;
    private GroupConverter groupConverter;

    @Autowired
    public SubjectConverter(ClientConverter clientConverter, GroupConverter groupConverter) {
        this.clientConverter = clientConverter;
        this.groupConverter = groupConverter;
    }

    /**
     * Convert {@link Subject} to {@link XRoadId}
     * @param subject
     * @return {@link XRoadId}
     */
    public XRoadId convert(Subject subject) {
        XRoadObjectType subjectType = SubjectTypeMapping.map(subject.getSubjectType()).get();
        XRoadId xRoadId;
        switch (subjectType) {
            case MEMBER:
            case SUBSYSTEM:
                xRoadId = clientConverter.convertId(subject.getId());
                break;
            case GLOBALGROUP:
                xRoadId = groupConverter.convertGlobalGroupId(subject.getId());
                break;
            case LOCALGROUP:
                xRoadId = LocalGroupId.create(subject.getId());
                break;
            default:
                throw new BadRequestException("Invalid subject type");
        }
        return xRoadId;
    }

    /**
     * Convert a list of {@link Subject subjects} to a list of {@link XRoadId xRoadIds}
     * @param subjects
     * @return List of {@link XRoadId xRoadIds}
     */
    public List<XRoadId> convert(List<Subject> subjects) {
        return subjects
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

}