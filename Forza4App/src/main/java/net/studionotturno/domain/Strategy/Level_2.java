package net.studionotturno.domain.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.studionotturno.domain.MainElements.Comb;
import net.studionotturno.domain.MainElements.Hole;
import net.studionotturno.domain.MainElements.Match;
import net.studionotturno.domain.PlayerFactory.Player;


public class Level_2 implements Strategy{

	@Override
	public ArrayList<Hole> getColChoosed(Player player) {

		//TODO tutto
		int round=Match.getInstance().getRound();
		ArrayList<Hole> list=null;
		//attingendo alle combinazioni sceglie la mossa migliore
		if(round<4) list=getInLine(player, round);
		else list=getInLine(player, 4);
		
		//se non viene trovata un combinazione viene scelto un foro a caso
		if(list==null) {
			player.setStrategy(StrategyRegister.getInstance().getStategy("level1"));
			player.getStategy().getColChoosed(player);
		}
		
		
		System.out.println("");
		return list;
	}
	
	/**Per ogni foro con un token di quel Player,
	 * e per ogni combinazione, vine cercato un foro libero
	 * adiacente ad un foro esistente che possa fare fila 4
	 * @param player
	 * @param inLine
	 * @return
	 */
	private ArrayList<Hole> getInLine(Player player,int inLine){
		
		ArrayList<ArrayList<Hole>> tabellone=new ArrayList<ArrayList<Hole>>();
		//scorre tutte le possibili combinazioni per ogni foro del player
		for(Hole hole : player.getFullHoles()) {
			for(Integer comb : Comb.getInstance().getCombos()) {
				//in foro in esame
				Hole h=hole;
				int count=0;
				//una lista per memorizzare le combo buone
				ArrayList<Hole> vicini=new ArrayList<Hole>();
				vicini.add(h);
				do {
					//ad ogni ciclo sposta il focus sul foro adiacente a quello preso in esame precedentemente
					Hole newHole=test(h,player,comb);
					if(newHole!=null) {
						h=newHole;
						vicini.add(h);
					}
					count++;
				}while(count<3);
				if(vicini.size()==4) {
					tabellone.add(vicini);
				}
			}
		}
		//dal tabellone devo ricavare quella combinazione
		//la quale ha un foro,con un below, che possa contenere un nuovo token
		
		//ritorna tutti i fori del tabellone che hanno un below
		ArrayList<Hole> newList=null;
		for(ArrayList<Hole> vicini : tabellone){
			
			newList=(ArrayList<Hole>)vicini.stream().filter(hole->{
				return hole.getBelow()==null || !hole.getBelow().isEmpty();
			}).collect(Collectors.toList());
			
			newList.stream().forEach(hole->System.out.println(hole.toString()));
			
		}
		
		
		return null;
	}
	
	private Hole test(Hole hole,Player player,int n) {
		// un foro adiacente potrebbe non esistere,quindi ritornare null
		Hole next=Match.getInstance().getBoard().getHole(hole.getId()+n);
		
		//il foro deve stare nella Board e se ha un token deve essere del player
		if(next!=null) {
			if(!next.isEmpty()) {
				if(next.getToken().getId().equals(player.getName())) return next;
			}
			else return next;
		}
		return null;			
	}
	
	private ArrayList<Hole> getCol(ArrayList<Hole> vicini){
		Predicate<Hole> pred=(hole)->hole.getBelow()!=null;
		return (ArrayList<Hole>)vicini.stream().filter(pred).collect(Collectors.toList());
	}

	
	@Override
	public String toString() {
		return "Level 2";
	}
}

