package net.studionotturno.domain.MainElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contiene una serie di holes (6 rows e 7 columns di holes) dove vengono posizionati i gettoni ({@link Token});<br>
 *
 * dovendo mantenere la memoria dei holes è responsabile anche di crearli (e' creator e information exper degli {@link Hole})
 *
 */
public class Board {

	/**
	 * Quantita' di righe in una tabella per Forza 4
	 */
	private static final int rows = 6;
	/**
	 * Quantita' di colonne in una tabella per Forza 4
	 */
	private static final int columns = 7;
	/**
	 * La lista dei fori che fanno parte della tabella
	 */
	private List<Hole> holes;
	/**
	 * La lista dei fori ordinata per colonne
	 */
	private HashMap<Integer,ArrayList<Hole>> columnsList;
	/**
	 * La lista dei fori ordinata per righe
	 */
	private HashMap<Integer,ArrayList<Hole>> rowsList;


	/**
	 * Costruttore, costruisce un oggetto di tipo Board che contiene una serie di fori
	 * @see Hole
	 */
	public Board() {
		this.holes=new ArrayList<Hole>();
		this.columnsList=new HashMap<Integer,ArrayList<Hole>>();
		this.rowsList=new HashMap<Integer,ArrayList<Hole>>();
		createHoles();
	}

	/**
	 * Consente di aggiungre una serie di fori alla board,
	 * viene richiamato direttamente dal costruttore
	 */
	private void createHoles() {
		int count=0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				Hole h=new Hole(j,i,count,null);
				this.holes.add(h);
				count++;
			}
		}

		trackColumn();
		trackRows();
	}

	/**
	 * Consente di ordinare i fori in colonne
	 */
	private void trackColumn() {
		for(int i=0;i<columns;i++) {
			ArrayList<Hole> list=new ArrayList<Hole>();//lista dei fori in colonna
			for(int j=0;j<rows;j++) {
				Hole h=this.getHole(j,i);
				Hole below=this.getHole(h.getId()-7);
				h.setBelow(below);
				list.add(h);
			}
			this.columnsList.put(i,list);//lista delle colonne di fori
		}
	}

	/**
	 * Consente di ordinare i fori per righe
	 */
	private void trackRows() {
		for(int i=0;i<rows;i++) {
			ArrayList<Hole> list=new ArrayList<Hole>();//lista dei fori in riga
			for(int j=0;j<columns;j++) {
				Hole h=this.getHole(i,j);
				list.add(h);
			}
			this.rowsList.put(i,list);//lista delle colonne di fori
		}
	}

	/**
	 * Ritorna una specifica colonna (un insieme di fori) della board
	 * @param i la colonna desiderata
	 * @return Una specifica colonna (un insieme di fori) della board
	 */
	public ArrayList<Hole> getColumnsList(int i){
		return this.columnsList.get(i);
	}

	/**
	 * Ritorna una specifica riga (un insieme di fori) della board
	 * @param i la riga desiderata
	 * @return Una specifica riga (un insieme di fori) della board
	 */
	public ArrayList<Hole> getRowsList(int i){
		return this.rowsList.get(i);
	}

	/**
	 * Metodo get per la lista dei fori ordinata per righe
	 * @return la truttura dati contenente la lista dei fori ordinata per righe
	 */
	public HashMap<Integer,ArrayList<Hole>> getRowsList(){
		return this.rowsList;
	}

	/**La lista dei fori che fanno parte della Board, ogni Hole ha le proprie coordinate nella Board
	 * @return
	 */
	public List<Hole> getHoles(){
		return this.holes;
	}

	/**
	 * @return le righe della board
	 */
	public int getRighe() {
		return rows;
	}

	/**
	 * @return le colonne della board
	 */
	public int getColonne() {
		return columns;
	}

	/**
	 * Metodo get per uno specifico foro
	 * @param riga
	 * @param colonna
	 * @return uno specifico foro della board
	 */
	public Hole getHole(int riga,int colonna) {
		return this.holes.stream().filter(foro->{
			return foro.getCol()==colonna && foro.getRow()==riga;
		}).findFirst().get();
	}

	/**
	 * I fori sono tutti numerati, questo metodo consente di avere un riferimento ad uno specifico foro
	 * @param id  l'indice del foro
	 * @return uno specifico foro
	 */
	public Hole getHole(Integer id) {
		Predicate<Hole> pred=(hole)->hole.getId()==id;
		Hole h=null;
		try {
			h=this.holes.stream().filter(pred).findFirst().get();
		}catch(IndexOutOfBoundsException | NoSuchElementException e) {}
		return h;
	}

	/**
	 * Metodo conoscere tutte le colonne libere disponibili in fase di gioco
	 * @return le colonne libere disponibili in fase di gioco
	 */
	public Map<Integer,ArrayList<Hole>> getColonneLibere() {

		Predicate<Entry<Integer,ArrayList<Hole>>> pred=(list)->list.getValue().size()>0;
		return  this.columnsList.entrySet().stream().filter(pred)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * Un giocatore sceglie una colonna per aggiungere un gettone
	 * @param col  la colonna dove il giocatore vuole inserire il gettone
	 */
	public void addToken(ArrayList<Hole> col) {
		Predicate<Entry<Integer,ArrayList<Hole>>> pred=(list->{
			return list.getValue().equals(col);
		});

		ArrayList<Hole> list=this.columnsList.entrySet().stream().filter(pred).findFirst().get().getValue();

		list.remove(0);

	}
}