package java_projet.Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ashx
 */
public class ConnectionPageController implements Initializable {

    @FXML
    private Button copsBtn;
    @FXML
    private Button fmBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void copConnection(MouseEvent event)
    {
        System.out.println("i'm a policeman");
    }

    @FXML
    private void fireConnection(MouseEvent event)
    {
        System.out.println("i'm a fireman");
    }
    
}
