package com.clonefish.cocktail.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.clonefish.cocktail.CocktailViewActivity;
import com.clonefish.cocktail.MainActivity;
import com.clonefish.cocktail.R;
import com.facebook.widget.FacebookDialog;

public class CocktailInfoFragment extends Fragment
{
	private ViewGroup rootView;
	private TextView header;
	private TextView tags;
	private TextView cocktail_info;
	private Button share;
	private int pageNumber;
	
	private static final String TAG = "CocktailInfoFragment";
	
	public CocktailInfoFragment()
	{
		
	}
	
	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}
	
	private FacebookDialog.ShareDialogBuilder createShareDialogBuilder() {
        return new FacebookDialog.ShareDialogBuilder(getActivity())
                .setName("Hello Facebook")
                .setDescription("The 'Hello Facebook' sample application showcases simple Facebook integration")
                .setLink("http://developers.facebook.com/android");
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_info_fragment, container, false); 
		header = (TextView) rootView.findViewById(R.id.header);
		tags = (TextView) rootView.findViewById(R.id.tags);
		cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_text);
		share = (Button) rootView.findViewById(R.id.share);
        share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CocktailViewActivity.activity.getUIHelper().trackPendingDialogCall(createShareDialogBuilder().build().present());
			}
		});
        setInfo(pageNumber);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
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
		if(header == null) header = (TextView) rootView.findViewById(R.id.header);
		header.setText(MainActivity.getCocktailList().get(id).name);
		if(cocktail_info == null) cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_text);
		cocktail_info.setText(MainActivity.getCocktailList().get(id).text);
	}
}
