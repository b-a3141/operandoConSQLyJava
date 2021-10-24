package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.TreeMap;

import agencia.Absoluta;
import agencia.Atraccion;
import agencia.AxB;
import agencia.Porcentual;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import agencia.Promocion;
import agencia.Usuario;
import jdbc.ConnectionProvider;

public class PromocionDAOImpl implements PromocionDAO {

	AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();

	public int insert(Promocion promocion) {// throws SQLException {

		try {
			String sql = "INSERT INTO promociones (tipoPromo, tipoAtra, nombre, descuento,"
					+ " precio, cupo, tiempo,id)" + " VALUES (?, ?, ?, ?, ?, ?, ?,?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, promocion.getNombre());
			ResultSet resultados = statement.executeQuery();

			if (resultados.next()) {

			
				statement.setString(1, promocion.getTipoDePromocionToString());
				statement.setString(2, promocion.getTipoDeAtraccionToString());
				statement.setString(3, promocion.getNombre());
				statement.setInt(4, promocion.getDescuento());
				statement.setInt(5, promocion.getPrecio());
				statement.setInt(6, promocion.getCupo());
				statement.setDouble(7, promocion.getTiempo());

				int rows = statement.executeUpdate();

				return rows;
			} else {
				return 0;
			}
		} catch (Exception e) {
			throw new MissingDataExceptions(e);
		}
	}

