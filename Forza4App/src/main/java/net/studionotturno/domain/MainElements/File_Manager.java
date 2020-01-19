package net.studionotturno.domain.MainElements;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;

import net.studionotturno.domain.BackConnect.User;
import net.studionotturno.domain.PlayerFactory.Bot1;
import net.studionotturno.domain.PlayerFactory.Player;
import net.studionotturno.domain.PlayerFactory.PlayerRegistry;
import net.studionotturno.domain.PlayerFactory.RealPlayer;
import net.studionotturno.domain.Strategy.StrategyRegister;

/**
 * Gestore el file della cache
 * Legge e scrive dati utili per l'autenticazione e le statistiche del giocatore su un file di testo
 * @author feder
 *
 */
public class File_Manager {
	
	private static File_Manager instance;
	private String path;
	private File privateStorage;
			
	/**
	 * Costruttore
	 */
	private File_Manager() {
		this.path=System.getProperty("user.dir")+"\\CacheFile";

		this.privateStorage=new File(this.path+"\\cache.txt");
		/*
		if(Platform.isDesktop()) {
			this.privateStorage=new File(System.getProperty("user.dir")+"\\"+this.path+"cache.txt");
		}
		else if(Platform.isAndroid() || Platform.isIOS()) {
			try {
				this.privateStorage = Services.get(StorageService.class)
					      .flatMap(StorageService::getPrivateStorage)
					      .orElseThrow(() -> new FileNotFoundException("Could not access private storage."));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		*/
	}
	
	public static File_Manager getInstance() {
		if(instance==null) instance=new File_Manager();
		return instance;
	}
	
	
	/**
	 * Controlla se nel file della cache ci sono dei dati dell'autenticazione
	 * se ci sono li carica, altriemti viene richiesto di immettere le credenziali
	 * @return true se l'utent e' autenticato, false altrimenti
	 */
	public boolean checkUser() {
    	try {
    		//apre e legge il file di cache
			List<String> linee=readCacheFile();
			//viene letta la prima riga, se contiene uno ero vuol dire che l'utente non si e' regitrato o autenticato
			String s=linee.get(0).split(":")[0];
			System.out.println("s:"+s);
			if(s.equals("0")) {
				//TODO non si devono vedere le pagine di registrazione e autenticazione
				//ma sono quelle del gioc,della difficolta' e della modifica delle credenziali
				//System.out.println("utente non registrato");
				return false;	
			}
			else {
				return true;
			}			
		} catch (IOException | IndexOutOfBoundsException e) {
			//System.out.println("utente non registrato");
			return false;
		}    	
    }

	/**
	 * Apre e legge il file di cache
	 * @return le linee lette nel file di cache
	 */
	private List<String> readCacheFile() throws IOException {
		BufferedReader br=new BufferedReader(new FileReader(this.privateStorage));
		List<String> linee=new ArrayList<>();
		br.lines().forEach(linee::add);
		br.close();
		return linee;
	}

	/**
	 * Recupera le url da un path di una cartella dove ci sono i file .class dei factory (concrete factory)
	 * @throws MalformedURLException
	 */
	public URL[] initPlayerFiles() throws MalformedURLException {
		//recupero della cartella dove sono i file compilati dei Player (concreteProduct e concreteFactory)
		String folder = System.getProperty("user.dir");
		File newPlayerFolder=new File(folder+"\\src\\main\\resources\\plugin\\");
		//creazione URL da passare al class loader
		URL[] urls= {newPlayerFolder.toURI().toURL()};
		return urls;
	}

	/**
	 * 
	 * Carica tutte le classi necessarie per caricare i Register dei Player e delle Strategy
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void loadComponents() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

		PlayerRegistry.getInstance().loadLoader(initPlayerFiles());

		StrategyRegister.getInstance().loadLoader(initPlayerFiles());
	}

	/**
	 * Vengono aggiornate le statistiche del giocatore
	 * @param p
	 */
	public void updateLocalData(Player p) {
		if(p instanceof RealPlayer) User.getInstance().victory(User.getInstance().getVictory()+1);
		
		else if(p instanceof Bot1) User.getInstance().loses(User.getInstance().getLoses()+1);
		
		else User.getInstance().draw(User.getInstance().getDraw()+1);
		
		updateCache();
	}
	
	/**
	 *Viene aggiornato il file di cache
	 */
	public void updateCache() {
		try {		
			FileWriter writer=new FileWriter(this.privateStorage);
			
			writer.write(User.getInstance().toStringTotal());	
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Al primo avvio viene creato il file della cache
	 */
	public void fileCreator() {

		if(!this.privateStorage.exists()) {
			try {
				this.privateStorage.createNewFile();
				//scrive sul file uno zero per identificare che l'utente non e' autenticato
				FileWriter writer=new FileWriter(this.privateStorage);
				writer.write("0");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * Crea la cartella per memorizzare un file di cache
	 */
	public void directoryCreator() {

		File directory=new File(this.path);

		if(!directory.exists()) directory.mkdir();
		
		/*
		if(Platform.isDesktop()) {
			File directory=new File(this.path);

			if(!directory.exists()) {
				directory.mkdir();
			}
		}
		else if(Platform.isAndroid() || Platform.isIOS()) {
			File imageDir = new File(this.path, "otn-images");
	        imageDir.mkdir();
		}
		*/
	}
	
	public File getFile() {
	    return this.privateStorage;
	}

}
