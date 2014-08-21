/*
MainActivity.java에서 null체크
점수 100이상  초과여부 체크
 
* file info:
* MainActivity1.java
* SecondActivity.java
* 
*  화면이동 및 전달받은 데이터 가공하기
*  activity_main.xml
*  	- TextView, EditText 셋트로 4셋트 추가, Button(저장)추가
*  second_main.xml
*  	- LinearLayout(Vertical) 추가, TextView 추가 
*  AndroidManifest.xml
*  	- <activity android:name="SecondActivity"></activity>
*  MainActivity.java
*  	- ID에 해당되는 객체의 참조값 반환, 이벤트 소스와 리스너 연결, 이벤트 핸들러
*  SecondActivity.java
*  	- 데이터 가져오기,  합계/평균 구하기
*/
package kr.android.move2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity1 extends Activity {
	
	EditText etName, etKorean, etMath, etEnglish;
	Button btnSave;
	
	String etN,  etK, etM, etE;

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
			
				Intent intent = new Intent(MainActivity1.this, SecondActivity.class);	
				// toString()이 반환하는 String 데이터는 해당 객체의 캐릭터 라인 표현
				// getClass(). getName() + '@' + Integer.toHexString(hashCode())
				// 빈 스트링과 연산하여 추가정보를 제거한다.
				etN = "" + etName.getText().toString().trim();
				etK = "" + etKorean.getText().toString();
				etM = "" + etMath.getText().toString();
				etE = "" + etEnglish.getText().toString();
				
				// 길이값(str.length==0)을 체크하여, 입력누락(null 값)을 방지한다.
				if(etN.toString()=="" || etK.toString()=="" || etM.toString()=="" || etE.toString()==""){
					Toast.makeText(MainActivity1.this,
							"빈 항목이 있으면 안됩니다.\n바르게 입력해 주세요.",
							Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				// inputType을 "number"로 제한했으므로.. "100" 초과만 체크한다.
				// name 값은 위에서 길이값("0")만 체크한다. 더 필요하면 정규표현식? 
				if(Integer.parseInt(etK.toString())>100 || Integer.parseInt(etM.toString())>100 || Integer.parseInt(etE.toString())>100){
					Toast.makeText(MainActivity1.this,
							"100점보다 높을 수는 없습니다.\n바르게 입력해 주세요.",
							Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				intent.putExtra("name", etN);				
				intent.putExtra("korean", etK);
				intent.putExtra("math", etM);
				intent.putExtra("english", etE);
				
				startActivity(intent);
			}
		});
	}
}
