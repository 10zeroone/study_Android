/* Spinner
 * 
 * Spinner의 부모 AdapterView
 * 
*/
package kr.android.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
	
	//배열 생성
	String[] items = {"대한민국", "미국", "영국", "일본", "아프리카", "프랑스", "오스트레일리아", "칠레", "스페인", "독일", "뉴질랜드", "중국", "일본", "방콕", "홍콩", "방글라데시", "인도", "알레스카"};
	Spinner spinner;
	TextView myTextView;
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myTextView = (TextView)findViewById(R.id.tvView);
		spinner = (Spinner)findViewById(R.id.spContry);
		
		//adapter 생성
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		
		//드롭다운 화면에 표시할 리소스 지정
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//Spinner에 ArrayAdapter를 등록
		spinner.setAdapter(adapter);
		
		//이벤트 소스와 이벤트 리스너 연결
		spinner.setOnItemSelectedListener(this);
	}

	//AdapterView<?> parent: 이벤트가 발생한 spinner객체
	//View view: 이벤트가 발생한 View(데이터 매핑)
	//int position: 위치값
	//long id: position 값과 일치, DB사용시 변경될수 있음(primary key로)
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		myTextView.setText(items[position]);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		myTextView.setText("");
	}
}
