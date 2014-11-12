/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.comprobantes.modelo.factura;

import com.iveloper.comprobantes.modelo.CampoAdicional;
import com.iveloper.comprobantes.modelo.InfoTributaria;
import com.iveloper.comprobantes.modelo.Detalle;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Alex
 */
@XmlRootElement
@XmlType(propOrder = {
    "id", "version", "infoTributaria", "infoFactura", "detalle", "campoAdicional"})
public class Factura {

    private String version;
    private String id;
    private InfoTributaria infoTributaria;
    private InfoFactura infoFactura;
    private List<CampoAdicional> campoAdicional;
    private List<Detalle> detalle;

    @XmlAttribute
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoFactura getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InfoFactura infoFactura) {
        this.infoFactura = infoFactura;
    }

    @XmlElementWrapper(name = "infoAdicional")
    public List<CampoAdicional> getCampoAdicional() {
        if (campoAdicional == null) {
            campoAdicional = new ArrayList<CampoAdicional>();
        }
        return campoAdicional;
    }

    public void setCampoAdicional(List<CampoAdicional> CampoAdicional) {
        this.campoAdicional = CampoAdicional;
    }

    @XmlElementWrapper(name = "detalles")
    public List<Detalle> getDetalle() {
        if (detalle == null) {
            detalle = new ArrayList<Detalle>();
        }
        return detalle;
    }

    public void setDetalle(List<Detalle> detalle) {
        this.detalle = detalle;
    }

}
