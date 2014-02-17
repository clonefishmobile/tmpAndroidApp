package com.example.cocktail.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.cocktail.R;

public class CocktailInfo
{
	private ViewGroup rootView;
	private TextView header;
	private TextView tags;
	private TextView cocktail_info;
	
	private static final String TAG = "CocktailInfo";
	
	public CocktailInfo(ViewGroup viewGroup, Activity activity)
	{
		this.rootView = viewGroup;
		header = (TextView) rootView.findViewById(R.id.header);
		tags = (TextView) rootView.findViewById(R.id.tags);
		cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_info);
		setMinimumToHalf(activity);
		setInfo("docs/brain_fuck.txt", activity);
	}
	
	public ViewGroup getView()
	{
		return rootView;
	}
	
	private void setMinimumToHalf(Activity activity)
	{
		int measuredWidth = 0;
		int measuredHeight = 0;
		Point size = new Point();
		WindowManager w = activity.getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
		    w.getDefaultDisplay().getSize(size);

		    measuredWidth = size.x;
		    measuredHeight = size.y;
		}
		else
		{
		    Display d = w.getDefaultDisplay();
		    measuredWidth = d.getWidth();
		    measuredHeight = d.getHeight();
		}
		
		rootView.setMinimumWidth(measuredWidth/2);
	}
	
	private void setInfo(String infoFilePath, Activity activity)
	{
		AssetManager am = activity.getAssets();
		InputStream is;
		
		try {
			//открываем стрим для файлика
			is = am.open(infoFilePath);
			//делаем буфер чтения
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    try 
		    {
		    	//если чет прочитали
		       	while((line = br.readLine()) != null)
		       	{
		       		// фигачим все считаное безобразие в отображалку текста
		       		cocktail_info.append(line);
		       		cocktail_info.append("\n");
		       	}
		    } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Log.d(TAG, "info seted");
	}
}
