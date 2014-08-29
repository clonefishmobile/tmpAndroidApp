package com.clonefish.cocktail.database.tables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cocktail")
public class Cocktail 
{
	public static final String TAG_COLUMN = "tag";
	public static final String CATEGORY_COLUMN = "category";
	public static final String NAME_COLUMN = "name";
	
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(dataType = DataType.STRING)
	public String video_id;
	
	@DatabaseField(dataType = DataType.STRING, index = true, columnName = NAME_COLUMN)
	public String name;
	
	@DatabaseField(dataType = DataType.STRING)
	public String info;
	
	@DatabaseField(dataType = DataType.STRING, index = true, columnName = CATEGORY_COLUMN)
	public String category;
	
	@DatabaseField(dataType = DataType.STRING)
	public String timing;
	
	@DatabaseField(dataType = DataType.STRING, index = true, columnName = TAG_COLUMN)
	public String tags;
	
	@DatabaseField(dataType = DataType.STRING)
	public String recepie;
	
	public Cocktail()
	{
	
	}
}
