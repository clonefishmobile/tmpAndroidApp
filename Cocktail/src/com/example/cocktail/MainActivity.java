package com.example.cocktail;

import com.example.cocktail.fragments.ZoomOutPageTransformer;
import com.example.cocktail.fragments.adapters.ScreenSlidePageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
/*
 * TODO ПРО СВАЙП!!!
 * Вообщем, надо разобраться с свайпом, но уже поздно.
 * А мысли записать надо.
 * Вообщем так.
 * 		1. Создать класс адаптер, для страничек для свайпа 
 * (они у мобилок и таблеток разные как бы будут)
 * ОТ этого класса будут наследоваться все последующие странички.
 * 		2. В зависимости от того, что у нас - мобилка или таблетка - на ViewPager
 * будут пихаться соответствующие странички ( а так как они наследуються от одного адаптера - то пох)
 * 		3. Научиться пихать соответствующий лайаут в зависимости от размеров экрана
 * 		4. ...
 */
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
        setContentView(R.layout.activity_main);
        activity = this;
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
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
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
