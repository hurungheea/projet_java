package java_projet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.FranckBarbier.Java._BCMS.BCMS;
import com.pauware.pauware_engine._Exception.Statechart_exception;

/**
 *
 * @author achantelou
 */

public class Java_projet extends Application 
{
    private static BCMS bcms;
    
    private static Stage stage;
    private static Parent page;
    private static Scene scene;
    
    public Java_projet() throws Statechart_exception
    {
        stage = new Stage();
        bcms = new BCMS();
        bcms.start();
    }
    
    /**
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("connectionPage.fxml"));
        Java_projet.scene = new Scene(root);
        Java_projet.scene.getStylesheets().add(Java_projet.class.getResource("style.css").toExternalForm());
        Java_projet.stage.setScene(scene);
        Java_projet.stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
    /**
     * @param fxml
     * @throws IOException 
     */
    public static final void changeScene(String fxml) throws IOException
    {
        Java_projet.page = (Parent) FXMLLoader.load(Java_projet.class.getResource(fxml));
        
        Java_projet.scene = Java_projet.stage.getScene();
        Java_projet.scene = new Scene(Java_projet.page,700,420);
        Java_projet.scene.getStylesheets().add(Java_projet.class.getResource("style.css").toExternalForm());
        
        Java_projet.stage.setScene(Java_projet.scene);
        Java_projet.stage.show();
    }
    
    public static final BCMS getBcms()
    {
        return Java_projet.bcms;
    }
    
    public static final void quit()
    {
        System.exit(0);
    }
}
