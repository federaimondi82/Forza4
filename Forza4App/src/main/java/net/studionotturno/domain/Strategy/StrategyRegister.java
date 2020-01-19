package net.studionotturno.domain.Strategy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Facade per le strategie dei bot. Rappresenta il punto di accesso e il registro per le strategie
 * @author feder
 *
 */
public class StrategyRegister {
	
	private static StrategyRegister instance;
	private HashMap<String,Strategy> registry;
	
	private StrategyRegister(){
		this.registry=new HashMap<String, Strategy>();
	}
	
	public void addStrategy(String name,Strategy strategy) {
		this.registry.put(name, strategy);
	}
	
	public static StrategyRegister getInstance() {
		if(instance==null) instance=new StrategyRegister();		
		return instance;
	}
	
	public Strategy getStategy(String name) {
		return this.registry.get(name);
	}
	
	public HashMap<String,Strategy> getStrategies(){
		return this.registry;
	}
	
	public void loadLoader(URL[] urls) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		//legge un file di testo con i factory da caricare
		File newPlayerTXT=new File(System.getProperty("user.dir")+"\\src\\main\\resources\\plugin\\strategy.txt");
						
		//lettura del file di testo con le classi
		List<String> list=Files.readAllLines( newPlayerTXT.toPath() );
				
		try(URLClassLoader loader = new URLClassLoader( urls )){
			for(String s1 : list) {
			String s2[] =s1.split(" ");//splitta la linea di testo
			Class<? extends Strategy> clazz= Class//carica le classi
			.forName(s2[1],true,loader)
			.asSubclass(Strategy.class);
			//System.out.println(s2[0]+"-"+clazz.getCanonicalName());
		//memorizza le classi del register con il proprio nome
			addStrategy(s2[0], clazz.getConstructor().newInstance() );				
		}			
		}
	}

	/**
	 * Consente di sapere quali strategie sono disponibili
	 * @return una lista con i nomi dei livelli disponibili
	 */
	public List<String> getStrategiesNames(){
		List<String> list=new ArrayList<String>();
		for(String s : this.registry.keySet()) {
			list.add(s);
		}
		return list;
	}
}
