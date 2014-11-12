/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.comprobantes.dao;

import com.iveloper.comprobantes.utils.ConexionJDBC;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class ClaveAccesoDAO {
public void insertar(String clave, int estado, String codautorizacion) {
        String mensaje = "";
        boolean fallo = false;
        ConexionJDBC conn = new ConexionJDBC();
        Connection connection = conn.getConnection();
        Statement stmt = null;
        String sql = "INSERT INTO clave_acceso (clave, estado, codigoautorizacion) VALUES ('"
                + clave
                + "',"
                + estado
                + ",'" + codautorizacion + "');";
        try {
            stmt = connection.createStatement();
            stmt.execute(sql);
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    mensaje = e.getMessage();
                    fallo = true;
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    mensaje = e.getMessage();
                    fallo = true;
                }
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
            fallo = true;
        } finally {
            if (!fallo) {
                System.out.println("Se inserto clave " + clave);
            } else {
                System.err.print("Error de base de datos");
            }
        }
    }
}
