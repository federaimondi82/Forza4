package net.studionotturno.domain.BackConnect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Mette a dispsizione dei metodi per l'invio di chimate REST veros la parte backend
 * E' interfaccia verso l'esterno per la parte front end
 * @author feder
 *
 */
public class BackEndConnect {
	
	private static BackEndConnect instance;
	private String domain;
	private int port;
	
	
	private URL url;
	private HttpURLConnection con;
	private BufferedReader br;
	private String line;
	
	
	private BackEndConnect() {
		this.domain="http://localhost";
		this.port=8080;
	}
	
	public static BackEndConnect getInstance() {
		if(instance==null) instance=new BackEndConnect();
		return instance;
	}
	
	public String getDomain() {
		return this.domain+":"+String.valueOf(this.port);
	}
	
	/**Consente di effettuare una chiamata http usando i metodi post e put;
	 * @param path il path univoco per fare la chiamata rest
	 * @param method il metodo http da applicare
	 * @param obj un elemento da inserire nel database
	 * @return ritorna la risposta dal server
	 */
	public boolean restRequest(String path,String method,Object obj) {
		
		List<? super Object> list=new ArrayList<Object>();
		boolean response=false;
		try {
			this.url = new URL(getDomain()+path+obj);
		
			this.con = (HttpURLConnection) url.openConnection();
			this.con.setRequestMethod(method);
			
			this.br=new BufferedReader(new InputStreamReader(this.con.getInputStream()));
			
			list.add(this.br.readLine());
			response=Boolean.parseBoolean(list.get(0).toString());

			this.con.disconnect();
			
		} catch (IOException e) {
			System.out.println("************************");
			System.out.println("********MALFORMED URL1********");
			System.out.println("************************");
			e.printStackTrace();
		}
		return response;
		
	}
	
	/**Consente di effettuare una chiamata http usando i metodi get;
	 * 
	 * @param path il path univoco per fare la chiamata rest
	 * @param method il metodo http da applicare
	 * @return ritorna la risposta dal server
	 */
	public List<? super Object> restRequest(String path,String method) {
		
		List<? super Object> list=new ArrayList<Object>();
		try {
			this.url = new URL(getDomain()+path);
		
			this.con = (HttpURLConnection) url.openConnection();
			this.con.setRequestMethod(method);
			this.br=new BufferedReader(new InputStreamReader(this.con.getInputStream()));
			
			list.add(this.br.readLine());
			this.con.disconnect();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	

}
