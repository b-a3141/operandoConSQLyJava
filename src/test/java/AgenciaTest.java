import static org.junit.Assert.*;


import org.junit.Test;

import agencia.Agencia;


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
	
	
	
	
}
