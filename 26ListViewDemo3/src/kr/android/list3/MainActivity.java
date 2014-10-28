package kr.android.list3;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener{
	
	ArrayList<String> items;
	ArrayAdapter<String> adapter;
	ListView myListView;
	EditText myEditText;
	Button btnAdd, btnDel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myEditText = (EditText)findViewById(R.id.etText);
		btnAdd = (Button)findViewById(R.id.btnAdd);
		btnDel = (Button)findViewById(R.id.btnDel);
		myListView = (ListView)findViewById(R.id.lvList);
		
		//데이터 자장소 생성 및 데이터 추가
		items = new ArrayList<String>();
		items.add("First");
		items.add("Second");
		items.add("Third");
		
		//라디오버튼으로 선택할 수  ListView만드는 adapter객체 생성 
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, items);
		
		
		//ListView에 ArrayAdapter등록
		myListView.setAdapter(adapter);
		
		//선택모드 지정(단일 선택)
		myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		//이벤트소스와 이벤트 리스너 연결
		btnAdd.setOnClickListener(this);
		btnDel.setOnClickListener(this);
		
	}

	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnAdd:	//데이터 추가
				String text = myEditText.getText().toString();
				if(text.length()>0){	//데이터 입력시
					//데이터 저장
					items.add(0, text);
					//ListView갱신
					adapter.notifyDataSetChanged();
					//EditText 초기화
					myEditText.setText("");
				}
				//필수, 생략시 아래의 case구문이 수행되어 오류 발생
				break;	
				
			case R.id.btnDel:	//데이터 삭제
				//선택한 포지션 가져오기
				int id = myListView.getCheckedItemPosition();
				
				if(id !=ListView.INVALID_POSITION){	//데이터 선택시에만 삭제
					items.remove(id);										
					//RadioButton을 클릭해서 제거한 후 RadioButton의 선택이 남아있지 않게 항목 선택 값 초기화
					myListView.clearChoices();					
					adapter.notifyDataSetChanged();
				}
				
				break;
		}
	}
}
