package net.studionotturno.Observer;

import java.util.List;

import net.studionotturno.domain.MainElements.Hole;
import net.studionotturno.domain.MainElements.Winner;
import net.studionotturno.domain.PlayerFactory.Player;


/**
 * Secondo il design pattern Observer, un Observer fornisce una interfaccia per gli oggetti che devono essere notificati dal Subject.
 * Rappresenta una UI, che sia una interfaccia grafica o una a linea di comando.
 * @see Subject
 */
public interface Observer {


	/**
	 * Consente di aggiornare una Vista in base ai dati del Subject
	 * @param subject un elemento che funge da model dei dati
	 */
	public void update(Subject subject);

	/**
	 * consente di aggiornare i dati della vista notificando l'eventuali vincitore
	 * @param subject
	 * @param winner
	 */
	public void updateWinner(Subject subject,Winner<Player, List<Hole>> winner);

}