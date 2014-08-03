/* ImageView

- 자동으로 종,회비율 맞춰줌

southkorea.jpg
영문과 숫자만 사용, 소문자만 사용(구분을 용이하게 하기 위해)

southkorea.jpg
southkorea.png
안드로이드는 southkorea로만 상수로 등록하게 되어 jpg와 png를 구분하지 못해 충돌하게됨
southkorea1.jpg
southkorea2.png
형태로 파일명을 구분해 주어야 함

영역별 R class 호출 방법:
java: 	R.drawable.southkorea
xml:	@drawable/southkorea

*/
package kr.android.image;


import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);      
    }
}