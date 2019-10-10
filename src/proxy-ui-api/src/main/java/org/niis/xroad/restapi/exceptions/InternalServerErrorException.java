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
package org.niis.xroad.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

/**
 * Thrown if internal server error occurs.
 * Results in http 500 INTERNAL_SERVER_ERROR
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends DeviationAwareRuntimeException {

    public InternalServerErrorException(DeviationAware deviations) {
        super(deviations.getFatalError(), deviations.getWarnings());
    }

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String msg) {
        super(msg);
    }

    public InternalServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    public InternalServerErrorException(String msg, FatalError fatalError) {
        super(msg, fatalError);
    }

    public InternalServerErrorException(String msg, Throwable t, FatalError fatalError) {
        super(msg, t, fatalError);
    }

    public InternalServerErrorException(Throwable t, FatalError fatalError, Collection<Warning> warnings) {
        super(t, fatalError, warnings);
    }

    public InternalServerErrorException(FatalError fatalError, Collection<Warning> warnings) {
        super(fatalError, warnings);
    }

    public InternalServerErrorException(FatalError fatalError) {
        super(fatalError);
    }

    public InternalServerErrorException(Throwable t, FatalError fatalError) {
        super(t, fatalError);
    }








    /**
     * Use deviation data from original exception
     * @param e
     */
    public InternalServerErrorException(DeviationAwareRuntimeException e) {
        this(e, e.getFatalError(), e.getWarnings());
    }

}
