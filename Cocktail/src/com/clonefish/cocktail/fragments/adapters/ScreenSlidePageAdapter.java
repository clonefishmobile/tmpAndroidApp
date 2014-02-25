package com.clonefish.cocktail.fragments.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clonefish.cocktail.R;
import com.clonefish.cocktail.fragments.CocktailInfoFragment;
import com.clonefish.cocktail.fragments.RecepieFragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

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
    
    private YouTubePlayerSupportFragment videoFragment;
    private RecepieFragment recepie;
    private CocktailInfoFragment cockteilInfo;
    
    /**
     * Факторка для фрагментов. Делает фрагмент с заданым номером странички
     */
    public static ScreenSlidePageAdapter create(int numOfPage) 
    {
        ScreenSlidePageAdapter fragment = new ScreenSlidePageAdapter();
        Bundle args = new Bundle();
        //аналогично номер странички
        args.putInt(ARG_PAGE, (numOfPage+1));
        //и все сохраняем
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageAdapter() 
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        if(videoFragment == null)
        {
        	videoFragment = new YouTubePlayerSupportFragment();
        	videoFragment.initialize("AIzaSyC_entdejj1ep8RIeoIFJIcuxeXPTacmGw", new YouTubeInitListener());
        }
        
        if(recepie == null) recepie = new RecepieFragment();
        
        if(cockteilInfo == null) cockteilInfo = new CocktailInfoFragment();
        
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.
        	add(R.id.video, videoFragment).
        	add(R.id.recepie, recepie).
        	add(R.id.place_for_cocktail_info, cockteilInfo).
        	commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Берем корневой вью
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_screen, container, false);
        rootView.setId(mPageNumber);
        return rootView;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    	transaction.
    		remove(videoFragment).
    		remove(recepie).
    		remove(cockteilInfo).
    		commit();
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	videoFragment = null;
    	recepie = null;
    	cockteilInfo = null;
    }
    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

	
	private class YouTubeInitListener implements OnInitializedListener
	{
		@Override
		public void onInitializationFailure(Provider provider, YouTubeInitializationResult msg) {
			Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
			Log.e("video", "all bad" + videoFragment.getParentFragment().getId());
		}
		
		@Override
		public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) 
		{
			
		}
	}
}
