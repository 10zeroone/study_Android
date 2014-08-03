package kr.android.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Tabhost객체 생성
		TabHost myTabHost = (TabHost)findViewById(R.id.tabhost);
		//메인액티비티에 탭호스트 등록
		myTabHost.setup();

		//첫번째 Tab작업 (시계)
		//TabSpec객체 생성
		//tag_analogClock이름은 유니크한것 아니고 참고용
		TabHost.TabSpec tabSpec = myTabHost.newTabSpec("tag_analogClock");
		tabSpec.setContent(R.id.tab1_analogClock);		
		//SDK4.0이상에서는 아이콘 미표시
		//호환성을 위해서 아이콘을 넣어줌
		tabSpec.setIndicator("시계", getResources().getDrawable(R.drawable.ic_launcher));
		//TabHost에 등록
		myTabHost.addTab(tabSpec);
		
		
		//두번째 Tab 작업 (버튼)
		//TabSpec객체 생성
		tabSpec = myTabHost.newTabSpec("tag_button");
		tabSpec.setContent(R.id.tab2_button);
		tabSpec.setIndicator("버튼", getResources().getDrawable(R.drawable.ic_launcher));
		//TabHost에 등록
		myTabHost.addTab(tabSpec);		
		//초기 보여질 탭 지정(default: 0)
		myTabHost.setCurrentTab(0);
		//myTabHost.setCurrentTab(1);
		
	}
}
