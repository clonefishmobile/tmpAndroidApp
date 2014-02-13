package com.example.frepeereader.experimental;

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

import com.example.frepeereader.R;

public class TextViewFragment extends Fragment
{
	private ViewGroup rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.d("frag", "view created");
		rootView = (ViewGroup) inflater.inflate(R.layout.textview_fragment, container, false); 
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		Log.d("frag", "activ created");
		TextView reader = (TextView) rootView.findViewById(R.id.test_reader);
		AssetManager am = ExperimentalMainActivity.activity.getAssets();
		InputStream is;
		
		try {
			//открываем стрим для файлика
			is = am.open("docs/chapter_1.txt");
			//делаем буфер чтения
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    try 
		    {
		    	//если чет прочитали
		       	while((line = br.readLine()) != null)
		       	{
		       		// фигачим все считаное безобразие в отображалку текста
		       		reader.append(line);
		       		reader.append("\n");
		       	}
		    } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		super.onActivityCreated(savedInstanceState);
	}
}
