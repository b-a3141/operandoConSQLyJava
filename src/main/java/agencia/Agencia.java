package agencia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import dao.AtraccionDAO;
import dao.AtraccionDAOImpl;
import dao.DAOFactory;
import dao.PromocionDAO;
import dao.UsuarioDAO;


public class Agencia {

	
	private List<Usuario> listaDeUsuarios;
	private UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
	private List<Atraccion> listaDeAtracciones;
	private AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
	private List<Promocion> listaDePromociones;
	private PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
	private HashMap<String, Atraccion> mapaAtraccionesPorNombre;
	public ArrayList<Promocion> listaPromocionesAventuras;
	public ArrayList<Promocion> listaPromocionesPaisaje;
	public ArrayList<Promocion> listaPromocionesDegustacion;
	private ArrayList<Atraccion> mapaAtraccionesAventuras;
	private ArrayList<Atraccion> mapaAtraccionesPaisaje;
	private ArrayList<Atraccion> mapaAtraccionesDegustacion;
	
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
		System.out.println(p.toString());
		this.listaDePromociones.add(p);
		}	
		
	}

	public List<Promocion> getListaPromociones(){
		return this.listaDePromociones ;
	}
	
	// Mapas de Promociones por tipo de Atraccion Ordenadas por precio
	public void listasDePromocionesPorTipoAtraccion() {

		// Mapa de Promociones de Aventuras
		listaPromocionesAventuras = new ArrayList<Promocion>();

		for (Promocion p : listaDePromociones) {
			if ((p.tipoDeAtraccion.name().equals("AVENTURA"))&& (p.getAtraccionesConCupo()))
			{
				listaPromocionesAventuras.add((Promocion) p);
				}
			}
		//Ordena el Array  por precio
		Collections.sort(this.listaPromocionesAventuras, new OrdenadorPorPrecio());

		// Mapa de Promociones de Paisaje
		listaPromocionesPaisaje = new ArrayList<Promocion>();

		for (Promocion p : listaDePromociones) {
			if ((p.tipoDeAtraccion.equals(tipoDeAtraccion.PAISAJE))
					&& (p.getAtraccionesConCupo())	) {
				listaPromocionesPaisaje.add((Promocion) p);
			}
		}
		//Ordena el Array  por precio
		Collections.sort(this.listaPromocionesPaisaje, new OrdenadorPorPrecio());

		
		// Mapa de Degustacion
		listaPromocionesDegustacion = new ArrayList<Promocion>();

		for (Promocion p : listaDePromociones) {
			if (p.tipoDeAtraccion.equals(tipoDeAtraccion.DEGUSTACION)
					&& (p.getAtraccionesConCupo())) {
				listaPromocionesDegustacion.add((Promocion) p);
			}
		}
		
		//Ordena el Array  por precio
		Collections.sort(this.listaPromocionesDegustacion, new OrdenadorPorPrecio());

	}
	
	// Mapas de Atraccion Ordenadas por precio
	public void mapasDeAtraccionPorPrecio() {

		// Mapa de Aventuras
		atraccionesAventuras();

		atraccionesDegustacion();

		atraccionesPaisaje();

	}

	private void atraccionesPaisaje() {
		mapaAtraccionesPaisaje = new ArrayList<Atraccion>();

		for (Producto p : listaDeAtracciones) {
			if (p.tipoDeAtraccion.equals(tipoDeAtraccion.PAISAJE)) {
				mapaAtraccionesPaisaje.add((Atraccion) p);
			}
		}
		
		//Ordena el Array  por precio
		Collections.sort(this.mapaAtraccionesPaisaje, new OrdenadorPorPrecio());
		
	}

	private void atraccionesDegustacion() {
		mapaAtraccionesDegustacion = new ArrayList<Atraccion>();

		for (Producto p : listaDeAtracciones) {
			if (p.tipoDeAtraccion.equals(tipoDeAtraccion.DEGUSTACION)) {
				mapaAtraccionesDegustacion.add((Atraccion) p);
			}
		}
		//Ordena el Array  por precio
		Collections.sort(this.mapaAtraccionesDegustacion, new OrdenadorPorPrecio());
		
	}

	private void atraccionesAventuras() {
		mapaAtraccionesAventuras = new ArrayList<Atraccion>();

		for (Producto p : listaDeAtracciones) {
			if (p.tipoDeAtraccion.equals(tipoDeAtraccion.AVENTURA)) {
				mapaAtraccionesAventuras.add((Atraccion) p);
			}
		}
		//Ordena el Array  por precio
		Collections.sort(this.mapaAtraccionesAventuras, new OrdenadorPorPrecio());
	}

	//----------------------------------------------------------------------------------------
	
	// Filtros
	
	// Recorre lista usuarios
	public void filtroSugerencias() {

		this.mapaAtraccionPorNombre();
		
		for (Usuario u : listaDeUsuarios) {

			// Por cada usuario se genera el mapa para que los cupos estén actualizados

			this.listasDePromocionesPorTipoAtraccion();
			// Primero entra en el mapa del tipo de atraccion preferida

			if (u.getPreferencia().equals(tipoDeAtraccion.AVENTURA)) {

				// primero ofrece las de su gusto y luego las que no coinciden
				filtroPreferenciaAventura(u);
				filtroAtraccionAventura(u);
				filtroPreferenciaDegustacion(u);
				filtroPreferenciPaisaje(u);
				filtroAtraccionDegustacion(u);
				filtroAtraccionPaisaje(u);
			}

			if (u.getPreferencia().equals(tipoDeAtraccion.DEGUSTACION) ) {

				// primero ofrece las de su gusto y luego las que no coinciden
				filtroPreferenciaDegustacion(u);
				filtroAtraccionDegustacion(u);
				filtroPreferenciPaisaje(u);
				filtroPreferenciaAventura(u);
				filtroAtraccionAventura(u);
				filtroAtraccionPaisaje(u);
			}

			if (u.getPreferencia().equals(tipoDeAtraccion.PAISAJE)) {

				// primero ofrece las de su gusto y luego las que no coinciden
				filtroPreferenciPaisaje(u);
				filtroAtraccionPaisaje(u);
				filtroPreferenciaDegustacion(u);
				filtroPreferenciaAventura(u);
				filtroAtraccionAventura(u);
				filtroAtraccionDegustacion(u);

			}

			System.out.println("muchas gracias " + u.getNombre() + " por tratar con nuestra agencia");

		}

	}

	private void filtroPreferenciPaisaje(Usuario u) {
		
			for (Promocion cadaPromoPaisaje : listaPromocionesPaisaje) {

				// comprueba que le alcanza el dinero
				if (u.getMonedasDeOro() >= cadaPromoPaisaje.getPrecio()) {
						// Comprueba que le alcance el tiempo
						if (u.getTiempoDisponible() >= cadaPromoPaisaje.getTiempo()) {

							// Si cumple con todo, la ofrece
							if (this.ofertar(u, cadaPromoPaisaje)) {

								// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
								u.restarDinero((int) cadaPromoPaisaje.getPrecio());
								u.setTiempoDisponible(cadaPromoPaisaje.getTiempo());
								u.setSugerenciasDiarias(cadaPromoPaisaje);
								cadaPromoPaisaje.reducirCupo();

								// Actualiza el cupo de las y atracciones
								for (Atraccion a : cadaPromoPaisaje.getAtraccionesContenidas()) {
									a.reducirCupo();
									
								}
							}
						}
					}

				}

			}
	
	private void filtroPreferenciaDegustacion(Usuario u) {
	
		for (Promocion cadaPromoDegustacion : listaPromocionesDegustacion) {

			// comprueba que le alcanza el dinero
			if (u.getMonedasDeOro() >= cadaPromoDegustacion.getPrecio()) {
					// Comprueba que le alcance el tiempo
					if (u.getTiempoDisponible() >= cadaPromoDegustacion.getTiempo()) {

						// Si cumple con todo, la ofrece
						if (this.ofertar(u, cadaPromoDegustacion)) {

							// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
							u.restarDinero((int) cadaPromoDegustacion.getPrecio());
							u.setTiempoDisponible(cadaPromoDegustacion.getTiempo());
							u.setSugerenciasDiarias(cadaPromoDegustacion);

							// Actualiza el cupo de las y atracciones
							cadaPromoDegustacion.reducirCupo();
						}
					}
			}
		}
	}
	private void filtroPreferenciaAventura(Usuario u) {
		
		for (Producto cadaPromoAventura : listaPromocionesAventuras) {

			// comprueba que le alcanza el dinero
			if (u.getMonedasDeOro() >= cadaPromoAventura.getPrecio()) {
					// Comprueba que le alcance el tiempo
					if (u.getTiempoDisponible() >= cadaPromoAventura.getTiempo()) {

						// Si cumple con todo, la ofrece
						if (this.ofertar(u, cadaPromoAventura)) {

							// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
							u.restarDinero((int) cadaPromoAventura.getPrecio());
							u.setTiempoDisponible(cadaPromoAventura.getTiempo());
							u.setSugerenciasDiarias(cadaPromoAventura);

							// Actualiza el cupo de las y atracciones
							cadaPromoAventura.reducirCupo();
							}
						}
					}
			}
		
	}
	private void filtroAtraccionAventura(Usuario u) {

		this.atraccionesAventuras();
		for (Atraccion a : mapaAtraccionesAventuras) {

			// comprueba que le alcanza el dinero
			if (u.getMonedasDeOro() >= a.getPrecio() &&

					u.getTiempoDisponible() >= a.getTiempo()) {

				// Si cumple con todo, la ofrece
				if (this.ofertar(u, a)) {

					// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
					u.restarDinero((int) a.getPrecio());
					u.setTiempoDisponible(a.getTiempo());
					u.setSugerenciasDiarias(a);

					// Actualiza el cupo de las y atracciones
					a.reducirCupo();

				}
			}
		}
	}

	private void filtroAtraccionPaisaje(Usuario u) {

		this.atraccionesPaisaje();
		for (Atraccion a : mapaAtraccionesPaisaje) {

			// comprueba que le alcanza el dinero
			if (u.getMonedasDeOro() >= a.getPrecio() &&

					u.getTiempoDisponible() >= a.getTiempo()) {

				// Si cumple con todo, la ofrece
				if (this.ofertar(u, a)) {

					// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
					u.restarDinero((int) a.getPrecio());
					u.setTiempoDisponible(a.getTiempo());
					u.setSugerenciasDiarias(a);

					// Actualiza el cupo de las y atracciones
					a.reducirCupo();

				}
			}
		}
	}

	private void filtroAtraccionDegustacion(Usuario u) {

		this.atraccionesDegustacion();
		for (Atraccion a : mapaAtraccionesDegustacion) {

			// comprueba que le alcanza el dinero
			if (u.getMonedasDeOro() >= a.getPrecio() &&

					u.getTiempoDisponible() >= a.getTiempo()) {

// Si cumple con todo, la ofrece
				if (this.ofertar(u, a)) {

//Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra	
					u.restarDinero((int) a.getPrecio());
					u.setTiempoDisponible(a.getTiempo());
					u.setSugerenciasDiarias(a);

					// Actualiza el cupo de las y atracciones
					a.reducirCupo();;

				}
			}
		}
	}
	
		/*
		public void filtroSugerencias() {
		this.getListaUsuarios();
		for (Usuario u : listaDeUsuarios) {

		// Primero entra en promociones preferida
	
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
	 */	
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
		//SinCulpa.mapaAtraccionPorNombre();
	//	SinCulpa.filtroSugerencias();
	}
}
