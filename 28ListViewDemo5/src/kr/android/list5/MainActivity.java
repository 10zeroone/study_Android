
/*
 * ListActivity
ListView정보 가지고 있음
이벤트 핸들러가 구현되어 있어 이벤트를 재정의 해서 사용하면 됨

*/

package kr.android.list5;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	
	//배열 생성
	String[] items={"미국", "영국", "일본", "아프리카", "프랑스", "독일", "뉴질랜드", "중국", "일본", "방콕", "홍콩", "방글라데시", "인도", "알레스카"};
	TextView myTextView;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//View만드는 adapter객체 생성
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		//ListView에 ArrayAdapter를 등록, 
		//ListActivity는 생성되는 Listview에 대한 정보를 가지고 있으며 listView는 ListActivity의 고유ID로 명시되어야 하며
		//고유ID에 adapter가 등록됨
		setListAdapter(adapter);
		
		myTextView = (TextView)findViewById(R.id.tvView);
		
	}
	
	//이벤트 핸들러 재정의
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id){
		myTextView.setText(items[position]);
		
	}

}
