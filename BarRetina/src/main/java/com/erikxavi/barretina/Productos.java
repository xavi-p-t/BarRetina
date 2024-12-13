package com.erikxavi.barretina;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Productos implements Initializable {

    public Button guard;
    public Button volv;
    @FXML
    private VBox yPane;

    private String idCom = contenedorGlobal.getVariableGlobal();
    private String comand = "";

    private List<productDetail> productControllers = new ArrayList<>();

    public String url = "jdbc:mysql://localhost:33007/BarRetina";
    public String user = "xavierik";
    public String password = "X@v13r1k";

    private String[][] BDtexto() {
        //System.out.println(this.idCom);

        String query = "select comanda from comanda where id_comanda = "+this.idCom;

        // System.out.println(query);

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            String comanda = "";
            while (rs.next()) {
                comanda = rs.getString("comanda");
                //System.out.println(comanda);
            }
            //comanda.substring(0,comanda.length()-1);
            String[][] listRes = procesarProductos(comanda);
            return listRes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String[][] procesarProductos(String input) {
        String[] productos = input.split(",");
        int totalFilas = 0;

        for (String producto : productos) {
            String[] partes = producto.split(":");
            if (partes.length == 4) {
                int cantidad = Integer.parseInt(partes[1]);
                totalFilas += cantidad;
            }
        }


        String[][] resultado = new String[totalFilas][4];

        int indice = 0;
        for (String producto : productos) {
            String[] partes = producto.split(":");
            //System.out.println(partes[0]+" "+partes[1]+" "+partes[2]);
            if (partes.length == 4) {
                String nombre = partes[0];

                int cantidad = Integer.parseInt(partes[1]);
                //System.out.println(cantidad);
                String estado = partes[2];
                //System.out.println(estado);
                double precioTotal = Double.parseDouble(partes[3]);
                double precioUnitario = precioTotal / cantidad; // Ajusta el precio para cada unidad

                for (int i = 0; i < cantidad; i++) {
                    resultado[indice][0] = nombre;
                    resultado[indice][1] = "1";
                    resultado[indice][2] = estado;
                    resultado[indice][3] = String.format("%.2f", precioUnitario); // Formatea el precio
                    indice++;
                }
            }
        }

        return resultado;
    }


    @FXML
    private void cargarBD() throws IOException {
        String[][] list = BDtexto();


        URL resource = this.getClass().getResource("/com/erikxavi/barretina/assets/vistaProducto.fxml");
        //System.out.println("Recurso FXML: " + resource);


        yPane.getChildren().clear();

        //yPane.getChildren().add(new Label("Este es un elemento de prueba"));

        for (String[] listElement : list) {

            //System.out.println(listElement[0]+" "+listElement[1]);
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            productDetail itemCont = loader.getController();

            itemCont.inicioDesp();
            itemCont.setProd(listElement[0]);
            itemCont.setPrecio(listElement[3]);
            itemCont.setDespl(listElement[2]);

            productControllers.add(itemCont);
            yPane.getChildren().add(itemTemplate);

        }

        yPane.requestLayout();
        //System.out.println("Elementos añadidos a yPane: " + yPane.getChildren().size());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cargarBD();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardarComand(ActionEvent actionEvent) {
        try {
            comand = "";

            for (productDetail itemCont : productControllers) {
                comand += itemCont.toString();
            }
            comand.substring(0,comand.length()-1);
            //System.out.println(comand);
            String query = "update comanda set comanda = ? where id_comanda = ?";
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1,comand);
                ps.setInt(2, Integer.parseInt(idCom));
                ps.executeUpdate();
            }
            UtilsViews.addView(getClass(), "listasTags", "/com/erikxavi/barretina/assets/listasTags.fxml");
            UtilsViews.setViewAnimating("listasTags");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atras(ActionEvent actionEvent) {
        try {
            UtilsViews.addView(getClass(), "listasTags", "/com/erikxavi/barretina/assets/listasTags.fxml");
            UtilsViews.setViewAnimating("listasTags");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
