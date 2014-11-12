/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.entidades;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Alex
 */
public class Documento {
    String iddocumento;
    String benefnombre;
    String descripcionadicional;
    BigDecimal valoradicional;
    String benefid;
    int tramite;
    Servicio servicio;
    Date fechacreacion;

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getBenefnombre() {
        return benefnombre;
    }

    public void setBenefnombre(String benefnombre) {
        this.benefnombre = benefnombre;
    }

    public String getBenefid() {
        return benefid;
    }

    public void setBenefid(String benefid) {
        this.benefid = benefid;
    }

    public int getTramite() {
        return tramite;
    }

    public void setTramite(int tramite) {
        this.tramite = tramite;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }  

    public String getDescripcionadicional() {
        return descripcionadicional;
    }

    public void setDescripcionadicional(String descripcionadicional) {
        this.descripcionadicional = descripcionadicional;
    }

    public BigDecimal getValoradicional() {
        return valoradicional;
    }

    public void setValoradicional(BigDecimal valoradicional) {
        this.valoradicional = valoradicional;
    }     

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
    
    
}
