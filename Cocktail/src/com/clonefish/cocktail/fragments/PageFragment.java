package com.clonefish.cocktail.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clonefish.cocktail.CocktailViewActivity;
import com.clonefish.cocktail.MainActivity;
import com.clonefish.cocktail.R;
import com.facebook.widget.FacebookDialog;
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
    
    public static final String ARG_VIDEO_TIMING = "video_timing";
    
    /**
     * Ключик для аргумента, в которым сохраним ид видео фрагмента когда он войдет в onSaveInstanceState
     */
    public static final String ARG_SAVED_VIDEO_ID = "video_id_saved";
    
    /**
     * Ключик для аргумента, в которым сохраним время видео, на котором мы ушли в паузу
     */
    public static final String ARG_SAVED_VIDEO_TIME = "video_time_saved";
    
    public static final String ARG_SAVED_VIDEO_TIMING = "video_timing_saved";
    
    private static ArrayList<PageFragment> pages = new ArrayList<PageFragment>();
    
    /**
     * Номер странички, берется из {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    /**
     * Ид видео, берется из {@link #ARG_VIDEO}.
     */
    private String video_id;
    private int[] timing;
    private YouTubePlayerSupportFragment videoFragment;
    private YouTubePlayer videoPlayer;
    private RecepieFragment recepie;
    private CocktailInfoFragment cockteilInfo;
    private int savedTime = 0;
    private LinearLayout buttonLayout;
    
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
        //timing
        args.putIntArray(ARG_VIDEO_TIMING, MainActivity.getCocktailList().get(numOfPage).timing);
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
        	savedTime = savedInstanceState.getInt(ARG_SAVED_VIDEO_TIME, 0);
        	timing = savedInstanceState.getIntArray(ARG_SAVED_VIDEO_TIMING);
        } else {
        	Log.i("SSPA", "create new instance");
        	mPageNumber = getArguments().getInt(ARG_PAGE);
        	video_id = getArguments().getString(ARG_VIDEO_ID);
        	timing = getArguments().getIntArray(ARG_VIDEO_TIMING);
        }
        
        Log.d("SSPA", "-----fragment "+ mPageNumber + " created-----");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Берем корневой вью
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_screen, container, false);
        buttonLayout = (LinearLayout) rootView.findViewById(R.id.video_timing);
        createTimingButton();
        Log.v("SSPA", "-----fragment "+ mPageNumber + " view created----- ");
        return rootView;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	Log.v("SSPA", "-----fragment "+ mPageNumber + " started-----");
        pages.add(this);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " is on saving-----");
    	outState.putInt(ARG_SAVED_PAGE, mPageNumber);
    	outState.putString(ARG_SAVED_VIDEO_ID, video_id);
    	outState.putIntArray(ARG_SAVED_VIDEO_TIMING, timing);
    	if(videoPlayer != null) outState.putInt(ARG_SAVED_VIDEO_TIME, videoPlayer.getCurrentTimeMillis());
    	onSetCurriet();
    	FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    	transaction.
    		remove(videoFragment).
    		remove(recepie).
    		remove(cockteilInfo).
    		commit();
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " saved-----");
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	videoFragment = null;
    	recepie = null;
    	cockteilInfo = null;
    	videoPlayer = null;
    	pages.remove(this);
    	Log.d("SSPA", "-----fragment "+ mPageNumber + " stoped-----");
    }
    
    @Override
    public void onDestroy() {
    	Log.d("SSPA", "-----fragment "+ mPageNumber + " destroed-----");
    	super.onDestroy();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " paused-----");
    }
    
    @Override
    public void onResume() 
    {
    	FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    	if(videoFragment == null)
        {
        	videoFragment = YouTubePlayerSupportFragment.newInstance();
        	onSetCurriet();
        	transaction.add(R.id.video, videoFragment);
        }
        
        if(recepie == null)
        {
        	recepie = new RecepieFragment();
        	transaction.add(R.id.recepie, recepie);
        }
        
        if(cockteilInfo == null) 
        {
        	cockteilInfo = new CocktailInfoFragment();
        	cockteilInfo.setPageNumber(mPageNumber);
        	transaction.add(R.id.place_for_cocktail_info, cockteilInfo);
        }
        if(!transaction.isEmpty()) transaction.commit();
//        pages.add(this);
    	super.onResume();
    	Log.w("SSPA", "-----fragment "+ mPageNumber + " resume----- ");
    }
    /**
     * Returns the page number represented by this fragment
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    
	private class YouTubeInitListener implements OnInitializedListener
	{
		
		@Override
		public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
			if (errorReason.isUserRecoverableError()) 
			{
				errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
			} else {
				Toast.makeText(getActivity(), errorReason.toString(), Toast.LENGTH_LONG).show();
			}
		}
		
		@Override
		public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) 
		{
			Log.w("SSPA", "-----fragment "+ mPageNumber + " video player init----- ");
			videoPlayer = player;
			if(CocktailViewActivity.activity.getCurrietItem() == mPageNumber)
			{
				videoPlayer.cueVideo(video_id, savedTime);
				Log.i("SSPA", "-----fragment "+ mPageNumber + " video cued----- ");
			}
		}
	}
	
	public static void onPageChanged()
	{
		for(int i = 0; i < pages.size(); i++)
		{
			pages.get(i).onSetCurriet();
		}
	}
	
	public void onSetCurriet()
	{
		Log.d("SSPA", "-----fragment "+ mPageNumber + " run onSetCurriet-----");
		if(videoPlayer == null)
		{
			if(CocktailViewActivity.activity.getCurrietItem() == mPageNumber)
			{
				Log.i("SSPA", "-----fragment "+ mPageNumber + " player is null, create new----- ");
				videoFragment.initialize("AIzaSyC_entdejj1ep8RIeoIFJIcuxeXPTacmGw", new YouTubeInitListener());
			} else {
				Log.i("SSPA", "-----fragment "+ mPageNumber + " nothing to do with player----- ");
			}
		} else {
			Log.i("SSPA", "-----fragment "+ mPageNumber + " player is not null, try to reset----- ");
			if(CocktailViewActivity.activity.getCurrietItem() != mPageNumber)
			{
				if(videoPlayer != null)
				{
					Log.i("SSPA", "-----fragment "+ mPageNumber + " resets player----- ");
					savedTime = videoPlayer.getCurrentTimeMillis();
					videoPlayer.release();
					videoPlayer = null;
				}
				Log.i("SSPA", "-----fragment "+ mPageNumber + " video player is null----- ");
			}
		}
	}
	
	public void createTimingButton()
	{
		for (int time : timing) 
		{
			final int set = time;
			Log.e(ARG_VIDEO_TIMING, "" + mPageNumber + " " + time);
			ImageButton bb = new ImageButton(getActivity());
			bb.setImageResource(R.drawable.ic_launcher);
			bb.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(videoPlayer != null)
					{
						videoPlayer.seekToMillis(set);
					}
				}
			});
			buttonLayout.addView(bb);
		}
	}
}
