package com.crispr.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import languages.GameText;

public class GameTextUtill {
	
	/**adds custom text to the game text repo
	 * @param id the unique text id of the new text
	 * @param text the new text content
	 */
	public static void addGameText(int id,String text) {
		try {
			Field gtf = GameText.class.getDeclaredField("gameTexts");
			gtf.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<Integer, List<String>> gameTexts = (Map<Integer, List<String>>) gtf.get(null);
			List<String> texts = new ArrayList<>();
			texts.add(text);
			texts.add(text);
			texts.add("TestVal"+id);
			gameTexts.put(id, texts);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("failed to add new game text ",e);
		}
		
	}
}
