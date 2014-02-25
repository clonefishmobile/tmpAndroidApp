package com.clonefish.cocktail;

/**
 * Контейнер для хранения инфы о коктейле 
 * @author Fluffy </br>
 */
public class Cocktail {
	public int id;
	public String name;
	public String[] tags;
	public String text;
	public String video_id;
	
	private static final String TAG = "Cocktail";
	
	/**
	 * @param name
	 * @param tags
	 * @param text
	 * @param video_id
	 */
	public Cocktail(String name, String[] tags, String text, String video_id) {
		this.name = name;
		this.tags = tags;
		this.text = text;
		this.video_id = video_id;
	}

}
