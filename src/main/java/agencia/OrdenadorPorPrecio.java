package agencia;
import java.util.Comparator;

public class OrdenadorPorPrecio  implements Comparator<Producto>{


	public int compare(Producto producto, Producto otroProducto) {
		return(-1)* Double.compare(producto.getPrecio(), otroProducto.getPrecio());
	}
}
