/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.comprobantes.utils;

import java.util.logging.Logger;

/**
 *
 * @author Rolando
 */
public class KeyStoreProviderFactory {

    private static final Logger log = Logger.getLogger(KeyStoreProviderFactory.class.getName());

    public static KeyStoreProvider createKeyStoreProvider() {
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().indexOf("WINDOWS") == 0) {
            return new WindowsOtherKeyStoreProvider();
        }
        throw new IllegalArgumentException("Sistema operativo no soportado!");
    }
}

