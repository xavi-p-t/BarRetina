package com.erikxavi.barretina;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class contProd {
    @FXML
    private Label prod,estado,hora;

    @FXML
    private Button detalles;

    public void setMesa(String title) {
        this.prod.setText(title);
    }

    public void setEstados(String subtitle) {
        this.estado.setText(subtitle);
    }

    public void setHora(String subtitle) {
        this.hora.setText(subtitle);
    }
}
