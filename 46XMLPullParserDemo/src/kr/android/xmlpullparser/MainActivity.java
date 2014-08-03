package kr.android.xmlpullparser;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	//리스트 객체 생성
	ArrayList<String> items = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try{
			//XML파일을 읽어들여 XmlPullParser 객체 생성
			XmlPullParser xpp = getResources().getXml(R.xml.words);
			
			while(xpp.getEventType()!=XmlPullParser.END_DOCUMENT){
				if(xpp.getEventType()==XmlPullParser.START_TAG){
					if(xpp.getName().equals("word")){
						items.add(xpp.getAttributeValue(0));
					}
				}
				//다음 항목(element, text등)으로 이동
				xpp.next();				
			}			
		}catch(Exception e){
			Log.e("XMLPullParserDemo", e.toString());			
		}
		
		//Adapter 생성
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		//ListView의 ArrayAdapter등록
		setListAdapter(adapter);		
	}
}
