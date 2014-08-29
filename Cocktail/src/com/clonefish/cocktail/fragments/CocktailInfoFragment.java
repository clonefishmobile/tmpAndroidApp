package com.clonefish.cocktail.fragments;

import java.sql.SQLException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clonefish.cocktail.R;
import com.clonefish.cocktail.database.CocktailDAO;
import com.clonefish.cocktail.database.DatabaseHelperFactory;
import com.clonefish.cocktail.database.tables.Cocktail;
import com.clonefish.cocktail.social.ShareButton;
import com.clonefish.cocktail.social.SocialActivity;
import com.clonefish.cocktail.utils.StringConverter;

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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		rootView = (ViewGroup) inflater.inflate(R.layout.cocktail_info_fragment, container, false); 
		header = (TextView) rootView.findViewById(R.id.header);
		tags = (TextView) rootView.findViewById(R.id.tags);
		cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_text);
		share = (Button) rootView.findViewById(R.id.share);
		share.setOnClickListener(new ShareButton((SocialActivity) getActivity(), "Смотрите какой классный коктейл можно сделать! Рецепт в Barmen Dev!"));
        try {
			setInfo(pageNumber);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
	}
	
	public void setInfo(int id) throws SQLException
	{
		CocktailDAO dao = DatabaseHelperFactory.getHelper().getCocktailDAO();
		Cocktail cocktail = dao.queryForId(id);
		if(header == null) header = (TextView) rootView.findViewById(R.id.header);
		header.setText(cocktail.name);
		if(cocktail_info == null) cocktail_info = (TextView) rootView.findViewById(R.id.cocktail_text);
		cocktail_info.setText(cocktail.info);
		if(tags == null) tags = (TextView) rootView.findViewById(R.id.tags);
		String tages = "";
		String[] tage = StringConverter.convertStringToStringArray(cocktail.tags);
		for (String element : tage) 
		{
			tages += element + "; ";
		}
		tags.setText(tages);
	}
}
