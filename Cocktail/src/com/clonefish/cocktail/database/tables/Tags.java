package com.clonefish.cocktail.database.tables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tags")
public class Tags 
{
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(dataType = DataType.STRING, index = true)
	public String name;
	
	@DatabaseField(dataType = DataType.STRING, index = true)
	public String image;
	
	public Tags() {
	}
	
}
