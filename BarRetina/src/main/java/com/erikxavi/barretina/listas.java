package com.erikxavi.barretina;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class listas implements Initializable {

//    public static void main(String [] args){
//        String[][] a = cargarXML();
//    }

    @FXML
    private VBox yPane = new VBox();



    private String[][] BDtexto() {
        String url = "jdbc:mysql://localhost:33007/BarRetina";
        String user = "xavierik";
        String password = "X@v13r1k";
        String query = "select id_mesa,estado,Date_Format(fecha_comanda,'%H:%i') as fecha from comanda";
        String queryCant = "select count(*) as total from comanda";
        int cant = 0;



        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet rsCant = statement.executeQuery(queryCant);

            while (rsCant.next()){
                cant = rsCant.getInt("total");
            }

            ResultSet rs = statement.executeQuery(query);

            String[][] listRes = new String[cant][];

            while (rs.next()) {
                int i = 0;
                int idMes = rs.getInt("id_mesa");
                String estado = rs.getString("estado");
                String fecha = rs.getString("fecha");
                System.out.println("Id de la mesa: "+idMes+" estado: "+estado+" fecha: "+fecha);
                listRes[i] = new String[]{idMes+"",estado,fecha};
                i++;
            }
            return listRes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void cargarBD() throws IOException {
        String[][] list = BDtexto();

        URL resource = this.getClass().getResource("/com/erikxavi/barretina/assets/productos.fxml");
        System.out.println("Recurso FXML: " + resource);


        yPane.getChildren().clear();

        //yPane.getChildren().add(new Label("Este es un elemento de prueba"));

        for (String[] listElement : list) {

            //System.out.println(listElement[0]+" "+listElement[1]);
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            contProd itemController = loader.getController();


            itemController.setMesa("Mesa " + listElement[0]);
            itemController.setEstados(listElement[1]);
            itemController.setHora(listElement[2]);



            yPane.getChildren().add(itemTemplate);

        }
        yPane.requestLayout();
        //System.out.println("Elementos aÃ±adidos a yPane: " + yPane.getChildren().size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cargarBD();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
