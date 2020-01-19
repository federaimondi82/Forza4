package net.studionotturno.domain.PlayerFactory;

import net.studionotturno.domain.PlayerFactory.Player;
import net.studionotturno.domain.PlayerFactory.RealPlayer;

/**
 * Concrete Factory per la creazione del Real Player,l'utente che usa l'applicativo
 * @author feder
 *
 */
public class RealPlayerFactory implements PlayerFactory {

	@Override
	public Player createPlayer() {
		return new RealPlayer("John_"+(int)(Math.random()*10000));
	}

}