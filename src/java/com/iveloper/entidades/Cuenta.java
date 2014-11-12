/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.entidades;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alex
 */
public class Cuenta {
    /*
     * COLUMNAS
     * cuenta, fechacreacion, ultimoacceso, tipo, id, pwd, mail
     * 
     */

    public int id;
    public String usuario;
    public Date fechacreacion;
    public Date ultimoacceso;
    public String roles;
    public String mail;
    public byte[] pwd;
    public byte[] salt;
    public int activo;
    public DetallesCuenta detallesCuenta;

    public Cuenta(int id, String cuenta, Date fechacreacion, Date ultimoacceso, String tipo, String mail, byte[] pwd, byte[] salt) {
        this.id = id;
        this.usuario = cuenta;
        this.fechacreacion = fechacreacion;
        this.ultimoacceso = ultimoacceso;
        this.roles = tipo;
        this.mail = mail;
        this.pwd = pwd;
        this.salt = salt;
    }

    public Cuenta(int id, String cuenta, Date fechacreacion, Date ultimoacceso, String tipo, String mail, byte[] pwd, byte[] salt, int activo) {
        this.id = id;
        this.usuario = cuenta;
        this.fechacreacion = fechacreacion;
        this.ultimoacceso = ultimoacceso;
        this.roles = tipo;
        this.mail = mail;
        this.pwd = pwd;
        this.salt = salt;
        this.activo = activo;
    }

    public Cuenta(String cuenta, String tipo, String mail, byte[] pwd, byte[] salt) {
        this.usuario = cuenta;
        this.roles = tipo;
        this.mail = mail;
        this.pwd = pwd;
        this.salt = salt;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String cuenta) {
        this.usuario = cuenta;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public byte[] getPwd() {
        return pwd;
    }

    public void setPwd(byte[] pwd) {
        this.pwd = pwd;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getUltimoacceso() {
        return ultimoacceso;
    }

    public void setUltimoacceso(Date ultimoacceso) {
        this.ultimoacceso = ultimoacceso;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public DetallesCuenta getDetallesCuenta() {
        return detallesCuenta;
    }

    public void setDetallesCuenta(DetallesCuenta detallesCuenta) {
        this.detallesCuenta = detallesCuenta;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.id);
            json.put("usuario", this.usuario);
            json.put("pwd", this.pwd);
            json.put("mail", this.mail);
            json.put("roles", this.roles);
            json.put("fechacreacion", this.fechacreacion);
            json.put("ultimoacceso", this.ultimoacceso);

        } catch (JSONException ex) {
            System.out.println("" + ex);
        }
        return json;
    }
}
