package agencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import agencia.Producto.tipoDeProducto;

import dao.AtraccionDAOImpl;
import dao.DAOFactory;
import dao.PromocionDAOImpl;
import dao.UsuarioDAO;

public class Agencia {

	
	private List<Usuario> listaDeUsuarios;
	private UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

	
	public List<Usuario> getListaUsuarios(){
		return this.listaDeUsuarios = usuarioDAO.findAll();
	}
	
	public void filtroSugerencias() {
		
		for (Usuario u : listaDeUsuarios) {

		// Primero entra en promociones preferida
			PromocionDAOImpl promocionesDelUsuario = new PromocionDAOImpl();
			ArrayList <Promocion> listaDeLaPrimeraSeleccion = new ArrayList <Promocion>();
			listaDeLaPrimeraSeleccion =	promocionesDelUsuario.findByPreferenciasUsuario(u);

				for (Promocion promocion : listaDeLaPrimeraSeleccion) {

					// Si cumple con todo, la ofrece
					if (this.ofertar(u, promocion)) {

					// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
					usuarioDAO.updateTiempoYDinero(u, promocion);
					u.setSugerenciasDiarias(promocion);
					promocion.reducirCupo();
					promocionesDelUsuario.update(promocion);
					usuarioDAO.updateItinerario(promocion);
					

					// Actualiza el cupo de las y atracciones
					for (Atraccion a : promocion.getAtraccionesContenidas()) {
						a.reducirCupo();
					}
				}
			}
		// Segundo entra en atracciones preferida	
				AtraccionDAOImpl atraccionesDelUsuario= new AtraccionDAOImpl();
				ArrayList <Atraccion> listaDeLaSegundaSeleccion = new ArrayList <Atraccion>();
				listaDeLaSegundaSeleccion =	atraccionesDelUsuario.findByPreferenciasUsuario(u);
					
				for (Atraccion atraccion : listaDeLaSegundaSeleccion) {

						// Si cumple con todo, la ofrece
						if (this.ofertar(u, atraccion)) {

						// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
						usuarioDAO.updateTiempoYDinero(u, atraccion);
						u.setSugerenciasDiarias(atraccion);
						atraccion.reducirCupo();
						atraccionesDelUsuario.update(atraccion);
						usuarioDAO.updateItinerario(atraccion);
						}
				}
		// Tercero entra en promociones sobrantes
		PromocionDAOImpl promocionesSobrantes = new PromocionDAOImpl();
		ArrayList<Promocion> listaDeLaTerceraSeleccion = new ArrayList<Promocion>();
		listaDeLaTerceraSeleccion = promocionesSobrantes.findByNotPreferenciasUsuario(u);

		for (Promocion promocion : listaDeLaTerceraSeleccion) {

			// Si cumple con todo, la ofrece
			if (this.ofertar(u, promocion)) {

				// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
				usuarioDAO.updateTiempoYDinero(u, promocion);
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

		// Cuarto entra en atracciones sobrantes
		AtraccionDAOImpl atraccionesSobrantes = new AtraccionDAOImpl();
		ArrayList<Atraccion> listaDeLaCuartaSeleccion = new ArrayList<Atraccion>();
		listaDeLaCuartaSeleccion = atraccionesSobrantes.findByNotPreferenciasUsuario(u);

		for (Atraccion atraccion : listaDeLaCuartaSeleccion) {

			// Si cumple con todo, la ofrece
			if (this.ofertar(u, atraccion)) {

				// Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra
				usuarioDAO.updateTiempoYDinero(u, atraccion);
				u.setSugerenciasDiarias(atraccion);
				atraccion.reducirCupo();
				atraccionesSobrantes.update(atraccion);
				usuarioDAO.updateItinerario(atraccion);
			}
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

	
}
