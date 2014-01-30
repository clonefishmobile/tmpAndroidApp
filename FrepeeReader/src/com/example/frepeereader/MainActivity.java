package com.example.frepeereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView reader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		reader = (TextView) findViewById(R.id.readerView);
		showText("common.txt");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) 
	    {
	    	case R.id.common:
	    		showText("common.txt");
	    		return true;
	    	case R.id.strategy:
	    		showText("strategyOverview.txt");
	    		return true;
	    	case R.id.takeAndRun:
	    		showText("takeAndRun.txt");
	    		return true;
	    	case R.id.hakimTwo:
	    		showText("hakimTwo.txt");
	    		return true;
	    	case R.id.trends:
	    		showText("trends.txt");
	    		return true;
	    	case R.id.martingeil:
	    		showText("martingeil.txt");
	    		return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	    
	private void showText(String filename)
	{
		reader.setText("");
		AssetManager am = this.getAssets();
		InputStream is;
		try {
			is = am.open(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    try 
		    {
		       	while((line = br.readLine()) != null)
		       	{
		       	    reader.append(line);
		       	    reader.append("\n");
		       	}
		    } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
