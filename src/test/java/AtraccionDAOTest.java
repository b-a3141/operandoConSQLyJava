import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import agencia.Atraccion;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import agencia.Usuario;
import dao.AtraccionDAO;
import dao.DAOFactory;

public class AtraccionDAOTest {
	AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		
	@Test
	public void ConectaYCuentaTest() {
		atraccionDAO.countAll();
		assertEquals(atraccionDAO.countAll(),17);
	}
	
	@Test
	public void insertaTest() {
		atraccionDAO.countAll();
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Sabores medievales", 2, 15, 2.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Sabores modernos", 5, 10, 1.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Sabores Orientales", 60, 10, .50));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.AVENTURA, "Luchas medievales", 1, 8, 1));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.AVENTURA, "Garganta del Diablo", 4, 40, 2.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.AVENTURA, "El salto del Titán", 30, 10, 1));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.PAISAJE, "Cavernas escondidas", 8, 150, 2.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.PAISAJE, "Playas vírgenes", 8, 8, 2.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.PAISAJE, "Esculturas naturales", 8, 10, 1.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.PAISAJE, "Arenas del sol", 8, 60, 9));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Carnívoros 100%", 42, 50, 2.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.AVENTURA, "Sobrevive sin mapa", 12, 20, 2.0));
		atraccionDAO.insert(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.AVENTURA, "Asalto al castillo", 25, 15, 2.0));
		
		atraccionDAO.countAll();
		
	}

	@Test
	public void buscaYConstruyeTest() {
	Atraccion prueba =	atraccionDAO.findByAtraccionname("Arenas del sol");
		assertEquals(8, prueba.getPrecio());
		assertEquals(60, prueba.getCupo());
		assertEquals(9, prueba.getTiempo(),0);
	}
	

	
	
	@Test
	public void borraTest() {
		atraccionDAO.delete(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Sabores medievales", 8, 150, 2.0));
		atraccionDAO.countAll();
		
	}

	
	@Test
	public void actualizaTest() {
		Atraccion civilizacionMex = new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.PAISAJE, "Arquitectura Maya", 90, 20, 5);
		atraccionDAO.insert(civilizacionMex);
		Atraccion comidaMex = new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Medioevo Picante", 9, 70, 0.5);
		atraccionDAO.insert(comidaMex);
		comidaMex.setCupoDisponible(80);
		atraccionDAO.update(comidaMex);
		assertEquals(80, comidaMex.getCupoDisponible());
	}

	@Test
	public void findByPreferenciaDeUnUsuarioTest() {
		Usuario u = new Usuario ("usuarioTest", 20, 3, tipoDeAtraccion.AVENTURA);
		ArrayList<Atraccion> listaTest = atraccionDAO.findByPreferenciasUsuario(u);
		assertNotNull(listaTest);
	}
	
	@Test
	public void atraccionesContenidasEnLaPromoTest() {
		Atraccion a = atraccionDAO.toAtraccionContenidaEnPromocion("Medioevo Picante");
		assertNotNull(a);
		assertEquals(a.getCupo(), 80);
		assertEquals(a.getPrecio(), 9);
	}
}
