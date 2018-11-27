package java_projet.model;

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
import org.apache.log4j.BasicConfigurator;

public class GestionPF extends Application 
{
    private static BCMS bcms;
        
    private static Stage stage;
    private static Parent page;
    private static Scene scene;
    
    public GestionPF() throws Statechart_exception
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
        Parent root = FXMLLoader.load(getClass().getResource(JavaOutils.getInstance().file.get("connectionPage")));
        GestionPF.scene = new Scene(root);
        GestionPF.scene.getStylesheets().add(GestionPF.class.getResource(JavaOutils.getInstance().file.get("style")).toExternalForm());
        GestionPF.stage.setResizable(false);
        GestionPF.stage.setScene(scene);
        GestionPF.stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        launch(args);
    }
    
    /**
     * @param fxml
     * @throws IOException 
     */
    public static final void changeScene(String fxml) throws IOException
    {
        GestionPF.page = (Parent) FXMLLoader.load(GestionPF.class.getResource(fxml));
        
        GestionPF.scene = GestionPF.stage.getScene();
        GestionPF.scene = new Scene(GestionPF.page,700,420);
        GestionPF.scene.getStylesheets().add(GestionPF.class.getResource(JavaOutils.getInstance().file.get("style")).toExternalForm());
        
        GestionPF.stage.setScene(GestionPF.scene);
        GestionPF.stage.show();
    }
    
    public static final BCMS getBcms()
    {
        return GestionPF.bcms;
    }
    
    public static final void quit()
    {
        System.exit(0);
    }
}
