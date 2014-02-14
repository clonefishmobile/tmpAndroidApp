package com.example.cocktail.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.cocktail.R;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class CocktailInfoFragment extends Fragment
{
	private ViewGroup rootView;
	private TextView header;
	private TextView tags;
	private TextView cocktail_info;
	
	private static final String TAG = "CocktailInfoFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.d(TAG, "view created");
		rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_info_fragment, container, false); 
		header = (TextView) rootView.findViewById(R.id.header);
		tags = (TextView) rootView.findViewById(R.id.tags);
		cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_info);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		setMinimumToHalf();
		setInfo("docs/brain_fuck.txt");
	}
	
	private void setMinimumToHalf()
	{
		int measuredWidth = 0;
		int measuredHeight = 0;
		Point size = new Point();
		WindowManager w = getActivity().getWindowManager();

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
	
	private void setInfo(String infoFilePath)
	{
		AssetManager am = getActivity().getAssets();
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
	}
}
