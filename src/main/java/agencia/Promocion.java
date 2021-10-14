package agencia;

import java.util.*;


public abstract class Promocion extends Producto {
	
	List<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	String nombre;
	
	public Promocion(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,String nombre) {
		super(tipo, tipoAtraccion);
		this.nombre = nombre;
	}

	public abstract String getTipoDePromocionToString();
	public abstract String getTipoDeAtraccionToString();
	public abstract int getDescuento();
		
	public List<Atraccion> getAtraccionesContenidas() {
		return atraccionesContenidas;
	}
	
	public List<Atraccion> setAtraccionesContenidas(Atraccion a) {
		this.atraccionesContenidas.add(a);
		return  this.atraccionesContenidas;
	}
	

	
	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		String datos = "Nombre de la PROMOCIÓN: " + this.nombre + "; ";
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			datos +=   		
					"Nombre de la Atracción: " + atraccionesContenidas.get(i).getNombre();
		}
		
		return datos;
	}
	@Override
	public int getCupo() {
		
		
		int noNulo = 1;
		for(Atraccion a: atraccionesContenidas) {
			noNulo  *= 	a.getCupo();
		}
		return noNulo;
	}
		
	@Override
	public int getPrecio() {
	
		return 0;
	}
	
	public boolean getAtraccionesConCupo() { return false;
	}
	
	
	
	
}
