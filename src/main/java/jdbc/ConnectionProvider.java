package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.MissingDataExceptions;

public class ConnectionProvider {

	
	private static String urlAgencia = "jdbc:sqlite:C:\\Users\\Berardi\\Documents\\Ale\\Argentina Programa\\Yo Programo\\AgenciaConSQL\\agencia.db";
	//atracciones.db
	private static Connection connection;
 
	
	public static Connection getConnection() throws SQLException { 
		if (connection == null) {
			connection = DriverManager.getConnection(urlAgencia);			
		}
		return connection;
		}

} 
