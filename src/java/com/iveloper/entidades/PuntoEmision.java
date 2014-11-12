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
public class PuntoEmision {
    private String codEstablecimiento;
    private String codPuntoEmision;
    private String direccion;
    private String tipoDireccion;
    private Emisor emisor;
    private Ciudad ciudad;
    private ConfEmision confEmision;

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getCodPuntoEmision() {
        return codPuntoEmision;
    }

    public void setCodPuntoEmision(String codPuntoEmision) {
        this.codPuntoEmision = codPuntoEmision;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public ConfEmision getConfEmision() {
        return confEmision;
    }

    public void setConfEmision(ConfEmision confEmision) {
        this.confEmision = confEmision;
    }

    
}
