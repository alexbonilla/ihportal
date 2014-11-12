/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.comprobantes.utils;

/**
 *
 * @author Alex
 */
public class Constantes {

    public static String PKCS12_RESOURCE;
    public static String PKCS12_PASSWORD;

    public static String getRutaFirma() {
        if (PKCS12_RESOURCE == null || PKCS12_RESOURCE.isEmpty()) {
            PKCS12_RESOURCE = "C:\\Users\\Alex\\Documents\\certificado\\alex_fernando_bonilla_gordillo.p12"; //Ruta de archivo de claves
        }
        return PKCS12_RESOURCE;
    }
}
