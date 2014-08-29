package com.clonefish.cocktail;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clonefish.cocktail.adapters.CocktailListAdapter;
import com.clonefish.cocktail.database.CocktailDAO;
import com.clonefish.cocktail.database.DatabaseHelperFactory;
import com.clonefish.cocktail.database.tables.Cocktail;
import com.clonefish.cocktail.utils.StringConverter;
import com.clonefish.cocktail.utils.UniqueArrayList;
import com.j256.ormlite.stmt.QueryBuilder;

public class SearchResultActivity extends FragmentActivity 
{
	private String query;
	private String type;
	private ListView resultList;
	private UniqueArrayList<Cocktail> result;
	private Activity activity;
	
	private String TAG = SearchResultActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		activity = this;
	    setContentView(R.layout.activity_search_result);
	    resultList = (ListView) findViewById(R.id.searchResult);
	    Intent intent = getIntent();
	    query = intent.getStringExtra("query");
	    type = intent.getStringExtra("type");
	    result = new UniqueArrayList<Cocktail>();
	    String[] separateQuery = StringConverter.convertStringToStringArray(query, " ");
	    try {
			CocktailDAO dao = DatabaseHelperFactory.getHelper().getCocktailDAO();
			QueryBuilder<Cocktail, ?> queryBuilder = dao.queryBuilder();
			if(type.equals("tags"))
				for (String str : separateQuery) 
				{
					queryBuilder.where().like(Cocktail.TAG_COLUMN, "%"+ str +"%");
					result.addAll(dao.query(queryBuilder.prepare()));
				}
			else if(type.equals("name"))
				for (String str : separateQuery) 
				{
					queryBuilder.where().like(Cocktail.NAME_COLUMN, "%"+ str +"%");
					result.addAll(dao.query(queryBuilder.prepare()));
				}
			else if(type.equals("category"))
			{
				queryBuilder.where().like(Cocktail.CATEGORY_COLUMN, "%"+ query +"%");
				result.addAll(dao.query(queryBuilder.prepare()));
			}
			else result = (UniqueArrayList<Cocktail>) dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    resultList.setOnItemClickListener(new OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				TextView cId = (TextView) view.findViewById(R.id.cocktailId);
				String pos = (String) cId.getText();
				//делаем интент для запуска новой активити
				Intent intent = new Intent(activity, CocktailViewActivity.class);
				//сохроняем туда номер позиции клика (соответствует номеру странички на PageView
				intent.putExtra(MainActivity.POSITION, (Integer.parseInt(pos)-1));
				//сохроняем туда количество страниц (элементов в ListView соответственно)
				intent.putExtra(MainActivity.NUM_PAGES, resultList.getCount());
				intent.putExtra("query", query);
				intent.putExtra("category", type.equals("category"));
				//запускаем новую активити
				startActivity(intent);
			}
        });
	    
	    CocktailListAdapter adapter = new CocktailListAdapter(this, result);
	    resultList.setAdapter(adapter);
	}
}
