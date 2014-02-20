package com.clonefish.cocktail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.clonefish.cocktail.database.DBHelper;
import com.clonefish.cocktail.fragments.adapters.ScreenSlidePageAdapter;

public class MainActivity extends FragmentActivity
{
	/**
     * Собсно, будет отображать контент и отвечать за анимации свайпа
     */
    private ViewPager mPager;

    /**
     * Количество страничек
     */
    private static int NUM_PAGES;
    
    /**
     * Адаптер, подсовывающий странички mPagerу
     */
    private PagerAdapter mPagerAdapter;
    
    public DBHelper dbHelper;
    
    public static MainActivity activity;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        activity = this;
        // Создаем pager и его адаптер
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //ресетим акшон бар, т.к. каждый фрагмент акшоны сам обрабатывает
            	//но при должно желаниии обработку можно перекинуть на активити
                invalidateOptionsMenu();
            }
        });
//        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
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
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	 Log.d("CREATE_DB", "--- Clear mytable: ---");
         // удаляем все записи
         int clearCount = db.delete("mytable", null, null);
         Log.d("CREATE_DB", "deleted rows count = " + clearCount);
    }
    /**
     * Адаптер, который кажет объекты {@link ScreenSlidePageFragment} последовательно
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageAdapter.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    
    private void createDB()
    {
//    		mytable
//  	    cocktail_id
//  	    cocktail_name
//  	    video_id
//  	    cocktail_info
    	
    	
    	dbHelper = new DBHelper(this);
    	
        String[] video = {"0_u_DeUOBj0", "9WZhBBy5SwE", "zCde1kb4_zw", "9yK0bR3waOg"};
        String[] cockteil = {"Оригами", "Огуречный рома", "Гарниш", "Клубничный веер"};
        String[] cockteil_text = {
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
        
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(int i = 0; i < 4; i++)
        {
        	// создаем объект для данных
        	ContentValues cv = new ContentValues();
        	
        	// подключаемся к БД
        	
        	cv.put("cocktail_name", cockteil[i]);
        	cv.put("video_id", video[i]);
        	cv.put("cocktail_info", cockteil_text[i]);
        	
        	long rowID = db.insert("mytable", null, cv);
            Log.d("CREATE_DB", "row inserted, ID = " + rowID);
        }
        
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        
     	c.moveToLast();
     	NUM_PAGES = c.getPosition(); 
        
        dbHelper.close();
    }
}
