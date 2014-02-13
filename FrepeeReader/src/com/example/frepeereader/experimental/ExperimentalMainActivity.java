package com.example.frepeereader.experimental;

import com.example.frepeereader.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ExperimentalMainActivity extends FragmentActivity
{
	public static ExperimentalMainActivity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragments_test);
		activity = this;
		Log.d("activ", "act created");
	}
}
