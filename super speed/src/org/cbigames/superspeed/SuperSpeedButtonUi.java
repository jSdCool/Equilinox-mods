package org.cbigames.superspeed;

import java.lang.reflect.Field;

import org.lwjgl.util.vector.Vector2f;

import gameManaging.GameManager;
import gameManaging.GameSpeed;
import gameManaging.GameSpeed.SpeedSetting;
import guiRendering.GuiRenderData;
import guis.GuiTexture;
import mainGuis.ColourPalette;
import mainGuis.GuiRepository;
import userInterfaces.GuiClickEvent;
import userInterfaces.GuiClickable;

/**the manifestation of the super speed button
 * @author jSdCool
 */
public class SuperSpeedButtonUi extends GuiClickable{
	/**a method from the game, presumably used when rendering the texture
	 */
	@Override
	protected void getGuiTextures(GuiRenderData data) { data.addTexture(getLevel(), this.texture); }
	
	private final GuiTexture texture;
	int state = 0;
	final int MAX_STATE=2;
	  
	  public SuperSpeedButtonUi() {
		//steal the texture from the existing button
	    this.texture = new GuiTexture(GuiRepository.FAST_FORWARD);//apply the texture
	    addTurnOnListener();
	  }
	  
	  /**as far as I can tell this is required but does basally nothing interesting
	   */
	  protected void updateGuiTexturePositions(Vector2f position, Vector2f scale) {//texture things
		    super.updateGuiTexturePositions(position, scale);
		    this.texture.setPosition(position.x, position.y, scale.x, scale.y);
		  }
	  
	  /**button things (this does not feel like it is needed)
	   */
	  protected void updateSelf() { super.updateSelf(); }
	  
	  /**the code we want to run when the button is clicked
	   */
	  private void addTurnOnListener() {
		  addListener((GuiClickEvent event) -> {
			  if (event.isLeftClick()) {//if the left mouse button is clicked
				  //only the left mouse button can actual be detected here so no idea why this is necessary but the vanilla fast forward button has it so it must be important
	        	
				  changeState();
				  return;
			  }
	        
		  });
	  }
	  
	  
	  /**mousing over the vanilla fast button will reset the game speed so updated that button if that happens
	   */
	  void updateState() {
		  if(state!=0) {//check to see if the internal state is not 0
			  Field CurrentSpeed;
			  
			  
			try {
				//get the current game speed setting
				CurrentSpeed = GameSpeed.class.getDeclaredField("current");
				CurrentSpeed.setAccessible(true);
				SpeedSetting speed = (SpeedSetting)CurrentSpeed.get(GameManager.getGameSpeed());
				  
				//if the game speed is not FAST then something else changed it
				if(!speed.equals(SpeedSetting.FAST)) {
					  //reset the button color and internal state to default
					state=0;
					SuperSpeedButtonUi.this.texture.setOverrideColour(ColourPalette.WHITE);
				}
			  
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		  }
	  }
	  
	  /**this is where the speed and button color change happens
	   */
	  void changeState() {
		//cycle through the states
		  state++;
      	if(state>MAX_STATE)
      		state=0;
      	
      	switch(state) {
      		case 0:
      			//reset to default speed
      			SuperSpeedButtonUi.this.texture.setOverrideColour(ColourPalette.WHITE);
		        GameManager.getGameSpeed().normalSpeed();
      			break;
      			
      		case 1:
      			//Vanilla fast forward speed
      			SuperSpeedButtonUi.this.texture.setOverrideColour(ColourPalette.BASE_BLUE);
		        GameManager.getGameSpeed().fastSpeed();
      			break;
      			
      		case 2:
      			//SUPER SPEED

  				//this speed does not exist in the game so we have to be craft about how we apply it
				
        		SuperSpeedButtonUi.this.texture.setOverrideColour(ColourPalette.BRIGHT_RED);//set the button texture to red
        		
        		GameManager.getGameSpeed().fastSpeed();//set the other speed setting to fast to make the rest of the fast speed stuff work (prevent "pausing"(super slow mo)) 
        		GameManager.getGameSpeed().timeSpeedControl.setTarget(20);//set the game speed to 20X default speed (this is what actually makes a difference)
  		
      			break;
      	}
	  }

}
