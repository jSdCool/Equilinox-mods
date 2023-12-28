package com.crispr.api;

import java.lang.reflect.Field;
import java.util.Map;

import blueprints.Blueprint;
import resourceManagement.BlueprintRepository;

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
}
