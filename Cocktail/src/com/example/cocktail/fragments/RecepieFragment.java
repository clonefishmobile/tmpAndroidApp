package com.example.cocktail.fragments;

import java.io.IOException;
import java.io.InputStream;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableRow;

import com.example.cocktail.R;

public class RecepieFragment extends Fragment 
{
	private ViewGroup rootView;
	private TableRow row1;
	private TableRow row2;
	
	private static final String TAG = "RecepieFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.d(TAG, "view created");
		rootView = (ViewGroup) inflater.inflate(R.layout.recepie_fragment, container, false);
		row1 = (TableRow) rootView.findViewById(R.id.first_row);
		row2 = (TableRow) rootView.findViewById(R.id.second_row);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		setMinimumToHalf();
//		addImages();
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
	
	private void addImages()
	{
		ImageView img1 = new ImageView(getActivity());
		ImageView img2 = new ImageView(getActivity());
		AssetManager am = getActivity().getAssets();
		InputStream is;
		
		try {
			//ох уж ети стримы
			is = am.open("img/1.png");
			//фигачим картинку как битмап через фабрику декодированием потока
			img1.setImageBitmap(BitmapFactory.decodeStream(is));
			img2.setImageBitmap(BitmapFactory.decodeStream(is));
		} catch (IOException e) {
			e.printStackTrace();
		}
		row1.addView(img1);
		row2.addView(img2);
	}
}
