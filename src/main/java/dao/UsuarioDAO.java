package dao;

import agencia.Producto;
import agencia.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario>{

	//Métodos específicos para Usuarios 
	public int updateRestaTiempoYDineroPorLaCompra(Usuario u, Producto p);
	public int updateItinerario(Producto p);
	public Usuario findByUsuarioName(String Name);
	public int cargaTiempoYDinero(Usuario usuario, double tiempo, int dinero);
}
