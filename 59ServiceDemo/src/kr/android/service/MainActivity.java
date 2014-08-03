package kr.android.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	Button btnNewsStart, btnNewsEnd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnNewsStart = (Button)findViewById(R.id.btnNewsStart);
		btnNewsEnd = (Button)findViewById(R.id.btnNewsEnd);
		
		btnNewsStart.setOnClickListener(this);
		btnNewsEnd.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		//Intent객체 생성
		Intent intent = new Intent(this, NewsService.class);
		
		if(v.getId()==R.id.btnNewsStart){
			//서비스 시작
			startService(intent);
			
		}else if(v.getId()==R.id.btnNewsEnd){
			//서비스 종료
			stopService(intent);
		}		
	}
}
