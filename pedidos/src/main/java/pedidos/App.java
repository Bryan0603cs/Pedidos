package pedidos;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Pedidos"), 640, 480);
        stage.setScene(scene);
        stage.show();
        Producto p1 = new Producto("101", "Manzana", 1.5);
        Producto p2 = new Producto("102", "Naranja", 1.2);

  
        Pedido pedido = new Pedido("001", "2024-10-02");
        pedido.agregarProducto(p1);
        pedido.agregarProducto(p2);

  
        try {
            ArchivoPedido.guardarPedido(pedido);
            System.out.println("Pedido guardado exitosamente.");

            
            List<Pedido> pedidos = ArchivoPedido.leerPedidos();
            for (Pedido p : pedidos) {
                System.out.println(p.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}