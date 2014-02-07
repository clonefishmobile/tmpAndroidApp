package com.example.frepeereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ScreenSlidePageFragment extends Fragment 
{
    /**
     * Ключик для аргумента, в которым сохраним имя файла который отобразит фрагмент
     */
    public static final String ARG_FILENAME = "filename";
    
    /**
     * Ключик для аргумента, в которым сохраним номер странички фрагмента (чтоб усе было по порядку)
     */
    public static final String ARG_PAGE = "page";
    
    /**
     * Ключик для аргумента, в которым сохраним имя картинки который отобразит фрагмент
     */
    public static final String ARG_IMAGENAME = "image";
    
    /**
     * Номер странички, берется из {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    
    /**
     * Факторка для фрагментов. Делает фрагмент с заданым номером странички
     */
    public static ScreenSlidePageFragment create(int numOfPage) 
    {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        //привязываем к фрагменту файлик аргументом
        args.putString(ARG_FILENAME, "docs/chapter_" + (numOfPage+1) + ".txt");
        //аналогично номер странички
        args.putInt(ARG_PAGE, (numOfPage+1));
        //и картинку
        args.putString(ARG_IMAGENAME, "img/image_" + (numOfPage+1) + ".jpg");
        //и все сохраняем
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
        // Берем корневой вью
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        //Ищем где текст кажем
        TextView reader = (TextView) rootView.findViewById(R.id.text);
        boolean isFirst = true;
        //Берем асет манагер
        AssetManager am = MainActivity.activity.getAssets();
		InputStream is;
		try {
			//открываем стрим для файлика
			is = am.open(getArguments().getString(ARG_FILENAME));
			//делаем буфер чтения
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    try 
		    {
		    	//если чет прочитали
		       	while((line = br.readLine()) != null)
		       	{
		       		//и это первая строка
		       		if(isFirst)	{
		       			//то суем ее в хедер аки заголовок
		       			((TextView) rootView.findViewById(R.id.header)).append(line);
		       			isFirst = false;
		       		} else { //иначе же
		       			// фигачим все считаное безобразие в отображалку текста
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
		
		//Ищем где картинке место
		ImageView image = (ImageView) rootView.findViewById(R.id.image);
		try {
			//ох уж ети стримы
			is = am.open(getArguments().getString(ARG_IMAGENAME));
			//фигачим картинку как битмап через фабрику декодированием потока
			image.setImageBitmap(BitmapFactory.decodeStream(is));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//делаем фигню про три строки текста
		TextView basement = (TextView) rootView.findViewById(R.id.basement);
		basement.append("ТУТ ДОЛЖНО БЫТЬ ТРИ СТРОКИ ТЕКСТА");
		basement.append("\n");
		basement.append("ТУТ ДОЛЖНО БЫТЬ ТРИ СТРОКИ ТЕКСТА");
		basement.append("\n");
		basement.append("ТУТ ДОЛЖНО БЫТЬ ТРИ СТРОКИ ТЕКСТА");
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
