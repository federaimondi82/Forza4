package net.studionotturno.Forza4.domain.MainElements;

import java.util.List;

import net.studionotturno.Forza4.domain.PlayerFactory.Player;

/**
* Generic per l'identificazione del giocatore vincitore
* @author feder
*
* @param <P>
 * @param <L>
 */
public class Winner <P extends Player,L extends List> {

	private P player;
	private L list;

	/**
	 * @param player
	 * @param list
	 */
	public Winner(P player,L list) {
		this.player=player;
		this.list=list;
	}
	/**
	 * @return the player
	 */
	public P getPlayer() {
		return player;
	}

	/**
	 * @return the list
	 */
	public L getList() {
		return list;
	}
}
