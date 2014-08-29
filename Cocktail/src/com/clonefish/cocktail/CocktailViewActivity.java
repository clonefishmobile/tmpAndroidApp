package com.clonefish.cocktail;


import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.clonefish.cocktail.database.CocktailDAO;
import com.clonefish.cocktail.database.DatabaseHelperFactory;
import com.clonefish.cocktail.database.tables.Cocktail;
import com.clonefish.cocktail.fragments.PageFragment;
import com.clonefish.cocktail.social.SocialActivity;
import com.clonefish.cocktail.utils.StringConverter;
import com.clonefish.cocktail.utils.UniqueArrayList;
import com.j256.ormlite.stmt.QueryBuilder;


public class CocktailViewActivity extends SocialActivity
{
	/**
     * Собсно, будет отображать контент и отвечать за анимации свайпа
     */
    private ViewPager mPager;

    /**
     * Количество страничек
     */
    private int NUM_PAGES;
    
    private String QUERY;
    
    private Boolean CATEGORY = false;
    
    private Boolean NAME;
    
    private List<Cocktail> list;
    
    /**
     * Адаптер, подсовывающий странички mPagerу
     */
    private PagerAdapter mPagerAdapter;
    
    public static final String TAG = "CocktailViewActivity";
    
    public static CocktailViewActivity activity;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);
        NUM_PAGES = getIntent().getIntExtra(MainActivity.NUM_PAGES, 0);
        QUERY = getIntent().getStringExtra("query");
        CATEGORY = getIntent().getBooleanExtra("category", false);
        if(CATEGORY) {
        	list = new UniqueArrayList<Cocktail>();
    	    try {
    			CocktailDAO dao = DatabaseHelperFactory.getHelper().getCocktailDAO();
    			QueryBuilder<Cocktail, ?> queryBuilder = dao.queryBuilder();
    			queryBuilder.where().like(Cocktail.CATEGORY_COLUMN, QUERY);
    			list.addAll(dao.query(queryBuilder.prepare()));
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
        } else if(!QUERY.isEmpty()) {
        	list = new UniqueArrayList<Cocktail>();
    	    String[] separateQuery = StringConverter.convertStringToStringArray(QUERY, " ");
    	    try {
    			CocktailDAO dao = DatabaseHelperFactory.getHelper().getCocktailDAO();
    			QueryBuilder<Cocktail, ?> queryBuilder = dao.queryBuilder();
    			for (String str : separateQuery) 
    			{
    				queryBuilder.where().like(Cocktail.TAG_COLUMN, "%"+ str +"%");
    				list.addAll(dao.query(queryBuilder.prepare()));
    			}
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
        	
        } else {
        	Log.e(TAG, "ALL");

        	list = new UniqueArrayList<Cocktail>();
    	    try {
    			CocktailDAO dao = DatabaseHelperFactory.getHelper().getCocktailDAO();
    			list = dao.queryForAll();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
        }
        activity = this;
        
        // Создаем pager и его адаптер
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), list);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(getIntent().getIntExtra(MainActivity.POSITION, 0), true);
        mPager.setOnPageChangeListener(new OnPageChangeListener() 
        {
			
			@Override
			public void onPageSelected(int pageNumber) 
			{
				PageFragment.onPageChanged();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
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
    }
    @Override
    protected void onPause() 
    {
    	super.onPause();
    }
    
    @Override
    protected void onResume() 
    {
    	super.onResume();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    }
    
    /**
     * Адаптер, который кажет объекты {@link ScreenSlidePageFragment} последовательно
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    	
    	private List<Cocktail> list;
    	
        public ScreenSlidePagerAdapter(FragmentManager fm, List<Cocktail> l) 
        {
            super(fm);
            list = l;
        }

        @Override
        public Fragment getItem(int position) 
        {
            try {
				return PageFragment.create(list.get(position).id);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    
    public int getCurrietItem()
    {
    	return mPager.getCurrentItem();
    }
}
