package com.clonefish.cocktail.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper 
{
	private static final String TAG = DatabaseHelper.class.getSimpleName();

	//имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
	private static final String DATABASE_NAME ="ibartender.db";
	
	//с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
	private static final int DATABASE_VERSION = 1;
	   
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,int arg3) 
	{
		// TODO Auto-generated method stub
		
	}
}
