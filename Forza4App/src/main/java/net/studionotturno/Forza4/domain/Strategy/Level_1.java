package net.studionotturno.Forza4.domain.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.studionotturno.Forza4.domain.MainElements.Hole;
import net.studionotturno.Forza4.domain.MainElements.Match;
import net.studionotturno.Forza4.domain.PlayerFactory.Player;

/**
* Classe per una strategia banale per le scelte del bot nel momento in cui inserisce un token
*
* Fondamentalmente sceglie una colonna a caso tra quelle che non sono piene
* @author feder
*
*/
public class Level_1 implements Strategy{

	@Override
	public int getLevel() {
		return 1;
	}


	@Override
	public ArrayList<Hole> getColChoosed(Player player) {
		//Le colonne che non sono piene
		Map<Integer,Integer> colNotFull=new HashMap<Integer,Integer>();
		colNotFull=Match.getInstance().getBoard().getColonneLibere();

		//quantità delle colonne non piene totalmente
		AtomicInteger j=new AtomicInteger(colNotFull.size());

		//sceglie un numero tra zero e la qualità delle colonne non piene
		Random rand=new Random();
		int bo=rand.nextInt(j.get());

		Integer index=colNotFull.entrySet().stream().collect(Collectors.toList()).get(bo).getValue();
		return Match.getInstance().getBoard().getColumnsList(index);

	}

}

