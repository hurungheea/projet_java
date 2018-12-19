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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_projet.model.JavaOutils;
import java_projet.model.GestionPF;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Alexis
 */
public class PolicemanController implements Initializable
{
    private final BCMS bcms;
    private int nbPoliceTrucks;
    private final List<String> listPVehicles;
    @FXML
    private HBox choosenRoadLayout;
    @FXML
    private Insets x1;
    @FXML
    private Label topLabel;
    @FXML
    private ChoiceBox<String> select;
    @FXML
    private TextArea maConsole;
    @FXML
    private Button backBtn;
    @FXML
    private Button disagreeButton;
    @FXML
    private Button agreeButton;
    
    public PolicemanController() throws SQLException
    {
        this.bcms = GestionPF.getBcms();
        this.nbPoliceTrucks = 0;
        this.listPVehicles = this.bcms.get_police_vehicles();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    	this.choosenRoadLayout.setDisable(true);
        maConsole.setEditable(false);
        for(int i=1;i<=this.listPVehicles.size();i++)
        {
            select.getItems().add(String.valueOf(i));
        }
         ReadOnlyObjectProperty<String> selectedItemProperty = select.getSelectionModel().selectedItemProperty();
                selectedItemProperty.addListener((obs,old,newValue) -> 
                {
                	this.nbPoliceTrucks = Integer.parseInt(newValue);
                    select.setDisable(true);
                    try
                    {
                        this.sendPoliceTruck();
                        this.bcms.route_for_police_vehicles();
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
    		this.bcms.FSC_agrees_about_fire_truck_route();//PSC_agrees_about_fire_truck_route n'existe pas 
    		this.dispatchedPoliceTrucks();
            this.breakdownFireTrucks();
            this.arrivedFireTruck();
            this.bcms.close();
    	}
    	else
    	{
    		this.bcms.FSC_disagrees_about_fire_truck_route();//PSC_disagrees_about_fire_truck_route n'existe pas non plus
    		this.bcms.route_for_police_vehicles();
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
    
    private void sendPoliceTruck() throws Statechart_exception
    {
    	this.bcms.PSC_connection_request();
    	this.bcms.state_police_vehicle_number(this.nbPoliceTrucks);
        this.listPVehicles.stream().limit(this.nbPoliceTrucks).forEach(
            elt ->
            { 
                JavaOutils.getInstance().afficheMaConsole(maConsole,"Idle " + elt);
                JavaOutils.getInstance().logger.info("Idle " + elt);
            });
        JavaOutils.getInstance().afficheMaConsole(maConsole,"");//saute une ligne dans la console
    }
    
    private void dispatchedPoliceTrucks() throws Statechart_exception
    {
        this.listPVehicles.stream().limit(this.nbPoliceTrucks).forEach(
        elt ->
        {
            try
            {
                this.bcms.police_vehicle_dispatched(elt);
                this.bcms.get_police_vehicles(BCMS.Status.Dispatched);  
                JavaOutils.getInstance().afficheMaConsole(maConsole,BCMS.Status.Dispatched + ": " + elt);
            }
            catch(Statechart_exception | SQLException ex)
            {
                JavaOutils.getInstance().logger.fatal(ex);
            }
        });
        JavaOutils.getInstance().afficheMaConsole(maConsole,"");//saute une ligne dans la console
    }
    
    private void breakdownFireTrucks() throws Statechart_exception
    {
        this.listPVehicles.stream().limit(this.nbPoliceTrucks).forEach(
        elt ->
        { 
            try
            {
            	this.bcms.get_police_vehicles(BCMS.Status.Breakdown);
                this.bcms.police_vehicle_breakdown(elt,"");
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
    
    private void arrivedFireTruck() throws Statechart_exception
    {
        this.listPVehicles.stream().limit(this.nbPoliceTrucks).forEach(
            elt ->
            {
                JavaOutils.getInstance().logger.info(BCMS.Status.Arrived + ": " + elt);
                JavaOutils.getInstance().afficheMaConsole(maConsole,BCMS.Status.Arrived + ": " + elt);                              
            }
        );
    }
        
    @FXML
    private void backHome(MouseEvent event) throws IOException, Statechart_exception
    {
        this.bcms.close();
        GestionPF.changeScene(JavaOutils.getInstance().file.get("connectionPage"),true);
    }

    @FXML
    private void quitApp(MouseEvent event) throws Statechart_exception
    {
        this.bcms.close();
        GestionPF.quit();
    }
}