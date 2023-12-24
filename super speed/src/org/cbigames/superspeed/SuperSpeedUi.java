package org.cbigames.superspeed;

import mainGuis.ColourPalette;
import userInterfaces.GuiPanel;

/**a container to put the button in
 * this is basically copied for the in game fast forward button
 * @author jSdCool
 *
 */
public class SuperSpeedUi extends GuiPanel {
	  
	  public SuperSpeedUi() { super(ColourPalette.DARK_GREY, 0.75F); }

	  public SuperSpeedButtonUi button;//the button Object for accessing outside of this class
	  boolean inited =false;
	  
	  protected void init() {
	    super.init();
	    button = new SuperSpeedButtonUi();
	    addButton();
	    inited=true;
	  }


	  
	  private void addButton() { addComponent(button, 0.15F, 0.15F, 0.7F, 0.7F); }
}
