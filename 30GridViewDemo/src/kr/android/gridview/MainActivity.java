package kr.android.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

	//배열 생성
	String[] items={"호두", "사과", "복숭아", "고추", "수박", "메론", "바나나", 
			"망고", "옥수수", "딸기", "피망", "파인애플", "자두", "살구", "앵두", "코코아", "호박", "토마토", "키위", "배"};
	TextView myTextView;
	GridView myGridView;
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myTextView = (TextView)findViewById(R.id.tvView);
		myGridView = (GridView)findViewById(R.id.gvView);
		
		//배열을 이용하는 adapter객체 생성
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		//GridView에 ArrayAdapter등록
		myGridView.setAdapter(adapter);
		
		//이벤트소스와 이벤트 리스너 연결
		myGridView.setOnItemClickListener(this);
		
	}

	//이벤트 핸들러 재정의
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 
		myTextView.setText(items[position]);
		
	}
	
}
