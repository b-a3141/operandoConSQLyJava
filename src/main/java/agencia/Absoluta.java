package agencia;
import java.util.ArrayList;
import java.util.List;
public class Absoluta extends Promocion {
	
	ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo;
	private double tiempo;
	private int descuento = 1;
	private int precio;
	
	public Absoluta( tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,
			String nombre, int descuento, ArrayList<Atraccion> lista) {
		super(tipo, tipoAtraccion,nombre);
		this.atraccionesContenidas = lista;
		this.setTiempo();
		this.setAtraccionConCupo();
		this.descuento = descuento;
	}
	
	//-----------------------------------------------------
		//Cálculo de costo, descuento y precio final
		
	@Override
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
		this.precio = calculaPrecioSinDescuento()- this.descuento;
	}
	
	@Override
	public int getPrecio() {
		setPrecio();
			return this.precio;
		}
		
	//Ajustes Sobre el array de atracciones 
	//---------------------------------------------------------------------------
	
	public ArrayList<Atraccion> getAtraccionesContenidas(){
		return this.atraccionesContenidas;
	}
	
	@Override
	public void reducirCupo() {
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			atraccionesContenidas.get(i).reducirCupo();
		}
		this.setAtraccionConCupo();
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

	public void agregarAtracciones(List<Atraccion> lista) {
		this.atraccionesContenidas.addAll(lista);
	}
	
	//Otros getter y setter
	//------------------------------------------------------------------------------------			  
		public void setTiempo() {
			for (int i = 0; i < atraccionesContenidas.size(); i++) {
				this.tiempo += atraccionesContenidas.get(i).getTiempo();
			}
		}
		
		public double getTiempo() {
			return this.tiempo;
		}
		
		@Override
		public String getNombre() {
			return nombre;
		}
			
		@Override
		public String toString() {
			String retorno = this.getNombre() +  " Incluye un descuento de  "+ 
		this.descuento  + " monedas, "+ "\n" +" con un precio final de "+ this.getPrecio()+
		" monedas de oro, y contiene las atracciones: " +"\n" + atraccionesContenidas;
			return retorno;
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

	
	}
