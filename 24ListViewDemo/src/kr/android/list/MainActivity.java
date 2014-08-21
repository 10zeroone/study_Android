/* 목록 작업 */
package kr.android.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener{
	
	//데이터 보관
	String[] items = {"서울", "대전", "대구", "부산", "광주", "인천", "여수", "목포", "포항", "통영", "제주", "수원", "독도"};
	
	TextView text;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView)findViewById(R.id.textView1);
		//단순히 데이터를 표시
		list = (ListView)findViewById(R.id.listView1);
		
		//목록 작업 단계
		//1)배열이 데이터를 가지고 있으므로 이 배열을 listview에 매칭해 주어야 함
		//  1번째인자 this는 현재의 메인 Acitivity를 표시
		//2)3번째 인자인 배열을 읽어와서
		//3)2번째 인자인 android.R.layout.simple_list_item_1 : 이미 만들어진 텍스트뷰를 만들어서
		//4)어레이 어탭터를 생성한후 (배열의 데이터를  ListView에 매핑하는 역할하는 어탭터 생성)
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		//5)리스트뷰에 ArrayAdapter 등록, 데이터를 매칭시킴
		list.setAdapter(adapter);
		
		//이벤트 소스와 이벤트 리스너 연결
		list.setOnItemClickListener(this);
	}
										
	//AdapterView<?> parent	: 내부적으로 처리되는 데이터를 표현하기 위해 ?인 와일드카드형식으로 표시(오브젝트 타입을 의미)
	//						  이벤트가 발생한 리스트뷰의 정보
	//View view: 리스트의 아이템별 개별적인 view
	//			 ListView에서 이벤트가 발생한 데이터를 표시하는 View
	//int position: 인덱스 값, 데이터를 표시할 때의 위치값
	//long id: 포지션값과 일치, 데이터베이스 연동시 primary key의 id가 명시됨
	//이벤트 핸들러
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		text.setText(items[position]);
		
	}
}
