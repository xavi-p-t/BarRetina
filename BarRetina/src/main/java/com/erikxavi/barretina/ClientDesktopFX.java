package com.erikxavi.barretina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ClientDesktopFX extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final int windowWidth = 600;
        final int windowHeight = 400;

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/erikxavi/barretina/assets/inicioContr.fxml"));
        UtilsViews.addView(getClass(), "inicioContr", "/com/erikxavi/barretina/assets/inicioContr.fxml");
        UtilsViews.addView(getClass(), "listasTags", "/com/erikxavi/barretina/assets/listasTags.fxml");
        //UtilsViews.parentContainer.getChildren().add(fxmlLoader.load());
        // Crear la escena con el archivo FXML cargado
        Scene scene = new Scene(UtilsViews.parentContainer);

        stage.setScene(scene);
        stage.onCloseRequestProperty(); // Call close method when closing window
        stage.setTitle("JavaFX - NodeJS");
        stage.setMinWidth(windowWidth);
        stage.setMinHeight(windowHeight);
        stage.show();
    }
}
