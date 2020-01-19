package net.studionotturno.domain.BackConnect;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Classe che effettua i controlli sulle stringhe e interroga il database per conoscere la presenza
 * di alcuni dati relativi alla registrazione
 * 
 * @author feder
 *
 */
public class DataController {
	
	
	private static DataController instance;
	
	private DataController() {
		
	}
	
	public static DataController getInstance() {
		if(instance==null)instance=new DataController();
		return instance;
	}
	
	/**
	 * Viene controllata la stringa  della email
	 * @param email
	 * @return ritorna true se la string va bene, altrimenti false
	 */
	public boolean checkEmail(String email) {
		//se la emial e' scritta correttamente 
		//viene fatta una query sul db
		//se ritorna false vuol dire che la email non e' presente,
		//quindi e' possibile salvarla
		if(email.contains("@")) {
			List<Object> l=BackEndConnect.getInstance().restRequest("/users/email/"+email+"", "GET");
			String s=(String)l.get(0);
			boolean b= Boolean.parseBoolean(s);
			if(b==false) return true;
		}else {
			return false;
		}
		return false;
	}

	public boolean checkNickname(String nickname) {
		//viene fatta una query sul db
		//se ritorna false vuol dire che il nick non e' presente,
		//quindi e' possibile salvarlo
		List<Object> l=BackEndConnect.getInstance().restRequest("/users/nickname/"+nickname+"", "GET");
		String s=(String)l.get(0);
		boolean b= Boolean.parseBoolean(s);
		if(b==false) return true;
		else return false;

	}


	/**
	 * Cifra la password prima di inviarla al server
	 * @param pass
	 * @return
	 */
	public String cifraPass(String pass) {
		//TODO cifratura password
		/*
		try {
			
			String key="sDFp!d";
			Key secret=new SecretKeySpec(key.getBytes(), "AES");
		
			Cipher cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,secret);
			
			byte[] out=cipher.doFinal(pass.toString().getBytes());
			
			System.out.println(out.toString());
			
		} catch (NoSuchAlgorithmException |NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		*/
		
		return pass;
	}

}
