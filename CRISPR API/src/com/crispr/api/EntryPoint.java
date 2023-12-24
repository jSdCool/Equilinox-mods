package com.crispr.api;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import com.modloader.ModInitializer;
import com.modloader.events.OnGameLoad;

import gameManaging.GameManager;

/**the mod entry point required for all mods
 * @author jSdCool
 *
 */
public class EntryPoint extends ModInitializer implements OnGameLoad{
	@Override
	public void initMod(List<String> args) {
		replaceSessionManager();
	}

	@Override
	public void gameLoaded() {
		//add the with crispr logo to the main menu
		MainMenuLogo logo = new MainMenuLogo();
		logo.addModdedLogoToMainMenu();
		
	}
	
	/**replaces the default session manager with a custom one that allows modded code to be run by the main game thread
	 */
	void replaceSessionManager() {
		try {
			EntryPoint.class.getClassLoader().loadClass("gameManaging.GameManager");
	
			//get the field of the session manager
			Field gameSessionManager = GameManager.class.getDeclaredField("sessionManager");
			//make that feild not final
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(gameSessionManager, gameSessionManager.getModifiers() & ~Modifier.FINAL);
			
			//replace the session manager instance with our modded version
			gameSessionManager.set(null,new ModdedSessionManager());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
