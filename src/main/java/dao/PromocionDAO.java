package dao;

import java.util.ArrayList;
import agencia.Promocion;
import agencia.Usuario;


//una interfaz espacífica para Atracciones
public interface PromocionDAO extends GenericDAO<Promocion>{

	//Métodos específicos para Atracciones 
	public abstract Promocion findByPromocionName(String name); //throws SQLException;
	public abstract ArrayList<Promocion> findByPreferenciasUsuario(Usuario usuario);
}
