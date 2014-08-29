package com.clonefish.cocktail.database;

import java.sql.SQLException;
import java.util.List;

import com.clonefish.cocktail.database.tables.Cocktail;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class CocktailDAO extends BaseDaoImpl<Cocktail, Integer> {

	protected CocktailDAO(ConnectionSource connectionSource,Class<Cocktail> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

}
