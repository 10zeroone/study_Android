/* Button, 이벤트 처리 - 내부 인터페이스
 * 
 * layout에 생성한 버튼을 읽어와서 
 * 연월일시간분초를 표시하기
 *   
 * 날짜 표현 형식 설정 SimpleDateFormat("yyyy-MM-dd a hh:mm:ss")
 * 
 * 이벤트 연결
 * - 내부 인터페이스 사용
 * import android.view.view; view.view안에 있는 인터페이스 사용
 * implement
 * 
*/
package kr.android.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;

//implements View.OnClickListener하여 이벤트 처리 객체 생성
public class MainActivity extends Activity implements View.OnClickListener{
	
	Button btn;
	//날짜 표현 형식 설정
											//년-월-일 AM/PM 시(0~23):분:초
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //findViewById()메소드를 이용해 해당 ID의 객체 참조
        btn = (Button) findViewById(R.id.button1);
        
        //버튼 이벤트 소스와 이벤트 리스너 연결
        btn.setOnClickListener(this);
        
        updateTime();
    }
    
    //현재 날짜와 시간
    private void updateTime(){
    	//Date -> String
    	//btn.setText(new Date().toString());
    	btn.setText(sf.format(new Date()));
    }

    //이벤트 핸들러
	@Override			//이벤트가 발생한 이벤트 소스	(cf. JAVA:이벤트 객체)
	public void onClick(View v) {
		//클릭할 때 마다 새롭게 시간을 읽어서 Button의 text로 전달하여 표시
		updateTime();		
	}
}
