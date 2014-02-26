package com.clonefish.cocktail;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.clonefish.cocktail.database.DB;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor>
{
    public static MainActivity activity;
    
    private ListView cocktailList;
    private DB db;
    private SimpleCursorAdapter scAdapter;
    private static ArrayList<Cocktail> cocktailArray = new ArrayList<Cocktail>();
    
    public static final String POSITION = "position";
    public static final String NUM_PAGES = "num";
    
    /*
     * АХТУНГ АЛЯРМ!
     * ЕСЛИ В ПЕРВЫЙ РАЗ ЗАПУСКАЕТЬСЯ ПРИЛОЖЕНИЕ НА ДЕВАЙСЕ
     * ТО НУЖНО РАСКОМЕНТИТЬ createDB() в onСreate чтобы дб сделать
     */
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        activity = this;
        
        db = new DB(this);
        db.open();
        /*
         * АХТУНГ АЛЯРМ!
         * ЕСЛИ В ПЕРВЫЙ РАЗ ЗАПУСКАЕТЬСЯ ПРИЛОЖЕНИЕ НА ДЕВАЙСЕ
         * ТО НУЖНО РАСКОМЕНТИТЬ createDB() в onСreate чтобы дб сделать
         */
//        createDB();
        
        // формируем столбцы сопоставления
        String[] from = new String[] {DB.COLUMN_NAME}; //что искать 
        int[] to = new int[] {R.id.cocktailName}; //куда класть

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        cocktailList = (ListView) findViewById(R.id.cocktail_list);
        cocktailList.setAdapter(scAdapter);
        cocktailList.setOnItemClickListener(new OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				//длеаем интент для запуска новой активити
				Intent intent = new Intent(activity, CocktailViewActivity.class);
				//сохроняем туда номер позиции клика (соответствует номеру странички на PageView
				intent.putExtra(POSITION, position);
				//сохроняем туда количество страниц (элементов в ListView соответственно)
				intent.putExtra(NUM_PAGES, cocktailArray.size());
				//запускаем новую активити
				startActivity(intent);
			}
        	
        });
        
        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() 
    {
    	super.onDestroy();
    	db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
      return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
      scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
    
    static class MyCursorLoader extends CursorLoader {

      DB db;
      
      public MyCursorLoader(Context context, DB db) {
        super(context);
        this.db = db;
      }
      
      @Override
      public Cursor loadInBackground() {
        Cursor cursor = db.getAllData();
        Log.w("main", "" + cursor.getCount());
        createCocktailList(cursor);
        return cursor;
      }
      
    }
    
    private static void createCocktailList(Cursor cursor)
    {
    	Cursor allData = cursor;
    	allData.moveToFirst();
    	for(int i = 0; i < allData.getCount(); i++)
    	{
    		String name = allData.getString(allData.getColumnIndex(DB.COLUMN_NAME));
    		String[] tags = null;
    		String text  = allData.getString(allData.getColumnIndex(DB.COLUMN_INFO));
    		String video_id = allData.getString(allData.getColumnIndex(DB.COLUMN_VIDEO));
    		
    		cocktailArray.add(new Cocktail(name, tags, text, video_id));
    		allData.moveToNext();
    	}
    }
    
    private void createDB()
    {
        String[] video_id = {"0_u_DeUOBj0", "9WZhBBy5SwE", "zCde1kb4_zw", "9yK0bR3waOg"};
        String[] cocktail_name = {"Оригами", "Огуречный рома", "Гарниш", "Клубничный веер"};
        String[] cocktail_info = {
        		"Источник безграничной книжной романтики и неиссякаемого аппетита во время званных ужинов. " +
        		"Получается путем перегонки сидра из разных сортов яблок, выдержанного в дубовых бочках не менее 2 лет." +
        		"Как говорит героиня Ремарка, - кальвадос скорее вдыхаешь, чем пьешь и, попробовав один раз, ты мечтаешь об этом." +
        		"Со своей стороны добавим главное - первое трепетное знакомство, для которого рекомендуем Джек Роуз." +
        		"40%, 700 мл, Франция.", 
        		"Источник безграничной книжной романтики и неиссякаемого аппетита во время званных ужинов. " +
                "Получается путем перегонки сидра из разных сортов яблок, выдержанного в дубовых бочках не менее 2 лет." +
                "Как говорит героиня Ремарка, - кальвадос скорее вдыхаешь, чем пьешь и, попробовав один раз, ты мечтаешь об этом." +
                "Со своей стороны добавим главное - первое трепетное знакомство, для которого рекомендуем Джек Роуз." +
                "40%, 700 мл, Франция.", 
                "Источник безграничной книжной романтики и неиссякаемого аппетита во время званных ужинов. " +
                "Получается путем перегонки сидра из разных сортов яблок, выдержанного в дубовых бочках не менее 2 лет." +
                "Как говорит героиня Ремарка, - кальвадос скорее вдыхаешь, чем пьешь и, попробовав один раз, ты мечтаешь об этом." +
                "Со своей стороны добавим главное - первое трепетное знакомство, для которого рекомендуем Джек Роуз." +
                "40%, 700 мл, Франция.", 
                "Источник безграничной книжной романтики и неиссякаемого аппетита во время званных ужинов. " +
                "Получается путем перегонки сидра из разных сортов яблок, выдержанного в дубовых бочках не менее 2 лет." +
                "Как говорит героиня Ремарка, - кальвадос скорее вдыхаешь, чем пьешь и, попробовав один раз, ты мечтаешь об этом." +
                "Со своей стороны добавим главное - первое трепетное знакомство, для которого рекомендуем Джек Роуз." +
                "40%, 700 мл, Франция."};
        
        for(int i = 0; i < 4; i++)
        {
        	db.addRec(cocktail_name[i], cocktail_info[i], video_id[i]);
        }
    }
    
    public static ArrayList<Cocktail> getCocktailList()
    {
    	return cocktailArray;
    }
}