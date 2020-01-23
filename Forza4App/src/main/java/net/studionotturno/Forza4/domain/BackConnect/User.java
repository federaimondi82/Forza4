package net.studionotturno.Forza4.domain.BackConnect;

public class User {
	
	private int id;
	private String name;
	private String lastname;
	private String email;
	private String pass;
	private String nickname;
	private int victory;
	private int loses;
	private int draw;
	
	private static User instance;
	
	private User() {
	}
	
	public static User getInstance() {
		if(instance==null) instance=new User();
		return instance;
	}
	
	public User id(int id) {
		this.id=id;
		return instance;
	}
	public User name(String name) {
		this.name=name;
		return instance;
	}
	public User lastname(String lastname) {
		this.lastname=lastname;
		return instance;
	}
	public User email(String email) {
		this.email=email;
		return instance;
	}
	public User pass(String pass) {
		this.pass=pass;
		return instance;
	}
	public User nickname(String nickname) {
		this.nickname=nickname;
		return instance;
	}
	public User victory(int victory) {
		this.victory=victory;
		return instance;
	}
	public User loses(int loses) {
		this.loses=loses;
		return instance;
	}
	public User draw(int draw) {
		this.draw=draw;
		return instance;
	}
	
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @return the victory
	 */
	public int getVictory() {
		return victory;
	}

	/**
	 * @return the loses
	 */
	public int getLoses() {
		return loses;
	}

	/**
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}

	
	@Override	
	public String toString() {
		return name + ":" + lastname + ":" + email + ":" + pass + ":"+nickname;
	}
	
	public String getStat() {
		return victory + ":"+loses + ":"+draw;
	}
	
	public String toStringTotal() {
		return id+":"+name + ":" + lastname + ":" + email + ":" + pass + ":"+nickname+":"+victory + ":"+loses + ":"+draw;
	}
}
