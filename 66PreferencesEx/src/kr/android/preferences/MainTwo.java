package kr.android.preferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainTwo extends Activity{
	
	Button btnReadTwo;
	TextView tvViewTwo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main_two);
		
		btnReadTwo = (Button)findViewById(R.id.btnReadTwo);
		tvViewTwo = (TextView)findViewById(R.id.tvViewTwo);
		
		btnReadTwo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//1.SharedPreferences객체 호출
				SharedPreferences sharedPreferences = 
						getSharedPreferences("PreferencesEx", MODE_PRIVATE);
				//2.프리퍼런스의 데이터 읽기
				tvViewTwo.setText(sharedPreferences.getString("text", ""));
				
			}
		});
	}

	
}
