package com.clonefish.cocktail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SplashActivity extends FragmentActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d("main","_______SPLASH_________");
		Intent intent = new Intent(this, MainActivity.class);
		//запускаем новую активити
		startActivity(intent);

		finish();
	}
}
