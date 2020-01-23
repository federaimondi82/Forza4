package net.studionotturno.Forza4.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.studionotturno.Forza4.MainClass;
import net.studionotturno.Forza4.Observer.HoleUI;
import net.studionotturno.Forza4.Observer.Observer;
import net.studionotturno.Forza4.Observer.Subject;
import net.studionotturno.Forza4.domain.PlayerFactory.Bot1Factory;
import net.studionotturno.Forza4.domain.PlayerFactory.PlayerRegistry;
import net.studionotturno.Forza4.domain.MainElements.File_Manager;
import net.studionotturno.Forza4.domain.MainElements.Hole;
import net.studionotturno.Forza4.domain.MainElements.Match;
import net.studionotturno.Forza4.domain.MainElements.Mediator;
import net.studionotturno.Forza4.domain.MainElements.Winner;
import net.studionotturno.Forza4.domain.PlayerFactory.Player;
import net.studionotturno.Forza4.domain.Strategy.StrategyRegister;

//import net.studionotturno.Forza4.views.AppViewManager.SIGNIN_VIEW;


public class GamePresenter extends GluonPresenter<MainClass> implements Observer {

	@FXML
	public View game;

	@FXML
	public GridPane grid_0;

	@FXML
	private Button btnBotMe,btnMeBot,btnWithFriend,btnBotBot;
	
	@FXML
	private Label lblWinner;

	@FXML
	private Spinner spinner;

	/**
	 *mappa dei fori, che sono anche i pulsanti per l'inserimento dei token;identificano le colonne
	 */
	private HashMap<Integer, HoleUI> buttonMap;

	/**
	 * mappa dei fori dlle gui associti a quelli della business logic
	 */
	private Map<Hole,HoleUI> mapper;

	/**
	 * Istanza per la gestione dell'identificazione del vincitore
	 */
	private Winner<Player, List<Hole>> winner;

	ExecutorService exec;

