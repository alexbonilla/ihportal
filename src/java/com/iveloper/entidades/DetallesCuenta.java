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
public class DetallesCuenta {
    String idcliente;   
    String idempresa;
    String nombre_cliente;
    String nombre_empresa;
    String logo_empresa;

    
    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(String idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getLogo_empresa() {
        return logo_empresa;
    }

    public void setLogo_empresa(String logo_empresa) {
        this.logo_empresa = logo_empresa;
    }
    
    
}
