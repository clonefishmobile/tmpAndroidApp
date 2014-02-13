package com.example.cocktail.fragments;

import javax.swing.text.TableView;

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

import com.example.cocktail.R;

public class RecepieFragment extends Fragment 
{
	private ViewGroup rootView;
	
	private static final String TAG = "RecepieFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.d(TAG, "view created");
		rootView = (ViewGroup) inflater.inflate(R.layout.recepie_fragment, container, false); 
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		setMinimumToHalf();
		rootView.setBackgroundColor(Color.GREEN);
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
		
		rootView.setMinimumHeight(measuredHeight/2);
		rootView.setMinimumWidth(measuredWidth/2);
	}
}
