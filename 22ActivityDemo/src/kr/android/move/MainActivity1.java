/* 화면 이동

인텐트 역할:
액티비티와 액티비티 호출(매개역할)
어떤 기능을 실행할 지를 지정하기 위해 사용

AndroidManifest.xml
액티비티 추가시 여기에 반드시 명시
-android:versionCode="1"	구글에서 보여지는 버전정보
-android:versionName="1.0" 	일반 사용자에게 보여지는 버전정보 

*/
package kr.android.move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity1 extends Activity implements View.OnClickListener{
	
	Button btnButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		//ID에 해당되는 객체의 참조값 반환
		btnButton = (Button) findViewById(R.id.button1);
		//이벤트 소스와 이벤트 리스너 연결
		btnButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 화면 이동
		// 인텐트 객체 생성
		Intent intent = null;
		if(v.getId()==R.id.button1){
								//현재 액티비티, 이동할 액티비티
			intent = new Intent(this, SecondActivity.class);
		}
		//인텐트의 매개변수를 통해 액티비티 실행
		startActivity(intent);
	}
}
