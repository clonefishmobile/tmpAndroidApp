package com.clonefish.cocktail.fragments;

import java.sql.SQLException;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clonefish.cocktail.CocktailViewActivity;
import com.clonefish.cocktail.R;
import com.clonefish.cocktail.database.CocktailDAO;
import com.clonefish.cocktail.database.DatabaseHelperFactory;
import com.clonefish.cocktail.utils.StringConverter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * 
 * @author Tigli
 * Основной фрагмент, отображает все
 */
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
    /**
     * Фрагмент для ТыТрубы
     */
    private YouTubePlayerSupportFragment videoFragment;
    /**
     * Плеер ТыТрубы
     */
    private YouTubePlayer videoPlayer;
    private RecepieFragment recepie;
    private CocktailInfoFragment cockteilInfo;
    private int savedTime = 0;
    private LinearLayout buttonLayout;
    
    /**
     * Факторка для фрагментов. Делает фрагмент с заданым номером странички
     * @throws SQLException 
     */
    public static PageFragment create(int numOfPage) throws SQLException 
    {
    	CocktailDAO dao = DatabaseHelperFactory.getHelper().getCocktailDAO();
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        //номер странички
        args.putInt(ARG_PAGE, (numOfPage));
        //ид видео
        args.putString(ARG_VIDEO_ID, dao.queryForId(numOfPage).video_id);
        //timing
        args.putIntArray(ARG_VIDEO_TIMING, StringConverter.convertStringToIntArray(dao.queryForId(numOfPage).timing));
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
    	/*
    	 * Сохраняем ид видео, текущее время и НА ВСЯКИЙ СЛУЧАЙ убираем с экрана все фрагменты,
    	 * но не трем их
    	 */
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
    	/*
    	 * Все трем нафиг, чтоб уж наверняка
    	 */
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
    	//начинаем транзакции фрагментов
    	FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    	//проверяем есть ли фрагмерт видяшки
    	if(videoFragment == null)
        {
    		//делает новый фрагмент
        	videoFragment = YouTubePlayerSupportFragment.newInstance();
        	onSetCurriet();
        	//засовываем на экран
        	transaction.add(R.id.video, videoFragment);
        }
        
    	//проверяем есть ли фрагмент
        if(recepie == null)
        {
        	//делаем новый фрагмент и засовываем на экран
        	recepie = new RecepieFragment();
        	transaction.add(R.id.recepie, recepie);
        }
        
      //проверяем есть ли фрагмент
        if(cockteilInfo == null) 
        {
        	//делаем новый фрагмент и засовываем на экран
        	cockteilInfo = new CocktailInfoFragment();
        	cockteilInfo.setPageNumber(mPageNumber);
        	transaction.add(R.id.place_for_cocktail_info, cockteilInfo);
        }
        
        //если есть транзакции, то их комитим
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
    
    /**
     * 
     * @author Tigli
     * Слушатель инициализации плеера ТыТрубы
     */
	private class YouTubeInitListener implements OnInitializedListener
	{
		
		@Override
		/**
		 * Если все плохо
		 */
		public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
			//но пользователь может это поправить
			if (errorReason.isUserRecoverableError()) 
			{
				//то просим его это сделать
				errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
			} else {
				//А если нет, то выводим ошибка на экран. Долго, чтоб точно успеть заметить
				Toast.makeText(getActivity(), errorReason.toString(), Toast.LENGTH_LONG).show();
				//и пишим в лог
				Log.e("SSPA", "video player init fail " + errorReason.toString());
			}
		}
		
		@Override
		/**
		 * Если все хорошо
		 */
		public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) 
		{
			Log.w("SSPA", "-----fragment "+ mPageNumber + " video player init----- ");
			//то инитим плеер
			videoPlayer = player;
			//и если страница является текущей
			if(CocktailViewActivity.activity.getCurrietItem() == mPageNumber)
			{
				//то начинаем грузить видео
				videoPlayer.cueVideo(video_id, savedTime);
				Log.i("SSPA", "-----fragment "+ mPageNumber + " video cued----- " + videoPlayer.isPlaying());
			}
		}
	}
	
	/**
	 * Запускается при смене страницы
	 */
	public static void onPageChanged()
	{
		for(int i = 0; i < pages.size(); i++)
		{
			pages.get(i).onSetCurriet();
		}
	}
	
	/**
	 * Проверяем текущая ли страничка и все инитим с проверками
	 */
	public void onSetCurriet()
	{
		Log.d("SSPA", "-----fragment "+ mPageNumber + " run onSetCurriet-----");
		//если плеера нету
		if(videoPlayer == null)
		{
			if(CocktailViewActivity.activity.getCurrietItem() == mPageNumber)
			{
				Log.i("SSPA", "-----fragment "+ mPageNumber + " player is null, create new----- ");
				//то запускаем его инициализацию давай апи ключ и слушатель
				videoFragment.initialize("AIzaSyC_entdejj1ep8RIeoIFJIcuxeXPTacmGw", new YouTubeInitListener());
			} else {
				Log.i("SSPA", "-----fragment "+ mPageNumber + " nothing to do with player----- ");
			}
			//Если таки плеер есть
		} else {
			Log.i("SSPA", "-----fragment "+ mPageNumber + " player is not null, try to reset----- ");
			//проверем является ли страница текущей
			if(CocktailViewActivity.activity.getCurrietItem() != mPageNumber)
			{
				//Если нет, то проверям состояние плеера
				if(videoPlayer != null)
				{
					//если он есть
					Log.i("SSPA", "-----fragment "+ mPageNumber + " resets player----- ");
					//то сохроняем время
					savedTime = videoPlayer.getCurrentTimeMillis();
					//пользуемся апишной функцией для очистки
					videoPlayer.release();
					//и трем на всякий случай
					videoPlayer = null;
				}
				Log.e("SSPA", "-----!fragment "+ mPageNumber + " video player is null!----- ");
			}
		}
	}
	
	/**
	 * Делаем кнопки тайминга
	 */
	public void createTimingButton()
	{
		for (int time : timing) 
		{
			final int set = time;
			Log.e(ARG_VIDEO_TIMING, "" + mPageNumber + " " + time);
			Button bb = new Button(getActivity());
			bb.setText("1");
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
