/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.comprobantes.utils;

import java.io.File;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Rolando
 */
public class ValidadorEstructuraDocumento {

    private File archivoXSD;
    private File archivoXML;

    public ValidadorEstructuraDocumento(String strArchivoXSD, String strArchivoXML) {
        this.archivoXSD = new File(strArchivoXSD);
        this.archivoXML = new File(strArchivoXML);
    }

    public String validacion() {
//        System.err.println(archivoXML.getAbsolutePath());
//        System.err.println(archivoXSD.getAbsolutePath());
        validarArchivo(archivoXSD, "archivoXSD");
        validarArchivo(archivoXML, "archivoXML");
        String mensaje = null;
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema;
        try {
            schema = schemaFactory.newSchema(archivoXSD);
        } catch (SAXException e) {
            throw new IllegalStateException("Existe un error en la sintaxis del esquema", e);
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(archivoXML));
        } catch (Exception e) {
            return e.getMessage();
        }
        return mensaje;
    }

    protected void validarArchivo(File archivo, String nombre)
            throws IllegalStateException {
        if (null == archivo || archivo.length() <= 0L) {
            throw new IllegalStateException((new StringBuilder()).append(nombre).append(" es nulo o esta vacio").toString());
        } else {
            return;
        }
    }
}
