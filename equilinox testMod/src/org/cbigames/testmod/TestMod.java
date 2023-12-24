package org.cbigames.testmod;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.modloader.ModInitializer;
import com.modloader.events.OnGameLoad;

import languages.GameText;

/**this was a mod designed to test the basic functionality of the mod loader
 * all this mod does is replace every string in the game it can with peepeepoopoo
 * @author jSdCool
 *
 */
public class TestMod extends ModInitializer implements  OnGameLoad{
	
	public void initMod(List<String> args) {
		
	}
	
	/**replaces every static string field in the target class with pee pee poo poo
	 * @param inClass the class to replace the Strings of
	 */
	void replaceStrings(Class<?> inClass) {
		Field[] fiels = inClass.getDeclaredFields();
		for(int i=0;i<fiels.length;i++) {
			if(fiels[i].getType() == String.class&&java.lang.reflect.Modifier.isStatic(fiels[i].getModifiers())) {
				setText(fiels[i]);
			}
		}
	}
	
	/**sets the value of the target field to pee pee poo poo
	 * @param f the field to replace
	 */
	void setText(Field f) {
		try {
			f.setAccessible(true);
			try {
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			}catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			
			f.set(null, "pee pee poo poo");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**do the replacement when the game has laoded
	 */
	@Override
	public void gameLoaded() {
		System.out.println("laoded");
		doReplacement();
	}

	/**call replaceStrings on all the folowing classes
	 */
	void doReplacement() {
		System.out.println("patching text");

		replaceStrings(optionsMenu.OptionsPanelUi.class);
		replaceStrings(controllerUi.ControlUi.class);
		replaceStrings(controllerUi.ControlsTabUi.class);
		replaceStrings(entityInfoGui.ColourInfoGui.class);
		replaceStrings(entityInfoGui.EntityInfoGui.class);
		replaceStrings(environment.AltitudeFactor.class);
		replaceStrings(environment.DislikedSpeciesFactor.class);
		replaceStrings(environment.EnviroComponent.class);
		replaceStrings(environment.EnviroPopUp.class);
		replaceStrings(environment.EnvironmentComponentLoader.class);
		replaceStrings(environment.FaveBiomeFactor.class);
		replaceStrings(environment.LikedSpeciesFactor.class);
		replaceStrings(environment.PreferredBiomeFactor.class);
		replaceStrings(environment.UnsuitableBiomeFactor.class);
		replaceStrings(environmentWarning.EnviroTipUi.class);
		replaceStrings(equipping.ItemComponent.class);
		replaceStrings(errors.ErrorManager.class);
		replaceStrings(evolutionUi.EvolveProgressUi.class);
		replaceStrings(evolveStatusOverview.EvolveOverviewTopUi.class);
		replaceStrings(fighting.FightAi.class);
		replaceStrings(fighting.FightCompBlueprint.class);
		replaceStrings(fighting.FightComponent.class);
		replaceStrings(fighting.HostileCompBlueprint.class);
		replaceStrings(fighting.HostileComponent.class);
		replaceStrings(flinging.FlingingCompBlueprint.class);
		replaceStrings(food.FoodCompBlueprint.class);
		replaceStrings(food.FoodToShare.class);
		replaceStrings(food.RootVegSectionBlueprint.class);
		replaceStrings(fruit.FruitFallCompBlueprint.class);
		replaceStrings(fruit.FruitFallComponent.class);
		replaceStrings(fruit.FruiterComponent.class);
		replaceStrings(gallopMovement.GallopMovementBlueprint.class);
		replaceStrings(gameMenu.CreditsGui.class);
		replaceStrings(gameMenu.CreditsPanelGui.class);
		replaceStrings(gameMenu.MenuPanelGui.class);
		replaceStrings(gameMenu.OptionsScreenGui.class);
		replaceStrings(geneticModificationUi.ColourModifierUi.class);
		replaceStrings(geneticModificationUi.FloatModifierUi.class);
		replaceStrings(geneticModificationUi.FreeColourModifierUi.class);
		replaceStrings(geneticModificationUi.ModifierUI.class);
		replaceStrings(growth.GrowthCompBlueprint.class);
		replaceStrings(growth.GrowthComponent.class);
		replaceStrings(health.Disease.class);
		replaceStrings(health.HealSearchAi.class);
		replaceStrings(health.HealthPopUp.class);
		replaceStrings(health.LifeCompBlueprint.class);
		replaceStrings(health.LifeComponent.class);
		replaceStrings(hiveComponents.BeeCompBlueprint.class);
		replaceStrings(hiveComponents.HiveComponent.class);
		replaceStrings(hunting.FleeCompBlueprint.class);
		replaceStrings(hunting.SimpleFleeAi.class);
		replaceStrings(instances.Entity.class);
		replaceStrings(instances.Reproducer.class);
		replaceStrings(loadWorldScreen.ConfirmGui.class);
		//replaceStrings(loadWorldScreen.SaveInfoPanel.class);//this will crash the game for some reason
		replaceStrings(loadWorldScreen.SaveSlotsPanel.class);
		replaceStrings(main.MainApp.class);
		replaceStrings(materials.ColourTraitBlueprint.class);
		replaceStrings(materials.MaterialComponent.class);
		replaceStrings(monkeys.ClingAi.class);
		replaceStrings(monkeys.TreeSwingAi.class);
		replaceStrings(monkeys.TreeSwingCompBlueprint.class);
		replaceStrings(musicTab.CurrentPlayingUi.class);
		replaceStrings(musicTab.MusicUi.class);
		replaceStrings(musicTab.StatusUi.class);
		replaceStrings(nesting.NestingCompBlueprint.class);
		replaceStrings(nightBloom.BloomCompBlueprint.class);
		replaceStrings(nightBloom.BloomComponent.class);
		replaceStrings(notificationPopUp.NotificationListUi.class);
		replaceStrings(perching.PerchCompBlueprint.class);
		replaceStrings(placementUi.PlacementUi.class);
		replaceStrings(saves.SaveSlot.class);
		replaceStrings(session.AutoSaveUi.class);
		replaceStrings(shellHide.HidingAi.class);
		replaceStrings(shellHide.ShellHideCompBlueprint.class);
		replaceStrings(shopping.BlueprintShopItem.class);
		replaceStrings(shopping.Shop.class);
		replaceStrings(shops.ShopItemGui.class);
		replaceStrings(sleeping.SleepAi.class);
		replaceStrings(sleeping.SleepCompBlueprint.class);
		replaceStrings(sound.RandomSounder.class);
		replaceStrings(speciesInformation.SpeciesInfoGui.class);
		replaceStrings(speciesInformation.StatsGui.class);
		replaceStrings(spitting.SpitCompBlueprint.class);
		replaceStrings(stinging.StingingCompBlueprint.class);
		replaceStrings(taskUi.ClaimButtonGui.class);
		replaceStrings(taskUi.TaskInfoFrameUi.class);
		replaceStrings(tasks.BaaReq.class);
		replaceStrings(tasks.ButterflyCatchReq.class);
		replaceStrings(tasks.CountReq.class);
		replaceStrings(tasks.EarningReq.class);
		replaceStrings(tasks.EatReq.class);
		replaceStrings(tasks.EntityCountReq.class);
		replaceStrings(tasks.HalfGrownOakReq.class);
		replaceStrings(tasks.HoneyHarvesterReq.class);
		replaceStrings(tasks.HoneyReq.class);
		replaceStrings(tasks.Task.class);
		replaceStrings(tasks.TaskCompletorRequirement.class);
		replaceStrings(tasks.TaskState.class);
		replaceStrings(tasks.TaskUnlockReward.class);
		replaceStrings(text3D.TraitDisplayOptionUi.class);
		replaceStrings(time.Calendar.class);
		replaceStrings(toolbar.BiomePickerGui.class);
		replaceStrings(toolbar.DpCounter.class);
		replaceStrings(toolbar.DpText.class);
		replaceStrings(toolbar.DppmCounter.class);
		replaceStrings(toolbar.Toolbar.class);
		replaceStrings(traitGuis.TraitsPanelGui.class);
		replaceStrings(treeCharging.TreeChargeAi.class);
		replaceStrings(treeCharging.TreeChargeCompBlueprint.class);
		replaceStrings(unlockGuide.UnlockGuideUi.class);
		replaceStrings(userInterfaces.ProgressBarUi.class);
		replaceStrings(userInterfaces.TextStatInfo.class);
		replaceStrings(worldOptions.WorldOptionsUi.class);
		
		//for good measure replace everything in the game text translation list with pee pee poo poo
		//at this point in the game loading it is not likely to make any diffrence but why not we have come this far
		Map<Integer, List<String>> newTexts = new HashMap<>();
		for(int i=0;i<1188;i++) {
			List<String> texts = new ArrayList<String>();
			texts.add("pee pee poo poo");
			texts.add("pee pee poo poo");
			texts.add("pee pee poo poo");
			newTexts.put(Integer.valueOf(i+1), texts);
		}
		try {
			Field gameTextField = GameText.class.getDeclaredField("gameTexts");
			gameTextField.setAccessible(true);
			gameTextField.set(null, newTexts);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}
	

}
