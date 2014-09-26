package kr.android.preferences;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	EditText etText;
	Button btnWrite, btnMove, btnRead;
	TextView tvView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etText = (EditText)findViewById(R.id.etText);
		btnWrite = (Button)findViewById(R.id.btnWrite);
		btnRead = (Button)findViewById(R.id.btnRead);
		btnMove = (Button)findViewById(R.id.btnMove);
		tvView = (TextView)findViewById(R.id.tvView);		
		
		btnWrite.setOnClickListener(this);
		btnRead.setOnClickListener(this);
		btnMove.setOnClickListener(this);
	} 

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnWrite){	//프리퍼런스에 데이터 쓰기
			//프리퍼런스에 데이터 쓰기 단계
			//1.SharedPreferences객체 호출
			//PreferencesEx파일명으로 덮어쓰기 모드로 파일 생성
			SharedPreferences sharedPreferences = 
					getSharedPreferences("PreferencesEx", MODE_PRIVATE);
			//2.프리퍼런스에 데이터를 쓰기
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("text", etText.getText().toString());
			//3.데이터 저장
			editor.commit();
						
			//EditText초기화
			etText.setText("");
			
		}else if(v.getId()==R.id.btnRead){	//프리퍼런스 읽기
			//1.SharedPreferences객체 호출
			SharedPreferences sharedPreferences = 
					getSharedPreferences("PreferencesEx", MODE_PRIVATE);
			//2.프리퍼런스의 데이터 읽기			
													//key, defaultValue
			tvView.setText(sharedPreferences.getString("text", ""));				
			
		}else if(v.getId()==R.id.btnMove){	//화면 이동
			Intent intent = new Intent(this, MainTwo.class);
			startActivity(intent);			
		}
	}	
}
