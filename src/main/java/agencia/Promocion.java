package agencia;

import java.util.*;


public abstract class Promocion extends Producto {
	
	ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	String nombre;
	
	public Promocion(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,String nombre) {
		super(tipo, tipoAtraccion);
		this.nombre = nombre;
	}

	public abstract String getTipoDePromocionToString();
	public abstract String getTipoDeAtraccionToString();
	public abstract int getDescuento();
		
	public ArrayList<Atraccion> getAtraccionesContenidas() {
		return atraccionesContenidas;
	}
	
	public ArrayList<Atraccion> setAtraccionesContenidas(Atraccion a) {
		this.atraccionesContenidas.add(a);
		return  this.atraccionesContenidas;
	}
	
	public abstract int calculaPrecio() ;

	
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
	
		int contador = 0;
		for(Atraccion a: atraccionesContenidas) {
			if(a.getCupo() <= 0) {
				contador = contador * 0;
			} else {
				contador =+ a.getCupo();
			}
		}
		return contador;
	}
		
	@Override
	public int getPrecio() {
	
		return 0;
	}
	
	public boolean getAtraccionesConCupo() { return false;
	}
	
	
	
	
}
