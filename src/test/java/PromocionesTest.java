import static org.junit.Assert.*;


import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import agencia.Agencia;
import agencia.Atraccion;
import agencia.Promocion;

public class PromocionesTest {

	Agencia SinCulpa = new Agencia();
	
	@Test
	public void AxBtest() throws SQLException {
		SinCulpa.construyeLaListaDeAtraccionesContenidas();
		ArrayList <Promocion> listaPromociones = new ArrayList <Promocion>();
		listaPromociones = (ArrayList<Promocion>) SinCulpa.getListaPromociones();
	//	System.out.println(listaPromociones.get(0));
		int tamanioArray = listaPromociones.get(0).getAtraccionesContenidas().size();
		Promocion p = listaPromociones.get(0);
	
		assertEquals(tamanioArray,2,0);
		assertEquals(p.getAtraccionesContenidas().get(0).getPrecio(),12,0);
		assertEquals(p.getAtraccionesContenidas().get(1).getPrecio(),90,0);
		assertEquals(p.getPrecio(),90,0);
		assertEquals(p.getDescuento(),1,0);
	}

	@Test
	public void AbsolutaTest() throws SQLException {
		SinCulpa.construyeLaListaDeAtraccionesContenidas();
		ArrayList <Promocion> listaPromociones = new ArrayList <Promocion>();
		listaPromociones = (ArrayList<Promocion>) SinCulpa.getListaPromociones();
	
		//Imprimo en pantalla para ver que sea promoción ABSOLUTA
		System.out.println(listaPromociones.get(12));
		int tamanioArray = listaPromociones.get(12).getAtraccionesContenidas().size();
		Promocion p = listaPromociones.get(12);
		//Muestra los nombres de las Atracciones de esa promocion
		for(Atraccion a: p.getAtraccionesContenidas()) {
			System.out.println(a.getNombre());
		}
		
		assertEquals(tamanioArray,2,0);
		assertEquals(p.getAtraccionesContenidas().get(0).getPrecio(),8,0);
		assertEquals(p.getAtraccionesContenidas().get(1).getPrecio(),8,0);
		assertEquals(p.getPrecio(),15,0);
		assertEquals(p.getDescuento(),1,0);
	}

	@Test
	public void PorcentualTest() throws SQLException {
		SinCulpa.construyeLaListaDeAtraccionesContenidas();
		ArrayList <Promocion> listaPromociones = new ArrayList <Promocion>();
		listaPromociones = (ArrayList<Promocion>) SinCulpa.getListaPromociones();
	
		//Imprimo en pantalla para ver que sea promoción ABSOLUTA
		System.out.println(listaPromociones.get(9));
		int tamanioArray = listaPromociones.get(9).getAtraccionesContenidas().size();
		Promocion p = listaPromociones.get(9);
		//Muestra los nombres de las Atracciones de esa promocion
		for(Atraccion a: p.getAtraccionesContenidas()) {
			System.out.println(a.getNombre());
		}
		
		assertEquals(tamanioArray,2,0);
		assertEquals(p.getAtraccionesContenidas().get(0).getPrecio(),12,0);
		assertEquals(p.getAtraccionesContenidas().get(1).getPrecio(),15,0);
		assertEquals(p.calculaPrecioSinDescuento(),27,0);
		assertEquals(p.getPrecio(),24,0);
		assertEquals(p.getDescuento(),10,0);
	}
}
