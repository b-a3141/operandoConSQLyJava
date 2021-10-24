import static org.junit.Assert.*;

import org.junit.Test;
import agencia.Producto.tipoDeAtraccion;
import agencia.Usuario;
import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.UsuarioDAO;

@SuppressWarnings("unused")
public class UsuarioDAOTest {
	UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
	AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
	Usuario usuarioBorrable = new Usuario("Gost", 15, 4.0, tipoDeAtraccion.PAISAJE);
		
	@Test
	public void ConectaYCuentaTest() {
		usuarioDAO.countAll();
	}
	
	@Test
	public void findAllTest() {
		usuarioDAO.findAll();
	
	}
	/*
	 * @Test public void insertaTest() { usuarioDAO.countAll();
	 * usuarioDAO.insert(new Usuario("Marcos", 8, 2.0,
	 * tipoDeAtraccion.DEGUSTACION)); usuarioDAO.insert(new Usuario("Daniela", 80,
	 * 20.0, tipoDeAtraccion.PAISAJE)); usuarioDAO.insert(new Usuario("Lucía", 800,
	 * 2.0, tipoDeAtraccion.AVENTURA)); usuarioDAO.insert(new Usuario("Javier", 50,
	 * 5.0, tipoDeAtraccion.DEGUSTACION)); usuarioDAO.insert(new Usuario("Valeria",
	 * 25, 4.0, tipoDeAtraccion.PAISAJE)); usuarioDAO.insert(new Usuario("Braian",
	 * 30, 3.0, tipoDeAtraccion.AVENTURA)); usuarioDAO.insert(new Usuario("Luana",
	 * 18, 1.0, tipoDeAtraccion.DEGUSTACION)); usuarioDAO.insert(new
	 * Usuario("Lucas", 20, 20.0, tipoDeAtraccion.AVENTURA)); usuarioDAO.insert(new
	 * Usuario("Martín", 40, 10.0, tipoDeAtraccion.AVENTURA)); usuarioDAO.insert(new
	 * Usuario("Bautista", 55, 9.0, tipoDeAtraccion.PAISAJE)); usuarioDAO.insert(new
	 * Usuario("Ana", 70, 6.0, tipoDeAtraccion.DEGUSTACION)); usuarioDAO.insert(new
	 * Usuario("Brisa", 15, 4.0, tipoDeAtraccion.PAISAJE));
	 * usuarioDAO.insert(usuarioBorrable);
	 * 
	 * usuarioDAO.countAll();
	 * 
	 * }
	 */

	/*
	 * @Test public void buscaTest() {
	 * 
	 * Usuario Lucia = usuarioDAO.findByUsuarioName("Lucía"); assertEquals(800,
	 * Lucia.getMonedasDeOro()); assertEquals(2, Lucia.getTiempoDisponible(),0);
	 * assertEquals(tipoDeAtraccion.AVENTURA, Lucia.getPreferencia()); }
	 */
	/*
	 * @Test public void buscaAtraccionesPreferidasTest() {
	 * 
	 * Usuario Marcos = usuarioDAO.findByUsuarioName("Marcos"); ArrayList<Atraccion>
	 * arrayAtraccionesMarcos = atraccionDAO.findByPreferenciasUsuario(Marcos);
	 * assertTrue(arrayAtraccionesMarcos != null); }
	 */
	
	/*
	 * @Test
	 * 
	 * public void buscaYborraTest() {
	 * 
	 * usuarioDAO.delete(usuarioBorrable); assertEquals(12, usuarioDAO.countAll());
	 * 
	 * }
	 */
	/*
	 * @Test public void actualizaTest() { Usuario Marcos =
	 * usuarioDAO.findByUsuarioName("Marcos"); usuarioDAO.cargaTiempoYDinero(Marcos,
	 * 10, 50); }
	 */
	
}
