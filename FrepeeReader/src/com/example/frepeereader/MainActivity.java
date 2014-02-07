package com.example.frepeereader;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


/**
 * 
 * @author Fluffy
 *
 * TLDR</br>
 * На самом деле мы тут делаем фрагменты программно, что собсно и не хорошо, и не плохо</br>
 * ОДНАКО можно фрагментики указывать в xml и причем еще и для разных размеров экрана</br>
 * Дела блин</br>
 */
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

    public static MainActivity activity;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        activity = this;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Добовлаем кнопки туда/закончить (смотря чо кажем)
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_previous:
                //идем обратно (или ничо не делаем, если уже на первой страничке)
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                //идем туда, или ничо не делаем, если уже на послденей страничке
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

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
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
