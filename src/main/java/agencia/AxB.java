package agencia;
import java.util.*;



public class AxB extends Promocion {

	ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	
	public boolean isAtraccionConCupo() {
		return atraccionConCupo;
	}

	protected boolean atraccionConCupo;
	private int costo = 0;
	private double tiempo;
	private int descuento = 1;
	
	public AxB(tipoDeProducto tipo, 
			tipoDeAtraccion tipoAtraccion,
			String nombre,
			int costo, 
			ArrayList<Atraccion> lista) {
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
				" atración/es sin cargo, y cuesta " + this.getPrecio() + " monedas de oro " 
	+ ", contiene a las atracciones: "+ atraccionesContenidas;
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
		return this.descuento;
	}

	
	@Override
	public int calculaPrecio() {
		
		int sumaSinDescuento = 0;
		int cantidadAtraccionesConCargo = this.atraccionesContenidas.size()-this.costo;
		for(Atraccion a: this.atraccionesContenidas) {
			if (cantidadAtraccionesConCargo > 0){
				cantidadAtraccionesConCargo --;
				sumaSinDescuento =+ a.getPrecio();
			}
		}
		return (sumaSinDescuento );
	}

	

	
}