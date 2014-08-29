package com.clonefish.cocktail;

import java.sql.SQLException;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.clonefish.cocktail.adapters.TagGridAdapter;
import com.clonefish.cocktail.database.DatabaseHelperFactory;
import com.clonefish.cocktail.database.TagsDAO;

public class SearchTagsActivity extends FragmentActivity
{
	private GridView tagsGrid;
	private ToggleButton tButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.search_tags_activity);
	    tagsGrid = (GridView) findViewById(R.id.tagsGrid);
	    tButton = (ToggleButton) findViewById(R.id.toggleButton1);
	    SearchView search = (SearchView) findViewById(R.id.search);
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
		search.setSearchableInfo(searchableInfo);
	    TagsDAO dao;
		try {
			dao = DatabaseHelperFactory.getHelper().getTagsDAO();
			TagGridAdapter adapter = new TagGridAdapter(this, dao, search);
			tagsGrid.setAdapter(adapter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			String query = intent.getStringExtra(SearchManager.QUERY);
			Intent result = new Intent(this, SearchResultActivity.class);
			result.putExtra("query", query);
			String type;
			if(tButton.isChecked()) type = "tags";
			else type = "name";
			result.putExtra("type", type);
			startActivity(result);
		}
	}
}
