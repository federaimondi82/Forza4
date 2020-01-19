package net.studionotturno.domain.MainElements;

import java.util.ArrayList;
import java.util.List;

import net.studionotturno.domain.PlayerFactory.Player;


/**
 * Classe per la gestione delle combinazioni dei gettoni;
 * Con l'idea che un gettone ha potenzialmente otto gettoni vicini viene testata la loro vicinanza
 *
 * E' un Singleton e quindi richiamabile in qualsisi classe del progetto
 * @author feder
 *
 */
public class Comb {

	/**
	 * Istanza per questo Singleton
	 */
	private static Comb instance;
	/**
	 * una lista per tenera traccia dei fori intorno ad un foro predeterminato
	 */
	private List<Integer> combo;
	/**
	 * Istanza per la determinazione del vincitore
	 */
	private Winner<Player,List<Hole>> winner;

	/**
	 * Costruttore
	 */
	private Comb() {
		this.combo=new ArrayList<Integer>();
		this.combo.add(7);this.combo.add(-7);
		this.combo.add(1);this.combo.add(-1);
		this.combo.add(6);this.combo.add(-6);
		this.combo.add(8);this.combo.add(-8);
	}


	/**
	 * Metodo per accedere al Singleton
	 * @return l'istanza di questo singleton
	 */
	public static Comb getInstance() {
		if(instance==null) instance=new Comb();
		return instance;
	}

	public List<Integer> getCombos(){
		return this.combo;
	}


	/**
	 * per ogni combinazione
	 * per ogni foro pieno del player
	 * controlla se ci sono altri token di quel giocatore
	 * riempie una lista e se la lista è di 4 token allora abbiamo un vincitore
	 * @param player
	 * @return true se vi e' un vincitore, altrimenti false
	 */
	public boolean controlla(Player player) {

		for(Hole hole : player.getFullHoles()) {
			for(Integer comb : combo) {
				Hole h=hole;
				int count=0;
				ArrayList<Hole> vicini=new ArrayList<Hole>();
				vicini.add(h);
				do {
					Hole newHole=test(h,player,comb);
					if(newHole!=null) {
						h=newHole;
						vicini.add(h);
					}
					count++;
				}while(count<3);
				if(vicini.size()==4) {
					this.winner=new Winner<Player, List<Hole>>(player, vicini);
					return true;
				}
			}
		}
		return false;
	}


	private Hole test(Hole hole,Player player,int n) {
		Hole next=Match.getInstance().getBoard().getHole(hole.getId()+n);

		if(next!=null && next.getToken()!=null && next.getToken().getId().equals(player.getName()))
			return next;
		return null;
	}


	public Winner<Player,List<Hole>> getWinner(){
		return this.winner;
	}
}
