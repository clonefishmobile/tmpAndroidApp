package com.example.cocktail.fragments;

import com.example.cocktail.R;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class VideoFragment extends Fragment 
{
	private ViewGroup rootView;
	private Button bPlay;
	
	private static final String TAG = "VideoFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.d(TAG, "view created");
		rootView = (ViewGroup) inflater.inflate(R.layout.video_fragment, container, false); 
		bPlay = (Button) rootView.findViewById(R.id.play_pause);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		setMinimumToHalf();
		bPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=eP3QZkNuUA4")));
			    Log.i("Video", "Video Playing....");
				
			}
		});
		rootView.setBackgroundColor(Color.BLUE);
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
