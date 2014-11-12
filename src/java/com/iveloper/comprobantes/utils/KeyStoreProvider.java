/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.comprobantes.utils;

import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 *
 * @author Rolando
 */
public abstract interface KeyStoreProvider {

    public abstract KeyStore getKeystore(char[] paramArrayOfChar)
            throws KeyStoreException;
}
