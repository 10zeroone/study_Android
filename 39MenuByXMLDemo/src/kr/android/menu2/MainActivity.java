package kr.android.menu2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	}

	//메뉴 생성 및 등록	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		//MenuInflater가 생성됨: XML형태로 되어 있는 메뉴정보를 읽어와 객체를 생성하고 메뉴로 등록함
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//메뉴의 아이템을 클릭 한 후 이벤트 발생에 대한 처리
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case R.id.itSeoul:
			Toast.makeText(this, "서울", Toast.LENGTH_SHORT).show();;
			return true;
		case R.id.itBusan:
			Toast.makeText(this, "부산", Toast.LENGTH_SHORT).show();;
			return true;			
		case R.id.itSuwon:
			Toast.makeText(this, "수원", Toast.LENGTH_SHORT).show();;
			return true;			
		case R.id.itJeju:
			Toast.makeText(this, "제주", Toast.LENGTH_SHORT).show();;
			return true;			
		}		
		
		return super.onOptionsItemSelected(item);
	}
}
