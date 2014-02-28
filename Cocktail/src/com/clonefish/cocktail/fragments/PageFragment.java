package com.clonefish.cocktail.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clonefish.cocktail.MainActivity;
import com.clonefish.cocktail.R;
import com.clonefish.cocktail.VideoManager;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class PageFragment extends Fragment
{
    /**
     * Ключик для аргумента, в которым сохраним номер странички фрагмента (чтоб усе было по порядку)
     */
    public static final String ARG_PAGE = "page";
    
    /**
     * Ключик для аргумента, в которым сохраним номер странички фрагмента когда он войдет в onSaveInstanceState
     */
    public static final String ARG_SAVED_PAGE = "page_saved";
    
    /**
     * Ключик для аргумента, в которым сохраним ид видео фрагмента
     */
    public static final String ARG_VIDEO_ID = "video_id";
    
    /**
     * Ключик для аргумента, в которым сохраним ид видео фрагмента
     */
    public static final String ARG_SAVED_VIDEO_ID = "video_id_saved";
    
    /**
     * Номер странички, берется из {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    private String video_id;
    
    private YouTubePlayerSupportFragment videoFragment;
    private YouTubePlayer videoPlayer;
    private RecepieFragment recepie;
    private CocktailInfoFragment cockteilInfo;
    
    /**
     * Факторка для фрагментов. Делает фрагмент с заданым номером странички
     */
    public static PageFragment create(int numOfPage) 
    {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        //номер странички
        args.putInt(ARG_PAGE, (numOfPage));
        //ид видео
        args.putString(ARG_VIDEO_ID, MainActivity.getCocktailList().get(numOfPage).video_id);
        //и все сохраняем
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() 
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Log.d("SSPA", "-----start creating fragment-----");
        if(savedInstanceState != null)
        {
        	Log.i("SSPA", "restore from saved instance");
        	mPageNumber = savedInstanceState.getInt(ARG_SAVED_PAGE);
        	video_id = savedInstanceState.getString(ARG_SAVED_VIDEO_ID);
        	Log.i("SSPA", "pages is " + mPageNumber + " video is " + video_id);
        } else {
        	Log.i("SSPA", "create new instance");
        	mPageNumber = getArguments().getInt(ARG_PAGE);
        	video_id = getArguments().getString(ARG_VIDEO_ID);
        	Log.i("SSPA", "pages is " + mPageNumber + " video is " + video_id);
        }
        
        if(videoFragment == null)
        {
        	videoFragment = YouTubePlayerSupportFragment.newInstance();
        	if(VideoManager.getInstance().getCur() == mPageNumber) onSetCurriet();
//        	videoFragment.initialize("AIzaSyC_entdejj1ep8RIeoIFJIcuxeXPTacmGw", new YouTubeInitListener());
        }
        
        if(recepie == null) recepie = new RecepieFragment();
        
        if(cockteilInfo == null) 
        {
        	cockteilInfo = new CocktailInfoFragment();
        }
        
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.
	        add(R.id.video, videoFragment).
	        add(R.id.recepie, recepie).
	        add(R.id.place_for_cocktail_info, cockteilInfo).
	        commit();
        
        Log.d("SSPA", "-----fragment "+ mPageNumber + " created-----");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Берем корневой вью
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_screen, container, false);
        Log.v("SSPA", "-----fragment "+ mPageNumber + " view created----- " + this.getId());
        return rootView;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	Log.v("SSPA", "-----fragment "+ mPageNumber + " started-----");
        cockteilInfo.setInfo(mPageNumber);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " is on saving-----");
    	outState.putInt(ARG_SAVED_PAGE, mPageNumber);
    	outState.putString(ARG_SAVED_VIDEO_ID, video_id);
    	Log.i("SSPA", "saved pages is " + mPageNumber + " saved video is " + video_id);
    	FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    	transaction.
    		remove(videoFragment).
    		remove(recepie).
    		remove(cockteilInfo).
    		commit();
    	onSetCurriet();
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " saved-----");
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	Log.d("SSPA", "-----fragment "+ mPageNumber + " stoped-----");
    	videoFragment = null;
    	recepie = null;
    	cockteilInfo = null;
    	videoPlayer = null;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " paused-----");
    }
    
    @Override
    public void onResume() 
    {
    	super.onResume();
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " resume----- ");
    }
    /**
     * Returns the page number represented by this fragment object.tag:^(?!dalvikvm)tag:^(?!dalvikvm)
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    
	private class YouTubeInitListener implements OnInitializedListener
	{
		@Override
		public void onInitializationFailure(Provider provider, YouTubeInitializationResult msg) {
			Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
		}
		
		@Override
		public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) 
		{
			Log.w("SSPA", "-----fragment "+ mPageNumber + " video player init----- ");
			videoPlayer = player;
			if(VideoManager.getInstance().getCur() == mPageNumber)
			{
				videoPlayer.cueVideo(video_id);
				Log.i("SSPA", "-----fragment "+ mPageNumber + " video cued----- " + toString());
			}
		}
	}
	
	public void onSetCurriet()
	{
		Log.d("SSPA", "-----fragment "+ mPageNumber + " run onSetCurriet in " + Thread.currentThread().getName() + "-----");
		if(videoPlayer == null)
		{
			if(VideoManager.getInstance().getCur() == mPageNumber)
			{
				Log.i("SSPA", "-----fragment "+ mPageNumber + " try to init player----- ");
				videoFragment.initialize("AIzaSyC_entdejj1ep8RIeoIFJIcuxeXPTacmGw", new YouTubeInitListener());
			}
		} else {
			if(VideoManager.getInstance().getCur() != mPageNumber)
			{
				Log.i("SSPA", "-----fragment "+ mPageNumber + " resets player----- ");
				if(videoPlayer != null) videoPlayer.release();
				Log.i("SSPA", "-----fragment "+ mPageNumber + " video player is null----- ");
			}
		}
	}
}