	public int update(Promocion promocion) { // throws SQLException {

		try {
			String sql = "UPDATE promociones SET cupo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, promocion.getCupo());
			statement.setString(2, promocion.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataExceptions(e);

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
		} catch (Exception e) {
			throw new MissingDataExceptions(e);
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
		} catch (Exception e) {
			throw new MissingDataExceptions(e);
		}
	}

	public Promocion toPromocion(int id_promocion, ArrayList<Atraccion> atraccionesDeLaPromo) throws SQLException {

		try {
			String sql = "SELECT TipoPromo, TipoAtra,nombre,descuento FROM promociones " +
							"WHERE id =" + id_promocion;
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();
			try {
				if (resultados.getString(1).compareTo("PORCENTUAL") != 0
						&& resultados.getString(1).compareTo("ABSOLUTA") != 0
						&& resultados.getString(1).compareTo("AxB") != 0) {
					int x = 5;
					int y = 0;
					@SuppressWarnings("unused")
					int error = x / y;
				}
			}
			catch (TypePromotionError tpe) {

				System.out.println("Algún tipo de Promoción no es adecuado");
			}
			tipoDeAtraccion tipoAtraccion;
			String paisaje = "PAISAJE";
			String aventura = "AVENTURA";
			if (aventura.equals(resultados.getString(2))) {
				tipoAtraccion = tipoDeAtraccion.AVENTURA;
			} else if (paisaje.equals(resultados.getString(2))) {
				tipoAtraccion = tipoDeAtraccion.PAISAJE;
			} else {
				tipoAtraccion = tipoDeAtraccion.DEGUSTACION;
			}
			// Construyo el ArrayList de Atracciones contenidas

			Promocion p;
			if (resultados.getString(1).compareTo("PORCENTUAL") == 0) {
				p = new Porcentual(tipoDeProducto.PROMOCION, tipoAtraccion, resultados.getString(3),
						resultados.getInt(4), atraccionesDeLaPromo);
				return p;
			} else if (resultados.getString(1).compareTo("ABSOLUTA") == 0) {
				p = new Absoluta(tipoDeProducto.PROMOCION, tipoAtraccion, resultados.getString(3), resultados.getInt(4),
						atraccionesDeLaPromo);
				return p;
			} else {
				return p = new AxB(tipoDeProducto.PROMOCION, tipoAtraccion, resultados.getString(3),
						resultados.getInt(4), atraccionesDeLaPromo);

			}

		} 
		catch (Exception e) {
			throw new MissingDataExceptions (e);
		}
	}

	/*
	 * public ArrayList<Integer> findByIdPromocion(String id_promocion) {
	 * 
	 * try { String sql = "SELECT promociones.id FROM promociones "
	 * 
	 * + "JOIN usuarios ON " + "usuarios.TipoAtraccion = promociones.TipoAtra " +
	 * " WHERE ('"+ usuario.getNombre()
	 * +"' = usuarios.nombre AND promociones.cupo > 0 " +
	 * "AND usuarios.monedas >= promociones.precio " +
	 * "AND usuarios.tiempo >= promociones.tiempo) " + "	ORDER BY precio DESC" ;
	 * Connection conn = ConnectionProvider.getConnection(); PreparedStatement
	 * statement = conn.prepareStatement(sql); ResultSet resultados =
	 * statement.executeQuery();
	 * 
	 * if (resultados.next()) { //Stores properties of a ResultSet object, including
	 * column count ResultSetMetaData rsmd = resultados.getMetaData(); int
	 * columnCount = rsmd.getColumnCount(); int i = 1; while( i <= columnCount) {
	 * listaSeleccionadaAlUsuaio.add(resultados.getInt(i++)); } } return
	 * listaSeleccionadaAlUsuaio; } catch (Exception e) { throw new
	 * MissingDataExceptions (e); }
	} */

	// Array con los id de las promociones seleccionadas para el usuario
	// Luego lo recorro para generar las atracciones contenidas en cada una
	// En toAtraccionContenidaEnPromocion() de AtraccionDAO
	public ArrayList<Integer> findByPreferenciasUsuario(Usuario usuario) {

		ArrayList<Integer> listaSeleccionadaAlUsuaio = new ArrayList<Integer>();
		try {
			String sql = "SELECT promociones.id FROM promociones " + "JOIN usuarios ON "
					+ "usuarios.TipoAtraccion = promociones.TipoAtra " + " WHERE ('" + usuario.getNombre()
					+ "' = usuarios.nombre AND promociones.cupo > 0 " + "AND usuarios.monedas >= promociones.precio "
					+ "AND usuarios.tiempo >= promociones.tiempo) " + "	ORDER BY precio DESC";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			if (resultados.next()) {
				// Stores properties of a ResultSet object, including column count
				ResultSetMetaData rsmd = resultados.getMetaData();
				int columnCount = rsmd.getColumnCount();
				int i = 1;
				while (i <= columnCount) {
					listaSeleccionadaAlUsuaio.add(resultados.getInt(i++));
				}
			}
			return listaSeleccionadaAlUsuaio;
		} catch (Exception e) {
			throw new MissingDataExceptions(e);
		}
	}

	public TreeMap<Integer, ArrayList<String>> findAtraccionesContenidas() {

		TreeMap<Integer, ArrayList<String>> promocionesYatracciones = new TreeMap<Integer, ArrayList<String>>();
		ArrayList<String> aux;
		try {
			String sql = "SELECT atraccionesContenidasEnPromociones.id_promocion, atracciones.nombre "
					+ " FROM atraccionesContenidasEnPromociones  " + "JOIN atracciones ON "
					+ "atraccionesContenidasEnPromociones.id_atraacion = atracciones.id  "
					+ "	ORDER BY atraccionesContenidasEnPromociones.id_promocion";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				int Key = resultados.getInt(1);
				if (!promocionesYatracciones.containsKey(Key)) {
					aux = new ArrayList<String>();
					aux.add(resultados.getString(2));
					promocionesYatracciones.put(Key, aux);
				} else {
					aux = promocionesYatracciones.get(Key);
					aux.add(resultados.getString(2));
					promocionesYatracciones.put(Key, aux);
				}
			}
			return promocionesYatracciones;
		} catch (Exception e) {
			throw new MissingDataExceptions(e);
		}
	}

	public List<Promocion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Promocion findByPromocionName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * public ArrayList<Promocion> findByNotPreferenciasUsuario(Usuario usuario) {
	 * 
	 * ArrayList<Promocion> listaSinPreferencia = new ArrayList<Promocion>(); try {
	 * String sql = "SELECT * FROM promociones WHERE " +
	 * "(cupo > 0 AND precio >= ? AND tiempo >= ?) " + "ORDER BY precio DESC;";
	 * Connection conn = ConnectionProvider.getConnection(); PreparedStatement
	 * statement = conn.prepareStatement(sql);
	 * 
	 * statement.setInt(1, usuario.getMonedasDeOro()); statement.setDouble(2,
	 * usuario.getTiempoDisponible()); ResultSet resultados =
	 * statement.executeQuery();
	 * 
	 * Promocion promocion = null;
	 * 
	 * if (resultados.next()) { promocion = toPromocion(resultados); }
	 * 
	 * listaSinPreferencia.add(promocion);
	 * 
	 * return listaSinPreferencia; } catch (Exception e) { throw new
	 * MissingDataExceptions (e); } }
	 */
}
