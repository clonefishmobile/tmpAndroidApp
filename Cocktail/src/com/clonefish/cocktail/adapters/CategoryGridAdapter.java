package com.clonefish.cocktail.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clonefish.cocktail.CocktailViewActivity;
import com.clonefish.cocktail.MainActivity;
import com.clonefish.cocktail.R;
import com.clonefish.cocktail.SearchResultActivity;
import com.clonefish.cocktail.database.tables.Category;

public class CategoryGridAdapter extends BaseAdapter
{
	Activity activity;
	List<Category> list;
	
	public CategoryGridAdapter(Activity activity, List<Category> list) 
	{
		this.activity = activity;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view;
		TextView text;
		ImageView image;
		if(convertView == null)
		{
			view = new View(activity);
			view = View.inflate(activity, R.layout.category_item, null);
		} else {
			view = convertView;
		}
		text = (TextView) view.findViewById(R.id.categoryName);
		image = (ImageView) view.findViewById(R.id.categoryImage);
		String name = list.get(position).name; 
		text.setText(name);
		if(name.equals("Инструменты")) image.setImageResource(R.drawable.ic_instruments);
		if(name.equals("Быстрые украшения")) image.setImageResource(R.drawable.ic_20sec);
		if(name.equals("Цедра и твисты")) image.setImageResource(R.drawable.ic_twist);
		if(name.equals("Формы и выемки")) image.setImageResource(R.drawable.ic_forms);
		if(name.equals("Карвинг")) image.setImageResource(R.drawable.ic_carving);
		if(name.equals("Конкурс и подиум")) image.setImageResource(R.drawable.ic_launcher);
		view.setOnClickListener(new GridClickListener(name));
		return view;
	}
	
	private class GridClickListener implements OnClickListener
	{
		private String text;
		
		public GridClickListener(String text) {
			super();
			this.text = text;
		}
		@Override
		public void onClick(View v) {
			//делаем интент для запуска новой активити
			Intent intent = new Intent(activity, SearchResultActivity.class);
			intent.putExtra("query", text);
			intent.putExtra("type", "category");
			//запускаем новую активити
			activity.startActivity(intent);			
		}
	}
}
