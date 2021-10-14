package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import agencia.Absoluta;
import agencia.Atraccion;
import agencia.AxB;
import agencia.Porcentual;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import agencia.Promocion;
import agencia.TipoDeDescuento;
import agencia.Usuario;
import jdbc.ConnectionProvider;


public class PromocionDAOImpl implements PromocionDAO{

	public int insert(Promocion promocion) {//throws SQLException {
		
		try{
		String sql = "INSERT INTO promociones (tipoPromo, tipoAtra, nombre, descuento,"
				+ " precio, cupo, tiempo, atr1, atr2, atr3, atr4, atr5, atr6)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = ConnectionProvider.getConnection();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString (1, promocion.getTipoDePromocionToString());
		statement.setString (2, promocion.getTipoDeAtraccionToString());
		statement.setString(3, promocion.getNombre());
		statement.setInt(4, promocion.getDescuento());
		statement.setInt(5, promocion.getPrecio());
		statement.setInt(6, promocion.getCupo());
		statement.setDouble(7, promocion.getTiempo());
		statement.setString (8, promocion.getAtraccionesContenidas().get(0).getNombre());
		statement.setString (9, promocion.getAtraccionesContenidas().get(1).getNombre());
		statement.setString (10, promocion.getAtraccionesContenidas().get(2).getNombre());
		statement.setString (11, promocion.getAtraccionesContenidas().get(3).getNombre());
		statement.setString (12, promocion.getAtraccionesContenidas().get(4).getNombre());
		statement.setString (13, promocion.getAtraccionesContenidas().get(5).getNombre());
		int rows = statement.executeUpdate();

		return rows;
		} catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public int update(Promocion promocion) { //throws SQLException {
		
		try {
		String sql = "UPDATE promociones SET cupo = ? WHERE nombre = ?";
		Connection conn = ConnectionProvider.getConnection();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, promocion.getCupo());
		statement.setString(2, promocion.getNombre());
		int rows = statement.executeUpdate();

		return rows;
		} catch (Exception e) {
			throw new MissingDataExceptions (e);
			
		}
	}

	public int delete(Promocion promocion) {// throws SQLException {
		
		try {
		String sql = "DELETE FROM promociones WHERE nombre = ?";
		Connection conn = ConnectionProvider.getConnection();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, promocion.getNombre());
		int rows = statement.executeUpdate();

		return rows;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public Promocion findByPromocionName(String name) { // throws SQLException {
		
		try {
		String sql = "SELECT * FROM atraccion WHERE nombre = ?";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, name);
		ResultSet resultados = statement.executeQuery();

		Promocion promocion = null;

		if (resultados.next()) {
			promocion = toPromocion(resultados);
		}

		return promocion;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public int countAll() { // throws SQLException {
		
		try {
		String sql = "SELECT COUNT(1) AS TOTAL FROM promociones";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultados = statement.executeQuery();

		resultados.next();
		int total = resultados.getInt("TOTAL");

		return total;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public int getCantiddDeColumnasDeLaTabla() {
		int total;
		try {
			String sql = "SELECT *  FROM promociones LIMIT 1";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			resultados.next();
			total = resultados.getInt("TOTAL");

	
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
		
		return total;
	}
	public List<Promocion> findAll() { //throws SQLException {
		
		try {
		String sql = "SELECT * FROM promociones";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultados = statement.executeQuery();

		List<Promocion> promociones = new LinkedList<Promocion>();
		while (resultados.next()) {
			promociones.add(toPromocion(resultados));
		}

		return promociones;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	private Promocion toPromocion(ResultSet resultados) throws SQLException {
		
		try {
		if (resultados.getString(1).compareTo("porcentual") != 0  &&
				resultados.getString(1).compareTo("absoluta") != 0	&&
				resultados.getString(1).compareTo("AxB") != 0) {
			int x= 5;
			int y= 0;
			@SuppressWarnings("unused")
			int error = x/y;
		}
		
		} catch (TypePromotionError tpe){ 
			
			System.out.println("Algún tipo de Promoción no es adecuado");
		}
			
			
		tipoDeAtraccion tipoAtraccion;
		String paisaje = "PAISAJE";
		String aventura = "AVENTURA";
		if(aventura.equals(resultados.getString(2))){
			tipoAtraccion = tipoDeAtraccion.AVENTURA;
		} else if (paisaje.equals(resultados.getString(2))){
			tipoAtraccion = tipoDeAtraccion.PAISAJE;
		} else {
			tipoAtraccion = tipoDeAtraccion.DEGUSTACION;
		}
		//Construyo el ArrayList de Atracciones contenidas
		ArrayList<Atraccion> lista = new ArrayList<Atraccion>();
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
	
		for(int i =8; i<12; i++) {
			Atraccion aux;
			aux = atraccionDAO.findByAtraccionname(resultados.getString(i));
			lista.add(aux);
		}
		
		Promocion p;
		if(resultados.getString(1).compareTo("porcentual") == 0) {
			p = new Porcentual(tipoDeProducto.PROMOCION, tipoAtraccion, 
				resultados.getString(3), resultados.getInt(4), lista );
		return p;
		} else if (resultados.getString(1).compareTo("absoluta") == 0) {
			p = new Absoluta( tipoDeProducto.PROMOCION, tipoAtraccion, 
					resultados.getString(3), resultados.getInt(4), lista );
			return p;
			} else {
				return p = new AxB( tipoDeProducto.PROMOCION, tipoAtraccion, 
						resultados.getString(3), resultados.getInt(4), lista );
				 
			}
		
		
	}

	public ArrayList<Promocion> findByPreferenciasUsuario(Usuario usuario) {
		
		ArrayList<Promocion> listaSeleccionadaAlUsuaio = new ArrayList<Promocion>();
		try {
			String sql = "SELECT * FROM promociones WHERE "
					+ "(tipoAtra = ? AND cupo > 0 AND precio >= ? AND tiempo >= ?) "
					+ "ORDER BY precio DESC;";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getPreferencia().toString());
			statement.setInt(2, usuario.getMonedasDeOro());
			statement.setDouble(3, usuario.getTiempoDisponible());
			ResultSet resultados = statement.executeQuery();

			Promocion promocion = null;

			if (resultados.next()) {
				promocion = toPromocion(resultados);
			}
			
			listaSeleccionadaAlUsuaio.add(promocion);
			
			return listaSeleccionadaAlUsuaio;
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
	}


public ArrayList<Promocion> findByNotPreferenciasUsuario(Usuario usuario) {
		
		ArrayList<Promocion> listaSinPreferencia = new ArrayList<Promocion>();
		try {
			String sql = "SELECT * FROM promociones WHERE "
					+ "(cupo > 0 AND precio >= ? AND tiempo >= ?) "
					+ "ORDER BY precio DESC;";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
		
			statement.setInt(1, usuario.getMonedasDeOro());
			statement.setDouble(2, usuario.getTiempoDisponible());
			ResultSet resultados = statement.executeQuery();

			Promocion promocion = null;

			if (resultados.next()) {
				promocion = toPromocion(resultados);
			}
			
			listaSinPreferencia.add(promocion);
			
			return listaSinPreferencia;
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
	}

	
}
