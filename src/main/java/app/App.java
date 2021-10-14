package app;

//import java.sql.SQLException;

import dao.DAOFactory;
import agencia.Atraccion;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import dao.AtraccionDAO;


public class App {

	//public static void main(String[] args) throws SQLException {
	public static void main(String[] args) {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		
		atraccionDAO.countAll();
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION,tipoDeAtraccion.AVENTURA, "Cascada Mortal", 12, 50, 1.0));
		atraccionDAO.countAll();
	}

}
