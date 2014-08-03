package kr.android.list2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	//변수 선언
	ArrayList<String> todoItems;
	ArrayAdapter<String> adapter;
	ListView myListView;
	EditText myEditText;
	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//해당객체의 참조값 반환
		myListView = (ListView)findViewById(R.id.lvList);
		myEditText = (EditText)findViewById(R.id.etText);
		button = (Button)findViewById(R.id.btnAdd);
		
		//데이터 저장소 생성
		todoItems = new ArrayList<String>();
		//저장소의 데이터를 ListView에 매핑하는 역할을 하는 adapter객체 생성
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		//ListView에 ArrayAdapter등록
		myListView.setAdapter(adapter);
		
		//이벤트 소스와 이벤트 리스너 연결 (익명 내부 클래스)
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//입력한 데이터 저장
				//Editable -> String
				//인덱스 0에 데이터를 입력해서 최근글이 위에 올라오도록 파라미터 지정
				todoItems.add(0, myEditText.getText().toString());
				
				//ListView에서 데이터 갱신해서 새로 등록된 데이터 읽어오기
				adapter.notifyDataSetChanged();
				//등록이 완료된 후 EditText 초기화
				myEditText.setText("");			
				
			}
		});		
	}
}
