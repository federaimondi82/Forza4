package net.studionotturno.domain.BackConnect;

public class Parser {
	
	private static Parser instance;
	
	
	private Parser() {
		
	}
	
	public static Parser getInstance() {
		if(instance==null)instance=new Parser();
		return instance;
	}
	
	/**Parsa i dati provenienti dal backend e riempie l'oggetto singleton User
	 * @param json
	 * @return
	 */
	public boolean parseUser(String json) {
		if(json.split(":")[0].equals("0")) {
			System.out.println("utente errato");
			return false;
		}
		else {
			String[] s=json.split(":");
			User.getInstance().id(Integer.parseInt(s[0]))
				.name(s[1])
				.lastname(s[2])
				.email(s[3])
				.pass(s[4])
				.nickname(s[5])
				.victory(Integer.parseInt(s[6]))
				.loses(Integer.parseInt(s[7]))
				.draw(Integer.parseInt(s[8]));
			System.out.println("Parser utente"+User.getInstance().toStringTotal());
			return true;
		}
		
	}

}
