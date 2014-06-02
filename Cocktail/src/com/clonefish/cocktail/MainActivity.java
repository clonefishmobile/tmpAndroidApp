package com.clonefish.cocktail;

import java.util.ArrayList;
import java.util.Random;

import android.app.SearchManager;
import android.app.SearchManager.OnDismissListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
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
        
        ImageButton instruments = (ImageButton) findViewById(R.id.instruments);
        stuff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Cursor cursor = db.searchInstrumentTags();
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
    		String[] tags = StringConverter.convertStringToStringArray(allData.getString(allData.getColumnIndex(DB.COLUMN_TAGS)));
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
        
        String[] tag1 = {"морковь","физалис","1 минута"};
        String[] tag2 = {"грейпфрут","быстро и просто","20 секунд"};
        String[] tag3 = {"грейпфрут","лайм","цедра", "фигурные ножницы","1 минута"};
        String[] tag4 = {"яблоко","карвинг","2 минуты","нож для карвинга"};
        String[] tag5 = {"яблоко","быстро и просто","20 секунд"};
        String[] tag6 = {"баклажан","карвинг","2 минуты","нож для карвинга"};
        String[] tag7 = {"ананас","лайм","вишня","нож для карвинга","2 минуты"};
        String[] tag8 = {"лимон","физалис","зестер","1 минута"};
        String[] tag9 = {"дыня","быстро и просто","1 минута"};
        String[] tag10 = {"апельсин","цедра","нож для карвинга","пилер","1 минута"};
        String[] tag11 = {"апельсин","клубника","цедра","пилер","нож для карвинга","2 минуты"};
        String[] tag12 = {"паприка","ананас","формы","выемки","3 минуты"};
        String[] tag13 = {"кумкват","дайкон","розмарин","нож для карвинга","2 минуты"};
        String[] tag14 = {"грейпфрут","цедра","быстро и просто","20 секунд"};
        String[] tag15 = {"лимон","вишня","цедра","пилер","нож для карвинга","2 минуты"};
        String[] tag16 = {"яблоко","быстро и просто","1 минута"};
        String[] tag17 = {"мандарин","лайм","вишня","быстро и просто","1 минута"};
        String[] tag18 = {"кумкват","ананнас","карвинг","нож для карвинга","2 минуты"};
        String[] tag19 = {"огурец","редис","карвинг","нож для карвинга","2 минуты"};
        String[] tag20 = {"свекла","банан","фигурные","ножницы","нож для карвинга","2 минуты"};
        String[] tag21 = {"грейпфрут","цедра","лемонграсс","быстро и просто","1 минута"};
        String[] tag22 = {"лимон","вишня","цедра","зестер","быстро и просто","20 секунд"};
        String[] tag23 = {"лимон","малина","цедра","пилер","быстро и просто","1 минута"};
        String[] tag24 = {"огурец","малина","розмарин","карвинг","нож для карвинга","2 минуты"};
        String[] tag25 = {"малина","апельсин","мята","формы","выемки","быстро и просто","1 минута"};
        String[] tag26 = {"апельсин","цедра","лемонграсс","пилер","1 минута"};
        String[] tag27 = {"морковь","малина","карвинг","нож для карвинга","2 минуты"};
        String[] tag28 = {"рудис","киви","груша","ананас","карвинг","нож для карвинга","2 минуты"};
        String[] tag29 = {"апельсин","ананас","нож шато","фигурные","ножницы","2 минуты"};
        String[] tag30 = {"лимон","лайм","цедра","пилер","зестер","2 минуты"};
        String[] tag31 = {"карамбола","быстро и просто","1 минута"};
        String[] tag32 = {"яблоко","быстро и просто","1 минута"};
        String[] tag33 = {"апельсин","цедра","зестер","быстро и просто","20 секунд"};
        String[] tag34 = {"редис","физалис","карвинг","нож для карвинга","1 минута"};
        String[] tag35 = {"кумкват","ананас","быстро и просто","1 минута"};
        String[] tag36 = {"редис","быстро и просто","20 секунд"};
        String[] tag37 = {"кумкват","быстро и просто","1 минута"};
        String[] tag38 = {"апельсин","цедра","зестер","быстро и просто","1 минута"};
        String[] tag39 = {"кумкват","киви","быстро и просто","20 секунд"};
        String[] tag40 = {"питахая","киви","грейпфрут","паприка","формы","выемки","3 минуты"};
        String[] tag41 = {"дайкон","розмарин","карвинг","нож для карвинга","2 минуты"};
        String[] tag42 = {"грейпфрут","цедра","нож для карвинга","2 минуты"};
        String[] tag43 = {"клубника","быстро и просто","20 секунд"};
        String[] tag44 = {"мини","банан","малина","ежевика","быстро и просто","20 секунд"};
        String[] tag45 = {"арбуз","лимон","цедра","формы","выемки","1 минута"};
        String[] tag46 = {"яблоко","ананас","нож для сердцевины яблок","1 минута"};
        
        String[] timing = {
        	StringConverter.convertArrayToString(origTiming),
        	StringConverter.convertArrayToString(romTiming),
        	StringConverter.convertArrayToString(garTiming),
        	StringConverter.convertArrayToString(cluTiming)
        	};
        
        String[] tags = {
        		StringConverter.convertArrayToString(tag1),
        		StringConverter.convertArrayToString(tag1),
        		StringConverter.convertArrayToString(tag1),
        		StringConverter.convertArrayToString(tag1),
        		StringConverter.convertArrayToString(tag1),
        		StringConverter.convertArrayToString(tag2),
        		StringConverter.convertArrayToString(tag3),
        		StringConverter.convertArrayToString(tag4),
        		StringConverter.convertArrayToString(tag5),
        		StringConverter.convertArrayToString(tag6),
        		StringConverter.convertArrayToString(tag7),
        		StringConverter.convertArrayToString(tag8),
        		StringConverter.convertArrayToString(tag9),
        		StringConverter.convertArrayToString(tag10),
        		StringConverter.convertArrayToString(tag11),
        		StringConverter.convertArrayToString(tag12),
        		StringConverter.convertArrayToString(tag13),
        		StringConverter.convertArrayToString(tag14),
        		StringConverter.convertArrayToString(tag15),
        		StringConverter.convertArrayToString(tag16),
        		StringConverter.convertArrayToString(tag17),
        		StringConverter.convertArrayToString(tag18),
        		StringConverter.convertArrayToString(tag19),
        		StringConverter.convertArrayToString(tag20),
        		StringConverter.convertArrayToString(tag21),
        		StringConverter.convertArrayToString(tag22),
        		StringConverter.convertArrayToString(tag23),
        		StringConverter.convertArrayToString(tag24),
        		StringConverter.convertArrayToString(tag25),
        		StringConverter.convertArrayToString(tag26),
        		StringConverter.convertArrayToString(tag27),
        		StringConverter.convertArrayToString(tag28),
        		StringConverter.convertArrayToString(tag29),
        		StringConverter.convertArrayToString(tag30),
        		StringConverter.convertArrayToString(tag31),
        		StringConverter.convertArrayToString(tag32),
        		StringConverter.convertArrayToString(tag33),
        		StringConverter.convertArrayToString(tag34),
        		StringConverter.convertArrayToString(tag35),
        		StringConverter.convertArrayToString(tag36),
        		StringConverter.convertArrayToString(tag37),
        		StringConverter.convertArrayToString(tag38),
        		StringConverter.convertArrayToString(tag39),
        		StringConverter.convertArrayToString(tag40),
        		StringConverter.convertArrayToString(tag41),
        		StringConverter.convertArrayToString(tag42),
        		StringConverter.convertArrayToString(tag43),
        		StringConverter.convertArrayToString(tag44),
        		StringConverter.convertArrayToString(tag45),
        		StringConverter.convertArrayToString(tag46)};
        
        int leng = cocktail_name.length;
        int tagsl = tags.length;
        for(int i = 0; i < leng; i++)
        {
        	db.addRec(cocktail_name[i], cocktail_info[0], video_id[i], cocktail_category[1], timing[3], tags[i]);
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