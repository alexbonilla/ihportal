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
public class DatoAdicionalFactura {
    private int idDatoAdicional;
    private String nombre;
    private String descripcion;
    private String accion;

    public int getIdDatoAdicional() {
        return idDatoAdicional;
    }

    public void setIdDatoAdicional(int idDatoAdicional) {
        this.idDatoAdicional = idDatoAdicional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    
}
