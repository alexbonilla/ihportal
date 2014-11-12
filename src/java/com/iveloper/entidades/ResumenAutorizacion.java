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
public class ResumenAutorizacion {

    String claveacceso;
    String autorizacion;
    String fechaautorizacion;
    String contenidoxml;
    String estado;
    String idcliente;
    int tipodocumento;

    public String getClaveacceso() {
        return claveacceso;
    }

    public void setClaveacceso(String claveacceso) {
        this.claveacceso = claveacceso;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getFechaautorizacion() {
        return fechaautorizacion;
    }

    public void setFechaautorizacion(String fechaautorizacion) {
        this.fechaautorizacion = fechaautorizacion;
    }

    public String getContenidoxml() {
        return contenidoxml;
    }

    public void setContenidoxml(String contenidoxml) {
        this.contenidoxml = contenidoxml;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public int getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(int tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

}
