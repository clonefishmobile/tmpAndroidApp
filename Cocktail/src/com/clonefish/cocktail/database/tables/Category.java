package com.clonefish.cocktail.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "category")
public class Category 
{
	public static final String CATEGORY = "name";
	
	public Category() {	}

	@DatabaseField(index = true, id = true, columnName = CATEGORY)
	public String name;
}
