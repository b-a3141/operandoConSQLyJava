package agencia;

public class Atraccion extends Producto {
	private String nombre;
	private int precio;
	private int cupo;
	private int cupoDisponible;
	private double tiempoNecesario;
	protected boolean atraccionConCupo = true;
	
	
	
	public Atraccion(tipoDeProducto tipo, 
			tipoDeAtraccion tipoDeAtraccion,
			String nombre,
			int precio,
			int cupo,
			double tiempoNecesario) {
		
		super(tipo, tipoDeAtraccion);
		this.nombre = nombre;
		this.precio = precio;
		this.cupo = cupo;
		this.tiempoNecesario = tiempoNecesario;
	}
	
	
	
	
	@Override
	public String toString() {
		String quienSoy = "";
		quienSoy += this.tipoDeAtraccion + " " + getNombre() + " " + getPrecio() + " " + getCupo() + " " + getTiempo();
		return quienSoy;
		
	}
	//Getters and setters
	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	
	
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	@Override
	public int getCupo() {
		return cupo;
	}

		
	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	public int getCupoDisponible() {
		return cupoDisponible;
	}

	public void setCupoDisponible(int cupoDisponible) {
		this.cupoDisponible = cupoDisponible;
	}

	@Override
	public double getTiempo() {
		
		return tiempoNecesario;
	}
	
	public String tiempoToString() {
		return Double.toString(this.tiempoNecesario);
	}
	
	public void setTiempoNecesario(double tiempoNecesario) {
		this.tiempoNecesario = tiempoNecesario;
	}
	@Override
	public boolean getAtraccionConCupo() {
		return atraccionConCupo;
	}

	public void setAtraccionConCupo(boolean atraccionConCupo) {
		this.atraccionConCupo = atraccionConCupo;
	}

	public tipoDeAtraccion getTipoDeAtraccion() {
		return this.tipoDeAtraccion;
	}

	public String getTipoDeAtraccionToString() {
		return this.tipoDeAtraccion.toString();
	}
	//metodos
	public void comprobarCupo() {
		atraccionConCupo = cupoDisponible > 0;
	}
	
	@Override
	public void reducirCupo() {
		setCupoDisponible(getCupoDisponible()-1);
		comprobarCupo();
	}




	
	
}
