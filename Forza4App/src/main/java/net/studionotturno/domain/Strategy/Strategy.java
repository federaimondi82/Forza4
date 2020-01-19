package net.studionotturno.domain.Strategy;
import java.util.ArrayList;

import net.studionotturno.domain.MainElements.Hole;
import net.studionotturno.domain.PlayerFactory.Player;

/**
 * Interfaccia per assegane ad un bot la strategia da adottare in fase di gioco
 *
 * @author feder
 *
 */
public interface Strategy {
	
	public ArrayList<Hole> getColChoosed(Player player);

}
