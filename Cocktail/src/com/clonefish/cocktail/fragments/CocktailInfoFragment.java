package com.clonefish.cocktail.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clonefish.cocktail.MainActivity;
import com.clonefish.cocktail.R;

public class CocktailInfoFragment extends Fragment
{
	private ViewGroup rootView;
	private TextView header;
	private TextView tags;
	private TextView cocktail_info;

	private static final String TAG = "CocktailInfoFragment";
	
	public CocktailInfoFragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.i(TAG, "-----view created-----");
		rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_info_fragment, container, false); 
		header = (TextView) rootView.findViewById(R.id.header);
		tags = (TextView) rootView.findViewById(R.id.tags);
		cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_text);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		Log.i(TAG, "-----activ created-----");
		super.onActivityCreated(savedInstanceState);
//		setInfo("docs/brain_fuck.txt");
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
	}
	
	public void setInfo(int id)
	{
		header.setText(MainActivity.getCocktailList().get(id).name);
		cocktail_info.setText(MainActivity.getCocktailList().get(id).text);
	}
}
