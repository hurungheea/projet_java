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

public class gestionPF extends Application 
{
    private static BCMS bcms;
    
    private static Stage stage;
    private static Parent page;
    private static Scene scene;
    
    public gestionPF() throws Statechart_exception
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
        gestionPF.scene = new Scene(root);
        gestionPF.scene.getStylesheets().add(gestionPF.class.getResource("style.css").toExternalForm());
        gestionPF.stage.setScene(scene);
        gestionPF.stage.show();
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
        gestionPF.page = (Parent) FXMLLoader.load(gestionPF.class.getResource(fxml));
        
        gestionPF.scene = gestionPF.stage.getScene();
        gestionPF.scene = new Scene(gestionPF.page,700,420);
        gestionPF.scene.getStylesheets().add(gestionPF.class.getResource("style.css").toExternalForm());
        
        gestionPF.stage.setScene(gestionPF.scene);
        gestionPF.stage.show();
    }
    
    public static final BCMS getBcms()
    {
        return gestionPF.bcms;
    }
    
    public static final void quit()
    {
        System.exit(0);
    }
}
