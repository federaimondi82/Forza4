package net.studionotturno;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.studionotturno.domain.MainElements.File_Manager;
import net.studionotturno.views.AppViewManager;

/**
 * Classe principale per l'avvio dell'applicazione
 */
public class MainClass extends MobileApplication {

    @Override
    public void init() {
		try {
			File_Manager.getInstance().loadComponents();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | IOException e) {
			e.printStackTrace();
		}
    	AppViewManager.registerViewsAndDrawer(this);   	
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
        
        ((Stage) scene.getWindow()).getIcons().add(new Image(MainClass.class.getResourceAsStream("/icon.png")));
        
        //creazione del file di testo di supporto per la cache
        File_Manager.getInstance().directoryCreator();
        File_Manager.getInstance().fileCreator();
        
        loadPage();
        //AppViewManager.SETTINGS.switchView();
    	
    }

	/**
	 * Viengono controllati i dati della cache per caricare la pagine iniziale giusta
	 */
	private void loadPage() {
		boolean a=File_Manager.getInstance().checkUser();
    	if(a==false) {
    		AppViewManager.SPLASH.switchView();
    	}
    	else {
    		AppViewManager.GAME_VIEW.switchView();
    	}
		
	}
	
}
