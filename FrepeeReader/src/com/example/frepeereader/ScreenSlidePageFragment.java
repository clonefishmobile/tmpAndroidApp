package com.example.frepeereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScreenSlidePageFragment extends Fragment 
{
    /**
     * The argument key for the filename with text which this fragment will represent.
     */
    public static final String ARG_FILENAME = "filename";
    
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    
    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    /**
     * The number of fragments page.
     */
    public static int numOfPages = 0;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int numOfPage) 
    {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILENAME, "docs/chapter_" + (numOfPage+1) + ".txt");
        args.putInt(ARG_PAGE, (numOfPage+1));
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        TextView reader = (TextView) rootView.findViewById(R.id.text);
        boolean isFirst = true;
        AssetManager am = MainActivity.activity.getAssets();
		InputStream is;
		try {
			Log.d(ARG_FILENAME, getArguments().getString(ARG_FILENAME));
			is = am.open(getArguments().getString(ARG_FILENAME));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    try 
		    {
		       	while((line = br.readLine()) != null)
		       	{
		       		if(isFirst)	{
		       			((TextView) rootView.findViewById(R.id.header)).append(line);
		       			isFirst = false;
		       		} else {
		       			reader.append(line);
		       			reader.append("\n");
		       		}
		       	}
		    } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
