package net.studionotturno.Forza4.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import net.studionotturno.Forza4.MainClass;
import net.studionotturno.Forza4.domain.BackConnect.BackEndConnect;
import net.studionotturno.Forza4.domain.BackConnect.DataController;
import net.studionotturno.Forza4.domain.BackConnect.User;

public class SignupPresenter extends GluonPresenter<MainClass>{

    @FXML
    private View signup;
    
    @FXML
	public TextField txtName,txtLastname,txtNickname,txtEmail;
    
    @FXML
    public PasswordField txtPass;
	
	@FXML
	public Label message;
	
	 @FXML
    private Button btnSignup,btnFeedback;
	    
	
	private boolean showPass;
	private String pass;

    public void initialize() {
    	
    	showPass=false;
    	
    	signup.setShowTransitionFactory(BounceInRightTransition::new);
        
    	signup.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Sign up");
            }
        });
    }
    
    
    /**
	 * Un utente decide di  registrarsi
	 */
    @FXML
	public void signup() {
		
		String name=txtName.getText();	String last=txtLastname.getText();
		String nickname=txtNickname.getText();	String email=txtEmail.getText();
		String pass=txtPass.getText();	String mess=""; boolean check=false;
		
		try {
			//vengono effettuati dei controlli lato client
			boolean e=DataController.getInstance().checkEmail(email);
			boolean n=DataController.getInstance().checkNickname(nickname);
			if(e==false) mess+="Email errata o gia' presente\n";
			if(n==false) mess+="Nickname gia' presente";
			
			message.setText(mess);
			if(message.getText().equals("")) {
				
				String pass2=DataController.getInstance().cifraPass(pass);
				
				User.getInstance().name(name)
				.lastname(last)
				.nickname(nickname)
				.email(email)
				.pass(pass2);
				check= BackEndConnect.getInstance().restRequest("/users/", "POST", User.getInstance().toString());
				if(check==false) message.setText("qualcosa e' adato storto");
				else{
					message.setText("Registrazione effettuata");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					AppViewManager.SIGNIN_VIEW.switchView();
				}
			}
		}catch(IndexOutOfBoundsException e) {
			message.setText("Hai riempito tutti i campi?");
		}
		
		
	}
	
	@FXML
    void sendFeedback(ActionEvent event) {

    }
	
}
