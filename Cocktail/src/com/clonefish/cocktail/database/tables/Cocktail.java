package com.clonefish.cocktail.database.tables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cocktail")
public class Cocktail 
{
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(dataType = DataType.STRING)
	public String video_id;
	
	@DatabaseField(dataType = DataType.STRING, index = true)
	public String name;
	
	@DatabaseField(dataType = DataType.STRING)
	public String info;
	
	@DatabaseField(dataType = DataType.STRING, index = true)
	public String category;
	
	@DatabaseField(dataType = DataType.STRING)
	public String timing;
	
	@DatabaseField(dataType = DataType.STRING, index = true)
	public String tags;
	
	public Cocktail()
	{
	
	}
}
