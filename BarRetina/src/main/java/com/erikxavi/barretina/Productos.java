package com.erikxavi.barretina;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Productos {

    @FXML
    private VBox yPane;

    public void initialize() {
        String[] productos = {"Producto 1", "Producto 2", "Producto 3", "Producto 4"};

        String[] estados = {"Pedido", "En preparación", "Listo", "Pagado"};

        yPane.setFillWidth(true);

        for (String producto : productos) {
            HBox hBox = new HBox(20);
            hBox.setStyle("-fx-padding: 10 0 10 0; -fx-border-width: 0 0 1 0; -fx-border-color: white;"); 
            
            Label nombreLabel = new Label(producto);
            nombreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: 'Verdana';");
            nombreLabel.setMaxWidth(Double.MAX_VALUE); 

            Label precioLabel = new Label("10.00 €");
            precioLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: 'Verdana';");
            precioLabel.setMaxWidth(Double.MAX_VALUE); 

            ChoiceBox<String> estadoBox = new ChoiceBox<>();
            estadoBox.getItems().addAll(estados);
            estadoBox.setValue("Pedido");
            estadoBox.setStyle("-fx-background-color: black; -fx-font-size: 14px;");
            estadoBox.setMaxWidth(Double.MAX_VALUE);
            
            HBox.setHgrow(nombreLabel, Priority.ALWAYS);
            HBox.setHgrow(precioLabel, Priority.ALWAYS);
            HBox.setHgrow(estadoBox, Priority.ALWAYS);

            hBox.getChildren().addAll(nombreLabel, precioLabel, estadoBox);

            yPane.getChildren().add(hBox);
        }

        VBox.setVgrow(yPane, Priority.ALWAYS);
    }
}
