package kr.android.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//ActionBar생성
		//ActionBar는 API 11부터 사용가능
		//AndroidMainifest.xml의 minSDKVersion을 11이상으로 지정해야 함
		ActionBar actionBar = getActionBar();
				
		//타이틀 셋팅
		actionBar.setTitle("ActionBar");
		
		//서브타이틀 셋팅
		actionBar.setSubtitle("SubTitle of ActionBar");
		
		//타이틀 영역을 제거하고 아이콘만 보이게 하기
		actionBar.setDisplayShowTitleEnabled(false);
		
		//ActionBar 숨기기
		actionBar.hide();
		
		//ActionBar 보이기
		actionBar.show();		
		
	}
}