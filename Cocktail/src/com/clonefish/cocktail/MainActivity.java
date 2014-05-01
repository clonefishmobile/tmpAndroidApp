package com.clonefish.cocktail;

import java.util.ArrayList;
import java.util.Random;

import android.app.SearchManager;
import android.app.SearchManager.OnDismissListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.clonefish.cocktail.database.DB;
import com.clonefish.cocktail.social.SocialActivity;
import com.clonefish.cocktail.social.facebook.LoginUsingLoginFragmentActivity;
import com.clonefish.cocktail.social.vkontakte.VKLoginFragmentActivity;
import com.clonefish.cocktail.utils.StringConverter;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends SocialActivity implements LoaderCallbacks<Cursor>
{
    public static MainActivity activity;
    
    private GraphUser user;
    private GraphPlace place;
    
    private ListView cocktailList;
    private DB db;
    private SimpleCursorAdapter scAdapter;
    private static ArrayList<Cocktail> cocktailArray = new ArrayList<Cocktail>();
    
    public static final String POSITION = "position";
    public static final String NUM_PAGES = "num";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        activity = this;
        
        db = new DB(this);
        db.open();
        if(!db.isTableExists()) createDB();
        
        // формируем столбцы сопоставления
        String[] from = new String[] {DB.COLUMN_NAME, DB.COLUMN_CAT , DB.COLUMN_ID}; //что искать 
        int[] to = new int[] {R.id.cocktailName, R.id.category, R.id.cId}; //куда класть

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        cocktailList = (ListView) findViewById(R.id.cocktail_list);
        cocktailList.setAdapter(scAdapter);
        cocktailList.setOnItemClickListener(new OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				TextView cId = (TextView) view.findViewById(R.id.cId);
				String pos = (String) cId.getText();
				//делаем интент для запуска новой активити
				Intent intent = new Intent(activity, CocktailViewActivity.class);
				//сохроняем туда номер позиции клика (соответствует номеру странички на PageView
				intent.putExtra(POSITION, (Integer.parseInt(pos) - 1));
				//сохроняем туда количество страниц (элементов в ListView соответственно)
				intent.putExtra(NUM_PAGES, cocktailArray.size());
				//запускаем новую активити
				startActivity(intent);
			}
        });
        
        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);
        handleIntent(getIntent());
        
        ImageButton cock = (ImageButton) findViewById(R.id.cockteail_cat);
        cock.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Cursor cursor = db.searchCategory("коктейли");
		        scAdapter.swapCursor(cursor);
			}
		});
        
        ImageButton stuff = (ImageButton) findViewById(R.id.stuff_cat);
        stuff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Cursor cursor = db.searchCategory("украшение");
		        scAdapter.swapCursor(cursor);
			}
		});
        
        ImageButton all = (ImageButton) findViewById(R.id.all);
        all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Cursor cursor = db.getAllData();
		        scAdapter.swapCursor(cursor);
			}
		});
        
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
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
        {
	        String query = intent.getStringExtra(SearchManager.QUERY);
	        Cursor cursor = db.search(query);
	        scAdapter.swapCursor(cursor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchManager.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				Cursor cursor = db.getAllData();
		        scAdapter.swapCursor(cursor);
			}
		});
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
	    switch (item.getItemId()) 
	    {
		    case R.id.action_search:
		    	onSearchRequested();
		    	return true;
		    default:
		    	return false;
	    }
    }

    @Override
    protected void onDestroy() 
    {
    	super.onDestroy();
    	db.close();
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
		return new MyCursorLoader(this, db);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		scAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	static class MyCursorLoader extends CursorLoader {

		DB db;

		public MyCursorLoader(Context context, DB db) {
			super(context);
			this.db = db;
		}

		@Override
		public Cursor loadInBackground() {
			Cursor cursor = db.getAllData();
			createCocktailList(cursor);
			return cursor;
		}

	}
    
    private static void createCocktailList(Cursor cursor)
    {
    	Cursor allData = cursor;
    	allData.moveToFirst();
    	for(int i = 0; i < allData.getCount(); i++)
    	{
    		String name = allData.getString(allData.getColumnIndex(DB.COLUMN_NAME));
    		String[] tags = null;
    		String text  = allData.getString(allData.getColumnIndex(DB.COLUMN_INFO));
    		String video_id = allData.getString(allData.getColumnIndex(DB.COLUMN_VIDEO));
    		String category = allData.getString(allData.getColumnIndex(DB.COLUMN_CAT));
    		int[] timing = StringConverter.convertStringToIntArray(allData.getString(allData.getColumnIndex(DB.COLUMN_TIMING)));
    		
    		cocktailArray.add(new Cocktail(name, tags, text, video_id, category, timing));
    		allData.moveToNext();
    	}
    }
    
    private void createDB()
    {
        String[] video_id = {
        		"0_u_DeUOBj0", 
        		"9WZhBBy5SwE", 
        		"zCde1kb4_zw", 
        		"9yK0bR3waOg",
        		"Es7khL-ZpE4",
        		"m16eMHLs0IE",
        		"KclyskwJ9qI",
        		"pPs8dWp3Jck",
        		"7C-g2TD0cFw",
        		"1MaEMQHTy2w",
        		"qrOSPiThlhQ",
        		"QZDH696JPBE",
        		"RFq96ohxRbk",
        		"4mMKEsbdRCg",
        		"Z753zcAC-28",
        		"pVf_F2SE6cA",
        		"jj_ajp1T4A4",
        		"KSGDqEuHdfc",
        		"KE35PHt0Zho",
        		"s28PS7mO-M4",
        		"g4xayAR8lnE",
        		"XHAFqkHhJZw",
        		"LBQG8wLYjRU",
        		"iZZt-Qywwi8",
        		"MlF4dqUVsBU",
        		"5_lPWovlBB8",
        		"_VzVbwDRt3E",
        		"AX5PXtJ1QBg",
        		"UOf25K9fLJ8",
        		"AIIi_NogfYg",
        		"fwSfvR2MJvM",
        		"q1I9e0liYQU",
        		"nT78e_v_kog",
        		"wJoHhzndoKw",
        		"3RpMr7ZGoKw",
        		"pmI9Yrav_do",
        		"21rooEtDBb0",
        		"RSPInTLhwYY",
        		"ifdJ3gXQH30",
        		"xJsOCuR3NfU",
        		"sUM2xQnU9x0",
        		"ViCE_HRWdRM",
        		"tZ9gdL2OLuA",
        		"aO0ZxY2NxzM",
        		"BR9b2IwgHg8",
        		"PqDNJWTEe18",
        		"D2N1c77MoDc",
        		"VmnpTf99xng",
        		"oVzDKn6qd6g",
        		"Ndus6TRsL4U"};
        String[] cocktail_name = {
        		"Оригами", 
        		"Огуречный рома", 
        		"Гарниш", 
        		"Клубничный веер",
        		"Sunny Sour",
        		"Spring Fashion",
        		"Sole Martini",
        		"Яблоня в цвету",
        		"Яблоко",
        		"Чеширский кот",
        		"Черри спайси слинг",
        		"фиолетовая бездна",
        		"Уголки",
        		"Телекинез",
        		"Солнечный ветер",
        		"Престиж",
        		"Последний махаон",
        		"Подснежник",
        		"Писко хот",
        		"Паруса",
        		"Палома саммер",
        		"Огнецвет",
        		"О'Гуру",
        		"Наместница",
        		"Мими",
        		"Мери лик",
        		"Малиновый смэш",
        		"Малиновые опята",
        		"Малина цвет",
        		"МалиКа",
        		"Летний вечер",
        		"Лесной",
        		"Кувшинка",
        		"Клуб НИКА",
        		"Карамба",
        		"Зиг Заг Удачи",
        		"Дреды",
        		"Длинный нос",
        		"Дикобраз",
        		"Гармошка",
        		"Гамбургдринк",
        		"Вселенная",
        		"Вкусная находка",
        		"Витраж",
        		"Взмах",
        		"Верный курс",
        		"Веер счастья",
        		"Бананово ванильный",
        		"Август АР",
        		"Гагарин"};
        String[] cocktail_info = {
        		"Источник безграничной книжной романтики и неиссякаемого аппетита во время званных ужинов. " +
        		"Получается путем перегонки сидра из разных сортов яблок, выдержанного в дубовых бочках не менее 2 лет." +
        		"Как говорит героиня Ремарка, - кальвадос скорее вдыхаешь, чем пьешь и, попробовав один раз, ты мечтаешь об этом." +
        		"Со своей стороны добавим главное - первое трепетное знакомство, для которого рекомендуем Джек Роуз." +
        		"40%, 700 мл, Франция."};
        String[] cocktail_category = {"коктейли", "коктейли", "украшение", "украшение"};
        int[] origTiming = {30000, 60000, 90000};
        int[] romTiming = {30000, 60000, 90000, 120000, 150000};
        int[] garTiming = {30000, 60000, 90000};
        int[] cluTiming = {30000};
        String[] timing = {
        	StringConverter.convertArrayToString(origTiming),
        	StringConverter.convertArrayToString(romTiming),
        	StringConverter.convertArrayToString(garTiming),
        	StringConverter.convertArrayToString(cluTiming)
        	};
        int leng = cocktail_name.length;
        for(int i = 0; i < leng; i++)
        {
        	db.addRec(cocktail_name[i], cocktail_info[0], video_id[i], cocktail_category[1], timing[3]);
        }
    }
    
    public static ArrayList<Cocktail> getCocktailList()
    {
    	return cocktailArray;
    }
    
    public GraphUser getUser()
    {
    	return user;
    }
}