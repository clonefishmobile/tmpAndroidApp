package com.clonefish.cocktail.database;

import java.sql.SQLException;

import com.clonefish.cocktail.database.tables.Tags;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class TagsDAO extends BaseDaoImpl<Tags, Integer> 
{

	protected TagsDAO(ConnectionSource connectionSource, Class<Tags> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

}
