/* 직접 view객체 추가하기 - 옛날방식

setContentView(R.layout.activity_main); layout 사용 안하고 
기존 자바방식으로 코딩
이해를 위해 코딩, 이후 부터 기존 방식으로 코딩

*/
package kr.android.text;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //글자를 표시할 수 있는 TextView객체 생성
        TextView tv = new TextView(this);
        
        //문장 입력
        tv.setText("텍스트 직접 입력");
        
        //Activity에 View등록
        setContentView(tv);       
    } 
}
