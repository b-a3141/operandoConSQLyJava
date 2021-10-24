package app;

import java.sql.SQLException;

import agencia.Agencia;


public class App {

	public static void main(String[] args) throws SQLException {
		
		Agencia LaMasBonita = new Agencia();
		
		LaMasBonita.execute();
		
	}

}
