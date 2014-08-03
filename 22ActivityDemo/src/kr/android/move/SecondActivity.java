package kr.android.move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity{
	
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//화면에 무엇을 보여줄 지를 결정하는 함수
		setContentView(R.layout.second_main);
		
		tv = (TextView)findViewById(R.id.textView1);		
		
		//데이터 가져오기
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if(bundle== null){	//데이터를 보관할 수 있는 번들객체가 null이면 데이터가 없음을 의미		
			tv.append("\n\n 저장된 데이터가 없습니다!");
		}else{
			String msg = bundle.getString("msg");
			tv.append("\n\n" + msg);
		}
	}	
}
