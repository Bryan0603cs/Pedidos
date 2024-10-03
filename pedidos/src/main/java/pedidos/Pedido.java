package pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String codigo;
    private String fechaPedido;
    public double total;
    public double iva;
    private List<Producto> productos;

    public Pedido(String codigo, String fechaPedido) {
        this.codigo = codigo;
        this.fechaPedido = fechaPedido;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        total += producto.precio;
        iva = total * 0.19;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(codigo).append(",").append(fechaPedido).append(",").append(total).append(",").append(iva).append(",");
        for (Producto p : productos) {
            sb.append(p.toString()).append("|");
        }
        return sb.toString();
    }
}

