package net.studionotturno.Observer;

import java.util.List;

/**
 * Secondo il design pattern Observer un Subject fornisce una interfaccia per accoppiare o disaccoppiare gli Observer.
 * In questo caso le classi che implementano Subject tengono traccia degli Observer e forniscono lo stato del Subject.
 * il Subject notifica i cambiamenti di stato proveniente dai dati della business Logic.
 * @see Observer
 */
public interface Subject {

	/**
	 * Consente di aggiungere un Observer ad una lista per notificare i cambiamenti di stato del Subject.
	 * @param observer Un elemento di tipo Observer
	 * @return Ritorna true se l'observer non era gia' presente nella lista
	 * @see Observer
	 */
	public boolean addObserver(Observer observer);

	/**
	 * Consente di rimuovere un Observer ad una lista per notificare i cambiamenti di stato del Subject.
	 * @param observer Un elemento di tipo Observer
	 * @return Ritorna true se l'observer è stato trovato ed eliminato dalla lista delgi Observer
	 * @see Observer
	 */
	public boolean removeObserver(Observer observer);


	/**La lista degli Observers (ossia delle View) caricata nella eventuale partita corrente
	 * @return Una lista per scorrere le View caricate e assegnate al dominio
	 */
	public List<Observer> getObservers();

	/**
	 * Notifica agli Observer i cambiamenti di stato del Subject.
	 * @return Ritorna true quando tutte le notifiche hanno avuto luogo
	 */
	public boolean noTify();

}