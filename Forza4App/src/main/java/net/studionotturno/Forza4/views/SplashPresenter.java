package net.studionotturno.Forza4.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.studionotturno.Forza4.MainClass;

public class SplashPresenter extends GluonPresenter<MainClass>{

	@FXML
	public View splash;
	@FXML
	public Button btnSignup,btnSignin;

	public void initialize() {

		splash.showingProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				AppBar appBar = MobileApplication.getInstance().getAppBar();
				appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
						MobileApplication.getInstance().getDrawer().open()));
				appBar.setTitleText("Forza 4 Mobile");
			}
		});
	}
	
	public void showSignin() {
		AppViewManager.SIGNIN_VIEW.switchView();
	}
	
	public void showSignup() {
		AppViewManager.SIGNUP_VIEW.switchView();
	}

	@FXML
	public void buttonClick(){}
}
