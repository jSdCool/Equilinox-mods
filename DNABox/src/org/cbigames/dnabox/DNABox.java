package org.cbigames.dnabox;

import java.util.List;

import com.crispr.api.ModdedBlueprintHelper;
import com.crispr.api.MyModFile;
import com.modloader.ModInitializer;
import com.modloader.events.OnGameLoad;

import blueprints.Blueprint;

public class DNABox extends ModInitializer implements OnGameLoad{

	@Override
	public void initMod(List<String> args) {
		

	}

	@Override
	public void gameLoaded() {
		System.out.println("adding DNA box");
		Blueprint b = ModdedBlueprintHelper.loadModBlueprint(186, MyModFile.of("cheaty-DP-box", "dnaBox.txt"));
		ModdedBlueprintHelper.addBlueprintToAnimalShop(b);
		
	}

}
