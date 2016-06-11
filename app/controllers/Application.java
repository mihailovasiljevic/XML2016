package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import net.sf.ezmorph.ObjectMorpher;

import org.h2.constant.SysProperties;

import com.fasterxml.jackson.databind.ObjectMapper;

import database.XMLPartialUpdate;
import database.Util;
import models.Player;

public class Application extends Controller {

	private static ArrayList<Player> players = new ArrayList<Player>();
	
	/** Ukoliko je potrebno koristiti sesiju to je moguce uz pomoc objekta session na sledeci nacin:
	 * session.put(key, value). Sesija se preuzima sa session.get(value). 
	 * Objekat session je dostupan iz svih delova aplikacije. */
	
    public static void index() {
    	System.out.println("Server je uspesno pokrenut");
    	render();
    }
    
    public static void save() {
    	System.out.println("save");
    	String result = params.get("body");
    	ObjectMapper mapper = new ObjectMapper();
    	Player player;
		try {
			player = mapper.readValue(result, Player.class);
			players.add(player);
			
			XMLPartialUpdate update = new XMLPartialUpdate();
			String patch = "\t<player>\n\t\t<name>"+player.getName()+"</name>\n"
					+ "\t\t<surname>"+player.getSurname()+"</surname>\n"
					+"\t\t<club>"+player.getClub()+"</club>\n"
					+"\t</player>";
			update.run(Util.loadProperties(), patch);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void getPlayers() {
    	System.out.println("getPlayers");
    	renderJSON(players);
    }

}