package pedidos;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class PedidosController {

    @FXML
    private ComboBox<String> comboBoxProductos;  

    @FXML
    private ListView<String> listaPedidos;  

    private List<Producto> productosSeleccionados = new ArrayList<>();  

    private Pedido pedidoActual = new Pedido("001", "2024-10-02");  

    @FXML
    public void initialize() {
        
        comboBoxProductos.getItems().addAll(
            "101#Manzana#1.5",
            "102#Naranja#1.2",
            "103#Banana#0.9"
        );
    }

    @FXML
    private void agregarProducto() {
        
        String productoSeleccionado = comboBoxProductos.getValue();
        if (productoSeleccionado != null) {
            String[] parts = productoSeleccionado.split("#");
            String codigo = parts[0];
            String nombre = parts[1];
            double precio = Double.parseDouble(parts[2]);

            
            Producto producto = new Producto(codigo, nombre, precio);
            productosSeleccionados.add(producto);
            pedidoActual.agregarProducto(producto);  

            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Producto agregado");
            alert.setHeaderText(null);
            alert.setContentText("Se agregó el producto: " + nombre);
            alert.showAndWait();
        }
    }

    @FXML
    private void realizarPedido() throws IOException {
        if (!productosSeleccionados.isEmpty()) {
           
            ArchivoPedido.guardarPedido(pedidoActual);
            
            
            productosSeleccionados.clear();
            pedidoActual = new Pedido("002", "2024-10-02");  

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pedido realizado");
            alert.setHeaderText(null);
            alert.setContentText("Pedido realizado y guardado correctamente.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se ha seleccionado ningún producto para el pedido.");
            alert.showAndWait();
        }
    }

    @FXML
    private void verPedidos() throws IOException {

        List<Pedido> pedidos = ArchivoPedido.leerPedidos();
        
       
        listaPedidos.getItems().clear();
        

        for (Pedido pedido : pedidos) {
            listaPedidos.getItems().add(pedido.toString());
        }

        if (pedidos.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No hay pedidos");
            alert.setHeaderText(null);
            alert.setContentText("No se encontraron pedidos guardados.");
            alert.showAndWait();
        }
    }
}
