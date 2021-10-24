package agencia;

import java.util.ArrayList;


public class Porcentual extends Promocion {
	
	ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo;
	private double tiempo;
	private int descuento;
	private int precio;
	

	public Porcentual(  tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,
			String nombre, int descuento, ArrayList<Atraccion> lista) {
		super(tipo, tipoAtraccion,nombre);
		this.descuento = descuento;
		this.atraccionesContenidas = lista;
		this.setTiempo();
		this.setAtraccionConCupo();
		
	}
	
	public ArrayList<Atraccion> getAtraccionesContenidas(){
		return this.atraccionesContenidas;
	}
	public ArrayList<Atraccion> setAtraccionesContenidas(Atraccion a) {
		this.atraccionesContenidas.add(a);
		return  this.atraccionesContenidas;
	}
	
	public String getTipoDePromocionToString() {
		return "porcentual";
	}
	
	public String getTipoDeAtraccionToString() {
		return this.tipoDeAtraccion.toString();
	}
	
	public void setTiempo() {
		
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			this.tiempo += atraccionesContenidas.get(i).getTiempo();
		}
		
	}
	
	public double getTiempo() {
		return this.tiempo;
	}
	
	public int getDescuento() {
		return this.descuento;
	}
	
	@Override
	public int calculaPrecioSinDescuento() {
		int acumulado = 0;
		for (Atraccion a: this.atraccionesContenidas) {
			acumulado += a.getPrecio();
		}
		return acumulado;
	}
	
	
	public void setPrecio() {
		double valorCalculado = ( calculaPrecioSinDescuento() * (100-this.descuento)/100);
		double parteDecimal = ( calculaPrecioSinDescuento() * ((100-this.descuento)/100))%1;
		double parteEntera = valorCalculado - parteDecimal;
		this.precio = (int) parteEntera;
	}
	
	@Override
	public int getPrecio() {
		setPrecio();
			return this.precio;
		}

	
	//Getters and setters
	
	

	@Override
	public String toString() {
		String retorno = this.getNombre() + " con un descuento de  "+ this.descuento +" % " + "\n" +
	" Queda a "+ this.getPrecio()+ " monedas de oro, y contiene las atracciones: " +  "\n" +
				atraccionesContenidas;
		return retorno;
	}

	public void setAtraccionesContenidas(ArrayList<Atraccion> atraccionesContenidas) {
		this.atraccionesContenidas = atraccionesContenidas;
	}

	
	public void setAtraccionConCupo() {
		int contador = 0;
		for (int i=0; i < atraccionesContenidas.size();i++) {
			if(atraccionesContenidas.get(i).atraccionConCupo==true) contador ++;
		}
		atraccionConCupo = contador == atraccionesContenidas.size();
	}
	
	
	public void reducirCupo() {
		
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			atraccionesContenidas.get(i).reducirCupo();
		}
		
		this.setAtraccionConCupo();
		
		}
	
	@Override
	public boolean getAtraccionesConCupo() {
		return this.atraccionConCupo;
	}


	
}
