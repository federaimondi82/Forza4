package net.studionotturno.Forza4;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;

import com.gluonhq.cloudlink.client.user.UserClient;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.studionotturno.Forza4.domain.MainElements.File_Manager;
import net.studionotturno.Forza4.domain.PlayerFactory.Bot1Factory;
import net.studionotturno.Forza4.domain.PlayerFactory.PlayerRegistry;
import net.studionotturno.Forza4.domain.PlayerFactory.RealPlayerFactory;
import net.studionotturno.Forza4.domain.Strategy.Level_1;
import net.studionotturno.Forza4.domain.Strategy.StrategyRegister;
import net.studionotturno.Forza4.views.AppViewManager;

/**
 * Classe principale per l'avvio dell'applicazione
 */
public class MainClass extends MobileApplication {

	@Override
	public void init() {

		if (Platform.isDesktop()) {

			try {
				File_Manager.getInstance().loadComponents();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException | IOException e) {
				e.printStackTrace();
			}
		} else if (Platform.isAndroid() || Platform.isIOS()) {

			try {
				PlayerRegistry.getInstance().registry("bot1", Bot1Factory.class.getConstructor().newInstance());
				PlayerRegistry.getInstance().registry("real", RealPlayerFactory.class.getConstructor().newInstance());

				StrategyRegister.getInstance().addStrategy("level1", Level_1.class.newInstance());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

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
	}

	/**
	 * Viengono controllati i dati della cache per caricare la pagine iniziale giusta
	 */
	private void loadPage() {
		boolean a = File_Manager.getInstance().checkUser();
		if (a == false) {
			AppViewManager.SPLASH.switchView();
		} else {
			AppViewManager.GAME_VIEW.switchView();
		}

	}
}