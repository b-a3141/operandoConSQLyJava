package agencia;
import java.util.*;



public class AxB extends Promocion {

	List<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	
	public boolean isAtraccionConCupo() {
		return atraccionConCupo;
	}

	protected boolean atraccionConCupo;
	private int costo = 0;
	private double tiempo;
	private int descuento = 0;
	
	public AxB(tipoDeProducto tipo, 
			tipoDeAtraccion tipoAtraccion,
			String nombre,
			int costo, 
			List<Atraccion> lista) {
		super(tipo, tipoAtraccion,nombre);
		atraccionesContenidas.addAll(lista);
		this.setTiempo();
		this.setPrecio();
		this.setAtraccionConCupo();
		
	}

	@Override
	public int getPrecio() {
		
		return costo;
	}
	
	public void setPrecio() {
		int acumulado = 0;
		for (int i = 0; i < atraccionesContenidas.size()-1; i++) {
			acumulado += atraccionesContenidas.get(i).getPrecio();
		}
		this.costo = acumulado;
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
	
	public void reducirCupoPromocion() {
		
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			atraccionesContenidas.get(i).reducirCupo();
		}
		
		this.setAtraccionConCupo();
		
		}
	
	@Override
	public String toString() {
		String retorno = this.getNombre() + " " + this.getPrecio() + " " + atraccionesContenidas;
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

	@Override
	public int getDescuento() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}