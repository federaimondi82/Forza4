package net.studionotturno.Forza4.domain.PlayerFactory;

import net.studionotturno.Forza4.domain.MainElements.Hole;
import net.studionotturno.Forza4.domain.MainElements.Match;
import net.studionotturno.Forza4.domain.MainElements.Token;
import net.studionotturno.Forza4.domain.Strategy.Strategy;
import net.studionotturno.Forza4.domain.Strategy.StrategyRegister;

import java.util.*;

/**
 * Classe per un giocatore bot, in fase di creazione del match gli si applica una strategia da applicare durante il gioco
 * @author feder
 *
 */
public class Bot1 implements Player{

	private String name;
	private List<Token> tokens;
	private List<Hole> foriFull;

	private Strategy strategy;


	public Bot1(String name) {
		this.name=name;
		this.tokens=new ArrayList<Token>();
		this.foriFull=new ArrayList<Hole>();
		createToken();
	}



	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void createToken() {
		for(int i=0;i<25;i++) {
			this.tokens.add(new Token(this.name));
		}
	}

	@Override
	public List<Token> getToken() {
		return this.tokens;
	}

	@Override
	public synchronized void takeTurn() {
		//viene scelto l'ultimo token
		Token t =this.tokens.get(this.tokens.size()-1);

		//viene scelta una delle colonne che non sono piene
		ArrayList<Hole> col=strategy.getColChoosed(this);

		Hole f=col.get(0);

		//posizionamento del token nel foro
		f.setToken(t);
		//il foro viene aggiunto in una lista per ricordare i fori con token di questo giocatore
		this.addHole(f);

		//viene inviato al subject(in questo caso il Match) il dato dell'utimo foro inserito
		//per consentire un aggiornamento dell'interfaccia
		Match.getInstance().setLastForo(f);

		//aggiornamento dei fori nella colonna
		Match.getInstance().getBoard().addToken(col);

		//viene rimosso l'ultimo token
		this.tokens.remove(this.tokens.size()-1);

	}


	@Override
	public void addHole(Hole f) {
		this.foriFull.add(f);

	}

	@Override
	public List<Hole> getFullHoles() {
		return this.foriFull;
	}

	@Override
	public void setStrategy(Strategy strategy) {
		this.strategy=strategy;
	}

	@Override
	public void setStrategy(String strategy) {
		this.strategy= StrategyRegister.getInstance().getStategy(strategy);
	}

	@Override
	public Strategy getStategy() {
		return this.strategy;
	}
}