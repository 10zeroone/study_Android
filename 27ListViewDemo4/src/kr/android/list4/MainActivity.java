package kr.android.list4;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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
	Button btnAdd, btnDel;
	EditText myEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//데이터 저장소 생성 및 추가
		items = new ArrayList<String>();
		items.add("One");
		items.add("Two");
		items.add("Three");
		
		//체크박스로 다중 선택할 수  ListView만드는 adapter객체 생성 
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
		
		myListView = (ListView)findViewById(R.id.lvList);
		//ListView에 ArrayAdapter등록
		myListView.setAdapter(adapter);
		//선택모드 지정(멀티 모드)
		myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		btnAdd = (Button)findViewById(R.id.btnAdd);
		btnDel = (Button)findViewById(R.id.btnDel);
		myEditText = (EditText)findViewById(R.id.etText);
		
		//이벤트 소스와 이벤트 리스너 연결
		btnAdd.setOnClickListener(this);
		btnDel.setOnClickListener(this);
	}

	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btnAdd:
			String text = myEditText.getText().toString();
			
			if(text.length()>0){
				items.add(0, text);
				adapter.notifyDataSetChanged();
				myEditText.setText("");
			}
			break;			
			
		case R.id.btnDel:
			//ListView 다중 선택시 선택한 position에 대한 정보를 보관하는 객체 생성
			SparseBooleanArray sba = myListView.getCheckedItemPositions();
			if(sba.size()>0){	//대상이 선택된 경우
				//리스트의 마지막 인덱스에서 부터 반복문 수행
				for(int i=myListView.getCount()-1; i>=0; i--){
					//get()는 position값이 존재하면 true반환
					if(sba.get(i)){	//선택한 position값 구하기
						items.remove(i);						
					}
				}				
			}
			//CheckBox 초기화
			myListView.clearChoices();
			//ListView 초기화
			adapter.notifyDataSetChanged();
			
			break;			
		}		
	}
}
