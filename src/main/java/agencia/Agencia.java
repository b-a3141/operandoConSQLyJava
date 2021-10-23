package agencia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

import agencia.Producto.tipoDeProducto;
import dao.AtraccionDAO;
import dao.AtraccionDAOImpl;
import dao.DAOFactory;
import dao.PromocionDAO;
import dao.PromocionDAOImpl;
import dao.UsuarioDAO;


public class Agencia {

	
	private List<Usuario> listaDeUsuarios;
	private UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
	private List<Atraccion> listaDeAtracciones;
	private AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
	private List<Promocion> listaDePromociones;
	private PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
	private HashMap<String, Atraccion> mapaAtraccionesPorNombre;
	
	
	public List<Usuario> getListaUsuarios(){
		return this.listaDeUsuarios = usuarioDAO.findAll();
	}

	public List<Atraccion> getListaAtracciones(){
		return this.listaDeAtracciones = atraccionDAO.findAll();
	}
	
	public void mapaAtraccionPorNombre() {
		getListaAtracciones();
		mapaAtraccionesPorNombre = new HashMap<String, Atraccion>();

		for (Atraccion a : listaDeAtracciones) {
			String key = a.getNombre();
			mapaAtraccionesPorNombre.put(key, a);
		}
	}
	
	private void construyeLaListaDeAtraccionesContenidas() throws SQLException {
		
		listaDePromociones = new ArrayList <Promocion>();
		TreeMap<Integer, ArrayList<String>> map = promocionDAO.findAtraccionesContenidas();
		ArrayList<Atraccion> contenidas; 
		ArrayList<String> aux;
		Promocion p = null;
		int idAux;
		for (Entry <Integer, ArrayList<String>> cadaPromo_id:  map.entrySet()) {
			contenidas = new ArrayList<Atraccion>();
			//pido la id de la promoción
			idAux = cadaPromo_id.getKey();
			//pido el ArrayList con los nombres de las atracciones contenidas
			aux = cadaPromo_id.getValue();
			//Contruto el ArrayList con las Atracciones contenidas
			for(String nombreAtraccion : aux) {
				Atraccion a = atraccionDAO.toAtraccionContenidaEnPromocion(nombreAtraccion);
				contenidas.add(a);
				}
		//construyo la Promoción	
		p = promocionDAO.toPromocion(idAux, contenidas);
		System.out.println(p);
		this.listaDePromociones.add(p);
		}	
		
	}

	
	public List<Promocion> getListaPromociones(){
		return this.listaDePromociones ;
	}
	
		
	public void filtroSugerencias() {
		this.getListaUsuarios();
		for (Usuario u : listaDeUsuarios) {

		// Primero entra en promociones preferida
		/*
		 * PromocionDAOImpl promocionesDelUsuario = new PromocionDAOImpl(); ArrayList
		 * <Promocion> listaDeLaPrimeraSeleccion = new ArrayList <Promocion>();
		 * listaDeLaPrimeraSeleccion =
		 * promocionesDelUsuario.findByPreferenciasUsuario(u);
		 * if(listaDeLaPrimeraSeleccion != null) { for (Promocion promocion :
		 * listaDeLaPrimeraSeleccion) {
		 * 
		 * // Si cumple con todo, la ofrece if (this.ofertar(u, promocion)) {
		 * 
		 * // Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la
		 * compra usuarioDAO.updateRestaTiempoYDineroPorLaCompra(u, promocion);
		 * u.setSugerenciasDiarias(promocion); promocion.reducirCupo();
		 * usuarioDAO.updateItinerario(promocion);
		 * 
		 * // Actualiza el cupo de las y atracciones
		 * 
		 * } promocionesDelUsuario.update(promocion); } } else {
		 * System.out.println("No se encontraron promociones adecuadas al usuario"); }
		 */	
		// Segundo entra en atracciones preferida	
				AtraccionDAOImpl atraccionesDelUsuario= new AtraccionDAOImpl();
				ArrayList <Atraccion> listaDeLaSegundaSeleccion = new ArrayList <Atraccion>();
				listaDeLaSegundaSeleccion =	atraccionesDelUsuario.findByPreferenciasUsuario(u);
				if (listaDeLaSegundaSeleccion != null) {	
				for (Atraccion atraccion : listaDeLaSegundaSeleccion) {

						// Si cumple con todo, la ofrece
						if (this.ofertar(u, atraccion)) {

						// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
						usuarioDAO.updateRestaTiempoYDineroPorLaCompra(u, atraccion);
						u.setSugerenciasDiarias(atraccion);
						atraccion.reducirCupo();
						atraccionesDelUsuario.update(atraccion);
						usuarioDAO.updateItinerario(atraccion);
						}
				}
		} else {
			System.out.println("No se encontraron atracciones adecuadas al usuario");
		}
		
		// Tercero entra en promociones sobrantes
		PromocionDAOImpl promocionesSobrantes = new PromocionDAOImpl();
		ArrayList<Promocion> listaDeLaTerceraSeleccion = new ArrayList<Promocion>();
	//	listaDeLaTerceraSeleccion = promocionesSobrantes.findByNotPreferenciasUsuario(u);
		if (listaDeLaTerceraSeleccion != null) {
		for (Promocion promocion : listaDeLaTerceraSeleccion) {

			// Si cumple con todo, la ofrece
			if (this.ofertar(u, promocion)) {

				// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
				usuarioDAO.updateRestaTiempoYDineroPorLaCompra(u, promocion);
				u.setSugerenciasDiarias(promocion);
				promocion.reducirCupo();
				promocionesSobrantes.update(promocion);
				usuarioDAO.updateItinerario(promocion);

				// Actualiza el cupo de las y atracciones
				for (Atraccion a : promocion.getAtraccionesContenidas()) {
					a.reducirCupo();
				}
			}
		}
	} 

		// Cuarto entra en atracciones sobrantes
		AtraccionDAOImpl atraccionesSobrantes = new AtraccionDAOImpl();
		ArrayList<Atraccion> listaDeLaCuartaSeleccion = new ArrayList<Atraccion>();
		listaDeLaCuartaSeleccion = atraccionesSobrantes.findByNotPreferenciasUsuario(u);
		if(listaDeLaCuartaSeleccion != null) {
		for (Atraccion atraccion : listaDeLaCuartaSeleccion) {

			// Si cumple con todo, la ofrece
			if (this.ofertar(u, atraccion)) {

				// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
				usuarioDAO.updateRestaTiempoYDineroPorLaCompra(u, atraccion);
				u.setSugerenciasDiarias(atraccion);
				atraccion.reducirCupo();
				atraccionesSobrantes.update(atraccion);
				usuarioDAO.updateItinerario(atraccion);
			}
		}
		} else {
			System.out.println("No se encontraron atracciones disponibles");
		}

		System.out.println("muchas gracias " + u.getNombre() + " por tratar con nuestra agencia");
	}
}
	
