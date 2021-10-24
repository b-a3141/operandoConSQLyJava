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
		this.cupoDisponible = cupo;
		this.tiempoNecesario = tiempoNecesario;
	}
	
	@Override
	public String toString() {
		String quienSoy = "";
		quienSoy += "Tipo de Atracción: " + this.tipoDeAtraccion + ", nombre: " + getNombre() + " \n" 
		+ "precio: " + getPrecio() + ", cupo: " + getCupo() + ", tiempo requerido: " + getTiempo();
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
		return this.precio;
	}

	
	public void setPrecio(int precio) {
		this.precio = precio;
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
	

	public tipoDeAtraccion getTipoDeAtraccion() {
		return this.tipoDeAtraccion;
	}

	//metodos sobre el Cupo
	
	public void setCupo(int cupo) {
		this.cupo = cupo;
		this.cupoDisponible = cupo;
	}
	
	@Override
	public int getCupo() {
		return cupo;
	}
	
	@Override
	public void reducirCupo() {
		this.cupoDisponible --;
		comprobarCupo();
	}

	public void setAtraccionConCupo(boolean atraccionConCupo) {
		this.atraccionConCupo = atraccionConCupo;
	}

	@Override
	public boolean getAtraccionConCupo() {
		return atraccionConCupo;
	}

	public int getCupoDisponible() {
		return cupoDisponible;
	}

	public void comprobarCupo() {
		atraccionConCupo = cupoDisponible > 0;
	}
	
}
