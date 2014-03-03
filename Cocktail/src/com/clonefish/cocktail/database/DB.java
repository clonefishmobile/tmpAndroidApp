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
	private static final int DB_VERSION = 1;
	private static final String DB_TABLE = "cocktail_table";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_VIDEO = "video_id";
	public static final String COLUMN_NAME = "cocktail_name";
	public static final String COLUMN_INFO = "cocktail_info";
	
	private static final String TAG = "DB";
	
	private static final String DB_CREATE = 
		"create table " + DB_TABLE + "(" +
		COLUMN_ID + " integer primary key autoincrement, " +
		COLUMN_NAME + " text, " +
		COLUMN_INFO + " text, " +
		COLUMN_VIDEO + " text " +
		");";
		  
	private final Context dbContext;
		  
	private DBHelper dbHelper;
	private SQLiteDatabase mDB;
	
	public DB(Context ctx) 
	{
		dbContext = ctx;
	}
	
//	открыть подключение
	public void open()
	{
		dbHelper = new DBHelper(dbContext, DB_NAME, null, DB_VERSION);
		mDB = dbHelper.getWritableDatabase();
	}
	
	//закрыть подключение
	public void close()
	{
		if (dbHelper!=null) dbHelper.close();
	}
	
	// получить все данные из таблицы DB_TABLE
	public Cursor getAllData()
	{
		return mDB.query(DB_TABLE, null, null, null, null, null, null);
	}
	
	// добавить запись в DB_TABLE
	public void addRec(String cocktail_name, String cocktail_info, String video_id) 
	{
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME, cocktail_name);
		cv.put(COLUMN_INFO, cocktail_info);
		cv.put(COLUMN_VIDEO, video_id);
		mDB.insert(DB_TABLE, null, cv);
	}
	
	// добавить запись в DB_TABLE
	public void addRec(ContentValues cv) 
	{
		mDB.insert(DB_TABLE, null, cv);
	}
	  
	// удалить запись из DB_TABLE
	public void delRec(long id) 
	{
		mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
	}
	
	public boolean isTableExists()
	{
	    Cursor cursor = mDB.query(DB_TABLE, null, null, null, null, null, null);
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
	
	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory, int version) 
		{
		      super(context, name, factory, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			// создаем таблицу с полями
			if(Constants.DEBUG) Log.i(TAG, "create db");
		    db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
	}
}
