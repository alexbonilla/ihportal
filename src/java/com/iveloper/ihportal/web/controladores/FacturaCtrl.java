/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.ihportal.web.controladores;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.iveloper.comprobantes.autorizacion.Autorizacion;
import com.iveloper.comprobantes.modelo.Detalle;
import com.iveloper.comprobantes.modelo.TotalImpuesto;
import com.iveloper.comprobantes.modelo.factura.Factura;
import com.iveloper.db.Conexion;
import com.iveloper.entidades.Cuenta;
import com.iveloper.entidades.ResumenAutorizacion;
import com.iveloper.entidades.ResumenFactura;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Alex
 */
@WebServlet(name = "FacturaCtrl", urlPatterns = {"/FacturaCtrl"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB 
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100)
public class FacturaCtrl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        String op = request.getParameter("op");
        if (op.equalsIgnoreCase("buscarFacturasAutorizadas")) {
            System.out.println("Entró a buscarFacturasAutorizadas");
            buscarFacturasAutorizadas(request, response);
        } else if (op.equalsIgnoreCase("verFacturaAutorizada")) {
            System.out.println("Entró a verFacturaAutorizada");
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(verFacturaAutorizada(request, response));
                out.close();
            }
        } else if (op.equalsIgnoreCase("descargarFacturaAutorizada")) {
            response.setContentType("application/pdf");
            System.out.println("Entró a descargarFacturaAutorizada");
            descargarFacturaAutorizada(request, response);
        } else if (op.equalsIgnoreCase("subirAutorizacion")) {
            response.setContentType("text/plain;charset=UTF-8");
            System.out.println("Entró a subirAutorizacion");
            try (PrintWriter out = response.getWriter()) {
                out.println(subirAutorizacion(request, response));
                out.close();
            }
        }
    }

    public void buscarFacturasAutorizadas(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Cuenta cuentaSesion = (Cuenta) session.getAttribute("cuentaSesion");
        String usuario = "";

        if (cuentaSesion != null && cuentaSesion.roles.equals("usuario")) {
            usuario = cuentaSesion.getUsuario();
        } else if (cuentaSesion != null && cuentaSesion.roles.equals("admin")) {
            usuario = request.getParameter("usuario") == null || request.getParameter("usuario").equals("") || request.getParameter("usuario").equals("null") ? null : request.getParameter("usuario");
        }

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path, cuentaSesion.getDetallesCuenta().getIdempresa());
        String xml = "<?xml version='1.0' encoding='UTF-8' ?>";

        String idcliente = cuentaSesion.getDetallesCuenta().getIdcliente();
        String fechaInicioStr = request.getParameter("fechaInicio") == null || request.getParameter("fechaInicio").equals("") ? null : request.getParameter("fechaInicio");
        String fechaFinalStr = request.getParameter("fechaFinal") == null || request.getParameter("fechaFinal").equals("") ? null : request.getParameter("fechaFinal");

        System.out.println("usuario: " + usuario);

        SimpleDateFormat fromUser = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            fechaInicioStr = myFormat.format(fromUser.parse(fechaInicioStr));
            fechaFinalStr = myFormat.format(fromUser.parse(fechaFinalStr));
        } catch (ParseException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.WARNING, "consultarCuenta: {1}", e);
        }
        System.out.println("idcliente: " + idcliente);
        System.out.println("fechaInicioStr: " + fechaInicioStr);
        System.out.println("fechaFinalStr: " + fechaFinalStr);

        try {
            PrintWriter pw = response.getWriter();

            c.conectar();
            List<ResumenFactura> resumenes = c.obtenerListaResumenFacturasAutorizadas(idcliente, fechaInicioStr, fechaFinalStr);
            Iterator<ResumenFactura> resumenesItr = resumenes.iterator();

            xml += "<facturas>";

            while (resumenesItr.hasNext()) {
                ResumenFactura resumen = resumenesItr.next();

                xml += "<factura>";
                xml += "<claveacceso>" + resumen.getClaveacceso() + "</claveacceso>";
                xml += "<numfactura>" + resumen.getNumfactura() + "</numfactura>";
                xml += "<fechaemision>" + resumen.getFechaemision() + "</fechaemision>";
                xml += "<idcliente>" + resumen.getIdcliente() + "</idcliente>";
                xml += "<importetotal>" + resumen.getImportetotal() + "</importetotal>";
                xml += "</factura>";

            }
            xml += "</facturas>";
            pw.println(xml);
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            c.desconectar();
        }
    }

    public String verFacturaAutorizada(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Cuenta cuentaSesion = (Cuenta) session.getAttribute("cuentaSesion");
        String usuario = null;

        if (cuentaSesion != null && cuentaSesion.roles.equals("usuario")) {
            usuario = cuentaSesion.getUsuario();
        } else if (cuentaSesion != null && cuentaSesion.roles.equals("admin")) {
            usuario = request.getParameter("usuario") == null || request.getParameter("usuario").equals("") || request.getParameter("usuario").equals("null") ? null : request.getParameter("usuario");
        }

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path, cuentaSesion.getDetallesCuenta().getIdempresa());
        String facturaHTML = "";

        String claveacceso = request.getParameter("claveacceso") == null || request.getParameter("claveacceso").equals("") ? null : request.getParameter("claveacceso");

        System.out.println("claveacceso: " + claveacceso);
        try {
            c.conectar();

            ResumenAutorizacion resumen = c.obtenerResumenAutorizacion(claveacceso);

            String xml = resumen.getContenidoxml();
            StringReader reader = new StringReader(xml);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(reader));

            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{Factura.class});
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Factura factura = (Factura) unmarshaller.unmarshal(doc);
            facturaHTML = generarRIDE(factura, cuentaSesion.getDetallesCuenta().getLogo_empresa(), resumen.getAutorizacion(), resumen.getFechaautorizacion(), true);
        } catch (Exception ex) {
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            c.desconectar();
        }

        return facturaHTML;
    }

    public void descargarFacturaAutorizada(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Cuenta cuentaSesion = (Cuenta) session.getAttribute("cuentaSesion");
        String usuario = null;

        if (cuentaSesion != null && cuentaSesion.roles.equals("usuario")) {
            usuario = cuentaSesion.getUsuario();
        } else if (cuentaSesion != null && cuentaSesion.roles.equals("admin")) {
            usuario = request.getParameter("usuario") == null || request.getParameter("usuario").equals("") || request.getParameter("usuario").equals("null") ? null : request.getParameter("usuario");
        }

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path, cuentaSesion.getDetallesCuenta().getIdempresa());
        String facturaHTML = "";

        String claveacceso = request.getParameter("claveacceso") == null || request.getParameter("claveacceso").equals("") ? null : request.getParameter("claveacceso");

        System.out.println("claveacceso: " + claveacceso);
        try {
            c.conectar();

            ResumenAutorizacion resumen = c.obtenerResumenAutorizacion(claveacceso);

            String xml = resumen.getContenidoxml();

            StringReader reader = new StringReader(xml);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(reader));

            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{Factura.class});
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Factura factura = (Factura) unmarshaller.unmarshal(doc);
            facturaHTML = generarRIDE(factura, cuentaSesion.getDetallesCuenta().getLogo_empresa(), resumen.getAutorizacion(), resumen.getFechaautorizacion(), false);
            generarFacturaPDF(request, response, facturaHTML);
        } catch (Exception ex) {
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            c.desconectar();
        }
    }

    protected void generarFacturaPDF(HttpServletRequest request, HttpServletResponse response, String facturaHTML)
            throws ServletException, IOException {

        ServletOutputStream servletOutputStream = response.getOutputStream();

        byte[] bytes = null;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4);
        PdfWriter pdfWriter;
        try {
            pdfWriter = PdfWriter.getInstance(document, os);

            document.open();
            document.addAuthor("Real Gagnon");
            document.addCreator("Real's HowTo");
            document.addSubject("Thanks for your support");
            document.addCreationDate();
            document.addTitle("Please read this");

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.parseXHtml(pdfWriter, document, new StringReader(facturaHTML));
            document.close();

            bytes = os.toByteArray();

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=factura.pdf");
            response.setContentLength(bytes.length);
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.INFO, "Longitud del PDF: {0}", bytes.length);
            servletOutputStream.write(bytes, 0, bytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (DocumentException ex) {
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void generarFacturaColorPDF(HttpServletRequest request, HttpServletResponse response, String facturaHTML)
            throws ServletException, IOException {
        try {
            CYaHPConverter converter = new CYaHPConverter();
            System.out.println("Entró a generarFacturaColorPDF");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            Map properties = new HashMap();
            List headerFooterList = new ArrayList();
            properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
                    IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);
            byte[] bytes = null;

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            converter.convertToPdf(facturaHTML, IHtmlToPdfTransformer.A4P, headerFooterList, "file:///temp/", os, properties);
            bytes = os.toByteArray();
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.INFO, "Longitud del PDF: {0}", bytes.length);
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=factura.pdf");
            response.setContentLength(bytes.length);
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.INFO, "Longitud del PDF: {0}", bytes.length);
            servletOutputStream.write(bytes, 0, bytes.length);
            servletOutputStream.flush();

        } catch (IHtmlToPdfTransformer.CConvertException ex) {
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String generarRIDE(Factura factura, String url_logo, String numautorizacion, String fechaautorizacion, boolean imprimir) {
        StringBuilder ride = new StringBuilder();
        ride.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>");
        ride.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        ride.append("<title>Imprimir Factura</title>");
        ride.append("<link href=\"css/factura.css\" rel=\"stylesheet\"/></head>");
        ride.append("<body>");
        ride.append("<table width=\"900\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
        ride.append("<tbody><tr>");
        ride.append("<td width=\"450\" height=\"109\" align=\"center\" valign=\"middle\"><img src=\"").append(url_logo).append("\"/></td>");
        ride.append("<td width=\"450\" rowspan=\"2\" align=\"center\" valign=\"middle\">");
        ride.append("<table width=\"373\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:thin dashed #333;\">");
        ride.append("<tbody><tr>");
        ride.append("<td width=\"363\" height=\"229\" align=\"left\" valign=\"top\" style=\"padding:5px; padding-left:25px\"><p class=\"sub-tit\">RUC: <span id=\"ruc\" class=\"prueba_txt2\" style=\"padding-left:10px\">").append(factura.getInfoTributaria().getRuc()).append("</span><br></br>");
        ride.append("FACTURA<br></br>");
        ride.append("No.: <span id=\"numfactura\" class=\"prueba_txt1\">").append(factura.getInfoTributaria().getEstab()).append(factura.getInfoTributaria().getPtoEmi()).append(factura.getInfoTributaria().getSecuencial()).append("</span><br></br>");
        ride.append("Número de Autorización:<br></br>");
        ride.append("<span id=\"numautorizacion\" class=\"txt1\">").append(numautorizacion).append("</span><br></br>");
        ride.append("Fecha y hora de aut.:<span id=\"fechaautorizacion\" class=\"txt1\">").append(fechaautorizacion).append("</span><br></br>");
        ride.append("Ambiente: <span id=\"ambiente\" class=\"txt1\">").append(getAmbiente(factura.getInfoTributaria().getAmbiente())).append("</span><br></br>");
        ride.append("Emisión: <span id=\"tipoemision\" class=\"txt1\">Normal<br></br>");
        ride.append("Clave de Acceso:<br></br>");
        ride.append("<img id=\"imgclaveacceso\" src=\"BarcodeCtrl?claveacceso=").append(factura.getInfoTributaria().getClaveAcceso()).append("\" alt=\"Clave de acceso\"/>");
        ride.append("<span id=\"claveacceso\" class=\"txt2\" style=\"text-align:center;\">").append(factura.getInfoTributaria().getClaveAcceso()).append("</span><br></br>");
        ride.append("</span></p></td>");
        ride.append("</tr>");
        ride.append("</tbody></table></td>");
        ride.append("</tr>");
        ride.append("<tr>");
        ride.append("<td height=\"135\" align=\"center\" valign=\"middle\">");
        ride.append("<table width=\"369\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:thin dashed #333;\">");
        ride.append("<tbody><tr>");
        ride.append("<td width=\"361\" align=\"left\" valign=\"top\" style=\"padding:5px\"><p id=\"razonsocial_emisor\" class=\"tit\">").append(factura.getInfoTributaria().getRazonSocial()).append("</p>");
        ride.append("<p class=\"sub-tit\">Dir Matriz: <span id=\"direccion_matriz\" class=\"txt1\">").append(factura.getInfoTributaria().getDirMatriz()).append("</span><br></br>");
        ride.append("Dir Sucursal: <span id=\"direccion_sucursal\" class=\"txt1\">").append(factura.getInfoTributaria().getDirMatriz()).append("</span>"
                + "<br></br>");
        if (factura.getInfoFactura().getContribuyenteEspecial() != null) {
            ride.append("Contribuyente Especial Nro.: <span id=\"contibespecial\" class=\"txt1\">").append(factura.getInfoFactura().getContribuyenteEspecial()).append("</span><br></br>");
        }
        ride.append("Obligado a llevar contabilidad: <span id=\"obligadocontabilidad\" class=\"txt1\">").append(factura.getInfoFactura().getObligadoContabilidad()).append("</span></p></td>");
        ride.append("</tr>");
        ride.append("</tbody></table></td>");
        ride.append("</tr>");
        ride.append("<tr>");
        ride.append("<td height=\"91\" colspan=\"2\" align=\"center\" valign=\"middle\"><table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:thin dashed #333;\">");
        ride.append("<tbody><tr>");
        ride.append("<td width=\"460\" height=\"22\" style=\"padding:5px\" align=\"left\"><span class=\"sub-tit\">Razón Social / Nombres y Apellidos: ");
        ride.append("</span><span id=\"razonsocial_comprador\" class=\"txt1\">").append(factura.getInfoFactura().getRazonSocialComprador()).append("</span></td>");
        ride.append("<td width=\"356\" style=\"padding:5px\" align=\"left\"><span class=\"sub-tit\">RUC/CI: </span><span id=\"identificacion_comprador\" class=\"txt1\">").append(factura.getInfoFactura().getIdentificacionComprador()).append("</span></td>");
        ride.append("</tr>");
        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px\" align=\"left\"><span class=\"sub-tit\">Fecha Emisión: </span><span id=\"fechaemision\" class=\"txt1\">").append(factura.getInfoFactura().getFechaEmision()).append("</span></td>");
        ride.append("<td width=\"356\" style=\"padding:5px\" align=\"left\"><span class=\"sub-tit\">Guía Remisión: </span></td>");
        ride.append("</tr>");
        ride.append("</tbody></table></td>");
        ride.append("</tr>");
        ride.append("<tr>");
        ride.append("<td height=\"48\" colspan=\"2\" align=\"center\" valign=\"top\"><table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        ride.append("<tbody>");
        ride.append("<tr>");
        ride.append("<td width=\"71\" height=\"22\" align=\"center\" valign=\"middle\" style=\"padding:5px; border:thin dashed #333;\"><span class=\"sub-tit\">Cod<br></br>Principal</span></td>");
        ride.append("<td width=\"70\" align=\"center\" valign=\"middle\" style=\"padding:5px; border:thin dashed #333;\"><span class=\"sub-tit\">Cod<br></br>Auxiliar</span></td>");
        ride.append("<td width=\"55\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Cant</td>");
        ride.append("<td width=\"152\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Descripción</td>");
        ride.append("<td width=\"91\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Detalle<br></br>Adicional</td>");
        ride.append("<td width=\"88\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Detalle<br></br>Adicional</td>");
        ride.append("<td width=\"93\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Detalle<br></br>Adicional</td>");
        ride.append("<td width=\"63\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Precio<br></br>Unitario</td>");
        ride.append("<td width=\"79\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Descuento</td>");
        ride.append("<td width=\"54\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">Precio<br></br>Total</td>");
        ride.append("</tr>");
        List<Detalle> detalles = factura.getDetalle();
        for (Detalle detalle : detalles) {
            ride.append("<tr>");
            ride.append("<td height=\"22\" align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(detalle.getCodigoPrincipal()).append("</td>");
            ride.append("<td align=\"center\" valign=\"middle\" style=\"padding:5px; border:thin dashed #333;\">&nbsp;</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(detalle.getCantidad()).append("</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(detalle.getDescripcion()).append("</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">&nbsp;</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">&nbsp;</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">&nbsp;</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(detalle.getPrecioUnitario()).append("</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(detalle.getDescuento()).append("</td>");
            ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(detalle.getPrecioTotalSinImpuesto()).append("</td>");
            ride.append("</tr>");
        }
        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">SUBTOTAL 12%</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(getSubtotalIVA12(factura.getInfoFactura().getTotalImpuesto())).append("</td>");
        ride.append("</tr>");

        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"5\" rowspan=\"7\" align=\"left\" valign=\"top\" style=\"padding:5px; border:thin dashed #333;\"><p class=\"tit\">Información Adicional</p>");
        ride.append("<p class=\"sub-tit\">").append((factura.getCampoAdicional() != null && factura.getCampoAdicional().size() > 0) ? (factura.getCampoAdicional().get(0).getNombre() + ":&nbsp;" + factura.getCampoAdicional().get(0).getValor()) : ("&nbsp;")).append("</p>");
        ride.append("<p class=\"sub-tit\">").append((factura.getCampoAdicional() != null && factura.getCampoAdicional().size() > 1) ? (factura.getCampoAdicional().get(1).getNombre() + ":&nbsp;" + factura.getCampoAdicional().get(1).getValor()) : ("&nbsp;")).append("</p>");
        ride.append("<p class=\"sub-tit\">").append((factura.getCampoAdicional() != null && factura.getCampoAdicional().size() > 2) ? (factura.getCampoAdicional().get(2).getNombre() + ":&nbsp;" + factura.getCampoAdicional().get(2).getValor()) : ("&nbsp;")).append("</p>");
        ride.append("<p class=\"sub-tit\">").append((factura.getCampoAdicional() != null && factura.getCampoAdicional().size() > 3) ? (factura.getCampoAdicional().get(3).getNombre() + ":&nbsp;" + factura.getCampoAdicional().get(3).getValor()) : ("&nbsp;")).append("</p>");
        ride.append("</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">SUBTOTAL 0%</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(getSubtotalIVA0(factura.getInfoFactura().getTotalImpuesto())).append("</td>");
        ride.append("</tr>");

        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">SUBTOTAL No Objeto IVA</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(getSubtotalExentoIVA(factura.getInfoFactura().getTotalImpuesto())).append("</td>");
        ride.append("</tr>");

        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">SUBTOTAL</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(factura.getInfoFactura().getTotalSinImpuestos().setScale(2, RoundingMode.HALF_UP)).append("</td>");
        ride.append("</tr>");

        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">TOTAL Descuento</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(factura.getInfoFactura().getTotalDescuento().setScale(2, RoundingMode.HALF_UP)).append("</td>");
        ride.append("</tr>");

        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">ICE</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(getICE(factura.getInfoFactura().getTotalImpuesto())).append("</td>");
        ride.append("</tr>");

        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">IVA 12%</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(getIVA12(factura.getInfoFactura().getTotalImpuesto())).append("</td>");
        ride.append("</tr>");

        ride.append("<tr>");
        ride.append("<td height=\"22\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td class=\"sub-tit\" style=\"padding:5px;\">&nbsp;</td>");
        ride.append("<td colspan=\"2\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\" align=\"left\">VALOR TOTAL</td>");
        ride.append("<td align=\"center\" valign=\"middle\" class=\"sub-tit\" style=\"padding:5px; border:thin dashed #333;\">").append(factura.getInfoFactura().getImporteTotal()).append("</td>");
        ride.append("</tr>");

        ride.append("</tbody></table></td>");
        ride.append("</tr>");
        ride.append("</tbody></table>");
        ride.append("<br></br>");

        if (imprimir) {
            ride.append("<table width=\"36\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
            ride.append("<tbody><tr>");
            ride.append("<td width=\"36\"><a href=\"FacturaCtrl?op=descargarFacturaAutorizada&claveacceso=").append(factura.getInfoTributaria().getClaveAcceso()).append("\" target=\"_blank\">Descargar</a></td>");
            ride.append("</tr>");
            ride.append("</tbody></table>");

        }

        ride.append("</body>");
        ride.append("</html>");
        return ride.toString();
    }

    private String getAmbiente(String ambiente) {
        if (ambiente.equals("1")) {
            return "PRUEBAS";
        } else {
            return "PRODUCCION";
        }
    }

    private BigDecimal getSubtotalIVA12(List<TotalImpuesto> totalConImpuestos) {
        BigDecimal subtotal12 = new BigDecimal(BigInteger.ZERO);
        Iterator<TotalImpuesto> totalConImpuestosItr = totalConImpuestos.iterator();
        while (totalConImpuestosItr.hasNext()) {
            TotalImpuesto totalImpuesto = totalConImpuestosItr.next();
            if (totalImpuesto.getCodigo().equals("2")) {
                if (totalImpuesto.getCodigoPorcentaje().equals("2")) {
                    subtotal12 = totalImpuesto.getBaseImponible();
                }
            }
        }
        subtotal12 = subtotal12.setScale(2, RoundingMode.HALF_UP);
        return subtotal12;
    }

    private BigDecimal getSubtotalIVA0(List<TotalImpuesto> totalConImpuestos) {
        BigDecimal subtotal0 = new BigDecimal(BigInteger.ZERO);
        Iterator<TotalImpuesto> totalConImpuestosItr = totalConImpuestos.iterator();
        while (totalConImpuestosItr.hasNext()) {
            TotalImpuesto totalImpuesto = totalConImpuestosItr.next();
            if (totalImpuesto.getCodigo().equals("2")) {
                if (totalImpuesto.getCodigoPorcentaje().equals("0")) {
                    subtotal0 = totalImpuesto.getBaseImponible();
                }
            }
        }
        subtotal0 = subtotal0.setScale(2, RoundingMode.HALF_UP);
        return subtotal0;
    }

    private BigDecimal getSubtotalExentoIVA(List<TotalImpuesto> totalConImpuestos) {
        BigDecimal subtotalExento = new BigDecimal(BigInteger.ZERO);
        Iterator<TotalImpuesto> totalConImpuestosItr = totalConImpuestos.iterator();
        while (totalConImpuestosItr.hasNext()) {
            TotalImpuesto totalImpuesto = totalConImpuestosItr.next();
            if (totalImpuesto.getCodigo().equals("2")) {
                if (totalImpuesto.getCodigoPorcentaje().equals("6")) {
                    subtotalExento = totalImpuesto.getBaseImponible();
                }
            }
        }
        subtotalExento = subtotalExento.setScale(2, RoundingMode.HALF_UP);
        return subtotalExento;
    }

    private BigDecimal getICE(List<TotalImpuesto> totalConImpuestos) {
        BigDecimal valorICE = new BigDecimal(BigInteger.ZERO);
        Iterator<TotalImpuesto> totalConImpuestosItr = totalConImpuestos.iterator();
        while (totalConImpuestosItr.hasNext()) {
            TotalImpuesto totalImpuesto = totalConImpuestosItr.next();
            if (totalImpuesto.getCodigo().equals("3")) {
                valorICE = valorICE.add(totalImpuesto.getValor());
            }
        }
        valorICE = valorICE.setScale(2, RoundingMode.HALF_UP);
        return valorICE;
    }

    private BigDecimal getIVA12(List<TotalImpuesto> totalConImpuestos) {
        BigDecimal valorIVA = new BigDecimal(BigInteger.ZERO);
        Iterator<TotalImpuesto> totalConImpuestosItr = totalConImpuestos.iterator();
        while (totalConImpuestosItr.hasNext()) {
            TotalImpuesto totalImpuesto = totalConImpuestosItr.next();
            if (totalImpuesto.getCodigo().equals("2")) {
                if (totalImpuesto.getCodigoPorcentaje().equals("2")) {
                    valorIVA = valorIVA.add(totalImpuesto.getValor());
                }
            }
        }
        valorIVA = valorIVA.setScale(2, RoundingMode.HALF_UP);
        return valorIVA;
    }

    public int subirAutorizacion(HttpServletRequest request, HttpServletResponse response) {
        int result = 0;
        try {

            Part filePart = request.getPart("autorizacion");
            String filename = getFilename(filePart);
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.INFO, "Se recibi\u00f3 archivo: {0}", filename);
            InputStream filecontent = filePart.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(filecontent);

            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{Autorizacion.class});
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Autorizacion autorizacion = (Autorizacion) unmarshaller.unmarshal(doc);

            String xml = autorizacion.getComprobante();

            StringReader reader = new StringReader(xml);

            dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(reader));

            jaxbContext = JAXBContext.newInstance(new Class[]{Factura.class});
            unmarshaller = jaxbContext.createUnmarshaller();

            Factura factura = (Factura) unmarshaller.unmarshal(doc);

            String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
            Conexion portal = new Conexion(path, factura.getInfoTributaria().getRuc());
            portal.conectar();
            portal.guardarAutorizacion(autorizacion, factura);
            portal.desconectar();
            result = 1;
        } catch (IOException | IllegalStateException | ServletException | SAXException | ParserConfigurationException | JAXBException ex) {
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FacturaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
