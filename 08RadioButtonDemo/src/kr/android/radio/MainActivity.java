/* RadioButton - 선택한 가위/바위/보 텍스트 출력 
 * 
 * 이벤트소스와 이벤트 리스너 연결-익명 내부 클래스 사용
 * 초기의 선택된 것 표시
 * 초기  선택 라이오 버튼 지정
 * 
 * --------------------------------------------------------------------------------
 * 라이오버튼 기본 선택 설정은 
 * java와 xml에서 모두 할 수 있지만 xml에서 단일화 하여 java코드를 줄이는 것이 좋음
 * 
*/
package kr.android.radio;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	RadioGroup rGroup;
	TextView tv;
			
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        tv = (TextView)findViewById(R.id.textView1);
        
        
        //초기  선택 라이오 버튼 지정
        rGroup.check(R.id.radio2);
        
        //초기의 선택된 것 표시
        RadioButton rb = (RadioButton)findViewById(rGroup.getCheckedRadioButtonId());
        tv.setText("기본 선택: "+ rb.getText());    
        
        //이벤트소스와 이벤트 리스너 연결-익명 내부 클래스 사용
        rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {        	
			
        	//이벤트 핸들러
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb =(RadioButton)findViewById(checkedId);				
				tv.setText( "당신의 선택: "+ rb.getText());				
			}
		});     
    }
}