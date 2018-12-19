package java_projet.controller;

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

import org.apache.log4j.Level;

import java_projet.model.GestionPF;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java_projet.model.JavaOutils;

/**
 * FXML Controller class
 *
 * @author ashx
 */
public class ConnectionPageController implements Initializable
{
	private final String[] logValues = {"All","Debug","Infos","Warn","Error","Fatal","Off","Trace"};
    @FXML
    private Button copsBtn;
    
    @FXML
    private Button fmBtn;
    
    @FXML
    private HBox logLayout;
    
    @FXML
    private Label logLabel;
    @FXML
    private ChoiceBox<String> selectLog;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    	this.logLayout.setVisible(false);
    	logLayout.toFront();
    	ObservableList<String> obsLogList = FXCollections.observableArrayList(logValues);
    	selectLog.setItems(obsLogList);
    	    	
    }
    
    @FXML
    private void displayLogLayout()
    {
    	TranslateTransition translateLog = new TranslateTransition();
    	if(this.logLayout.isVisible())
    	{
    		this.logLayout.setVisible(false);
    		this.logLayout.setTranslateY(-2.0);
    	}    		
    	else
    	{
    		this.logLayout.setVisible(true);
    		translateLog.setDuration(Duration.millis(200));
    		translateLog.setNode(logLayout);
    		translateLog.setByY(43);
    		translateLog.play();
    		//selectLog.show();
    	}
    }
    
    @FXML
    private void copConnection(MouseEvent event) throws IOException, Statechart_exception
    {
        GestionPF.changeScene(JavaOutils.getInstance().file.get("policeman"),false);
    }

    @FXML
    private void fireConnection(MouseEvent event) throws IOException, Statechart_exception
    {
        GestionPF.changeScene(JavaOutils.getInstance().file.get("fireman"),false);
    }

    @FXML
    private void quitApp(MouseEvent event)
    {
        GestionPF.quit();
    }
    
}
