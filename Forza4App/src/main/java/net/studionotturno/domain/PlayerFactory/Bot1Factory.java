package net.studionotturno.domain.PlayerFactory;

import java.util.Random;

/**
 * Concrete Facotry per il bot
 * @author feder
 *
 */
public class Bot1Factory implements PlayerFactory {

	@Override
	public Player createPlayer() {
		Random rand=new Random();
		return new Bot1("Bot-"+rand.nextInt(1001));
	}

}