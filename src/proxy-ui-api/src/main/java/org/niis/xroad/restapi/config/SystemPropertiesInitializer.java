/**
 * The MIT License
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.niis.xroad.restapi.config;

import ee.ria.xroad.common.SystemPropertiesLoader;

import java.util.concurrent.atomic.AtomicBoolean;

import static ee.ria.xroad.common.SystemProperties.CONF_FILE_PROXY;
import static ee.ria.xroad.common.SystemProperties.CONF_FILE_PROXY_UI;
import static ee.ria.xroad.common.SystemProperties.CONF_FILE_SIGNER;

/**
 * Helper wrapper which makes sure system properties are initialized
 * and are not initialized multiple times
 */
public final class SystemPropertiesInitializer {
    private SystemPropertiesInitializer() {
    }
    private static final AtomicBoolean XROAD_PROPERTIES_INITIALIZED;
    static {
        Printer.print("creating XROAD_PROPERTIES_INITIALIZED");
        XROAD_PROPERTIES_INITIALIZED = new AtomicBoolean(false);
        Printer.print("created XROAD_PROPERTIES_INITIALIZED, value=" + XROAD_PROPERTIES_INITIALIZED.get());
    }

    /**
     * initialize
     */
    public static synchronized void initialize() {
        if (XROAD_PROPERTIES_INITIALIZED.get()) {
            throw new IllegalStateException("Should only be initialized once!");
        }
        print("initialize");
        SystemPropertiesLoader.create().withCommonAndLocal()
                .with(CONF_FILE_PROXY)
                .with(CONF_FILE_PROXY_UI)
                .with(CONF_FILE_SIGNER)
                .load();
        XROAD_PROPERTIES_INITIALIZED.set(true);
        Printer.print("initialized, value=" + XROAD_PROPERTIES_INITIALIZED.get());
    }

    /**
     * Throw exception if not initialized yet
     */
    public static synchronized void verifyInitialized() {
        Printer.print("verifying, value=" + XROAD_PROPERTIES_INITIALIZED.get());
        print("verifyInitialized");
        if (!XROAD_PROPERTIES_INITIALIZED.get()) {
            throw new IllegalStateException(("Should be initialized, but is not"));
        }
    }

    public static final int XERO = 0;
    public static final int TVENTY = 20;

    private static void print(String msg) {
        for (int i = XERO; i < TVENTY; i++) {
            System.out.println("**************************** " + i);
        }
        System.out.println(msg);
    }

}
