module pedidos {
    requires javafx.controls;
    requires javafx.fxml;

    opens pedidos to javafx.fxml;
    exports pedidos;
}
