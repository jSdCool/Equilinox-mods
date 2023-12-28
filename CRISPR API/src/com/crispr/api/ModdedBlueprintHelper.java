package com.crispr.api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import blueprints.Blueprint;
import breedingTrees.BreedingTrees;
import componentArchitecture.ComponentType;
import gameManaging.GameManager;
import resourceManagement.BlueprintRepository;
import shopping.Shop;
import health.LifeCompBlueprint;

/**a tool to allow developers to easily load new blueprints into the game
 * @author jSdCool
 *
 */
public class ModdedBlueprintHelper{
	/**loads a blueprint from a mod and puts it into the game's blueprint repository
	 * @param id the unique id of the blueprint. make sure this is not the same as any other ever
	 * @param file the file that contains the blueprint data
	 * @return the blueprint that was loaded
	 */
	@SuppressWarnings("unchecked")
	public static Blueprint loadModBlueprint(int id,MyModFile file) {
		
		//check to make sure that no blueprint has been resisted with that ID
		if(BlueprintRepository.getBlueprint(id) != null) {
			throw new RuntimeException("a blueprint with id: "+id+" allready exsits");
		}
		//load the blueprint
		Blueprint loaded = Blueprint.load(id, file, false);
		
		Map<Integer, Blueprint> blueprints;
		//obtain the blueprint repo from the game
		try {
			Field repoField = BlueprintRepository.class.getDeclaredField("blueprints");
			repoField.setAccessible(true);
			blueprints = (Map<Integer, Blueprint>) repoField.get(null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Failed to acquire blueprint repository",e);
		}
		//place the newly loaded blueprint into the repo
		blueprints.put(id, loaded);
		
		return loaded;
	}
	
	/**add a blueprint to the animal shop
	 * @param blueprint the blueprint to add
	 */
	public static void addBlueprintToAnimalShop(Blueprint blueprint) {
		//get a reference to the animal shop
		Shop animalShop = GameManager.getShops().getAnimalShop();
		try {
			//get a way to call the add method because it is private
			Method addItem = Shop.class.getDeclaredMethod("addItem",Blueprint.class);
			addItem.setAccessible(true);
			addItem.invoke(animalShop, blueprint);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//add blueprint to breeding trees

        LifeCompBlueprint lifeComp = (LifeCompBlueprint)blueprint.getComponent(ComponentType.LIFE);
        if (lifeComp != null) {
        	try {
				Field tSc = BreedingTrees.class.getDeclaredField("totalSpeciesCount");
				Field bpk = BreedingTrees.class.getDeclaredField("blueprintKeys");
        		Method newNode = BreedingTrees.class.getDeclaredMethod("createNewTreeNode", Blueprint.class);
        		tSc.setAccessible(true);
        		bpk.setAccessible(true);
        		newNode.setAccessible(true);
        		
        		tSc.setInt(GameManager.BREED_TREES,tSc.getInt(GameManager.BREED_TREES)+1);
        		@SuppressWarnings("unchecked")
				Map<Blueprint, int[]> blueprintKeys = (Map<Blueprint, int[]>) bpk.get(GameManager.BREED_TREES);
        		if(!blueprintKeys.containsKey(blueprint)) {
        			newNode.invoke(GameManager.BREED_TREES, blueprint);
        		}
        		
        	} catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
        		throw new RuntimeException("Failed to add blueprinty to breed trees ",e);
        	}
        	
        }
		
	}
}
