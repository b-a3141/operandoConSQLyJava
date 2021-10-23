package agencia;

import java.util.ArrayList;
import java.util.List;

public class Porcentual extends Promocion {
	
	ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo;
	private double interesDeLaoferta;
	private double tiempo;
	private int descuento;
	

	public Porcentual(  tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,String nombre,
			 int descuento, ArrayList<Atraccion> lista) {
		super(tipo, tipoAtraccion,nombre);
		this.descuento = descuento;
		this.atraccionesContenidas = lista;
		this.interesDeLaoferta = descuento/100;
		this.setTiempo();
		this.setAtraccionConCupo();
		
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
	public int getPrecio() {
		int costo = 0;
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			costo += atraccionesContenidas.get(i).getPrecio();
		}
		costo = (int) costo * ((100-this.descuento)/100);
		return costo;
	}
	
	//Getters and setters
	
	

	@Override
	public String toString() {
		String retorno = this.getNombre() + " " + this.getPrecio()+ " " + atraccionesContenidas;
		return retorno;
	}

	public void setAtraccionesContenidas(ArrayList<Atraccion> atraccionesContenidas) {
		this.atraccionesContenidas = atraccionesContenidas;
	}

	public double getInteresDeLaoferta() {
		return interesDeLaoferta;
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


	@Override
	public int calculaPrecio() {
			int sumaSinDescuento = 0;
			for(Atraccion a: this.atraccionesContenidas) {
				sumaSinDescuento =+ a.getPrecio();
			}
			
			return (sumaSinDescuento * (100- this.descuento)/100 %10);
		}
	
	
	
	
}
