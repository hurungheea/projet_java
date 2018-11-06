/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_projet;

import com.FranckBarbier.Java._BCMS.BCMS;
import com.pauware.pauware_engine._Exception.Statechart_exception;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.regex.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Border;

/**
 * FXML Controller class
 *
 * @author alexiz
 */
public class PolicemanController implements Initializable
{
    
    private BCMS bcms;
    private int nbPvehicles;
    
    @FXML
    private Label topLabel;
    @FXML
    private ChoiceBox<String> select;
    @FXML
    private Button backBtn;
    
    public PolicemanController() throws SQLException
    {
        this.bcms = gestionPF.getBcms();
        this.nbPvehicles = this.bcms.get_police_vehicles().size();
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        for(int i=1;i<= this.nbPvehicles;i++)
        {
            select.getItems().add(String.valueOf(i));
        }
    }    

     @FXML
    private void quitApp()
    {
        gestionPF.quit();
    }
    
    @FXML
    private void backHome(MouseEvent event) throws IOException
    {
        gestionPF.changeScene("connectionPage.fxml");
    }

}