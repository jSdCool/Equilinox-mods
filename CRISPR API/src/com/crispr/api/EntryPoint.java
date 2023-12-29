package com.crispr.api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import com.modloader.Main;
import com.modloader.ModInfo;
import com.modloader.ModInitializer;
import com.modloader.events.OnGameLoad;
import com.modloader.events.SynchronousLooping;

import gameManaging.GameManager;
import gameMenu.CreditsGui;
import gameMenu.CreditsInfo;
import gameMenu.CreditsPanelGui;
import gameMenu.GameMenuGui;
import guis.GuiComponent;
import userInterfaces.GuiScrollPanel;

/**the mod entry point required for all mods
 * @author jSdCool
 *
 */
public class EntryPoint extends ModInitializer implements OnGameLoad, SynchronousLooping{
	@Override
	public void initMod(List<String> args) {
		replaceSessionManager();
	}

	@Override
	public void gameLoaded() {
		//add the with crispr logo to the main menu
		MainMenuLogo logo = new MainMenuLogo(this);
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
	
	void addCredits() {
		GameMenuGui gameMenuGui = MainMenuLogo.getMainGameMenuGui();
		try {
			Field secondaryScreen = GameMenuGui.class.getDeclaredField("secondaryScreen");
			secondaryScreen.setAccessible(true);
			GuiComponent menu = (GuiComponent) secondaryScreen.get(gameMenuGui);
			Method getComponents = GuiComponent.class.getDeclaredMethod("getComponents");
			getComponents.setAccessible(true);
			if(menu instanceof CreditsGui) {
				@SuppressWarnings("unchecked")
				List<GuiComponent> components = (List<GuiComponent>) getComponents.invoke(menu);
				for(GuiComponent component: components) {
					if(component instanceof GuiScrollPanel) {
						Field scrollContentField = GuiScrollPanel.class.getDeclaredField("contentPanel");
						scrollContentField.setAccessible(true);
						GuiComponent scrollContent = (GuiComponent) scrollContentField.get(component);
						if(scrollContent instanceof CreditsPanelGui) {
							Field creditInfoField = CreditsPanelGui.class.getDeclaredField("creditsInfo");
							creditInfoField.setAccessible(true);
							//Finally actually get the CreditsInfo object we need to modify
							CreditsInfo ci = (CreditsInfo) creditInfoField.get(scrollContent);
							//check if the extra credits have allready been added
							Field creditsField = CreditsInfo.class.getDeclaredField("credits");
							creditsField.setAccessible(true);
							@SuppressWarnings("unchecked")
							Map<String, String[]> credits = (Map<String, String[]>) creditsField.get(ci);
							if(credits.get("Mod Loader") == null) {
								
								//add the credits
								ci.addInfo("Mod Loader", new String[] {"jSdCool"});
								for(ModInfo mod: Main.modInfo) {
									ci.addInfo(mod.getModName(), mod.getAuthors());
								}

								Method addInfo = CreditsPanelGui.class.getDeclaredMethod("addInfo", String.class, String[].class);
								addInfo.setAccessible(true);
								//add the credits to display properly
								addInfo.invoke(scrollContent,"Mod Loader", new String[] {"jSdCool"});
								for(ModInfo mod: Main.modInfo) {
									addInfo.invoke(scrollContent,mod.getModName(), mod.getAuthors());
								}
								GuiScrollPanel gsp = (GuiScrollPanel) component;
								gsp.resize(0.23F + ci.getLineCount() * 0.07F);
							}
							
							break;
						}
						
					}
				}
			}
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void syncLoop() {
		addCredits();
	}

}
