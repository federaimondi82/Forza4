package net.studionotturno.Forza4.domain.MainElements;

import net.studionotturno.Forza4.domain.PlayerFactory.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;



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
	 * per ogni foro pieno del player
	 * per ogni combinazione
	 * controlla se ci sono altri token di quel giocatore e
	 * riempie una lista, se la lista è di 4 token allora abbiamo un vincitore
	 * @param player
	 * @return true se vi e' un vincitore, altrimenti false
	 */
	public boolean controlla(Player player) {

		List<Hole> playerHoles=player.getFullHoles();
		List<Hole> list=new ArrayList<Hole>();

		if(playerHoles.size()>=4) {
			for(int i=0;i< playerHoles.size();i++) {
				Hole t=playerHoles.get(i);

				for(int j=0;j< this.combo.size();j++) {
					int m=this.combo.get(j);

					Hole t2=t;

					//se il foro agiacente (ina mase alla combinazione)
					//è presente, lo aggiunge alla lista
					while(playerHoles.contains(t2) && t2!=null) {
						list.add(t2);
						if(list.size()==4) {
							this.winner=new Winner<Player, List<Hole>>(player, list);
							return true;
						}
						else t2=testOther(t2, player, m);
					}
					list.clear();
				}
			}
		}
		return false;
	}


	/**
	 * Recupera il foro che dvrebbe far parte della combinazione in base a:
	 * quello preso in esame più la combinazione; in più viene cercata una anomalia
	 * nella configurazione di 4 in fila.
	 * @param hole
	 * @param player
	 * @param m
	 * @return
	 */
	private Hole testOther(Hole hole,Player player,int m) {
		Hole x=null;
		try{
			Predicate<Hole> pred=(foro)-> foro.getId()==hole.getId()+m;
			x= player.getFullHoles().stream().filter(pred).findFirst().get();
		}catch(NoSuchElementException e) {
			return null;
		}

		switch(m) {
			case -8:{//diagonale dall'alto in basso da dx a sx
				if(hole.getCol()-x.getCol()!=1) {x= null;}
			}break;
			case +8:{//diagonale dal basso in alto da sx a dx
				if(hole.getCol()-x.getCol()!=-1) {x= null;}
			}break;
			case -7:{//verticalmente dall'alto in basso
				if(hole.getCol()-x.getCol()!=0) {x= null;}
			}break;
			case +7:{//verticalmente dal basso in alto
				if(hole.getCol()-x.getCol()!=0) {x= null;}
			}break;
			case -6:{//diagonale dall'alto in basso da sx a dx
				if(hole.getCol()-x.getCol()!=1) {x=null;}
			}break;
			case +6:{//diagonale dal basso in alto da dx a sx
				if(hole.getCol()-x.getCol()!=1) {x=null;}
			}break;
			case -1:{//orizzontalemente da dx a sx
				if(hole.getRow()!=x.getRow()) {x=null;}
			}break;
			case +1:{
				if(hole.getRow()!=x.getRow()) {x=null;}
			}break;
		}
		return x;
	}

	/**
	 *
	 * in base alla combinazione passata come argomento permette di sapere se il token nella posizione
	 * in esame più la combinazione è un token di quel giocatore
	 * @param hole
	 * @param player
	 * @param n
	 * @return
	 */
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
