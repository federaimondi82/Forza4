package net.studionotturno.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import net.studionotturno.MainClass;
import net.studionotturno.domain.BackConnect.BackEndConnect;
import net.studionotturno.domain.BackConnect.DataController;
import net.studionotturno.domain.BackConnect.Parser;
import net.studionotturno.domain.BackConnect.User;
import net.studionotturno.domain.MainElements.File_Manager;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;



public class SigninPresenter extends GluonPresenter<MainClass> implements Initializable {

	@FXML
	public View signin;
	@FXML
	public Button btnSignin;
	@FXML
	public TextField txtEmail;
	@FXML
	public PasswordField txtPass;
	@FXML
	private Label messaggio;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		signin.setShowTransitionFactory(BounceInRightTransition::new);
        
		signin.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Sign in");
            }
        });
		
	}
		
	
	/**
	 * L'utente effettua l'autenticazione
	 */
	 @FXML
	 public void signin(ActionEvent event) throws InterruptedException {
		//l'email viene controllata anche sul database;se ritorna false vuol dire che l'email e' presente
		boolean b=DataController.getInstance().checkEmail(txtEmail.getText());
		if(b==false) {
			String obj=txtEmail.getText()+":"+DataController.getInstance().cifraPass(txtPass.getText());
			List<Object> list=BackEndConnect.getInstance().restRequest("/users/signin/"+obj+"", "GET");
			//il json di ritorno dal server viene parsato per istanziare il singleton User
			String json=(String)list.get(0);
			Parser.getInstance().parseUser(json);
		}
		//messaggio.setText("Bentornato "+User.getInstance().getName());
		//Thread.sleep(2000);
		changePage();

	}

	/**
	 * Consente di spostare la pagina in seguito all'autenticazione
	 * Se c'e' una chiamata REST che ritorna una dato valido allora l'applizione si sposta sul gioco
	 * altrimenti si posta sulla pagina di registrazione
	 */
	private void changePage() {

		if(User.getInstance().getId()==0) {
			//l'utente non e' registrato quindi si deve registrare
			AppViewManager.SIGNUP_VIEW.switchView();
		}else {
			//l'utente e' registrato
			File_Manager.getInstance().updateCache();
			AppViewManager.GAME_VIEW.switchView();

		}
	}


}
