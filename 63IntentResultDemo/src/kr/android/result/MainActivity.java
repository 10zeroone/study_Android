package kr.android.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	Button btnIntentTwo, btnAddress;
	//호출한 Activity 식별을 위한 식별자 상수 정의
	private static final int SHOW_ACTIVITY_INTENTTWO = 1;
	private static final int SHOW_ACTIVITY_ADDRESS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnIntentTwo = (Button)findViewById(R.id.btnIntentTwo);
		btnAddress = (Button)findViewById(R.id.btnAddress);
		
		//이벤트 리스너 연결
		btnIntentTwo.setOnClickListener(this);
		btnAddress.setOnClickListener(this);
		
	}
	
	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		Intent intent = null;
		if(v.getId()==R.id.btnIntentTwo){		//IntetnTwo호출
			intent = new Intent(this, IntentTow.class);
										//requestCode: 식별자 
			startActivityForResult(intent, SHOW_ACTIVITY_INTENTTWO);
			
			
		}else if(v.getId()==R.id.btnAddress){	//주소록 호출
			intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
			//Activity호출
			startActivityForResult(intent, SHOW_ACTIVITY_ADDRESS);
		}
	}
	
	// Activity 호출시 결과값을 받는 메소드 작성
	// setResult()호출하면 onActivityResult( )에서 데이터를 받게 됨
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		case SHOW_ACTIVITY_INTENTTWO:		//IntentTwo의 결과값 처리
			if(resultCode==Activity.RESULT_OK){
				String result = data.getStringExtra("msg");
				Toast.makeText(this, "성공: " + result, Toast.LENGTH_SHORT).show();
			}else if(resultCode==Activity.RESULT_CANCELED){
				Toast.makeText(this, "취소됨", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case SHOW_ACTIVITY_ADDRESS:			//주소록에서 만들어진 결과값 처리
			
			if(resultCode==Activity.RESULT_OK){
				Toast.makeText(this, "성공: " + data.getData().toString(), Toast.LENGTH_SHORT).show();
			}else if(resultCode==Activity.RESULT_CANCELED){
				Toast.makeText(this, "취소됨", Toast.LENGTH_SHORT).show();
			}
			break;
		}		
	}
}
