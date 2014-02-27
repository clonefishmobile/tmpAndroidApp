package com.clonefish.cocktail;

import java.util.ArrayList;

import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

public class VideoManager 
{
	private static volatile VideoManager INSTANCE;
	
	private int previusItem;
	private int currietItem;
	private int nextItem;
	private int maxItems;
	private ArrayList<QueneItem> quene = new ArrayList<QueneItem>();
	
	/**
	 * Делаем синглтоны правильно
	 * 2 Double Checked Locking & volatile
	 * @see http://habrahabr.ru/post/129494/
	 */
	public static VideoManager getInstance() 
	{
		VideoManager localInstance = INSTANCE;
		if(localInstance == null) // проверка номер раз
		{
			synchronized (VideoManager.class) //синхронизируемся, чтоб многопоточность все не ломала
			{
				if(localInstance == null) //проверка номер два
				{
					INSTANCE = localInstance = new VideoManager(); //инициализируем
				}
			}
		}
		return localInstance;
	}
	
	public void onItemChanged(int newCurriet)
	{
		if(newCurriet > currietItem)
		{
			previusItem = newCurriet - 1;
			currietItem = newCurriet;
			if((newCurriet + 1) < maxItems) 
			{
				nextItem = newCurriet + 1; 
			} else nextItem = -1;
		} else {
			currietItem = newCurriet;
			nextItem = newCurriet + 1;
			if(newCurriet-- > 0) 
			{
				previusItem = currietItem - 1; 
			} else previusItem = -1;
		}
		Log.i("VideoManager", previusItem + " " + currietItem + " " + nextItem);
//		updateQuene(currietItem);
	}
	
	public void init(int curItem, int maxItems)
	{
		this.maxItems = maxItems;
		onItemChanged(curItem);
	}
	
	public void updateQuene(int itemNo)
	{
		QueneItem next = null;
		QueneItem cur = null;
		QueneItem prev = null;
		for(int i = 0; i<quene.size(); i++)
		{
			if(quene.get(i).id == itemNo)
			{
				cur = quene.get(i);
				if(itemNo == 0)
				{
					next = quene.get(i++);
					break;
				} else if(itemNo == maxItems)
				{
					prev = quene.get(i--);
					break;
				} else {
					next = quene.get(i++);
					prev = quene.get(i--);
					break;
				}
			}
		}
		
		if(cur != null) cur.player.cueVideo(cur.video_id);
		if(prev != null) prev. player.release();
		CocktailViewActivity.activity.onPlayerRelese(prev.id);
	}
	
	public void onPlayerInit(int itemNo, YouTubePlayer player, String video)
	{
		quene.add(new QueneItem(itemNo, player, video));
	}
	
	private class QueneItem 
	{
		public int id;
		public YouTubePlayer player;
		public String video_id;
		
		public QueneItem(int id, YouTubePlayer player, String video_id) 
		{
			this.id = id;
			this.player = player;
			this.video_id = video_id;
		}
	}
}
