package com.iveloper.db;

import com.iveloper.comprobantes.autorizacion.Autorizacion;
import com.iveloper.comprobantes.modelo.factura.Factura;
import com.iveloper.entidades.Cuenta;
import com.iveloper.entidades.DetallesCuenta;
import com.iveloper.entidades.ResumenAutorizacion;
import com.iveloper.entidades.ResumenFactura;

import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.XMLGregorianCalendar;

public class Conexion {

    private Connection con;
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String PGSQL_DRIVER = "org.postgresql.Driver";
    private static final String MYSQL_DBMS = "mysql";
    private static final String MSSQL_DBMS = "sqlserver";
    private static final String PGSQL_DBMS = "postgresql";

    private String driver = MYSQL_DRIVER;
    private String dbms = MYSQL_DBMS;

    private String host = "";
    private String port = "";
    private String database = "";
    private String user = "";
    private String password = "";

    public Conexion(String ruta_configuracion_db) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(ruta_configuracion_db));
            host = props.getProperty("datasource.hostname");
            port = props.getProperty("datasource.port");
            database = props.getProperty("datasource.database");
            user = props.getProperty("datasource.username");
            password = props.getProperty("datasource.password");

            String dbvendor_str = props.getProperty("datasource.dbvendor");
            setDBVendor(Integer.parseInt(dbvendor_str));
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Conexion(String ruta_configuracion_db, int dbid) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(ruta_configuracion_db));
            host = props.getProperty("datasource.hostname");
            port = props.getProperty("datasource.port");
            database = props.getProperty("datasource.database") + String.format("%03d", dbid);
            user = props.getProperty("datasource.username");
            password = props.getProperty("datasource.password");

            String dbvendor_str = props.getProperty("datasource.dbvendor");
            setDBVendor(Integer.parseInt(dbvendor_str));
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Conexion(String ruta_configuracion_db, String dbid) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(ruta_configuracion_db));
            host = props.getProperty("datasource.hostname");
            port = props.getProperty("datasource.port");
            database = props.getProperty("datasource.database") + dbid;
            user = props.getProperty("datasource.username");
            password = props.getProperty("datasource.password");

            String dbvendor_str = props.getProperty("datasource.dbvendor");
            setDBVendor(Integer.parseInt(dbvendor_str));
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Conexion(String host, String port, String database, String user, String password, int dbvendor) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        setDBVendor(dbvendor);
    }

    private void setDBVendor(int dbvendor) {
        switch (dbvendor) {
            case 0:
                driver = MYSQL_DRIVER;
                dbms = MYSQL_DBMS;
                break;
            case 1:
                driver = MSSQL_DRIVER;
                dbms = MSSQL_DBMS;
                break;
            case 2:
                driver = PGSQL_DRIVER;
                dbms = PGSQL_DBMS;
                break;
            default:
                driver = MYSQL_DRIVER;
                dbms = MYSQL_DBMS;
                break;
        }
    }

    public void conectar() throws Exception {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ce) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ce);
        }
        try {

            switch (dbms) {
                case MYSQL_DBMS:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + database, user, password);
                    break;
                case MSSQL_DBMS:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + ";"
                            + "databaseName=" + database + ";user=" + user + ";password=" + password + ";");
                    break;
                case PGSQL_DBMS:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + database, user, password);
                    break;
                default:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + database, user, password);
                    break;
            }

            System.out.println("EVENTO: Conexión exitosa con la base de datos");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                this.con.close();
            }
            return (true);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return (false);
        }
    }

    public boolean estaConectado() {
        try {
            return !(this.con.isClosed()) && this.con.isValid(2);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Connection getCon() {
        return con;
    }

    public Cuenta obtenerCuentaPorId(int idcuenta) {

        Cuenta c = null;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM cuentas WHERE idcuenta = ?; ");
            st.setInt(1, idcuenta);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                String cuenta = rs.getString("cuenta");
                Timestamp fechacreacion = rs.getTimestamp("fechacreacion");
                Timestamp ultimoacceso = rs.getTimestamp("ultimoacceso");
                String roles = rs.getString("roles");
                int id = rs.getInt("idcuenta");
                byte[] pwd = rs.getBytes("pwd");
                byte[] salt = rs.getBytes("salt");
                String mail = rs.getString("mail");

                c = new Cuenta(id, cuenta, fechacreacion, ultimoacceso, roles, mail, pwd, salt);
                DetallesCuenta dc = this.obtenerDetalleCuentaPorId(idcuenta);
                c.setDetallesCuenta(dc);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (c);
    }

    public DetallesCuenta obtenerDetalleCuentaPorId(int idcuenta) {

        DetallesCuenta dc = null;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM detalles_cuenta WHERE idcuenta = ?; ");
            st.setInt(1, idcuenta);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                dc = new DetallesCuenta();

                String idcliente = rs.getString("idcliente");
                String idempresa = rs.getString("idempresa");
                String nombre_cliente = rs.getString("nombre_cliente");
                String nombre_empresa = rs.getString("nombre_empresa");
                String logo_empresa = rs.getString("logo_empresa");

                dc.setIdcliente(idcliente);
                dc.setIdempresa(idempresa);
                dc.setNombre_cliente(nombre_cliente);
                dc.setNombre_empresa(nombre_empresa);
                dc.setLogo_empresa(logo_empresa);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (dc);
    }

    public ResumenAutorizacion obtenerResumenAutorizacion(String claveacceso) {
        ResumenAutorizacion resumen = null;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM autorizaciones WHERE claveacceso = ?;");
            st.setString(1, claveacceso);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                resumen = new ResumenAutorizacion();
                String autorizacion = rs.getString("autorizacion");
                String fechaautorizacion = rs.getString("fechaautorizacion");
                String contenidoxml = rs.getString("contenidoxml");
                String estado = rs.getString("estado");
                String idcliente = rs.getString("idcliente");
                int tipodocumento = rs.getInt("tipodocumento");

                resumen.setClaveacceso(claveacceso);
                resumen.setAutorizacion(autorizacion);
                resumen.setFechaautorizacion(fechaautorizacion);
                resumen.setContenidoxml(contenidoxml);
                resumen.setContenidoxml(contenidoxml);
                resumen.setEstado(estado);
                resumen.setIdcliente(idcliente);
                resumen.setTipodocumento(tipodocumento);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (resumen);
    }

    public List<ResumenFactura> obtenerListaResumenFacturasAutorizadas(String idcliente, String fechainicio, String fechafin) {
        List<ResumenFactura> resumenes = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM resumenes_facturas WHERE idcliente = ? AND fechaemision between ? AND ? AND estado = 'AUTORIZADO'; ");
            st.setString(1, idcliente);
            st.setString(2, fechainicio);
            st.setString(3, fechafin);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ResumenFactura resumen = new ResumenFactura();
                String claveacceso = rs.getString("claveacceso");
                String numfactura = rs.getString("numfactura");
                String fechaemision = rs.getString("fechaemision");
                BigDecimal importetotal = rs.getBigDecimal("importetotal");
                resumen.setClaveacceso(claveacceso);
                resumen.setNumfactura(numfactura);
                resumen.setFechaemision(fechaemision);
                resumen.setIdcliente(idcliente);
                resumen.setImportetotal(importetotal);
                resumenes.add(resumen);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (resumenes);
    }

    public boolean cuentaValida(String usuario, String rol) {
        boolean resultado = false;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM cuentas WHERE usuario = ? AND roles LIKE concat('%',?,'%') ; ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, usuario);
            st.setString(2, rol);
            ResultSet rs = st.executeQuery();
            resultado = rs.first();
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (resultado);
    }

    public ArrayList consultarCuentas() {
        ArrayList cuentas = new ArrayList();

        try {
            Statement st = this.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cuentas;");

            while (rs.next()) {
                String cuenta = rs.getString("usuario");
                Timestamp fechacreacion = rs.getTimestamp("fechacreacion");
                Timestamp ultimoacceso = rs.getTimestamp("ultimoacceso");
                String roles = rs.getString("roles");
                int id = rs.getInt("idcuenta");
                byte[] pwd = rs.getBytes("pwd");
                byte[] salt = rs.getBytes("salt");
                String mail = rs.getString("mail");
                int activo = rs.getInt("activo");

                Cuenta c = new Cuenta(id, cuenta, fechacreacion, ultimoacceso, roles, mail, pwd, salt, activo);
                DetallesCuenta dc = this.obtenerDetalleCuentaPorId(id);
                c.setDetallesCuenta(dc);
                cuentas.add(c);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cuentas);
    }

    public Cuenta consultarCuenta(String usuario) {
        Cuenta cuenta = null;
        try {

            Statement st = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM cuentas WHERE usuario = '" + usuario + "';");

            if (rs.first()) {

                Date fechacreacion = rs.getDate("fechacreacion");
                Date ultimoacceso = null;
                try {
                    ultimoacceso = rs.getDate("ultimoacceso");
                } catch (SQLException e) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.WARNING, "consultarCuenta: '{'0'}'{0}", e);
                }

                String roles = rs.getString("roles");
                int id = rs.getInt("idcuenta");
                byte[] pwd = rs.getBytes("pwd");
                byte[] salt = rs.getBytes("salt");
                String mail = rs.getString("mail");
                int activo = rs.getInt("activo");
                Logger.getLogger(Conexion.class.getName()).log(Level.INFO, "Se obtuvo usuario a memoria");
                cuenta = new Cuenta(id, usuario, fechacreacion, ultimoacceso, roles, mail, pwd, salt, activo);
                DetallesCuenta dc = this.obtenerDetalleCuentaPorId(id);
                cuenta.setDetallesCuenta(dc);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cuenta);
    }

    public boolean modificarCuenta(Cuenta c) {
        try {
            PreparedStatement st = null;
            st = con.prepareStatement("UPDATE cuentas SET roles=?, pwd=?, mail=?, salt=?, activo=?, ultimoacceso=? WHERE usuario=?;");
            st.setString(1, c.roles);
            st.setBytes(2, c.pwd);
            st.setString(3, c.mail);
            st.setBytes(4, c.salt);
            st.setInt(5, c.activo);

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            st.setString(6, sdf.format(c.ultimoacceso));
            st.setString(7, c.usuario);

            st.executeUpdate();
            st.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public int guardarAutorizacion(Autorizacion autorizacion, Factura factura) {
        int result = 0;
        try {
            PreparedStatement st = null;
            System.out.println("Conexion: " + con.isClosed());
            String query = "INSERT INTO autorizaciones (claveacceso,autorizacion,fechaautorizacion,contenidoxml,estado,idcliente,tipodocumento) values (?,?,?,?,?,?,?);";
            st = con.prepareStatement(query);
            st.setString(1, factura.getInfoTributaria().getClaveAcceso());
            st.setString(2, autorizacion.getNumeroAutorizacion());
            XMLGregorianCalendar cal = autorizacion.getFechaAutorizacion();
            java.util.Date date = cal.toGregorianCalendar().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateString = formatter.format(date);
            st.setString(3, dateString);
            st.setString(4, autorizacion.getComprobante());
            st.setString(5, autorizacion.getEstado());
            st.setString(6, factura.getInfoFactura().getIdentificacionComprador());
            st.setString(7, factura.getInfoTributaria().getCodDoc());
            st.executeUpdate();
            this.guardarMensajesAutorizacion(autorizacion.getMensajes().getMensaje(), factura.getInfoTributaria().getClaveAcceso());
            this.guardarResumenFactura(factura, autorizacion.getEstado());

            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarAutorizacion: " + exception);
        }
        return result;
    }

    public int guardarMensajesAutorizacion(List<Mensaje> mensajes, String claveacceso) {
        int result = 0;
        Iterator<Mensaje> mensajesItr = mensajes.iterator();
        while (mensajesItr.hasNext()) {
            Mensaje mensaje = mensajesItr.next();
            result = guardarMensajeAutorizacion(mensaje, claveacceso);
        }
        return result;
    }

    public int guardarMensajeAutorizacion(Mensaje mensaje, String claveacceso) {
        int result = 0;
        try {
            PreparedStatement st = null;
            String update = "INSERT INTO autorizaciones_mensajes (claveacceso,identificador,tipo,mensaje,infoadicional) values (?,?,?,?,?);";
            st = con.prepareStatement(update);
            st.setString(1, claveacceso);
            st.setString(2, mensaje.getIdentificador());
            st.setString(3, mensaje.getTipo());
            st.setString(4, mensaje.getMensaje());
            st.setString(5, mensaje.getInformacionAdicional());

            result = st.executeUpdate();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int guardarResumenFactura(Factura factura, String estado) {
        int result = 0;
        try {
            PreparedStatement st = null;
            String query = "INSERT INTO resumenes_facturas (claveacceso,numfactura,fechaemision,idcliente,importetotal,estado) values (?,?,?,?,?,?);";
            st = con.prepareStatement(query);
            st.setString(1, factura.getInfoTributaria().getClaveAcceso());
            st.setString(2, factura.getInfoTributaria().getEstab() + factura.getInfoTributaria().getPtoEmi() + factura.getInfoTributaria().getSecuencial());

            String string = factura.getInfoFactura().getFechaEmision();
            java.util.Date date = null;
            String dateString = null;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(string);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                dateString = formatter.format(date);
            } catch (ParseException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

            st.setString(3, dateString);
            st.setString(4, factura.getInfoFactura().getIdentificacionComprador());
            st.setBigDecimal(5, factura.getInfoFactura().getImporteTotal());
            st.setString(6, estado);
            st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarResumenFactura: " + exception);
        }
        return result;
    }
}
