/*
SecondActivity에서 null 체크 

 * file info:
 * MainActivity.java
 * SecondActivity1.java

*/
package kr.android.move2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity1 extends Activity{
	
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_main);		
		
		tv = (TextView)findViewById(R.id.tvContent);		

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		String name = bundle.getString("name");
		String korean = bundle.getString("korean");
		String english = bundle.getString("english");
		String math = bundle.getString("math");

		if(name.trim().length()<=0 || korean.trim().length()<=0 ||
				english.trim().length()<=0 || math.trim().length()<=0){
			tv.append("등록된 데이터가 없습니다");

		}else{

			int sum = Integer.parseInt(korean) + Integer.parseInt(english) + Integer.parseInt(math);
			int avg = sum/3;

			tv.setText("이름: " + name + "\n");
			tv.append("국어: " + korean + "\n");
			tv.append("영어: " + english + "\n");
			tv.append("수학: " + math + "\n");
			tv.append("총점: " + sum + "\n");
			tv.append("평균: " + avg + "\n");
		}	
	}
}