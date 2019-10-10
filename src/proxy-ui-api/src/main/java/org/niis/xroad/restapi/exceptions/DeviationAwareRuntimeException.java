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

import java.util.Collection;

/**
 * RuntimeException that (possibly) carries fatalError code.
 * Root of all deviation aware runtimeexceptions (do we need any?)
 */
public class DeviationAwareRuntimeException extends RuntimeException implements DeviationAware {

    private final FatalError fatalError;
    private final Collection<Warning> warnings;

    @Override
    public FatalError getFatalError() {
        return fatalError;
    }

    @Override
    public Collection<Warning> getWarnings() {
        return warnings;
    }

    /**
     * no args
     */
    public DeviationAwareRuntimeException() {
        super();
        this.fatalError = null;
        this.warnings = null;
    }

    /**
     * @param msg
     */
    public DeviationAwareRuntimeException(String msg) {
        super(msg);
        this.fatalError = null;
        this.warnings = null;
    }

    /**
     * @param msg
     * @param fatalError
     */
    public DeviationAwareRuntimeException(String msg, FatalError fatalError) {
        super(msg);
        this.fatalError = fatalError;
        this.warnings = null;
    }

    /**
     * @param msg
     * @param t
     */
    public DeviationAwareRuntimeException(String msg, Throwable t) {
        this(msg, t, null, null);
    }

    /**
     * @param msg
     * @param t
     * @param fatalError
     */
    public DeviationAwareRuntimeException(String msg, Throwable t, FatalError fatalError) {
        this(msg, t, fatalError, null);
    }

    /**
     * @param msg
     * @param t
     * @param fatalError
     * @param warnings
     */
    public DeviationAwareRuntimeException(String msg, Throwable t, FatalError fatalError,
                                          Collection<Warning> warnings) {
        super(msg, t);
        this.fatalError = fatalError;
        this.warnings = warnings;
    }

    /**
     * @param fatalError
     */
    public DeviationAwareRuntimeException(FatalError fatalError) {
        this(fatalError, null);
    }

    /**
     * @param fatalError
     * @param warnings
     */
    public DeviationAwareRuntimeException(FatalError fatalError, Collection<Warning> warnings) {
        this.fatalError = fatalError;
        this.warnings = warnings;
    }

    /**
     * @param t
     */
    public DeviationAwareRuntimeException(Throwable t) {
        this(t, null, null);
    }

    /**
     * @param t
     * @param fatalError
     */
    public DeviationAwareRuntimeException(Throwable t, FatalError fatalError) {
        this(t, fatalError, null);
    }

    /**
     * @param t
     * @param fatalError
     * @param warnings
     */
    public DeviationAwareRuntimeException(Throwable t, FatalError fatalError, Collection<Warning> warnings) {
        super(t);
        this.fatalError = fatalError;
        this.warnings = warnings;
    }
}
