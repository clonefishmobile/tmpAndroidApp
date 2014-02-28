package com.clonefish.cocktail;

import android.util.Log;

public class VideoManager 
{
	private static volatile VideoManager INSTANCE;
	
	private int previusItem;
	private int currietItem;
	private int nextItem;
	private int maxItems;
	
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
		
		Log.i("VideoManager", 
				previusItem + " " + 
				currietItem + " " + 
				nextItem);
	}
	
	public void init(int initialItem, int maxItems)
	{
		onItemChanged(initialItem);
		this.maxItems = maxItems;
	}
	
	public int getCur()
	{
		return currietItem;
	}
}
