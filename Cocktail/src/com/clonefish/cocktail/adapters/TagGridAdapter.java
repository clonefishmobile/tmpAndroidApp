package com.clonefish.cocktail.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.clonefish.cocktail.R;
import com.clonefish.cocktail.database.TagsDAO;

public class TagGridAdapter extends BaseAdapter {

	private TagsDAO dao;
	private Activity activity;
	private SearchView search;
	
	public TagGridAdapter(Activity activity, TagsDAO tDAO, SearchView search)
	{
		this.activity = activity;
		this.dao = tDAO;
		this.search = search;
	}
	
	@Override
	public int getCount() {
		try {
			return dao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Object getItem(int position) {
		try {
			return dao.queryForId(position+1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view;
		String text = "";
		String image_path = "";
		TextView tag;
		ImageView image;
		try{
			text = dao.queryForId(position+1).name;
			image_path = dao.queryForId(position+1).image;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		if(convertView == null)
		{
			view = View.inflate(activity, R.layout.category_item, null);
		} else {
			view = convertView;
		}
		tag = (TextView) view.findViewById(R.id.categoryName);
		tag.setText(text);
		image = (ImageView) view.findViewById(R.id.categoryImage);
		AssetManager am = activity.getAssets();
		InputStream is;
		try {
			//ох уж ети стримы
			if(image_path.isEmpty())
				is = am.open("tag/no_icon.png");
			else is = am.open("tag/" + image_path);
			//фигачим картинку как битмап через фабрику декодированием потока
			image.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeStream(is), 256, 256));
		} catch (IOException e) {
			e.printStackTrace();
		}
		view.setOnClickListener(new bLIstener(text));
		return view;
	}
	
	private class bLIstener implements OnClickListener
	{
		private String text;

		public bLIstener(String text) {
			super();
			this.text = text;
		}

		@Override
		public void onClick(View v) {
			CharSequence sText = search.getQuery();
			if(sText.length() == 0)
				search.setQuery(sText + text, false);
			else
				search.setQuery(sText + " " + text, false);
		}
		
	}

}
