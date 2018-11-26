package java_projet.model;

import com.FranckBarbier.Java._BCMS.BCMS;
import com.sun.prism.paint.Color;
import java.util.HashMap;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;
import java.util.Map;

public class JavaOutils 
{
    private JavaOutils()
    {
        this.file.put("connectionPage","/java_projet/view/connectionPage.fxml");
        this.file.put("fireman","/java_projet/view/fireman.fxml");
        this.file.put("policeman","/java_projet/view/policeman.fxml");
        this.file.put("style","/java_projet/view/style.css");
    }
    
    private static JavaOutils INSTANCE = new JavaOutils();
    public final static Logger logger = Logger.getLogger(GestionPF.class);
    public final Map<String,String> file = new HashMap<>();
    public static JavaOutils getInstance()
    {
        synchronized (JavaOutils.class) // pour le multithreading ! On ne sait jamais
        {
            if(INSTANCE == null)
                INSTANCE = new JavaOutils();
        }
        return INSTANCE;
    }
    
    public String getTruckStatus(BCMS.Status status)
    {
        String res = null;
        switch(status)
        {
            //Dispatched, Arrived, Blocked, Breakdown
            case Dispatched:
                res = "Truck " + BCMS.Status.Dispatched + " :=> ";
                break;
            case Arrived:
                res = "Truck " + BCMS.Status.Arrived + " :=> ";
                break;
            case Blocked:
                res = "Truck " + BCMS.Status.Blocked + " :=> ";
                break;
            case Breakdown:
                res = "Truck " + BCMS.Status.Breakdown + " :=> ";
                break;
        }
        return res;
    }
    
    public void afficheMaConsole(TextArea log,String s)
    {
        String mess = log.getText();
        //s.setFill(Color.RED);
        mess += "\t" + s + "\n";
        log.setText(mess);
    }
}
