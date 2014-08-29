package com.clonefish.cocktail.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clonefish.cocktail.R;
import com.clonefish.cocktail.database.tables.Cocktail;

public class CocktailListAdapter extends BaseAdapter {

	private Activity activity;
	private List<Cocktail> resultList;
	
	public CocktailListAdapter(Activity act, List<Cocktail> list)
	{
		this.activity = act;
		this.resultList = list;
	}
	
	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view;
		Cocktail cocktail;
		if(convertView == null)
		{
			view = new View(activity);
			LayoutInflater vi = LayoutInflater.from(activity);
			view = vi.inflate(R.layout.item, null);
		}
		else
			view = convertView;
		
		TextView name = (TextView)view.findViewById(R.id.cocktailName);
		TextView tags = (TextView)view.findViewById(R.id.category);
		TextView cid = (TextView)view.findViewById(R.id.cocktailId);
		cocktail = resultList.get(position);
		name.setText(cocktail.name);
		tags.setText(cocktail.tags);
		cid.setText("" + cocktail.id);
		return view;
	}

}
