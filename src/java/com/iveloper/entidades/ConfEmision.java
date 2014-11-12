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
public class ConfEmision {

    private int idConfEmision;
    private String tipoEmision;
    private String maxTiempoEspera;
    private String tipoAmbiente;
    private String tipoFirma;
    private String archivoFirma;

    public int getIdConfEmision() {
        return idConfEmision;
    }

    public void setIdConfEmision(int idConfEmision) {
        this.idConfEmision = idConfEmision;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(String tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    public String getMaxTiempoEspera() {
        return maxTiempoEspera;
    }

    public void setMaxTiempoEspera(String maxTiempoEspera) {
        this.maxTiempoEspera = maxTiempoEspera;
    }

    public String getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(String tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }

    public String getTipoFirma() {
        return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public String getArchivoFirma() {
        return archivoFirma;
    }

    public void setArchivoFirma(String archivoFirma) {
        this.archivoFirma = archivoFirma;
    }

    
}
