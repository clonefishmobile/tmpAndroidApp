package com.clonefish.cocktail.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;

import com.clonefish.cocktail.database.tables.Cocktail;
import com.clonefish.cocktail.database.tables.Tags;

public class XMLParser 
{
	public static final String TAG = XMLParser.class.getSimpleName();
	private static XmlPullParser initParser(int resId, Activity activty)
	{
		return activty.getResources().getXml(resId);
	}
	
	public static List<Tags> parseTagsDoc(int resourseId, Activity activty)
	{
		XmlPullParser xmlp = initParser(resourseId, activty);
		ArrayList<Tags> result = new ArrayList<Tags>();
		try {
			while (xmlp.getEventType() != XmlPullParser.END_DOCUMENT) {

				String tmp;
				Tags tag;
				switch (xmlp.getEventType()) {
				// начало документа
				case XmlPullParser.START_DOCUMENT:
					break;
				// начало тэга
				case XmlPullParser.START_TAG:
						tag = new Tags();
						for (int i = 0; i < xmlp.getAttributeCount(); i++) {
							if (xmlp.getAttributeName(i).equals("name"))
								tag.name = xmlp.getAttributeValue(i);
							if (xmlp.getAttributeName(i).equals("image"))
								tag.image = xmlp.getAttributeValue(i);
						result.add(tag);
					}
					break;
				// конец тэга
				case XmlPullParser.END_TAG:
					break;
				// содержимое тэга
				case XmlPullParser.TEXT:
					break;
				default:
					break;
				}
				// следующий элемент
				xmlp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		};
		return result;
	}
	
	public static List<Cocktail> parseCocktailDoc(int resourseId, Activity activty)
	{
		XmlPullParser xmlp = initParser(resourseId, activty);
		ArrayList<Cocktail> result = new ArrayList<Cocktail>();
		try {
			while (xmlp.getEventType() != XmlPullParser.END_DOCUMENT) {

				String tmp;
				Cocktail cocktail;
				switch (xmlp.getEventType()) {
				// начало документа
				case XmlPullParser.START_DOCUMENT:
					break;
				// начало тэга
				case XmlPullParser.START_TAG:
						cocktail = new Cocktail();
						for (int i = 0; i < xmlp.getAttributeCount(); i++) {
							if (xmlp.getAttributeName(i).equals("name"))
								cocktail.name = xmlp.getAttributeValue(i);
							if (xmlp.getAttributeName(i).equals("category"))
								cocktail.category = xmlp.getAttributeValue(i);
							if (xmlp.getAttributeName(i).equals("tags"))
								cocktail.tags = xmlp.getAttributeValue(i);
							if (xmlp.getAttributeName(i).equals("video"))
								cocktail.video_id = xmlp.getAttributeValue(i);
							if (xmlp.getAttributeName(i).equals("info"))
								cocktail.info = xmlp.getAttributeValue(i);
							if (xmlp.getAttributeName(i).equals("recepie"))
								cocktail.recepie = xmlp.getAttributeValue(i);
							if (xmlp.getAttributeName(i).equals("timing"))
								cocktail.timing = xmlp.getAttributeValue(i);
						result.add(cocktail);
					}
					break;
				// конец тэга
				case XmlPullParser.END_TAG:
					break;
				// содержимое тэга
				case XmlPullParser.TEXT:
					break;
				default:
					break;
				}
				// следующий элемент
				xmlp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		};
		return result;
	}
}
