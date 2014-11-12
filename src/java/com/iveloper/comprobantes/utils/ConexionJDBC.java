package com.iveloper.comprobantes.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConexionJDBC {

	private Connection connection = null;
	public static final String CONNECTION_URL = "jdbc:mysql://localhost/ce?user=ceusr&password=cepwd";
	public static final String DRIVER_CLASSNAME = "com.mysql.jdbc.Driver";

	public ConexionJDBC() {
		connection_SQL();
	}

	public void connection_SQL() {
		try {
			Class.forName(DRIVER_CLASSNAME);
			connection = DriverManager.getConnection(CONNECTION_URL);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se ha podido conectar a la base de datos.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void close() {
		if (connection != null)
			try {
				connection.close();
			} catch (Exception e) {
			}
	}

	public Connection getConnection() {
		return connection;
	}

}
