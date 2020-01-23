package net.studionotturno.Forza4.domain.MainElements;

/**
 * Responsabilita' di identificare un gettone del gioco Forza 4 all'interno della BoardTest.
 * Viene usato da un Player e posizionato in un foro;
 */
public class Token {

	private String id;
	private int x;
	private int y;

	/**
	 * 
	 * @param ID
	 * @param x
	 * @param y
	 */
	public Token(String id,int x,int y) {
		this.id=id;
		this.x=x;
		this.y=y;
	}
	
	/**
	 * 
	 * @param name
	 */
	public Token( String id) {
		this.id=id;
	}
	
	public void setlocation(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	public String getId() {
		return this.id;
	}

}