package com.example.cocktail;

import java.io.IOException;

import com.example.cocktail.fragments.tablet.ScreenSlidePageTabletAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class TabletActivity extends FragmentActivity
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
        setContentView(R.layout.main_tablet_sreen);
        try {
        	NUM_PAGES = getAssets().list("docs").length;
        } catch (IOException e) {
        	e.printStackTrace();
        }

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
            return ScreenSlidePageTabletAdapter.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
