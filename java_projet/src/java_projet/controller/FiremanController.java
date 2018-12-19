/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_projet.controller;

import com.FranckBarbier.Java._BCMS.BCMS;
import com.pauware.pauware_engine._Exception.Statechart_exception;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.log4j.Layout;

import java_projet.model.JavaOutils;
import java_projet.model.GestionPF;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import java_projet.model.JavaOutils;
import javafx.geometry.Insets;

/**
 * FXML Controller class
 *
 * @author alexiz
 */
public class FiremanController implements Initializable
{
    private final BCMS bcms;
    private int nbFireTrucks;
    private List<String> listFTruck;
    
    @FXML
    private HBox choosenRoadLayout;
    @FXML
    private Button backBtn;
    @FXML
    private Label topLabel;
    @FXML
    private ChoiceBox<String> select;
    @FXML
    private TextArea maConsole;
    @FXML
    private Insets x1;
    @FXML
    private Button agreeButton;
    @FXML
    private Button disagreeButton;
    
    public FiremanController()
    {
        this.bcms = GestionPF.getBcms();
        this.nbFireTrucks = 0;
        try
        {
            this.listFTruck = this.bcms.get_fire_trucks();
        }
        catch(SQLException err)
        {   
            JavaOutils.getInstance().afficheMaConsole(maConsole,"La connexion à la base de donnée est rompue, \n contactez votre administrateur reseau ");
            this.listFTruck = Collections.emptyList();
        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    	this.choosenRoadLayout.setDisable(true);
        maConsole.setEditable(false);
        for(int i=1;i<= this.listFTruck.size();i++)
        {
            select.getItems().add(String.valueOf(i));
        }
                    
        ReadOnlyObjectProperty<String> selectedItemProperty = select.getSelectionModel().selectedItemProperty();
                selectedItemProperty.addListener((obs,old,newValue) ->
                {
                	this.nbFireTrucks = Integer.parseInt(newValue);
                    select.setDisable(true);
                    try
                    {
                        this.sendFireTrucks();
                        this.bcms.route_for_fire_trucks();
                        JavaOutils.getInstance().afficheMaConsole(maConsole,"Please confirm the route to continue\n");
                        
                    }
                    catch(Statechart_exception ex)
                    {
                        JavaOutils.getInstance().logger.fatal(ex);
                    }
                    this.choosenRoadLayout.setDisable(false);
                });
    }

    private void agreeOrNot(boolean isAgree) throws Statechart_exception
    {
    	if (isAgree)
    	{    
    		this.disagreeButton.setDisable(true);
    		this.agreeButton.setDisable(true);
    		this.bcms.FSC_agrees_about_fire_truck_route();
    		this.dispatchedFireTrucks();
            this.breakdownFireTrucks();
            this.arrivedFireTruck();
            this.bcms.close();
    	}
    	else
    	{
    		this.bcms.FSC_disagrees_about_fire_truck_route();
    		this.bcms.route_for_fire_trucks();
    		JavaOutils.getInstance().afficheMaConsole(maConsole,"Please confirm the new path to continue\n");
    	}   
    }
    
    @FXML
    private void agreeTheRoad() throws Statechart_exception
    { 
    	agreeOrNot(true);
    	disagreeButton.setDisable(true);
    }
    @FXML
    private void disagreeTheRoad() throws Statechart_exception
    {
    	agreeOrNot(false);
    }
    
    private void sendFireTrucks( ) throws Statechart_exception
    {
		this.bcms.FSC_connection_request();
        this.bcms.state_fire_truck_number(this.nbFireTrucks);
            this.listFTruck.stream().limit(this.nbFireTrucks).forEach(
                elt ->
                { 
                    JavaOutils.getInstance().afficheMaConsole(maConsole,"Idle " + elt);
                    JavaOutils.getInstance().logger.info("Idle " + elt);
                });
            JavaOutils.getInstance().afficheMaConsole(maConsole,"");//saute une ligne dans la console

    }
    
    private void breakdownFireTrucks( )
    {
        this.listFTruck.stream().limit(this.nbFireTrucks).forEach(
            elt ->
            {
                try
                {
                    this.bcms.get_fire_trucks(BCMS.Status.Breakdown);
                    this.bcms.fire_truck_breakdown(elt,"");
                    JavaOutils.getInstance().logger.info(BCMS.Status.Breakdown + ": " + elt);
                    JavaOutils.getInstance().afficheMaConsole(maConsole,BCMS.Status.Breakdown + ": " + elt);
                }
                catch(Statechart_exception | SQLException ex)
                {
                    JavaOutils.getInstance().logger.fatal(ex);
                }
            }
        );
        JavaOutils.getInstance().afficheMaConsole(maConsole,"");//saute une ligne dans la console
    }
    
    private void dispatchedFireTrucks( )
    {
         this.listFTruck.stream().limit(this.nbFireTrucks).forEach(
            elt ->
            {
                try
                {
                    this.bcms.fire_truck_dispatched(elt);
                    this.bcms.get_fire_trucks(BCMS.Status.Dispatched);
                    JavaOutils.getInstance().afficheMaConsole(maConsole,BCMS.Status.Dispatched + ": " + elt);
                }
                catch(Statechart_exception|SQLException ex)
                {
                    JavaOutils.getInstance().logger.fatal(ex);
                }
            });
        JavaOutils.getInstance().afficheMaConsole(maConsole,"");//saute une ligne dans la console
    }
    
    private void arrivedFireTruck() 
    {
        this.listFTruck.stream().limit(this.nbFireTrucks).forEach(
            elt ->
            {
                try
                {
                    this.bcms.fire_truck_arrived(elt);
                    this.bcms.get_fire_trucks(BCMS.Status.Arrived);
                    JavaOutils.getInstance().logger.info(BCMS.Status.Arrived + ": " + elt);
                    JavaOutils.getInstance().afficheMaConsole(maConsole,BCMS.Status.Arrived + ": " + elt);
                }
                catch(Statechart_exception | SQLException ex)
                {
                    JavaOutils.getInstance().logger.fatal(ex);
                }

            }
        );
    }
    
    @FXML
    private void quitApp() throws Statechart_exception
    {
        this.bcms.close();
        GestionPF.quit();
    }
    
    @FXML
    private void backHome(MouseEvent event) throws IOException,Statechart_exception
    {
        this.bcms.close();
        GestionPF.changeScene(JavaOutils.getInstance().file.get("connectionPage"),true);
    }    

}
