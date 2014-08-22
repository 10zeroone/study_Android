/*
 * 정해진 ID를 사용할 경우 ListActivity클래스를 상속받아서 사용
*/
package kr.android.menu1;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	//배열 생성
	String[] items ={"바나나", "딸기", "배", "멜론", "사과", "망고", "감", "땅콩", 
			"호두", "밀감", "귤", "오렌지", "애플", "파인애플", "코코아"};
	
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//어댑터 생성
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		//ListView에 Adapter객체 등록
		setListAdapter(adapter);		
	}

	//메뉴 생성
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* Menu의 인자
		 * groupid	: 메뉴 아이템 그룹 지정, 미지정시 Menu.NONE=0
		 * itemid	: 메뉴 아이템 부여된 ID
		 * order	: 메뉴 아이템이 표시될 순서, 미지정시 Menu.NONE=0
		 * title	: 메뉴 아이템이 표시될 텍스트		
		*/
		menu.add(Menu.NONE, 1, Menu.NONE, "16px");
		menu.add(Menu.NONE, 2, Menu.NONE, "24px");
		menu.add(Menu.NONE, 3, Menu.NONE, "32px");
		menu.add(Menu.NONE, 4, Menu.NONE, "40px");
		return true;
	}

	//이벤트 핸들러
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//메뉴를 클릭했을 때 이벤트 처리
		switch(item.getItemId()){	//리스트의 각 아이템 사이의 높이 서렂ㅇ
		case 1:
			getListView().setDividerHeight(16);
			break;
		case 2:
			getListView().setDividerHeight(24);
			break;
		case 3:
			getListView().setDividerHeight(32);
			break;
		case 4:
			getListView().setDividerHeight(40);
			break;
		}
		
		return true;
	}
}
