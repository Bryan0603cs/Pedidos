package pedidos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoPedido {

    private static final String RUTA_ARCHIVO = "src/data/pedidos.txt";

    
    public static void guardarTexto(String contenido) throws IOException {
        File directorio = new File("src/data");
        if (!directorio.exists()) {
            if (!directorio.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + directorio.getAbsolutePath());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {  
            writer.write(contenido);
            writer.newLine();
            System.out.println("Archivo guardado correctamente en: " + RUTA_ARCHIVO);
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo en " + RUTA_ARCHIVO + ": " + e.getMessage(), e);
        }
    }

    
    public static List<Pedido> leerPedidos() throws IOException {
        List<Pedido> pedidos = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe: " + RUTA_ARCHIVO);
            return pedidos; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String codigo = parts[0];
                String fecha = parts[1];
                double total = Double.parseDouble(parts[2]);
                double iva = Double.parseDouble(parts[3]);

                Pedido pedido = new Pedido(codigo, fecha);
                pedido.total = total;
                pedido.iva = iva;

                String[] productosStr = parts[4].split("\\|");
                for (String prodStr : productosStr) {
                    String[] prodParts = prodStr.split("#");
                    String prodCodigo = prodParts[0];
                    String prodNombre = prodParts[1];
                    double prodPrecio = Double.parseDouble(prodParts[2]);

                    Producto producto = new Producto(prodCodigo, prodNombre, prodPrecio);
                    pedido.agregarProducto(producto);
                }

                pedidos.add(pedido);
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo en " + RUTA_ARCHIVO + ": " + e.getMessage(), e);
        }

        return pedidos;
    }

    
    public static void guardarPedido(Pedido pedido) throws IOException {
        guardarTexto(pedido.toString());
    }
}
