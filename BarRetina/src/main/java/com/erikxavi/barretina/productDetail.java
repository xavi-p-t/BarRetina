package com.erikxavi.barretina;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.Arrays;

public class productDetail {

    @FXML
    public Label prod;
    @FXML
    public Label precio;
    @FXML
    public ChoiceBox<String> despl;

    private String estadoActual;

    String[] estados = {"Pedido", "En preparación", "Listo", "Pagado"};



    public void setProd(String prod) {
        this.prod.setText(prod);
    }

    public void setPrecio(String precio) {

        this.precio.setText(precio);
    }

    public void inicioDesp() {
        this.despl.getItems().clear();
        this.despl.getItems().addAll(estados);
        this.despl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            estadoActual = newValue; // Actualiza el estado actual
            System.out.println("Estado cambiado a: " + estadoActual); // Debugging
        });
    }

    public void setDespl(String despl) {
        //inicioDesp();
        this.despl.setValue(despl);
    }

    public Label getProd() {
        return prod;
    }

    public Label getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        System.out.println(estadoActual);
        return prod.getText() + ":1:" + estadoActual + ":"+ precio.getText()+',';
    }
}
