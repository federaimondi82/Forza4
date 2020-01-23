package net.studionotturno.Forza4.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import net.studionotturno.Forza4.MainClass;

public class SettingPresenter extends GluonPresenter<MainClass>{

    @FXML
    private View setting;

    public void initialize() {
    	
    	setting.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.SETTINGS.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Setting");
            }
        });
    }

}
