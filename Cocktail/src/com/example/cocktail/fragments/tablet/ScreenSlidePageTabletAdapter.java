package com.example.cocktail.fragments.tablet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cocktail.R;

public class ScreenSlidePageTabletAdapter extends Fragment 
{
	/**
     * Ключик для аргумента, в которым сохраним имя файла который отобразит фрагмент
     */
    public static final String ARG_FILENAME = "filename";
    
    /**
     * Ключик для аргумента, в которым сохраним номер странички фрагмента (чтоб усе было по порядку)
     */
    public static final String ARG_PAGE = "page";
    
    /**
     * Ключик для аргумента, в которым сохраним имя картинки который отобразит фрагмент
     */
    public static final String ARG_IMAGENAME = "image";
    
    /**
     * Номер странички, берется из {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    /**
     * Факторка для фрагментов. Делает фрагмент с заданым номером странички
     */
    public static ScreenSlidePageTabletAdapter create(int numOfPage) 
    {
        ScreenSlidePageTabletAdapter fragment = new ScreenSlidePageTabletAdapter();
        Bundle args = new Bundle();
        //привязываем к фрагменту файлик аргументом
        args.putString(ARG_FILENAME, "docs/chapter_" + (numOfPage+1) + ".txt");
        //аналогично номер странички
        args.putInt(ARG_PAGE, (numOfPage+1));
        //и картинку
        args.putString(ARG_IMAGENAME, "img/image_" + (numOfPage+1) + ".jpg");
        //и все сохраняем
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageTabletAdapter() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Берем корневой вью
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tablet_screen_layout, container, false);
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