	/**
	 * Inizializza la View con un menu, aggancia la view con la business logic
	 */
	public void initialize() {
		game.showingProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				AppBar appBar = MobileApplication.getInstance().getAppBar();
				appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
						MobileApplication.getInstance().getDrawer().open()));
				appBar.setTitleText("Game");
			}
		});


		//aggancia il subject(Match) all'observer
		Match.getInstance().addObserver(this);
		setComponents();
	}

	/**
	 * Permette di impostare una nuova partita in base all'input
	 * 0- bot contro umano
	 * 1- umano contro bot
	 * 2- umano contro umano
	 * @param i un indice per scegliere in che modo giocare
	 * @return un intero per sapere se la configurazione scelta esiste, se non esiste ritorna -1
	 */
	public int setupGame(int i) {
		switch(i) {
			case 0:{
				Match.getInstance().setPlayers(setBotLevel(),
						PlayerRegistry.getInstance().getPlayer("real").createPlayer());
				return 0;
			}
			case 1:{
				Match.getInstance().setPlayers(
						PlayerRegistry.getInstance().getPlayer("real").createPlayer(),
						setBotLevel());
				return 1;
			}
			case 2:{
				Match.getInstance().setPlayers(
						PlayerRegistry.getInstance().getPlayer("real").createPlayer(),
						PlayerRegistry.getInstance().getPlayer("real").createPlayer());
				return 2;
			}
			case 3:{
				Match.getInstance().setPlayers(setBotLevel(),setBotLevel());
				return 3;
			}
			default : return -1;
		}
	}

	/**
	 * Permette di impostare la strategia che il bot deve adottare
	 * @return il bot
	 * @see Bot1Factory
	 * @see PlayerRegistry
	 */
	public Player setBotLevel() {
		Player bot=PlayerRegistry.getInstance().getPlayer("bot1").createPlayer();
		String level=(String)spinner.getValue();
		bot.setStrategy(level);
		return bot;
	}

	/**
	 * Inizializza i componenti dell'interfaccia grafica e li
	 * connette con quelli delle business logic
	 */
	public void setComponents() {

		this.exec=Executors.newCachedThreadPool();
		this.mapper=new HashMap<Hole,HoleUI>();
		this.buttonMap=new HashMap<Integer,HoleUI>();

		//assegnamento dei componenti HoleUI come fori sulla View
		Match.getInstance().getBoard().getHoles().stream().forEach(foro->{
			HoleUI h=new HoleUI();

			GridPane.setHalignment(h, HPos.CENTER);
			GridPane.setValignment(h, VPos.CENTER);

			this.grid_0.add(h, foro.getCol(), foro.getRow());
			this.mapper.put(foro, h);
		});

		setDifficultComponent();
		enableButtons();

	}

	/**
	 * Imposta lo spinner con i valori delle strategie disponibili
	 */
	private void setDifficultComponent() {
		SpinnerValueFactory<String> livel2 = new SpinnerValueFactory
				.ListSpinnerValueFactory<String>(FXCollections.observableList(StrategyRegister.getInstance().getStrategiesNames()));

		spinner.setValueFactory(livel2);
	}

	/**
	 * Imposta gli handler sui fori della gui; rende i fori della prima riga qui pulsanti
	 * per la selezione della colonna dove inserire i token
	 */
	private void setsButtonHandlers() {

		//prende i fori delle prima riga e li inserisce in "buttonMap"
		this.mapper.entrySet().stream().filter(hole->{
			return hole.getKey().getRow()==0;
		}).forEach(holeUI->{
			this.buttonMap.put(holeUI.getKey().getId(), holeUI.getValue());
		});

		//aggiunta degli handler ai fori della prima riga
		for(Entry<Integer, HoleUI> hole : this.buttonMap.entrySet()) {
			hole.getValue().setOnMouseClicked((event)->{
				System.out.println("qui");
				try {
					Mediator.getInstance().add(new AtomicInteger(hole.getKey()));
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			});
		}
	}

	/**
	 * Consente di abilitare i pulsanti per l'avvio del gioco
	 */
	public void enableButtons() {
		btnBotMe.setDisable(false);
		btnMeBot.setDisable(false);
		btnWithFriend.setDisable(false);
		btnBotBot.setDisable(false);
	}


	/**
	 * Per iniziare una partita dove il primo giocatore e' il bot
	 */
	@FXML
	public void botMe() {
		setButtonsToStart();
		setupGame(0);
		this.exec.execute(avvia);
	}

	@FXML
	public void playBotBot(){
		setButtonsToStart();
		setupGame(3);
		this.exec.execute(avvia);
	}

	/**
	 * Per iniziare una partita dove il primo giocatore e' l'utente
	 */
	@FXML
	public void meBot() {
		setButtonsToStart();
		setupGame(1);
		this.exec.execute(avvia);
	}

	/**
	 * Per iniziare una partita tra due utenti reali
	 */
	@FXML
	public void playWithFriend() {
		setButtonsToStart();
		setupGame(2);
		this.exec.execute(avvia);
	}

	/**
	 * Imposta i pulsanti prima dell'avvio della partita
	 */
	private void setButtonsToStart() {
		btnBotMe.setDisable(true);
		btnMeBot.setDisable(true);
		btnWithFriend.setDisable(true);
		btnBotBot.setDisable(true);

		setsButtonHandlers();
	}

	Runnable avvia=()->{
		Match.getInstance().playMatch();
	};

	/**
	 * Controlla se le colonne sono piene, se lo sono disabilita il pulsante relativo a quell colonna
	 */
	private void disableButton() {
		//scorre la riga in alto per controllare se ci sono token
		//se ci sono cerca il pulsante relativo e lo disabilita
		Match.getInstance().getBoard().getRowsList(5)
				.stream().filter(hole->!hole.isEmpty()).forEach(hole->{
			//blocca i pulsanti
			this.buttonMap.entrySet().stream().filter(btn->{
				return btn.getKey()==hole.getCol();
			}).forEach(btn2->{
				btn2.getValue().setDisable(true);
			});
		});
	}

	/**
	 * COnsente di eliminare l'handler dai pulsanti per la scelta della colonna
	 */
	private void disableHoleUIButton() {
		this.buttonMap.entrySet().forEach(el->{
			el.getValue().setOnMouseClicked(null);
		});

	}

	@Override
	public void update(Subject subject) {
		disableButton();
		Platform.runLater(repaint);
	}

	Runnable repaint=()->{
		HoleUI r=this.mapper.get(Match.getInstance().getLastForo());

		if(Match.getInstance().getPseudoRound()%2==0)r.setColor(Color.YELLOW);
		else r.setColor(Color.RED);
		r.setSelected(true);
	};

	@Override
	public void updateWinner(Subject subject,Winner<Player, List<Hole>> winner) {
		this.winner=winner;
		Platform.runLater(repaintWinner);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	Runnable repaintWinner=()->{
		Player p=this.winner.getPlayer();
		List<Hole> list=this.winner.getList();

		list.stream().forEach(foro->{
			HoleUI r=(HoleUI)this.mapper.entrySet().stream().filter(element->{
				return element.getKey().getCol()==foro.getCol() && element.getKey().getRow()==foro.getRow();
			}).limit(1).collect(Collectors.toList()).get(0).getValue();

			r.setWinnerColor(r.getColor());
		});

		lblWinner.setText("The Winner is: "+p.getName());

		File_Manager.getInstance().updateLocalData(p);

		/*cancellComponent();
		Match.getInstance().reStart();
		Match.getInstance().addObserver(this);
		setComponents();*/
		resetGame();
	};

	@Override
	public void resetGame() {
		Platform.runLater(resetGame);

	}

	Runnable resetGame=()-> {
		cancellComponent();
		Match.getInstance().reStart();
		Match.getInstance().addObserver(this);
		setComponents();
	};
	/**
	 *Consente di azzerre le strutture dati e i compinenti grafici
	 */
	private void cancellComponent() {
		this.exec.shutdown();
		this.exec=null;
		this.mapper=null;
		disableHoleUIButton();
		this.buttonMap=null;
	}
}
