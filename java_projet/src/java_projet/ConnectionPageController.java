package java_projet;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.FranckBarbier.Java._BCMS.BCMS;
import com.pauware.pauware_engine._Exception.Statechart_exception;
import java.io.IOException;
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
public class ConnectionPageController implements Initializable
{
    private BCMS bcms;

    @FXML
    private Button copsBtn;
    @FXML
    private Button fmBtn;

    public ConnectionPageController()
    {
        this.bcms = Java_projet.getBcms();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void copConnection(MouseEvent event) throws IOException, Statechart_exception
    {
        Java_projet.changeScene("policeman.fxml");
        this.bcms.PSC_connection_request();
    }

    @FXML
    private void fireConnection(MouseEvent event) throws IOException, Statechart_exception
    {
        Java_projet.changeScene("fireman.fxml");
        this.bcms.FSC_connection_request();
    }

    @FXML
    private void quitApp(MouseEvent event)
    {
        System.exit(0);
    }
    
}
