package com.clonefish.cocktail.database;

import java.sql.SQLException;

import com.clonefish.cocktail.database.tables.Category;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class CategoryDAO extends BaseDaoImpl<Category, String> {

	protected CategoryDAO(ConnectionSource connectionSource,Class<Category> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	
}
