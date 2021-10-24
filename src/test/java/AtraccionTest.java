import static org.junit.Assert.*;

import org.junit.Test;

import agencia.Agencia;
import agencia.Atraccion;

public class AtraccionTest {

	Atraccion a1;
	Agencia SinCulpa = new Agencia();
	
	@Test
	public void seteaCupoTest() {
		//Busca una atraccion y sus atributos
		a1 = SinCulpa.getListaAtracciones().get(0);
		System.out.println(a1.toString());
		//setea cupo
		a1.setCupo(30);
		assertEquals(a1.getCupo(), 30);
	}
	
	@Test
	public void compruebaCupoTest() {
		//Busca una atraccion y sus atributos
		a1 = SinCulpa.getListaAtracciones().get(0);
		System.out.println(a1.toString());
		//setea cupo
		a1.setCupo(30);
		assertTrue(a1.getAtraccionConCupo());
	}
	@Test
	public void reduceCupoTest() {
		//Busca una atraccion y sus atributos
		a1 = SinCulpa.getListaAtracciones().get(0);
		System.out.println(a1.toString());
		//setea cupo
		a1.setCupo(2);
		assertEquals(a1.getCupo(), 2);
		assertEquals(2, a1.getCupoDisponible());
		a1.reducirCupo();
		assertEquals(a1.getCupo(), 2);
		assertEquals(1, a1.getCupoDisponible());
		assertTrue(a1.getAtraccionConCupo());
		a1.reducirCupo();
		assertFalse(a1.getAtraccionConCupo());
	}
}
