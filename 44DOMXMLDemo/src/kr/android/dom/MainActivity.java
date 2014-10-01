package kr.android.dom;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class MainActivity extends ListActivity {
	
	//아이템들을 담을 리스트 객체 선언
	ArrayList<String> items = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		// XML읽어서 ArrayList에 담기
		InputStream in = null;				
		try{
			//1)XML을 읽어서 InputStream생성
			in = getResources().openRawResource(R.raw.words);
			
			//2)DOM 파서 생성
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//3)InputStream을 읽어들여 DOM트리 생성
			//import org.w3c.dom.Document;
			Document doc = builder.parse(in);
			
			//NodeList words = doc.getDocumentElement().getElementsByTagName("word");
			//4)<word>태그를 가진 노드 리스트 확인
			NodeList words = doc.getElementsByTagName("word");
			
			for(int i=0; i<words.getLength(); i++){
				//import org.w3c.dom.Element;
				Element e = (Element)words.item(i);
				items.add( e.getAttribute("value") );				
			}
			
			//어댑터 생성
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
			//ListView에 어댑터 등록
			setListAdapter(adapter);
			
			
		}catch(Exception e){
			Toast.makeText(this, "예외: "+e.toString(), Toast.LENGTH_SHORT).show();
			
		}finally{
			
			if(in!=null){ try{in.close();}catch(IOException e){e.printStackTrace();} }
			
		}		
	}
}
