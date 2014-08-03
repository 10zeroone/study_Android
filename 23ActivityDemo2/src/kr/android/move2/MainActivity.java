package kr.android.move2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	EditText etName, etKorean, etMath, etEnglish;
	Button btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSave = (Button)findViewById(R.id.btnSave);
			
		etName = (EditText) findViewById(R.id.etName);
		etKorean = (EditText) findViewById(R.id.etKorean);
		etMath = (EditText) findViewById(R.id.etMath);
		etEnglish = (EditText) findViewById(R.id.etEnglish);
		
		btnSave.setOnClickListener(new OnClickListener() {				
			
			//이벤트 핸들러
			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent(MainActivity.this, SecondActivity.class);	
				
				intent.putExtra("name", etName.getText().toString());
				intent.putExtra("korean", etKorean.getText().toString());
				intent.putExtra("math", etMath.getText().toString());
				intent.putExtra("english", etEnglish.getText().toString());
				
				startActivity(intent);
			}
		});
	}
}
