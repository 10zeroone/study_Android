/*  layout의 객체 참조

* 코드와 UI가 철저히 분리되어 있음
-java 코드
-xml UI

* R.class
-객체를 식별하고 참조할 수 있도록 해주는 클래스
-acitivity_main.xml 에서 ID부여하고 android:id="@+id/textView1" 로 식별
											@: R.class의미
-안드로이드는 ID만 관리하는 객체가 따로 존재하며
-R.java(gen\kr.android.text2.R.java)클래스에서 ID 관리

*findViewById()
-R.java에 등록된 UI오브젝트 ID값을 참조하여 TextView형식의 인스턴스명에 해당 TextView의 참조값을 대입시켜 주는 함수

 * 
 * 
*/
package kr.android.text2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //객체 생성
        setContentView(R.layout.activity_main);
        
        
        
        //View -> TextView
        //TextView			: UI오브젝트 형식
        //text				: 인스턴스 명
        //=					: 대입 연산자
        //(TextView)		: 대입할 인스턴스 UI오브젝트 형식으로 형변환
        //findViewById()	: 사전에 등록한  ID를 통해 TextView객체 호출
        //					    상수와 매칭된 TextView객체 호출
        TextView text = (TextView)findViewById(R.id.textView1);
        //배경색 설정
        text.setBackgroundColor(Color.BLUE);
        //글꼴색 설정
        text.setTextColor(Color.CYAN);        
    }
}
