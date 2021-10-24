package agencia;
import java.util.*;



public class AxB extends Promocion {

	ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo;
	private double tiempo;
	private int descuento ;
	private int precio;
	
	public AxB(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,
			String nombre, int descuento, ArrayList<Atraccion> lista) {
		super(tipo, tipoAtraccion,nombre);
		this.atraccionesContenidas = lista;
		this.descuento = descuento;
		this.setTiempo();
		this.setPrecio();
		this.setAtraccionConCupo();
	}
	
	public boolean isAtraccionConCupo() {
		return atraccionConCupo;
	}

	public ArrayList<Atraccion> getAtraccionesContenidas(){
		return this.atraccionesContenidas;
	}
	
	//-----------------------------------------------------
	//Cálculo de costo, descuento y precio final
	
	@Override
	public int getPrecio() {
		setPrecio();
		return this.precio;
	}
	
	public void setPrecio() {
		this.precio = calculaPrecioSinDescuento() ;
		int cantidadDeAtracciones = this.atraccionesContenidas.size();
		if (this.descuento < cantidadDeAtracciones) {
			for( int i = 0; i < descuento ; i++) {
				this.precio -= this.atraccionesContenidas.get(i).getPrecio();
			}
		}
	}
	
	@Override
	public int calculaPrecioSinDescuento() {
		int acumulado = 0;
		for (Atraccion a: this.atraccionesContenidas) {
			acumulado += a.getPrecio();
		}
		return acumulado;
	}
	
	@Override
	public int getDescuento() {
		return this.descuento;
	}
	  
	public void setTiempo() {
		
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			this.tiempo += atraccionesContenidas.get(i).getTiempo();
		}
		
	}
	
	public double getTiempo() {
		return this.tiempo;
	}
	
	public void agregarAtraccionesContenidas(List<Atraccion> lista) {
		atraccionesContenidas.addAll(lista);
	}
	
	@Override
	public void reducirCupo() {
		
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			atraccionesContenidas.get(i).reducirCupo();
		}
		
		this.setAtraccionConCupo();
		
		}
	
	@Override
	public String toString() {
		String retorno = this.getNombre() + ",  incluye "+ this.getDescuento() + 
				" atración/es sin cargo, " +"\n"  + " y cuesta: " + this.getPrecio() + " monedas de oro " 
	+ ", contiene a las atracciones: "+  "\n" + atraccionesContenidas;
		return retorno;
	}
	
	public void setAtraccionConCupo() {
		int contador = 0;
		for (int i=0; i < atraccionesContenidas.size();i++) {
			if(atraccionesContenidas.get(i).atraccionConCupo==true) contador ++;
		}
		atraccionConCupo = contador == atraccionesContenidas.size();
	}
	
	@Override
	public boolean getAtraccionesConCupo() {
		return this.atraccionConCupo;
	}
	
	@Override
	public String getTipoDePromocionToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTipoDeAtraccionToString() {
		// TODO Auto-generated method stub
		return null;
	}


	  public static void main(String[] args) {
	  
	 
	 // AxB aPorB = new AxB(tipoDeProducto, tipoDeAtraccion, nombre, costo,
	 // atraccionesContenidas); }
	 
	  }
}
	  