package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import agencia.Atraccion;
import agencia.Producto;
import agencia.Usuario;
import agencia.Producto.tipoDeAtraccion;
import jdbc.ConnectionProvider;

public class UsuarioDAOImpl implements UsuarioDAO {

	public List<Usuario> findAll() {
		try {
			String sql = "SELECT * FROM usuarios";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Usuario> listaDeTodosLosUsuarios = new LinkedList<Usuario>();
			while (resultados.next()) {
				listaDeTodosLosUsuarios.add(toUsuario(resultados));
			}

			return listaDeTodosLosUsuarios;
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
	}

	private Usuario toUsuario(ResultSet resultados) throws SQLException {
		tipoDeAtraccion tipoAtraccion;
		String paisaje = "PAISAJE";
		String aventura = "AVENTURA";
				
		if(aventura.equals(resultados.getString(4))){
			tipoAtraccion = tipoDeAtraccion.AVENTURA;
		} else if (paisaje.equals(resultados.getString(4))){
			tipoAtraccion = tipoDeAtraccion.PAISAJE;
		} else {
			tipoAtraccion = tipoDeAtraccion.DEGUSTACION;
		}
		
		
		return new Usuario(resultados.getString(1),  resultados.getInt(2), 
				resultados.getDouble(3), tipoAtraccion);
	}

	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM usuarios";
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
	
	public Usuario findByUsuarioName(String Name) { // throws SQLException {
		
		try {
		String sql = "SELECT * FROM usuarios WHERE nombre = ?";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, Name);
		ResultSet resultados = statement.executeQuery();

		Usuario usuario = null;

		if (resultados.next()) {
			usuario = toUsuario(resultados);
		}

		return usuario;
		}
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	public int insert(Usuario usuario) {
		
		if(findByUsuarioName(usuario.getNombre()) == null) {
			
		
		try{
			String sql = "INSERT INTO usuarios (nombre, monedas, tiempo, TipoAtraccion)"
					+ " VALUES (?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString (1, usuario.getNombre());
			statement.setInt(2, usuario.getMonedasDeOro());
			statement.setDouble(3, usuario.getTiempoDisponible());
			statement.setString(4, usuario.getPreferencia().toString());
			
			
			int rows = statement.executeUpdate();

			return rows;
			} 
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
		} else {
			System.out.println("El usuario " + usuario.getNombre() +", ya existe");
			return 0;
		}
	}

	public int delete(Usuario usuario) {
		try {
			String sql = "DELETE FROM usuarios WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;
			}
			catch (Exception e) {
				throw new MissingDataExceptions (e);
			}
	}

	public int updateRestaTiempoYDineroPorLaCompra(Usuario usuario, Producto productoAdquirido) {

		int monedasDisponibles = usuario.getMonedasDeOro()-productoAdquirido.getPrecio();
		double tiempoDisponible = usuario.getTiempoDisponible() - productoAdquirido.getTiempo();
		try {
			String sql = "UPDATE usuarios SET monedas = ? AND tiempo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, monedasDisponibles);
			statement.setDouble(2, tiempoDisponible);
			statement.setString(3, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;
			} catch (Exception e) {
				throw new MissingDataExceptions (e);
				
			}
	}

	public int cargaTiempoYDinero(Usuario usuario, double tiempo, int dinero) {

		int monedasDisponibles = usuario.getMonedasDeOro()+dinero;
		double tiempoDisponible = usuario.getTiempoDisponible() + tiempo;
		try {
			String sql = "UPDATE usuarios SET monedas = ? AND tiempo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, monedasDisponibles);
			statement.setDouble(2, tiempoDisponible);
			statement.setString(3, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;
			} catch (Exception e) {
				throw new MissingDataExceptions (e);
				
			}
	}
	
	public int update(Usuario usuario) {

		try {
			String sql = "UPDATE usuarios SET monedas = ? , tiempo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, usuario.getMonedasDeOro());
			statement.setDouble(2, usuario.getTiempoDisponible());
			statement.setString(3, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;
			} catch (Exception e) {
				throw new MissingDataExceptions (e);
				
			}
	}

	public int updateItinerario(Producto p) {
		/*try {
		//	String sql = "UPDATE itinerario SET TipoAtraccion = ?  WHERE nombre = ?";
		//	Connection conn = ConnectionProvider.getConnection();
		//	PreparedStatement statement = conn.prepareStatement(sql);
		//	statement.setString(1, usuario.getPreferencia().toString());
		*	statement.setString(2, usuario.getNombre());
		*	int rows = statement.executeUpdate();
*
			return rows;
			} catch (Exception e) {
				throw new MissingDataExceptions (e);
				
			}*/
		return 0;
	}




	

}
