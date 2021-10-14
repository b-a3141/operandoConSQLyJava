package dao;

import java.util.ArrayList;
import agencia.Atraccion;
import agencia.Usuario;


//una interfaz espacífica para Atracciones
public interface AtraccionDAO extends GenericDAO<Atraccion>{

	//Métodos específicos para Atracciones 
	public abstract Atraccion findByAtraccionname(String name); //throws SQLException;
	public abstract ArrayList<Atraccion> findByPreferenciasUsuario(Usuario usuario);
}
