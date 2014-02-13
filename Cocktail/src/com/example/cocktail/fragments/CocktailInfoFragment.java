package com.example.cocktail.fragments;

import com.example.cocktail.R;

import android.app.Fragment;
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

public class CocktailInfoFragment extends Fragment
{
	private ViewGroup rootView;
	
	private static final String TAG = "CocktailInfoFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.d(TAG, "view created");
		rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_info_fragment, container, false); 
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		setMinimumToHalf();
		rootView.setBackgroundColor(Color.RED);
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
}
