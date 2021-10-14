package dao;

import agencia.Producto;
import agencia.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario>{

	//Métodos específicos para Usuarios 
	public int updateTiempoYDinero(Usuario u, Producto p);
	public int updateItinerario(Producto p);
}