	private boolean ofertar(Usuario u, Producto p)  {
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		
		if(p.getTipoDeProducto().name().equals(tipoDeProducto.PROMOCION.name())) {
			System.out.println("---------------------------");
			System.out.println("Bienvenido/a "+ u.getNombre() + " usted tiene un credito disponible de: "+ u.getMonedasDeOro());
			System.out.println("Tiene un tiempo disponible de: "+ u.getTiempoDisponible() +" horas");
			System.out.println("Nos ha dicho que su preferencia son las atraccciones de tipo: " + u.getPreferencia());
			System.out.println("Para usted,  nos parece lo más adecuado sugerirle la Promoción:   " + p.getNombre() + ", que cuesta " + p.getPrecio() + " y requiere " +
			p.getTiempo() + " Horas");
			
			System.out.print( "Acepta la oferta?. Por favor responda S por si o N por No: ");
		}
		if (p.getTipoDeProducto().name().equals(tipoDeProducto.ATRACCION.name())) {
			System.out.println("---------------------------");
			System.out.println("Bienvenido/a "+ u.getNombre() + " usted tiene un credito disponible de: "+ u.getMonedasDeOro());
			System.out.println("Tiene un tiempo disponible de: "+ u.getTiempoDisponible() +"horas");
			System.out.println("Nos ha dicho que su preferencia son las atraccciones de tipo: " + u.getPreferencia());
			System.out.println("Para usted,  nos parece lo más adecuado sugerirle la Atracción:   "  + p.getNombre() + ", que cuesta " + p.getPrecio() + " y requiere " +
					p.getTiempo() + " Horas");
			System.out.print( "Acepta la oferta?. Por favor responda S por si o N por No: ");
		}
		

		String respuesta = teclado.next();
		//Arcaico pero funciona
		boolean respuestaEsperada = respuesta.toLowerCase().equals("n") || respuesta.toLowerCase().equals("s");
		
		while(respuestaEsperada != true) {
			System.out.println("Su respuesta no era valida por favor reingrese la respuesta.");
			respuesta  = teclado.next();
			respuestaEsperada = respuesta.toLowerCase().equals("n") || respuesta.toLowerCase().equals("s");
			
		}
		
		// acepta oferta
		if (respuesta.equals("S") || respuesta.equals("s")) {

			// Prueba de salida
			System.out.println("Oferta aceptada. ");
			System.out.println("Agregó a su itinerario :  " + p.getNombre());
			
			return true;
		}
		// rechaza oferta

		System.out.println("Oferta rechazada. ");
		return false;
		
	}

