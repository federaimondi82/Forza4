package net.studionotturno.Forza4.views;

import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.afterburner.AppViewRegistry;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.afterburner.Utils;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.scene.image.Image;
//import net.studionotturno.File_Manager;
import net.studionotturno.Forza4.MainClass;

import java.util.Locale;

import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.HOME_VIEW;
import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.SHOW_IN_DRAWER;
/**
 * Classe per la creazione e gestione delle pagine
 * @author feder
 *
 */
public class AppViewManager {

	//il registry delle views
    public static final AppViewRegistry REGISTRY = new AppViewRegistry();
   
    private static String name(Class<? extends GluonPresenter<?>> presenterClass) {
        return presenterClass.getSimpleName().toUpperCase(Locale.ROOT).replace("PRESENTER", "");
    }
    
    public static final AppView SPLASH = view("Splash", SplashPresenter.class, MaterialDesignIcon.HOME);
    public static final AppView GAME_VIEW = view("Play Match", GamePresenter.class, MaterialDesignIcon.DASHBOARD,SHOW_IN_DRAWER,HOME_VIEW);
    public static final AppView SIGNUP_VIEW = view("Sign up", SignupPresenter.class, MaterialDesignIcon.DASHBOARD);
    public static final AppView SIGNIN_VIEW = view("Sign in", SigninPresenter.class, MaterialDesignIcon.DASHBOARD);
    public static final AppView SETTINGS = view("Settings", SettingPresenter.class, MaterialDesignIcon.DASHBOARD,SHOW_IN_DRAWER);

    
    private static AppView view(String title, Class<? extends GluonPresenter<?>> presenterClass, MaterialDesignIcon menuIcon, AppView.Flag... flags ) {
        return REGISTRY.createView(name(presenterClass), title, presenterClass, menuIcon, flags);
    }
    
    public static void registerViewsAndDrawer(MobileApplication app) {
    	
    	REGISTRY.getViews().stream().forEach(view->view.registerView(app));

        NavigationDrawer.Header header = new NavigationDrawer.Header("Forza 4 App",
                "Un bel gioco",
               new Avatar(21, new Image(MainClass.class.getResourceAsStream("/icon.png"))));
        
        Utils.buildDrawer(app.getDrawer(), header, REGISTRY.getViews()); 
        
    	
        
    }
    
    

    

    
    
}
