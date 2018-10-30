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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.regex.*;
import javafx.scene.layout.Border;

/**
 * FXML Controller class
 *
 * @author alexiz
 */
public class FiremanController implements Initializable
{
    private BCMS bcms;

    @FXML
    private Button backBtn;
    @FXML
    private TextField fTruckInput;
    @FXML
    private Label topLabel;
    @FXML
    private Label textFieldErrorMessage;
    @FXML
    private Button manyBtn;
    
    public FiremanController()
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
    private void valideInt(MouseEvent event) throws IOException, Statechart_exception, InterruptedException
    {
        Pattern p = Pattern.compile("[0-9]{2}|[0-9]{1}");
        Matcher m = p.matcher(fTruckInput.getText());
        
        if(!(fTruckInput.getText().length() > 0 && m.matches()))
        {
            fTruckInput.getStyleClass().add("inputException");
        }
        else
        {
            fTruckInput.getStyleClass().add("textField");
            bcms.state_fire_truck_number(Integer.parseInt(fTruckInput.getText()));
            manyBtn.setDisable(true);
            bcms.route_for_fire_trucks();
            bcms.FSC_agrees_about_fire_truck_route();
            bcms.route_for_fire_trucks();
            bcms.FSC_agrees_about_fire_truck_route();
            Thread.sleep(100);
        }
    }    
    @FXML
    private void quitApp()
    {
        Java_projet.quit();
    }
    
    @FXML
    private void backHome(MouseEvent event) throws IOException
    {
        Java_projet.changeScene("connectionPage.fxml");
    }    
}
