/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.entidades;

/**
 *
 * @author Alex
 */
public class Emisor {
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String resolContribEsp;
    private String obligadoContabilidad;

    public Emisor(String ruc, String razonSocial, String nombreComercial, String resolContribEsp, String obligadoContabilidad) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.resolContribEsp = resolContribEsp;
        this.obligadoContabilidad = obligadoContabilidad;
    }

    
    
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombrecomercial() {
        return nombreComercial;
    }

    public void setNombrecomercial(String nombrecomercial) {
        this.nombreComercial = nombrecomercial;
    }

    public String getResolContribEsp() {
        return resolContribEsp;
    }

    public void setResolContribEsp(String resolContribEsp) {
        this.resolContribEsp = resolContribEsp;
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }
    
    
}
