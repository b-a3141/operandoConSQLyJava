package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import agencia.Atraccion;
import agencia.Promocion;
import agencia.Usuario;


//una interfaz espacífica para Atracciones
public interface PromocionDAO extends GenericDAO<Promocion>{

	//Métodos específicos para Atracciones 
	public abstract Promocion findByPromocionName(String name); //throws SQLException;
	public abstract ArrayList<Integer> findByPreferenciasUsuario(Usuario usuario);
	public abstract TreeMap<Integer, ArrayList<String>> findAtraccionesContenidas();
	public abstract Promocion toPromocion(int id_promocion, ArrayList<Atraccion> atraccionesDeLaPromo) throws SQLException;
	
}
