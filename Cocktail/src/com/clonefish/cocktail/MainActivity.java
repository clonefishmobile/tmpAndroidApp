package com.clonefish.cocktail;

import com.clonefish.cocktail.fragments.ZoomOutPageTransformer;
import com.clonefish.cocktail.fragments.adapters.ScreenSlidePageAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NUM_PAGES = 5;
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
}
