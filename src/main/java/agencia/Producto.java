package agencia;

import java.util.List;
import java.util.Objects;

public abstract  class Producto  implements Comparable<Producto>{
	 tipoDeProducto tipoDeProducto;
	 tipoDeAtraccion tipoDeAtraccion;
	
	public enum tipoDeProducto{
		ATRACCION,PROMOCION
	}
	
	public static enum tipoDeAtraccion {
		AVENTURA, PAISAJE, DEGUSTACION
	}
	
	public String tipoDeAtracciontoString(tipoDeAtraccion tipoAtr) {
			return tipoAtr.toString() ;
	}
	
	public Producto(tipoDeProducto tipo, tipoDeAtraccion tipoDeAtraccion) {
		this.tipoDeProducto = tipo;
		this.tipoDeAtraccion = tipoDeAtraccion;
	}
	
	//Getters and setters
	
	public tipoDeProducto getTipoDeProducto() {
		return tipoDeProducto;
	}

	public void setTipoDeProducto(tipoDeProducto tipoDeProducto) {
		this.tipoDeProducto = tipoDeProducto;
	}

	public tipoDeAtraccion getTipoDeAtraccion() {
		return tipoDeAtraccion;
	}

	public void setTipoDeAtraccion(tipoDeAtraccion tipoDeAtraccion) {
		this.tipoDeAtraccion = tipoDeAtraccion;
	}

	//Funciones que van a poder usar clases hijas//
	public int getCupo() {return 0;}
	
	public abstract void reducirCupo();
	
	public String getNombre() {return null;}
	
	public boolean getAtraccionConCupo() {return false;}

	public abstract double getTiempo() ;
	public abstract int getPrecio();
	

	@Override
	public int hashCode() {
		return Objects.hash(tipoDeAtraccion, tipoDeProducto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return tipoDeAtraccion == other.tipoDeAtraccion && tipoDeProducto == other.tipoDeProducto &&
				this.getTiempo() == other.getTiempo();
	}

	public int compareTo(Producto otro) {
		if(this.getTiempo() > otro.getTiempo()) {
			return 1;
		}
		if(this.getTiempo() < otro.getTiempo()) {
			return -1;
		}
		else return 0;
	}
	
	public List<Producto> getAtr(){
		return null;
	}
	


}

