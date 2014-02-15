package com.example.cocktail;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

/*
 * TODO ПРО СВАЙП!!!
 * Вообщем, надо разобраться с свайпом, но уже поздно.
 * А мысли записать надо.
 * Вообщем так.
 * 		1. Создать класс адаптер, для страничек для свайпа 
 * (они у мобилок и таблеток разные как бы будут)
 * ОТ этого класса будут наследоваться все последующие странички.
 * 		2. В зависимости от того, что у нас - мобилка или таблетка - на ViewPager
 * будут пихаться соответствующие странички ( а так как они наследуються от одного адаптера - то пох)
 * 		3. Научиться пихать соответствующий лайаут в зависимости от размеров экрана
 * 		4. ...
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablet_screen_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
