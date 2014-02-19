package com.clonefish.cocktail.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		Log.d(TAG, "view created");
		rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_info_fragment, container, false); 
		header = (TextView) rootView.findViewById(R.id.header);
		tags = (TextView) rootView.findViewById(R.id.tags);
		cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_text);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		Log.i(TAG, "activ created");
		super.onActivityCreated(savedInstanceState);
		setInfo("docs/brain_fuck.txt");
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		setInfo("docs/brain_fuck.txt");
		super.onSaveInstanceState(outState);
	}
	
	private void setInfo(String infoFilePath)
	{
		cocktail_info.setText("все говно ");
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
		Log.e(TAG, "info setted");
	}
}
