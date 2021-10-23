package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import agencia.Atraccion;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import agencia.Usuario;
import jdbc.ConnectionProvider;


public class AtraccionDAOImpl implements AtraccionDAO{

	public int insert(Atraccion atraccion) {//throws SQLException {
		
		try{
			
			String sql = "SELECT * FROM atracciones WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, atraccion.getNombre());
			ResultSet resultados = statement.executeQuery();

			if (!resultados.next()) {
				
		
		String sql2 = "INSERT INTO atracciones (tipoAtr, nombre, precio, cupo, tiempoRequerido)"
				+ " VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement statement2 = conn.prepareStatement(sql2);
		statement2.setString (1, atraccion.getTipoDeAtraccion().toString());
		statement2.setString(2, atraccion.getNombre());
		statement2.setInt(3, atraccion.getPrecio());
		statement2.setInt(4, atraccion.getCupo());
		statement2.setDouble(5, atraccion.getTiempo());
		
		int rows = statement2.executeUpdate();
		return rows;
			} else {
				return 0;
			}
				
		} catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	//resta uno al cupo de la atraccion
	public int update(Atraccion atraccion) { //throws SQLException {
		
		try {
		String sql = "UPDATE atracciones SET cupo = ? WHERE nombre = ?";
		Connection conn = ConnectionProvider.getConnection();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, atraccion.getCupoDisponible());
		statement.setString(2, atraccion.getNombre());
		int rows = statement.executeUpdate();
		System.out.println(rows);
		return rows;
		} catch (Exception e) {
			throw new MissingDataExceptions (e);
			
		}
	}

	public int delete(Atraccion atraccion) {// throws SQLException {
		
		try {
		String sql = "DELETE FROM atracciones WHERE nombre = ?";
		Connection conn = ConnectionProvider.getConnection();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, atraccion.getNombre());
		int rows = statement.executeUpdate();

		return rows;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public Atraccion findByAtraccionname(String atraccionName) { // throws SQLException {
		
		try {
		String sql = "SELECT * FROM atracciones WHERE nombre = ?";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, atraccionName);
		ResultSet resultados = statement.executeQuery();

		Atraccion atraccion = null;

		if (resultados.next()) {
			atraccion = toAtraccion(resultados);
		}

		return atraccion;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public int countAll() { // throws SQLException {
		
		try {
		String sql = "SELECT COUNT(1) AS TOTAL FROM atracciones";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultados = statement.executeQuery();

		resultados.next();
		int total = resultados.getInt("TOTAL");

		System.out.println(total);
		return total;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public List<Atraccion> findAll() { //throws SQLException {
		
		try {
		String sql = "SELECT * FROM atracciones";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultados = statement.executeQuery();

		List<Atraccion> atracciones = new LinkedList<Atraccion>();
		while (resultados.next()) {
			atracciones.add(toAtraccion(resultados));
		}

		return atracciones;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	static Atraccion toAtraccion(ResultSet resultados) throws SQLException {
		tipoDeAtraccion tipoAtraccion;
		String paisaje = "PAISAJE";
		String aventura = "AVENTURA";
				
		if(aventura.equals(resultados.getString(1))){
			tipoAtraccion = tipoDeAtraccion.AVENTURA;
		} else if (paisaje.equals(resultados.getString(1))){
			tipoAtraccion = tipoDeAtraccion.PAISAJE;
		} else {
			tipoAtraccion = tipoDeAtraccion.DEGUSTACION;
		}
		
		
		return new Atraccion(tipoDeProducto.ATRACCION, tipoAtraccion, 
				resultados.getString(2), resultados.getInt(3), resultados.getInt(4),
				resultados.getDouble(5));
	}

	public ArrayList<Atraccion> findByPreferenciasUsuario(Usuario usuario) {
		
		ArrayList<Atraccion> listaSeleccionadaAlUsuaio = new ArrayList<Atraccion>();
		try {
			String sql = "SELECT atracciones.TipoAtr, atracciones.nombre, atracciones.precio, "
					+ "atracciones.cupo, atracciones.tiempoRequerido FROM atracciones "
					+ "JOIN usuarios ON "
					+ "usuarios.TipoAtraccion = atracciones.TipoAtr "
					+ " WHERE ('"+ usuario.getNombre()  +"' = usuarios.nombre AND cupo > 0 "
					+ "AND usuarios.monedas >= atracciones.precio "
					+ "AND usuarios.tiempo >= atracciones.tiempoRequerido) "
					+ "	ORDER BY precio DESC" ;
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();
			Atraccion atraccion;
			resultados.next();
			if (resultados.next()) {
				
				atraccion = toAtraccion(resultados);
			} else {
				atraccion = null;
			}
			//System.out.println(atraccion.toString());
			listaSeleccionadaAlUsuaio.add(atraccion);
			
			return listaSeleccionadaAlUsuaio;
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
	}

	public ArrayList<Atraccion> findByNotPreferenciasUsuario(Usuario usuario) {
		
		ArrayList<Atraccion> listaSinPreferencia = new ArrayList<Atraccion>();
		try {
			String sql = "SELECT * FROM atracciones WHERE "
					+ "(cupo > 0 AND precio >= ? AND tiempoRequerido >= ?) "
					+ "ORDER BY precio DESC;";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, usuario.getMonedasDeOro());
			statement.setDouble(1, usuario.getTiempoDisponible());
			ResultSet resultados = statement.executeQuery();

			Atraccion atraccion = null;

			if (resultados.next()) {
				atraccion = toAtraccion(resultados);
			}
			
			listaSinPreferencia.add(atraccion);
			
			return listaSinPreferencia;
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
	}

	public Atraccion toAtraccionContenidaEnPromocion(String nombreAtraccion) {
		try {
			String sql = "SELECT atracciones.TipoAtr, atracciones.nombre, atracciones.precio,"
					+ " atracciones.cupo, atracciones.tiempoRequerido FROM atracciones " 
					+ "WHERE  ? = atracciones.nombre" ;
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombreAtraccion);
			ResultSet resultados = statement.executeQuery();

			Atraccion atraccion = null;

			if (resultados.next()) {
				atraccion = toAtraccion(resultados);
			}

			return atraccion;
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
	}
}
