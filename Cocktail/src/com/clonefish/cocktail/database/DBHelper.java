package com.clonefish.cocktail.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static String LOG_TAG = "DBHelper";
	
	public DBHelper(Context context) 
	{
		super(context, "cocktailDB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(LOG_TAG, "--- onCreate database ---");
		// создаем таблицу с полями
	      db.execSQL("create table mytable ("
	          + "cocktail_id integer primary key autoincrement," 
	          + "cocktail_name text,"
	          + "video_id text,"
	          + "cocktail_info text" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
