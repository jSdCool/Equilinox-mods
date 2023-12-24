package com.crispr.api;

import com.modloader.Main;

import session.SessionManager;

public class ModdedSessionManager extends SessionManager{
	
	static boolean gameLoaded =false;
	
	/**runs the default loadInitialSession provided by the game
	 * then runs any game load events that have been resisted by mods
	 * 
	 * ==this is the source of the game load event==
	 */
	 public void loadInitialSession() {
		 super.loadInitialSession();
		 if(!gameLoaded) {
			 Main.gameLoaded();
			 gameLoaded=true;
		 }
	 }
	 
	 /**this method is called by the game on every game loop to make the session manager do what it does
	  * first the default behavior of the session manager is run.
	  * then any resisted sync loop events are run
	  * 
	  * ==this is the source of the sync looping event==
	  */
	 public void update() {
		 super.update();
		 Main.gameTick();
	 }
}
