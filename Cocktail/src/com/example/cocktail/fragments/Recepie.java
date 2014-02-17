package com.example.cocktail.fragments;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableRow;

import com.example.cocktail.R;

public class Recepie
{
	private ViewGroup rootView;
	private TableRow row1;
	private TableRow row2;
	
	private static final String TAG = "RecepieFragment";
	
	public Recepie(ViewGroup viewGroup, Activity activity)
	{
		rootView = viewGroup;
		row1 = (TableRow) rootView.findViewById(R.id.first_row);
		row2 = (TableRow) rootView.findViewById(R.id.second_row);
		setMinimumToHalf(activity);
		
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
		
//		rootView.setMinimumHeight(measuredHeight/2);
		rootView.setMinimumWidth(measuredWidth/2);
	}
}
