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
import java.util.ResourceBundle;

public class listas implements Initializable {

//    public static void main(String [] args){
//        String[][] a = cargarXML();
//    }

    @FXML
    private VBox yPane = new VBox();



    private static String[][] cargarXML(){

        String userDir = System.getProperty("user.dir");
        //System.out.println(userDir);
        File dataDir = new File(userDir,"data");
        //System.out.println(dataDir);
        File inputFile = new File(dataDir, "PRODUCTES.XML");
        Document doc = parseXML(inputFile);
        NodeList prod = doc.getElementsByTagName("producto");

        String[][] lista = new String[prod.getLength()][];
        for (int i = 0; i < prod.getLength(); i++) {
            Element productos = (Element) prod.item(i);
            String nom = productos.getElementsByTagName("nombre").item(0).getTextContent();
            String cant = "0";
            lista[i] = new String[]{nom,cant};
            //System.out.println(lista[i][0]+" "+lista[i][1]);
        }
        return lista;
    }
    public static Document parseXML(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();
            System.out.println("Archivo XML cargado correctamente.");
            return doc;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void setFXML() throws Exception {

        String[][] list = cargarXML();

        URL resource = this.getClass().getResource("/com/erikxavi/barretina/assets/productos.fxml");
        System.out.println("Recurso FXML: " + resource);


        yPane.getChildren().clear();

        //yPane.getChildren().add(new Label("Este es un elemento de prueba"));

        for (String[] listElement : list) {

            //System.out.println(listElement[0]+" "+listElement[1]);
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            contProd itemController = loader.getController();


            itemController.setProd(listElement[0]);
            itemController.setCant(listElement[1]);



            yPane.getChildren().add(itemTemplate);

        }
        yPane.requestLayout();
        //System.out.println("Elementos añadidos a yPane: " + yPane.getChildren().size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setFXML();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
