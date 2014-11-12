/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.comprobantes.utils;

import com.iveloper.comprobantes.dao.ClaveAccesoDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

/**
 *
 * @author Alex
 */
public class ClaveAcceso {

    public static String cargarArchivosClavesAcceso(String ruta) {
        String resultado = "";
        File archivoClavesAcceso = new File(ruta);
        FileReader fr = null;
        BufferedReader br = null;
        ClaveAccesoDAO caDAO = new ClaveAccesoDAO();
        if (archivoClavesAcceso.exists()) {
            try {
                fr = new FileReader(archivoClavesAcceso);
                br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea != null && !linea.isEmpty()) {
                        //ClaveAcceso ca = new ClaveAcceso();
                        caDAO.insertar(linea, 0, "");
                    }
                }
            } catch (Exception e) {
                resultado = e.getMessage();
            }
        } else {
            resultado = "El archivo de claves no existe en la ruta especificada";
        }
        return resultado;
    }

    public static int generarDigitoModulo11(String clave) {
        int acumulador = 0;
        int factor = 2;
        int digito = 0;
        for (int i = clave.length() - 1; i >= 0; i--) {
            char d = clave.charAt(i);
            if (Character.isDigit(d)) {
                digito = d - 48;
                if (factor == 8) {
                    factor = 2;
                }
                acumulador += (digito * (factor++));
            } else {
                System.out.println("Error, la cadena incluye caracteres que no son digitos");
            }
        }
        int verificador = 11 - (acumulador % 11);
        if (verificador == 10) {
            return 1;
        }
        if (verificador == 11) {
            return 0;
        }
        return verificador;
    }

    public static String generarClaveAcceso(Date fechaEmision, String tipoComprobante,
            String ruc, String ambiente, String serie,
            String numeroComprobante, String codigoNumerico,
            String tipoEmision) {
        int verificador = 0;
        if (ruc != null && ruc.length() < 13) {
            ruc = String.format("%013d", new Object[]{ruc});
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(fechaEmision);
        StringBuilder clave = new StringBuilder(fecha);
        clave.append(tipoComprobante);
        clave.append(ruc);
        clave.append(ambiente);
        clave.append(serie);
        clave.append(numeroComprobante);
        clave.append(codigoNumerico);
        clave.append(tipoEmision);
        verificador = generarDigitoModulo11(clave.toString());

        clave.append(Integer.valueOf(verificador));
        String claveGenerada = clave.toString();
        if (clave.toString().length() != 49) {
            System.out.println(claveGenerada);
            claveGenerada = null;
        }
        return claveGenerada;
    }

    public String generaClaveContingencia(String fechaEmision, String tipoComprobante,
            String clavesContigencia, String tipoEmision)
            throws InputMismatchException {
        int verificador = 0;
        String claveGenerada = "";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
//        String fecha = dateFormat.format(fechaEmision);
        StringBuilder clave = new StringBuilder(fechaEmision);
        clave.append(tipoComprobante);
        clave.append(clavesContigencia);
        clave.append(tipoEmision);
        verificador = generarDigitoModulo11(clave.toString());
        if (verificador != 10) {
            clave.append(Integer.valueOf(verificador));
            claveGenerada = clave.toString();
        }
        if (clave.toString().length() != 49) {
            claveGenerada = null;
        }
        return claveGenerada;
    }

    public static void main(String[] args) {
        Date date = new Date();
        String tipoComprobante = "01";
        String ruc = "0913814455001";
        String ambiente = "1";
        //El número de serie es el código del establecimiento concatenado con el del punto de emisión
        //emisor.getCodigoEstablecimiento().concat(emisor.getCodPuntoEmision())
        String serie = "001001";
        String numeroComprobante = "000000117";
        //Clave interna de 8 dígitos
        String codigoNumerico = "12345678";
        String tipoEmision = "1";
//        cargarArchivosClavesAcceso("C:\\Users\\Alex\\Documents\\comprobantes_electronicos\\claves_contingencia_PRUEBAS20131229.txt");
        System.out.println(generarClaveAcceso(date, tipoComprobante, ruc, ambiente, serie, numeroComprobante, codigoNumerico, tipoEmision));
//        System.out.println("" + generarDigitoModulo11("41261533"));
    }
}
