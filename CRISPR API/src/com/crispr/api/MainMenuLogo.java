package com.crispr.api;

import java.lang.reflect.Field;

import gameMenu.GameMenuBackground;
import gameMenu.GameMenuGui;
import mainGuis.EquilinoxGuis;
import textures.Texture;
import userInterfaces.GuiImage;
import userInterfaces.ScalingImageUi;

public class MainMenuLogo {
	public final Texture MOD_LOGO;
	static GameMenuGui gameMenu = null;// EquilinoxGuis > GameMenuBackground > GameMenuGui
	GuiImage logo;
	
	MainMenuLogo(EntryPoint p){
		//load the texture from the mod
		//this must be done on a thread that has an OPENGL context
		MOD_LOGO = Texture.newTexture(MyModFile.of(p.getInfo().getModID(), "mlogo.png")).noFiltering().create();
	}
	
	/**add the logo for the mod loader to the main menu
	 */
	void addModdedLogoToMainMenu() {
		
		//if we do not have a reference to the main menu
		if(gameMenu == null) {
			getMainMenu();//then acquire one (logo)
		}
		

		logo = addHeader(MOD_LOGO);//place the texture
	}
	
	/**turns the texture into an object that can be put on the main menu
	 * @param logoTexture the texture to make the object out of
	 * @return the image object that can be drawn to the screen
	 */
	private GuiImage addHeader(Texture logoTexture) {
		//convert the texture into an image object
	    GuiImage header = new ScalingImageUi(logoTexture);
	    header.setPreferredAspectRatio(4.0F);
	    //add the component to the main menu centered at the position (image,x,y,scale)
	    //note the coordinates are based on a 0-1 scale
	    gameMenu.addCenteredComponentX(header, 0.75F, 0.4F, 0.4F);
	    return header;
	  }
	
	/**get a reference to the main menu
	 */
	static void getMainMenu() {
		try {
			//the reference is buried under 2 private variables
			//so we need to do reflection to access those variables and get the menu
			//the references is in: EquilinoxGuis > GameMenuBackground(gameMenu) > GameMenuGui(menu)
			Field GMB = EquilinoxGuis.class.getDeclaredField("gameMenu");
			GMB.setAccessible(true);
			GameMenuBackground gmb = (GameMenuBackground) GMB.get(null);
			Field GM = GameMenuBackground.class.getDeclaredField("menu");
			GM.setAccessible(true);
			gameMenu = (GameMenuGui) GM.get(gmb);
			//sorry that these variable names are so confusing
			
			
			//reflection can through a lot of different exceptions
			//although none of them are likely to ever be thrown (especially the SecurityException)
			//we still need to be safe
		} catch (NoSuchFieldException | SecurityException e) {//SecurityException is only thrown by a security manager with does not and will not exist in this context
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	static public GameMenuGui getMainGameMenuGui() {
		//if we do not have a reference to the main menu
		if(gameMenu == null) {
			getMainMenu();//then acquire one (logo)
		}
		return gameMenu;
	}

}
