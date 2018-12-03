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
import java_projet.model.JavaOutils;
import java_projet.model.GestionPF;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import java_projet.model.JavaOutils;

/**
 * FXML Controller class
 *
 * @author alexiz
 */
public class FiremanController implements Initializable
{
    private final BCMS bcms;
    private List<String> listFTruck;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Label topLabel;
    @FXML
    private ChoiceBox<String> select;
    @FXML
    private TextArea maConsole;
    
    public FiremanController()
    {
        this.bcms = GestionPF.getBcms();
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
        maConsole.setEditable(false);
        for(int i=1;i<= this.listFTruck.size();i++)
        {
            select.getItems().add(String.valueOf(i));
        }
                    
        ReadOnlyObjectProperty<String> selectedItemProperty = select.getSelectionModel().selectedItemProperty();
                selectedItemProperty.addListener((obs,old,newValue) -> 
                {
                    int nbTruck = Integer.parseInt(newValue);
                    select.setDisable(true);
                    try
                    {
                        this.sendFireTrucks(nbTruck);
                        this.dispatchedFireTrucks(nbTruck);
                        this.breakdownFireTrucks(nbTruck);
                        this.arrivedFireTruck(nbTruck);
                        this.bcms.close();
                    }
                    catch(Statechart_exception ex)
                    {
                        JavaOutils.getInstance().logger.fatal(ex);
                    }
                });
    }

    private void sendFireTrucks(int nbTruck) throws Statechart_exception
    {
        this.bcms.state_fire_truck_number(nbTruck);
            this.listFTruck.stream().limit(nbTruck).forEach(
                elt ->
                { 
                    JavaOutils.getInstance().afficheMaConsole(maConsole,"Idle " + elt);
                    JavaOutils.getInstance().logger.info("Idle " + elt);
                });
            JavaOutils.getInstance().afficheMaConsole(maConsole,"");//saute une ligne dans la console
            this.bcms.route_for_fire_trucks();
            this.bcms.FSC_disagrees_about_fire_truck_route();
            this.bcms.route_for_fire_trucks();
            this.bcms.FSC_agrees_about_fire_truck_route();
    }
    
    private void breakdownFireTrucks(int nbTruck)
    {
        this.listFTruck.stream().limit(nbTruck).forEach(
            elt ->
            {
                try
                {
                    this.bcms.get_fire_trucks(BCMS.Status.Breakdown);
                    JavaOutils.getInstance().logger.info(BCMS.Status.Breakdown + ": " + elt);
                    JavaOutils.getInstance().afficheMaConsole(maConsole,BCMS.Status.Breakdown + ": " + elt);
                }
                catch(SQLException ex)
                {
                    JavaOutils.getInstance().logger.fatal(ex);
                }
            }
        );
    }
    
    private void dispatchedFireTrucks(int nbTruck)
    {
         this.listFTruck.stream().limit(nbTruck).forEach(
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
    
    private void arrivedFireTruck(int nbTruck)
    {
        this.listFTruck.stream().limit(nbTruck).forEach(
            elt ->
            {
                try
                {
                    this.bcms.get_fire_trucks(BCMS.Status.Arrived);
                    JavaOutils.getInstance().logger.info(BCMS.Status.Arrived + ": " + elt);
                    JavaOutils.getInstance().afficheMaConsole(maConsole,BCMS.Status.Arrived + ": " + elt);
                }
                catch(SQLException ex)
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
        GestionPF.changeScene(JavaOutils.getInstance().file.get("connectionPage"));
    }    
}
