package com.example.frepeereader.experimental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.frepeereader.R;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageViewFragment extends Fragment 
{
	private ImageView rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		Log.d("frag", "view created");
		rootView = (ImageView) inflater.inflate(R.layout.imageview_fragment, container, false); 
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		Log.d("frag", "activ created");
		AssetManager am = ExperimentalMainActivity.activity.getAssets();
		InputStream is;
		
		try {
			//ох уж ети стримы
			is = am.open("img/image_1.jpg");
			//фигачим картинку как битмап через фабрику декодированием потока
			rootView.setImageBitmap(BitmapFactory.decodeStream(is));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onActivityCreated(savedInstanceState);
	}
}
