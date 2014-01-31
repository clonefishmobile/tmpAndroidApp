package com.example.frepeereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView reader;
	private ScrollView scroll;
	
	private static final String COMMON = "common.txt";
	private static final String STRATEGY  = "strategyOverview.txt";
	private static final String TAKE_AND_RUN  = "takeAndRun.txt";
	private static final String HAKIM_TWO  = "hakimTwo.txt";
	private static final String TRENDS  = "trends.txt";
	private static final String MARTINGEIL  = "martingeil.txt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		reader = (TextView) findViewById(R.id.readerView);
		scroll = (ScrollView) findViewById(R.id.scrollView1);
		showText(COMMON);
		showText(STRATEGY);
		showText(TAKE_AND_RUN);
		showText(HAKIM_TWO);
		showText(TRENDS);
		showText(MARTINGEIL);
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
	    return super.onOptionsItemSelected(item);
	}
	    
	private void showText(String filename)
	{
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
		reader.append("\n");
	}
}
