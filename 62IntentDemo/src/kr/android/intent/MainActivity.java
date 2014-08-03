package kr.android.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	int[] buttons ={R.id.btnCall, R.id.btnDial, R.id.btnMap, R.id.btnMessage, R.id.btnSetting, R.id.btnWeb};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//이벤트소스와 이벤트리스너 연결
		for(int button: buttons){
			Button btn = (Button)findViewById(button);
			btn.setOnClickListener(this);
		}
	}

	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		if(v.getId()==R.id.btnWeb){				//웹페이지 표시
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));			
		}else if(v.getId()==R.id.btnCall){		//통화시작
			//권한 명시 필요: <uses-permission android:name="android.permission.CALL_PHONE"/>
			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3287-6767"));
			
		}else if(v.getId()==R.id.btnDial){		//다이얼러표시
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1212-3434"));
		
		}else if(v.getId()==R.id.btnMap){		//지도 표시
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Seoul"));
			
		}else if(v.getId()==R.id.btnMessage){	//메시지 표시
			intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:010-3434-4545"));
			
		}else if(v.getId()==R.id.btnSetting){	//설정 호출
			intent = new Intent("android.settings.SETTINGS");	
			
		}
				
		startActivity(intent);
	}
}
