package net.studionotturno.Forza4.domain.PlayerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import net.studionotturno.Forza4.domain.MainElements.Hole;
import net.studionotturno.Forza4.domain.MainElements.Match;
import net.studionotturno.Forza4.domain.MainElements.Token;
import net.studionotturno.Forza4.domain.Strategy.Strategy;

/**
 * Responsabilita' di conoscere i dettagli di un giocatore,
 * come ad esempio il nome ed altro, responsabilita' di conoscere( grasp Expert )
 *
 * utilizza i token, quindi secondo il patter grasp creator è anche il creatore
 *  di token (ogni giocatore ha i suoi)
 *
 *  Ha una visione dei token che ha inserito nella Board, conosce i Fori riempiti con i propri token
 */
public interface Player {


	/**
	 * @return Il nome del giocatore
	 */
	public String getName();

	public void setName(String nome);

	/**
	 * Crea 25 Token per questo giocatore;
	 */
	public void createToken();

	public List<Token> getToken();

	/**
	 * Consente al giocatore di effettuare una mossa
	 * Differentemente da un giocatore relae,un bot aggiunge un token alla board secondo la strategia impostagli ad inizio partita;
	 *
	 * @see Strategy
	 */
	public void takeTurn();

	/**
	 * Aggiunge il foro con un token ad una lista
	 * @param f
	 */
	public void addHole(Hole f);

	/**
	 * Consente di tenere traccia dei fori con un token del giocatore
	 * @return la lista dei fori con un token di questo giocatore
	 */
	public List<Hole> getFullHoles();



	/**
	 * Ritorna il foro dove andra' inserito il token
	 * Filtra tutti i fori della colonna scelta che sono vuoti(non hanno il gettone)<br>
	 * e ritorna il foro più in basso
	 * @param i la colonna scelta dal giocatore
	 * @return il foro che andra' riemito con un gettone
	 */
	default Hole getHoleChoosed(AtomicInteger i) {
		//filtra tutti i fori della colonna scelta
		Predicate<Hole> predColonna=(foro)->{return i.get()==foro.getCol();};
		//filtra tutti i fori della colonna scelta, i quali sono vuoti(non hanno il gettone)
		Predicate<Hole> predRiga=(foro)->{return foro.isEmpty(); };

		Comparator<Hole> comp=(foro1,foro2)->{return foro1.getRow().compareTo(foro2.getRow());};

		Hole f=null;

		while(f==null) {
			try {
				f=Match.getInstance().getBoard().getHoles().stream()
						.filter(predColonna).filter(predRiga).min(comp).get();
			}catch(NullPointerException | NoSuchElementException e) {}
		}

		return f;
	}

	public void setStrategy(Strategy strategy);

	public void setStrategy(String strategy);

	public Strategy getStategy();


}