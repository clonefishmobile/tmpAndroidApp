package com.example.cocktail.fragments.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cocktail.R;
import com.example.cocktail.fragments.CocktailInfo;
import com.example.cocktail.fragments.Recepie;

public class ScreenSlidePageAdapter extends Fragment 
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
    public static ScreenSlidePageAdapter create(int numOfPage) 
    {
        ScreenSlidePageAdapter fragment = new ScreenSlidePageAdapter();
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

    public ScreenSlidePageAdapter() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Берем корневой вью
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_screen, container, false);
        rootView = new CocktailInfo(rootView, getActivity()).getView();
        rootView = new Recepie(rootView, getActivity()).getView();
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
