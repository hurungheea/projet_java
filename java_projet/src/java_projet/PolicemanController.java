/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_projet;

import com.FranckBarbier.Java._BCMS.BCMS;
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
 * @author alexiz
 */
public class PolicemanController implements Initializable
{
    private BCMS bcms;
    
    public PolicemanController()
    {
        this.bcms = gestionPF.getBcms();
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
    private void backHome(MouseEvent event) throws IOException
    {
        gestionPF.changeScene("connectionPage.fxml");
    }
}