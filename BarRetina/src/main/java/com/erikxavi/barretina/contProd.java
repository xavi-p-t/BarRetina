package com.erikxavi.barretina;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class contProd {
    @FXML
    private Label prod,cant;

<<<<<<< Updated upstream
    public void setProd(String title) {
=======
    private  String idComanda;

    @FXML
    private Button detalles;

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public void setMesa(String title) {
>>>>>>> Stashed changes
        this.prod.setText(title);
    }

    public void setCant(String subtitle) {
        this.cant.setText(subtitle);
    }

    public void Detalles(ActionEvent actionEvent) {
        try {
            System.out.println(idComanda);
            contenedorGlobal.setVariableGlobal(idComanda);
            UtilsViews.addView(getClass(), "productosComanda", "/com/erikxavi/barretina/assets/productosComanda.fxml");

            UtilsViews.setViewAnimating("productosComanda");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
