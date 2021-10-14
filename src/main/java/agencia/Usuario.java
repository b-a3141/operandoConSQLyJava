package agencia;

import java.util.*;

import agencia.Producto.tipoDeAtraccion;

public class Usuario {
	private String nombre;
	private int monedasDeOro;
	private double tiempoDisponible;
	private tipoDeAtraccion preferencia;
	private List<Producto> SugerenciasDiarias = new ArrayList<Producto>();
	//Constructor
	public Usuario(String nombre, int monedasDeOro, double tiempoDisponible, 
			tipoDeAtraccion preferencia) {
		super();
		this.nombre = nombre;
		this.monedasDeOro = monedasDeOro;
		this.tiempoDisponible = tiempoDisponible;
		this.preferencia = preferencia;
	}
	//Getters and setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getMonedasDeOro() {
		return monedasDeOro;
	}
	public void restarDinero(int monedasDeOro) {
		this.monedasDeOro -= monedasDeOro;
	}
	public double getTiempoDisponible() {
		return tiempoDisponible;
	}
	public void setTiempoDisponible(double tiempo) {
		this.tiempoDisponible -= tiempo;
	}
	public tipoDeAtraccion getPreferencia() {
		return preferencia;
	}
	public void setPreferencia(tipoDeAtraccion preferencia) {
		this.preferencia = preferencia;
	}
	public List<Producto> getSugerenciasDiarias() {
		return SugerenciasDiarias;
	}
	public void setSugerenciasDiarias(Producto producto) {
		SugerenciasDiarias.add(producto);
	}
	
	public String getSugerenciasDiariasToString() {
		String imprimir = "";
		
		for(Producto p: this.SugerenciasDiarias) {
			imprimir += p.getNombre() + " " + p.getTipoDeProducto() + "\n" ;
		}
		return imprimir;
	}
	
	//Metodos
	public static boolean aceptar() {
		Scanner teclado = null;
		boolean resultado = false;
		try {
		teclado = new Scanner(System.in);
		System.out.println(
		"Ingrese la opci�n deseada:" + 
		"\n 1)Para aceptar la oferta "+ 
		"\n 2)Para rechazar la oferta: ");
		int opcionElegida = teclado.nextInt();
		while(opcionElegida != 1 && opcionElegida != 2) {
			System.out.println("La opci�n ingresada no es valida, ingrese una opci�n valida: ");
			int hastaQuesalga = teclado.nextInt();
			opcionElegida = hastaQuesalga;
		}
		resultado = opcionElegida != 2; } finally {
			teclado.close();
		}
		return resultado;
	} 
	
	public String toString() {
		String quienSoy = "";
		quienSoy += getNombre() + " ";
		quienSoy += getMonedasDeOro() + " ";
		quienSoy += getTiempoDisponible() + " ";
		return quienSoy;
	}
	
	public static void main(String[] args) {
		
	}
	
	
}
