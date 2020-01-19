package net.studionotturno.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.studionotturno.MainClass;
import net.studionotturno.domain.Strategy.Strategy;
import net.studionotturno.domain.Strategy.StrategyRegister;

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
