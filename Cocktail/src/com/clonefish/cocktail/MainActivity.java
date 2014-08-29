package com.clonefish.cocktail;

import java.sql.SQLException;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.clonefish.cocktail.adapters.CategoryGridAdapter;
import com.clonefish.cocktail.database.DatabaseHelperFactory;
import com.clonefish.cocktail.social.SocialActivity;
import com.clonefish.cocktail.social.vkontakte.VKLoginFragmentActivity;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends SocialActivity
{
    public static MainActivity activity;
    
    private GraphUser user;
    private GraphPlace place;
    
    private GridView categoryGrid;
    
    public static final String POSITION = "position";
    public static final String NUM_PAGES = "num";

	protected static final String TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelperFactory.setHelper(getApplicationContext());
        activity = this;
        DatabaseHelperFactory.getHelper().createDB(activity);
        categoryGrid = (GridView) findViewById(R.id.category_grid);
        
        try {
			CategoryGridAdapter adapter = new CategoryGridAdapter(activity, DatabaseHelperFactory.getHelper().getCategoryDAO().queryForAll());
			categoryGrid.setAdapter(adapter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        LoginButton buttonLoginFB = (LoginButton) findViewById(R.id.fb_login);
        buttonLoginFB.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                MainActivity.this.user = user;
            }
        });
        ImageButton buttonLoginVK = (ImageButton) findViewById(R.id.vk_login);
        buttonLoginVK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(activity, VKLoginFragmentActivity.class);
                startActivity(intent);
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
	    switch (item.getItemId()) 
	    {
		    case R.id.action_search:
		    	Intent intent2 = new Intent(activity, SearchTagsActivity.class);
		        startActivity(intent2);
		    	return true;
		    default:
		    	return false;
	    }
    }

    @Override
    protected void onDestroy() 
    {
    	super.onDestroy();
    	DatabaseHelperFactory.releaseHelper();
    }
    
    public GraphUser getUser()
    {
    	return user;
    }
    
    
}