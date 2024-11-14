package com.erikxavi.barretina;

import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class inicioContr implements Initializable {

    public Label textoError;
    public StackPane parentContainer;
    public static UtilsWS wsClient;

    @FXML
    TextField urlfield;

    @FXML
    TextField locationField;
    @FXML
    Button startButon;

    public void iniciar(){
            String userDir = System.getProperty("user.dir");
            File dataDir = new File(userDir+File.separator+"config");
            File fitxerSortida = new File(dataDir, "CONFIG.XML");
            if (!dataDir.exists() | !fitxerSortida.exists()){
                if (urlfield.getText().equals("barretina")){
                    dataDir.mkdir();
                    Document doc = constrDoc();

                    guardDoc(doc,fitxerSortida);
                    try {
                        connectToServer(urlfield.getText());
                        UtilsViews.addView(getClass(), "listasTags", "/com/erikxavi/barretina/assets/listasTags.fxml");

                        UtilsViews.setViewAnimating("listasTags");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    textoError.setText("URL no valida");
                }
            }
            else {
                if (urlfield.getText().equals("barretina")) {
                    try {
                        connectToServer(urlfield.getText());
                        UtilsViews.addView(getClass(), "listasTags", "/com/erikxavi/barretina/assets/listasTags.fxml");

                        UtilsViews.setViewAnimating("listasTags");

                    } catch (IOException e) {
                        e.printStackTrace();
                        textoError.setText("Error al cargar la nueva vista");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    textoError.setText("URL no valida");
                }
            }
    }

    public Document constrDoc(){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element datos = doc.createElement("conexion");
            doc.appendChild(datos);
            Element url = doc.createElement("URL");
            url.appendChild(doc.createTextNode(urlfield.getText()));
            datos.appendChild(url);

            Element loc = doc.createElement("Ubicacion");
            loc.appendChild(doc.createTextNode(locationField.getText()));
            datos.appendChild(loc);
            return doc;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    private static void guardDoc(Document doc, File fitxerSortida) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fitxerSortida);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userDir = System.getProperty("user.dir");
        File dataDir = new File(userDir+File.separator+"config");
        File inputFile = new File(dataDir, "CONFIG.XML");
        if (inputFile.exists() ){
            String urlServ = "";
            String loc = "";
            Document doc = parseXML(inputFile);
            NodeList prod = doc.getElementsByTagName("conexion");
            for (int i = 0; i < prod.getLength(); i++) {
                Element productos = (Element) prod.item(i);
                urlServ = productos.getElementsByTagName("URL").item(0).getTextContent();
                loc = productos.getElementsByTagName("Ubicacion").item(0).getTextContent();
            }
            urlfield.setText(urlServ);
            locationField.setText(loc);
        }
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

    public static void connectToServer(String host){
        String protocol = "wss";
        wsClient = UtilsWS.getSharedInstance(protocol + "://" + host+".ieti.site:433");
    
        wsClient.onMessage(inicioContr::wsMessage);
        wsClient.onError(inicioContr::wsError);
    }
    private static void wsMessage(String response) {
        JSONObject msgObj = new JSONObject(response);
        switch (msgObj.getString("type")) {
            case "ping":
                String ping = msgObj.getString("message");
                out.println(ping);
                break;
            case "bounce":
                String msg = msgObj.getString("message");
                out.println(msg);
                break;
            case "products":
                String products = msgObj.getString("message");
                System.out.println(products);
                break;
            case "tags":
                String tags = msgObj.getString("message");
                System.out.println(tags);
                break;
        }
    }
    private static void wsError(String response) {
            String connectionRefused = "Connection was refused";
            Platform.runLater(() -> {
                out.println(connectionRefused);
                wsClient = null;
                out.println("Attempting to reconnect...");
                connectToServer("barretina5.ieti.site:433");
            });
        }
        
}
