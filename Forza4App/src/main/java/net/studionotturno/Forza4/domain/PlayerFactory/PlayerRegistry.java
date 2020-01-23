package net.studionotturno.Forza4.domain.PlayerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsabilita' di creare e memorizare i giocatori usando il factory method per i player. (GRASP creator e expert)
 * 
 * E' un punto di accesso per usare i giocatori, e' un simil Facade che consente di nasconder il factory method dei giocatory
 * E' usato per semplificare l'accesso ai dati.
 */
public class PlayerRegistry {
	
	/**
	 * Singleton per memorizzare i factory dei giocatori
	 */
	private static PlayerRegistry instance;
	
	private Map<String,PlayerFactory> registry;

	/**
	 * Inizializza il registry usando una HashMap<String,PlayerFactory>
	 */
	public PlayerRegistry() {
		this.registry=new HashMap<String, PlayerFactory>();
	}

	/**
	 * Unico punto di accesso al singleton
	 * 
	 * @return l'istanza del singleton PlayerRegistry
	 */
	public static PlayerRegistry getInstance() {
		if(instance==null) instance=new PlayerRegistry();
		return instance;
	}

	public PlayerFactory getPlayer(String name) {
		return this.registry.get(name);
	}

	/**Mostra la struttura dati per i factory dei giocatori
	 * @return la struttura dati dove sono stati memorizzati i tipi di factory per i giocatory
	 */
	public Map<String,PlayerFactory> getRegistry() {
		return this.registry;
	}

	/**Consente di memorizzare un nuovo player nel registry
	 * @param name
	 * @param playerFactory
	 */
	public void registry(String name, PlayerFactory playerFactory) {
		this.registry.put(name, playerFactory);
	}

	/**
	 * Richiamato dopo initPlayerFiles() e carica i factory.
	 * Fa in modo di rendere disponibili le classi dei factory per poterli usare che a loro volta creano i giocatori (concrete product)
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IOException 
	 */
	public void loadLoader(URL[] urls) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		//legge un file di testo con i factory da caricare
		File newPlayerTXT=new File(System.getProperty("user.dir")+"\\src\\main\\resources\\plugin\\player.txt");
						
		//lettura del file di testo con le classi
		List<String> list=Files.readAllLines( newPlayerTXT.toPath() );
				
		try(URLClassLoader loader = new URLClassLoader( urls )){
			for(String s1 : list) {
			String s2[] =s1.split(" ");//splitta la linea di testo
			Class<? extends PlayerFactory> clazz= Class//carica le classi
			.forName(s2[1],true,loader)
			.asSubclass(PlayerFactory.class);		
			
		//memorizza le classi del register con il proprio nome
			registry(s2[0], clazz.getConstructor().newInstance() );				
		}			
		}
	}

}