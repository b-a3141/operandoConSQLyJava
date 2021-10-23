package agencia;
import java.util.ArrayList;
import java.util.List;
public class Absoluta extends Promocion {
	
	ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	private int costo;
	protected boolean atraccionConCupo;
	private double tiempo;
	
	public Absoluta( tipoDeProducto tipo, 
			tipoDeAtraccion tipoAtraccion,String nombre, int costo, ArrayList<Atraccion> lista) {
		super(tipo, tipoAtraccion,nombre);
		this.costo = costo;
		atraccionesContenidas.addAll( lista);
		this.setTiempo();
		this.setAtraccionConCupo();
		
	}
	
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
	public int getPrecio() {
		int suma = 0;
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
		 suma += atraccionesContenidas.get(i).getPrecio();
		}
		costo = suma - 5;
		return this.costo;
	}
	
	public void agregarAtracciones(List<Atraccion> lista) {
		this.atraccionesContenidas.addAll(lista);
	}
	
	@Override
	public String toString() {
		String retorno = this.getNombre() + " " + this.getPrecio() + " " + atraccionesContenidas;
		return retorno;
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

	@Override
	public int getDescuento() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int calculaPrecio() {
		
		int sumaSinDescuento = 0;
		for(Atraccion a: this.atraccionesContenidas) {
			sumaSinDescuento =+ a.getPrecio();
		}
		return (sumaSinDescuento - this.costo );
	}
	
	
	}
