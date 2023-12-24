package com.crispr.api;

import java.io.InputStream;

import com.modloader.Main;
import com.modloader.ModInfo;

import utils.MyFile;

/**if your mod requires the loading of custom assets 
 * then use this class in place of the MyFile class
 * 
 * @author jSdCool
 *
 */
public class MyModFile extends MyFile {
	
	/**Because of the path processing that is required this method is used instead of a constructor
	 * creates a MyModFile for the specified file inside of the res folder in your mod jar
	 * @param modid the id of the mod you want to load from
	 * @param path the path to the file your want to point to inside of the mod's res folder
	 * @return a new instance of MyModFile that points to the file in your mod
	 */
	public static MyModFile of(String modid,String path) {
		ModInfo mod = null;
		for(int i=0;i<Main.modInfo.size();i++) {
			if(Main.modInfo.get(i).getModID().equals(modid)) {
				mod = Main.modInfo.get(i);
				break;
			}
		}
		if(mod ==null) {
			throw new RuntimeException("the mod "+modid+" could not be found");
		}
		
		
		Class<?> modClass;
		try {
			modClass = MyModFile.class.getClassLoader().loadClass(mod.getMainClass());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return new MyModFile("res/"+path,modClass);
	}
	
	Class<?> modClass;
	
	private MyModFile(String path,Class<?> modClass){
		super(path);
		this.modClass=modClass;
	}
	
	public InputStream getInputStream() { 
		return modClass.getResourceAsStream(getPath()); 
	}
}