	public void execute() {
		
		getListaUsuarios();
		filtroSugerencias();
	}

public void filtro2PorAtraccionesPreferidas() {
		this.getListaUsuarios();
		for (Usuario u : listaDeUsuarios) {

			System.out.println(u );
		// Segundo entra en atracciones preferida	
				AtraccionDAOImpl atraccionesDelUsuario= new AtraccionDAOImpl();
				ArrayList <Atraccion> listaDeLaSegundaSeleccion = new ArrayList <Atraccion>();
				
				listaDeLaSegundaSeleccion =	atraccionesDelUsuario.findByPreferenciasUsuario(u);
				
				if (listaDeLaSegundaSeleccion != null) {	
				for (Atraccion atraccion : listaDeLaSegundaSeleccion) {
					
				
					System.out.println(atraccion);
						// Si cumple con todo, la ofrece
						if (this.ofertar(u, atraccion)) {

						// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
						usuarioDAO.updateRestaTiempoYDineroPorLaCompra(u, atraccion);
						u.setSugerenciasDiarias(atraccion);
						atraccion.reducirCupo();
						atraccionesDelUsuario.update(atraccion);
						usuarioDAO.updateItinerario(atraccion);
						}
				}
		} else {
			System.out.println("No se encontraron atracciones adecuadas al usuario");
		}
		}
	}

	/*
	 * private void primerFiltro() { this.getListaUsuarios(); for (Usuario u :
	 * listaDeUsuarios) {
	 * 
	 * PromocionDAOImpl promocionesDelUsuario = new PromocionDAOImpl(); ArrayList
	 * <Promocion> listaDeLaPrimeraSeleccion = new ArrayList <Promocion>();
	 * listaDeLaPrimeraSeleccion =
	 * promocionesDelUsuario.findByPreferenciasUsuario(u);
	 * if(listaDeLaPrimeraSeleccion != null) { for (Promocion promocion :
	 * listaDeLaPrimeraSeleccion) {
	 * 
	 * // Si cumple con todo, la ofrece if (this.ofertar(u, promocion)) {
	 * 
	 * // Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la
	 * compra usuarioDAO.updateRestaTiempoYDineroPorLaCompra(u, promocion);
	 * u.setSugerenciasDiarias(promocion); promocion.reducirCupo();
	 * usuarioDAO.updateItinerario(promocion);
	 * 
	 * // Actualiza el cupo de las y atracciones
	 * 
	 * } promocionesDelUsuario.update(promocion); } } else {
	 * System.out.println("No se encontraron promociones adecuadas al usuario"); } }
	 * }
	 */
	@SuppressWarnings("unused")
	private void recorrerAtraccionesPreferidas() {
		AtraccionDAOImpl atraccionesDelUsuario = new AtraccionDAOImpl();
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
		Atraccion prueba =	atraccionesDelUsuario.findByAtraccionname("Arenas del sol");
		System.out.println(prueba.toString());
		List<Atraccion> listaTotal = atraccionesDelUsuario.findAll();			
		for (Atraccion a: listaTotal) {
			System.out.println(a.toString());
		}
		Usuario u = usuarioDAO.findByUsuarioName("Marcos");
		List<Atraccion> listaSeleccionada = atraccionesDelUsuario.findByPreferenciasUsuario(u);			
		for (Atraccion aS: listaSeleccionada) {
			System.out.println(aS.toString());
		}
	}
	
	//public void cargaPromocionesEnBaseDeDatos() {
	//	}
	
	public static void main(String[] args) throws SQLException {
		
		Agencia SinCulpa = new Agencia();
		SinCulpa.getListaUsuarios();
		//System.out.println(SinCulpa.listaDeUsuarios);
		SinCulpa.getListaAtracciones();
		//System.out.println(SinCulpa.listaDeAtracciones);
		SinCulpa.construyeLaListaDeAtraccionesContenidas();
		//System.out.println(SinCulpa.getListaPromociones());
	}
}
