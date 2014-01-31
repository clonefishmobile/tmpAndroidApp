package com.example.frepeereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView reader;
	private ScrollView scroll;
	
	private static final String COMMON = "common.txt";
	private static final String STRATEGY  = "strategyOverview.txt";
	private static final String TAKE_AND_RUN  = "takeAndRun.txt";
	private static final String HAKIM_TWO  = "hakimTwo.txt";
	private static final String TRENDS  = "trends.txt";
	private static final String MARTINGEIL  = "martingeil.txt";
	
	MainActivity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.main);
		reader = (TextView) findViewById(R.id.readerView);
		scroll = (ScrollView) findViewById(R.id.scrollView1);
		makeContentList();
		showText(COMMON);
		showText(STRATEGY);
		showText(TAKE_AND_RUN);
		
		SpannableString link = makeLinkSpan("scroll", new View.OnClickListener() {          
	        @Override
	        public void onClick(View v) {
	           Toast.makeText(activity, "" + scroll.getScrollY(), Toast.LENGTH_SHORT).show();
	        }
	    });
	    reader.append(link);
	    reader.append("\n");
		
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
		Log.d("reader","" + scroll.getMaxScrollAmount());
	}
	
	private void makeContentList()
	{
		String[] content =  {
			"Введение",
			"Сорви 53$ и бери",
			"Хаким-два",
			"Последовательности и тренды",
			"Усовершенствованная система мартингейла"};
		
		for (String item : content) {
			makeContentItem(item);
		}
		 makeLinksFocusable(reader);
		 reader.append("\n");
	}
	
	private void makeContentItem(final String itemName)
	{
		SpannableString link = makeLinkSpan(itemName, new View.OnClickListener() {          
	        @Override
	        public void onClick(View v) {
	           Toast.makeText(activity, itemName, Toast.LENGTH_SHORT).show();
	        }
	    });
	    reader.append(link);
	    reader.append("\n");
	   
	}
	
	private SpannableString makeLinkSpan(CharSequence text, View.OnClickListener listener) {
	    SpannableString link = new SpannableString(text);
	    link.setSpan(new ClickableString(listener), 0, text.length(), 
	        SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
	    return link;
	}
	
	private void makeLinksFocusable(TextView tv) {
	    MovementMethod m = tv.getMovementMethod();  
	    if ((m == null) || !(m instanceof LinkMovementMethod)) {  
	        if (tv.getLinksClickable()) {  
	            tv.setMovementMethod(LinkMovementMethod.getInstance());  
	        }  
	    }  
	}
	
	private static class ClickableString extends ClickableSpan {  
	    private View.OnClickListener mListener;          
	    public ClickableString(View.OnClickListener listener) {              
	        mListener = listener;  
	    }          
	    @Override  
	    public void onClick(View v) {  
	        mListener.onClick(v);  
	    }        
	}
}
