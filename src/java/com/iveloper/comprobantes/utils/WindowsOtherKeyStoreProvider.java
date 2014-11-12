/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.comprobantes.utils;

public class WindowsOtherKeyStoreProvider
        extends PKCS11KeyStoreProvider {

    private static final String config;

    static {
        StringBuffer sb = new StringBuffer();
        sb.append("name=Safenetikey2032\n");
        sb.append("library=C:\\WINDOWS\\SYSTEM32\\dkck201.dll\n");
        sb.append("disabledMechanisms={ CKM_SHA1_RSA_PKCS }");
        config = sb.toString();
    }

    public String getConfig() {
        return config;
    }
}

