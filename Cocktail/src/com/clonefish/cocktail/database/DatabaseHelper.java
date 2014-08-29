package com.clonefish.cocktail.database;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clonefish.cocktail.R;
import com.clonefish.cocktail.database.tables.Category;
import com.clonefish.cocktail.database.tables.Cocktail;
import com.clonefish.cocktail.database.tables.Tags;
import com.clonefish.cocktail.utils.XMLParser;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper 
{
	private static final String TAG = DatabaseHelper.class.getSimpleName();

	//имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
	private static final String DATABASE_NAME ="ibartender.db";
	
	//с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
	private static final int DATABASE_VERSION = 7;
	   
	private CocktailDAO cocktailDAO = null;
	private TagsDAO tagsDAO = null;
	private CategoryDAO categoryDAO = null;
	
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource conectionSource) 
	{
		try
		{
			TableUtils.createTable(connectionSource, Cocktail.class);
			TableUtils.createTable(connectionSource, Tags.class);
			TableUtils.createTable(connectionSource, Category.class);
		}
	    catch (SQLException e)
		{
	    	Log.e(TAG, "error creating DB " + DATABASE_NAME);
	    	throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource conectionSource, int oldVer,int newVer) 
	{
		try
		{
			TableUtils.dropTable(connectionSource, Cocktail.class, true);
			TableUtils.dropTable(connectionSource, Tags.class, true);
			TableUtils.dropTable(connectionSource, Category.class, true);
			onCreate(db, connectionSource);
	    }
		catch (SQLException e)
		{
			Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
			throw new RuntimeException(e);
		}	
	}
	
	public CocktailDAO getCocktailDAO() throws SQLException
	{
		if(cocktailDAO == null)
			cocktailDAO = new CocktailDAO(getConnectionSource(), Cocktail.class);
		return cocktailDAO;
	}
	
	public TagsDAO getTagsDAO() throws SQLException
	{
		if(tagsDAO == null)
			tagsDAO = new TagsDAO(getConnectionSource(), Tags.class);
		return tagsDAO;
	}
	
	public CategoryDAO getCategoryDAO() throws SQLException
	{
		if(categoryDAO == null)
			categoryDAO = new CategoryDAO(getConnectionSource(), Category.class);
		return categoryDAO;
	}
	
	 public void createDB(Activity activity)
	 {
		 
	    	List<Cocktail> list = XMLParser.parseCocktailDoc(R.xml.cocktails, activity);
			try {
				CocktailDAO cdao;
				cdao = getCocktailDAO();
				if(cdao.queryForId(1) == null)
				    for (Cocktail cocktail : list) 
				    {
						cdao.createOrUpdate(cocktail);
					}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	        
			List<Tags> tags = XMLParser.parseTagsDoc(R.xml.tags, activity);
			try {
				TagsDAO cdao;
				cdao = getTagsDAO();
				if(cdao.queryForId(1) == null)
				    for (Tags tag : tags) 
				    {
						cdao.createOrUpdate(tag);
					}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	        
	        String[] category = {
	        		"Инструменты",
	        		"Быстрые украшения",
	        		"Цедра и твисты",
	        		"Формы и выемки",
	        		"Карвинг",
	        		"Конкурс и подиум"
	        };
	        try {
				CategoryDAO dao = DatabaseHelperFactory.getHelper().getCategoryDAO();
				Category cat;
				if(dao.queryForId("Инструменты") == null)
					for (String ctgr : category) {
						cat = new Category();
						cat.name = ctgr;
						dao.createIfNotExists(cat);
					}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
}
