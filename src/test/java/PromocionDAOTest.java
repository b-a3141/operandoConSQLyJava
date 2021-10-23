import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import agencia.Atraccion;
import agencia.Porcentual;
import agencia.Producto;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import agencia.Promocion;
import dao.DAOFactory;
import dao.PromocionDAO;


public class PromocionDAOTest {
	PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
	private ArrayList<Atraccion> listaAtracciones = new ArrayList<Atraccion>(); 

	
	
	@Test
	public void cargaDatosTest() {
		promocionDAO.countAll();
		listaAtracciones.add(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Sabores modernos", 5, 10, 1.0))	;
		listaAtracciones.add(new Atraccion(
						tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Sabores Orientales", 60, 10, .50));
		listaAtracciones.add(new Atraccion(
				tipoDeProducto.ATRACCION, tipoDeAtraccion.DEGUSTACION, "Sabores medievales", 2, 15, 2.0));
		
		Producto sabores110 = new Porcentual(tipoDeProducto.PROMOCION, tipoDeAtraccion.DEGUSTACION,
				"Sabores 11 puntos", 10, listaAtracciones);
		promocionDAO.insert((Promocion) sabores110);
		assertEquals(promocionDAO.countAll(),17);
		
	}
	
	
	@Test
	public void ConectaYCuentaTest() {
		promocionDAO.countAll();
		
	}
	
	@Test
	public void insertaTest() {
		
		
	
		
	}

	@Test
	public void borraTest() {
	//	promocionDAO.delete();
	//	promocionDAO.countAll();
		
	}

	
	/*@Test
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
*/
	
}
