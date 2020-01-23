package net.studionotturno.Forza4.domain.Strategy;
import java.util.ArrayList;

import net.studionotturno.Forza4.domain.MainElements.Hole;
import net.studionotturno.Forza4.domain.PlayerFactory.Player;

/**
 * Interfaccia per assegane ad un bot la strategia da adottare in fase di gioco
 *
 * @author feder
 *
 */
public interface Strategy {
	
	public ArrayList<Hole> getColChoosed(Player player);

	public int getLevel();
}
