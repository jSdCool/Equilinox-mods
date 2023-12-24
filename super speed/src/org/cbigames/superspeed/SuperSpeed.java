package org.cbigames.superspeed;



import java.util.List;

import com.modloader.ModInitializer;
import com.modloader.events.AsyncLooping;
import com.modloader.events.OnGameLoad;

import guis.GuiComponent;
import mainGuis.EquilinoxGuis;
import toolbox.MyKeyboard;

/**entry point class for the super speed mod
 * @author jSdCool
 */
public class SuperSpeed extends ModInitializer implements OnGameLoad, AsyncLooping{

	@Override
	public void initMod(List<String> args) {
		

	}
	
	boolean loaded = false,tabDown=false;
	int counter = 0;
	SuperSpeedUi ssUi;
	final int TAB = 15; // the key ID of tab
	
	/**adds the super speed button the game UI
	 */
	private void addSpeedButton() {
		//obtain an instance of the bottom bar GUI component
		GuiComponent bottomBar = EquilinoxGuis.getBottomBar();
		
		//create an instance of our new button
		ssUi = new SuperSpeedUi();
	    float xPos = 0.88950276F;//x position of the existing fast forward button
	    //add the new button the the bottom bar
	    bottomBar.addComponent(ssUi, xPos+0.115F, 0.0F, 1.0F - xPos, 1.0F);
	  }

	/**add the super speed button when the game has laoded
	 */
	@Override
	public void gameLoaded() {
		System.out.println("adding super speed Button");
		addSpeedButton();
		loaded=true;
	}

	/**async check if the tab key has been pressed. if so then do the thing
	 */
	@Override
	public void asyncLoop() {
		if(loaded) {
			//only update every 100 loops to save on performance
			counter++;
			if(counter>100) {
				counter=0;
				//make the button update its self
				if(ssUi.inited) {
					ssUi.button.updateState();
				
					if(MyKeyboard.getKeyboard().isKeyDown(TAB)&& !tabDown) {
						tabDown = true;
						ssUi.button.changeState();
					}
					if(!MyKeyboard.getKeyboard().isKeyDown(TAB)&& tabDown) {
						tabDown = false;
						
					}
				}
				
			}
			
			
		}
		
	}

}
