package com.erikxavi.barretina;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class contProd {
    @FXML
    private Label prod,cant;

    public void setProd(String title) {
        this.prod.setText(title);
    }

    public void setCant(String subtitle) {
        this.cant.setText(subtitle);
    }
}
