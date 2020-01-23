package net.studionotturno.Forza4.domain.MainElements;

import java.util.ArrayList;
import java.util.List;

import net.studionotturno.Forza4.domain.PlayerFactory.Player;
import net.studionotturno.Forza4.Observer.Observer;
import net.studionotturno.Forza4.Observer.Subject;




/**
 * Ha le informazioni riguardati i Player e la board ( information expert )
 *
 * Deve gestire e memorizzare la board, quindi è il creatore di una Bord e
 * delega la creazione dei fori al costruttore di Board
 *
 * E' un elemento centrale del gioco, un punto di accesso alle informazioni globali.
 *
 * Essendo anche l'elemento che ha più informazioni del gioco, è un buon cadidato per essere un
 * Concrete Subject del pattern Observer; notifica i cambiamenti di stato proveniente dai dati della business Logic.
 *
 * Ad inizio gioco verra' chiesto all'utente quale tipo
 * di View (Concrete Observer) usare, ma il Subject(model o business logic) restera' questa
 *
 */
public class Match implements Subject{

	private static Board board;
	private static List<Player> players;
	private static List<Observer> observers;

	private static int pseudoRound=0;
	private static int round=0;
	private static Hole lastForo;

	private static boolean fine=false;

	private static Match instance;

	/**
	 * Metodo per accedere al Singleton
	 * @return l'istanza di questo singleton
	 */
	public static Match getInstance() {
		if(instance==null) 	instance= new Match();
		return instance;
	}


	/**
	 * Costruttore
	 */
	private Match() {
		players=new ArrayList<Player>();
		board=new Board();
		observers=new ArrayList<Observer>();
	}

	/**
	 * Azzera i componenti della partita: board,contatori,players ecc...
	 */
	public void reStart() {
		instance=null;
		players=null;
		board=null;
		observers=null;
		pseudoRound=0;
		round=0;
		lastForo=null;
		fine=false;
	}

	public void setPlayers(Player player1,Player player2) {
		//this.player1=player1;
		//this.player2=player2;
		this.players.add(player1);
		this.players.add(player2);
	}

	public List<Player> getPlayers(){
		return players;
	}

	public Board getBoard() {
		return board;
	}

	public int getPseudoRound() {
		return pseudoRound;
	}

	public int getRound() {
		return round;
	}

	public void setLastForo(Hole foro) {
		lastForo=foro;
	}

	public Hole getLastForo() {
		return lastForo;
	}

	/*---------------Override metodi Subject----------------------*/

	@Override
	public boolean addObserver(Observer observer) {
		return observers.add(observer);
	}

	@Override
	public boolean removeObserver(Observer observer) {
		return observers.remove(observer);
	}

	@Override
	public List<Observer> getObservers(){
		return observers;
	}

	@Override
	public boolean noTify() {
		observers.stream().forEach(observer->{
			observer.update(this);
		});
		return true;
	}

	@Override
	public void noTifyWinner(Winner<Player, List<Hole>> winner) {
		observers.stream().forEach(observer->{
			observer.updateWinner(this,winner);
		});
	}

	@Override
	public void noTifyResetGame() {
		observers.stream().forEach(observer->{
			observer.resetGame();
		});
	}

	/*---------------ATTIVAZIONE CASO D'USO----------------------*/
	/**
	 * Metodo per avviare il caso d'uso
	 */
	public void playMatch() {
		for(round=0;round<=42 && fine==false;round++) {
			playRound(round);
		}
	}

	private void playRound(int i) {
		for (Player player : players) {
			pseudoRound++;
			player.takeTurn();
			this.noTify();

			try { Thread.sleep(200);} catch (InterruptedException e) {	e.printStackTrace();}

			if(Comb.getInstance().controlla(player)) {
				noTifyWinner(Comb.getInstance().getWinner());
				fine=true;
				this.noTifyResetGame();
				break;
			}
		}
	}

}