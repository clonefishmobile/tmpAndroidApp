package com.clonefish.cocktail.database;

import com.clonefish.cocktail.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {
	
	private static final String DB_NAME = "cocktail_db";
	private static final int DB_VERSION = 5;
	private static final String DB_TABLE_COCKTAIL = "cocktail_table";
	private static final String DB_TABLE_TAGS = "tags_table";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_VIDEO = "video_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_INFO = "info";
	public static final String COLUMN_CAT = "category";
	public static final String COLUMN_TIMING = "timing";
	public static final String COLUMN_TAGS = "tags";
	
	private static final String TAG = "DB";
	
	private static final String COCKTAIL_CREATE = 
		"create table " + DB_TABLE_COCKTAIL + "(" +
		COLUMN_ID + " integer primary key autoincrement, " +
		COLUMN_NAME + " text, " +
		COLUMN_INFO + " text, " +
		COLUMN_VIDEO + " text, " +
		COLUMN_CAT + " text, " +
		COLUMN_TIMING + " text, " +
		COLUMN_TAGS + " text);";
	
	private static final String TAGS_CREATE = 
			"create table " + DB_TABLE_TAGS + "(" +
			COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_NAME + " text);";
		  
	private final Context dbContext;
		  
	private DBHelper dbHelper;
	private SQLiteDatabase mDB;
	
	public DB(Context ctx) 
	{
		dbContext = ctx;
		if(Constants.DEBUG) Log.i(TAG, "-----create instnace-----");
	}
	
//	открыть подключение
	public void open()
	{
		dbHelper = new DBHelper(dbContext, DB_NAME, null, DB_VERSION);
		mDB = dbHelper.getWritableDatabase();
		if(Constants.DEBUG) Log.i(TAG, "-----open database-----");
	}
	
	//закрыть подключение
	public void close()
	{
		if (dbHelper!=null) dbHelper.close();
		if(Constants.DEBUG) Log.i(TAG, "-----close database-----");
	}
	
	// получить все данные из таблицы DB_TABLE
	public Cursor getAllCocktailData()
	{
		if(Constants.DEBUG) Log.i(TAG, "-----getting all data-----");
		return mDB.query(DB_TABLE_COCKTAIL, null, null, null, null, null, null);
	}
	
	public Cursor getAllTagsData()
	{
		if(Constants.DEBUG) Log.i(TAG, "-----getting all data-----");
		return mDB.query(DB_TABLE_TAGS, null, null, null, null, null, null);
	}
	
	// добавить запись в DB_TABLE
	public void addCocktailRec(String cocktail_name, String cocktail_info, String video_id, String cathegory, String timing, String tags) 
	{
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME, cocktail_name);
		cv.put(COLUMN_INFO, cocktail_info);
		cv.put(COLUMN_VIDEO, video_id);
		cv.put(COLUMN_CAT, cathegory);
		cv.put(COLUMN_TIMING, timing);
		cv.put(COLUMN_TAGS, tags);
		mDB.insert(DB_TABLE_COCKTAIL, null, cv);
	}
	
	public void addTagRec(String tag_name) 
	{
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME,tag_name);
		mDB.insert(DB_TABLE_TAGS, null, cv);
	}
	
	// добавить запись в DB_TABLE
	public void addCocktailRec(ContentValues cv) 
	{
		mDB.insert(DB_TABLE_COCKTAIL, null, cv);
	}
	
	// добавить запись в DB_TABLE
		public void addTagRec(ContentValues cv) 
		{
			mDB.insert(DB_TABLE_TAGS, null, cv);
		}
	  
	// удалить запись из DB_TABLE
	public void delRec(long id) 
	{
		mDB.delete(DB_TABLE_COCKTAIL, COLUMN_ID + " = " + id, null);
	}
	
	public Cursor search(String query)
	{
		return mDB.query(true, DB_TABLE_COCKTAIL, new String[] { COLUMN_ID, COLUMN_NAME,
				COLUMN_CAT }, COLUMN_NAME + " LIKE" + "'%" + query + "%'", null,
				null, null, null, null);
	}
	
	public Cursor searchCategory(String query)
	{
		return mDB.query(true, DB_TABLE_COCKTAIL, new String[] { COLUMN_ID, COLUMN_NAME,
				COLUMN_CAT }, COLUMN_CAT + " LIKE" + "'%" + query + "%'", null,
				null, null, null, null);
	}
	
	public Cursor searchTags(String query)
	{
		return mDB.query(true, DB_TABLE_COCKTAIL, new String[] { COLUMN_ID, COLUMN_NAME,
				COLUMN_CAT, COLUMN_TAGS }, COLUMN_TAGS + " LIKE" + "'%" + query + "%'", null,
				null, null, null, null);
	}
	
	public Cursor searchInstrumentTags()
	{
		return mDB.query(true, DB_TABLE_COCKTAIL, new String[] { COLUMN_ID, COLUMN_NAME,
				COLUMN_CAT, COLUMN_TAGS }, COLUMN_TAGS + " LIKE" + "'%фигурные ножницы%'" 
		+ " or " + "'%нож для карвинга%'"
		+ " or " + "'%пилер%'"
		+ " or " + "'%нож шато%'"
		+ " or " + "'%нож для сердцевины яблок%'", null, 
				null, null, null, null);
	}
	
	public boolean isTableExists()
	{
	    Cursor cursor = mDB.query(DB_TABLE_COCKTAIL, null, null, null, null, null, null);
	    if(cursor != null) 
	    {
	        if(cursor.getCount() > 0) 
	        {
	        	if(Constants.DEBUG) Log.i(TAG, "table exist");
	            cursor.close();
	            return true;
	        }
	        cursor.close();
	    }
	    if(Constants.DEBUG) Log.i(TAG, "table not exist");
	    return false;
	}
	
	public void clearDatabase() 
	{
		   mDB.delete(DB_TABLE_COCKTAIL, null, null); //erases everything in the table.
		   mDB.close();
	}
	
	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory, int version) 
		{
		      super(context, name, factory, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			// создаем таблицу с полями
			if(Constants.DEBUG) Log.i(TAG, "-----create db-----");
		    db.execSQL(COCKTAIL_CREATE);
		    db.execSQL(TAGS_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if(Constants.DEBUG) Log.w(TAG, "-----try to upgrade table-----");
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_COCKTAIL);
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_TAGS);
			onCreate(db);
		}
		
		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if(Constants.DEBUG) Log.w(TAG, "-----try to downgrade table-----");
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_COCKTAIL);
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_TAGS);
			onCreate(db);
		}
	}
}
