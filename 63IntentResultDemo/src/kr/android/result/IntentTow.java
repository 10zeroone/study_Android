package kr.android.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IntentTow extends Activity implements OnClickListener{
	
	Button btnSuccess, btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		
		btnSuccess = (Button)findViewById(R.id.btnSuccess);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		
		btnSuccess.setOnClickListener(this);
		btnCancel.setOnClickListener(this);		
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.btnSuccess){
			Intent intentResult = getIntent();
			intentResult.putExtra("msg", "데이터");
			//현재 Activity에서 생성한 데이터를 호출한 Activity에 전달
			setResult(RESULT_OK, intentResult);
			//Activity 종료
			finish();			
		}else if(v.getId()==R.id.btnCancel){
			//취소 버튼, 백버튼 터치시
			setResult(RESULT_CANCELED, null);			
			finish();
		}		
	}
}
