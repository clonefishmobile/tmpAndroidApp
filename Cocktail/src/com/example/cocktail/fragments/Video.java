package com.example.cocktail.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.cocktail.R;

public class Video
{
	private ViewGroup rootView;
	private Button bPlay;
	
	private static final String TAG = "VideoFragment";
	
	public Video(ViewGroup viewGroup, final Activity activity)
	{
		rootView = viewGroup; 
		bPlay = (Button) rootView.findViewById(R.id.play_pause);
		setMinimumToHalf(activity);
		bPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=eP3QZkNuUA4")));
				Log.i("Video", "Video Playing....");
				
			}
		});
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
		
		rootView.setMinimumHeight(measuredHeight/2);
		rootView.setMinimumWidth(measuredWidth/2);
	}
}
