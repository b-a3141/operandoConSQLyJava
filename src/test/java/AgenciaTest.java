import static org.junit.Assert.*;

import org.junit.Test;

import agencia.Agencia;
import agencia.Atraccion;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import dao.AtraccionDAO;
import dao.DAOFactory;

public class AgenciaTest {
	Agencia SinCulpa = new Agencia();
		
	@Test
	public void ObtieneUsuariosTest() {
		SinCulpa.getListaUsuarios();
		int totalUsuarios = SinCulpa.getListaUsuarios().size();
		assertEquals(12, totalUsuarios);
	}
	
	@Test
	public void ObtienerUnUsuarioTest() {
		SinCulpa.getListaUsuarios();
		String nombrePrimerUsuario = SinCulpa.getListaUsuarios().get(0).getNombre();
		assertEquals("Marcos" , nombrePrimerUsuario);
	}
	
	@Test
	public void Filtro2Test() {
		SinCulpa.getListaUsuarios();
		SinCulpa.filtro2PorAtraccionesPreferidas();
	}
	
	
	
}
