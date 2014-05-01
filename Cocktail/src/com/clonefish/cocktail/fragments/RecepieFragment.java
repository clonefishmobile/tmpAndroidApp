package com.clonefish.cocktail.fragments;

import java.io.IOException;
import java.io.InputStream;

import com.clonefish.cocktail.R;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;

public class RecepieFragment extends Fragment 
{
	private ViewGroup rootView;
	private TableRow row1;
	private TableRow row2;

	private static final String TAG = "RecepieFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		rootView = (ViewGroup) inflater.inflate(R.layout.recepie_fragment, container, false);
		row1 = (TableRow) rootView.findViewById(R.id.tableRow1);
		row2 = (TableRow) rootView.findViewById(R.id.tableRow2);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		setImages();
	}
	
	private void setImages()
	{
		ImageView[] images = {
			(ImageView) row1.findViewById(R.id.imageView1),
			(ImageView) row1.findViewById(R.id.imageView2),
			(ImageView) row1.findViewById(R.id.imageView3),
			(ImageView) row1.findViewById(R.id.imageView4),
			(ImageView) row2.findViewById(R.id.imageView5),
			(ImageView) row2.findViewById(R.id.imageView6),
			(ImageView) row2.findViewById(R.id.imageView7),
			(ImageView) row2.findViewById(R.id.imageView8)
		};
		int i = 1;
		for (ImageView imageView : images) 
		{
			AssetManager am = getActivity().getAssets();
			InputStream is;

			try {
				//ох уж ети стримы
				is = am.open("img/"+ i +".png");
				//фигачим картинку как битмап через фабрику декодированием потока
				imageView.setImageBitmap(BitmapFactory.decodeStream(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}
