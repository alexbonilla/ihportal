/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.entidades;

import java.math.BigDecimal;
import org.json.JSONObject;

/**
 *
 * @author Alex
 */
public class Cliente {

    private String razonsocial;
    private String tipoIdentificacion;
    private String identificacion;
    private String tipoCliente;
    private String direccion;
    private String telefonoFijo;
    private String extensionFijo;
    private String telefonoMovil;
    private String email;
    private BigDecimal credito;

    public Cliente(String razonsocial, String tipoIdentificacion, String identificacion, String tipoCliente, String direCcion, String telefonoFijo, String extensionFijo, String telefonoMovil, String email, BigDecimal credito) {
        this.razonsocial = razonsocial;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.tipoCliente = tipoCliente;
        this.direccion = direCcion;
        this.telefonoFijo = telefonoFijo;
        this.extensionFijo = extensionFijo;
        this.telefonoMovil = telefonoMovil;
        this.email = email;
        this.credito = credito;
    }

    public Cliente(String razonsocial, String tipoIdentificacion, String identificacion, String direccion, String telefonoFijo) {
        this.razonsocial = razonsocial;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefonoFijo = telefonoFijo;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direCcion) {
        this.direccion = direCcion;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getExtensionFijo() {
        return extensionFijo;
    }

    public void setExtensionFijo(String extensionFijo) {
        this.extensionFijo = extensionFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }
    
    

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("razonsocial", this.razonsocial);
            json.put("identificacion", this.identificacion);
            json.put("tipoidentificacion", this.tipoIdentificacion);
            json.put("tipocliente", this.tipoCliente);
            json.put("direccion", this.direccion);
            json.put("telefonofijo", this.telefonoFijo);
            json.put("extensionfijo", this.extensionFijo);
            json.put("telefonomovil", this.telefonoMovil);
            json.put("email", this.email);

        } catch (Exception ex) {

        }
        return json;
    }
}
