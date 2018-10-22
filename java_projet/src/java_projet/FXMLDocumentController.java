/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_projet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author achantelou
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Button btnRedirect;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void caca(MouseEvent event) throws IOException
    {
        Java_projet.page = (Parent) FXMLLoader.load(Java_projet.class.getResource("two.fxml"));
        Java_projet.scene = Java_projet.stage.getScene();
        
        Java_projet.scene = new Scene(Java_projet.page,700,420);
        Java_projet.stage.setScene(Java_projet.scene);
        Java_projet.stage.show();
    }

    
}
