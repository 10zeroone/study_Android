/* CheckBox

import android.widget.CompoundButton;
implements CompoundButton.OnCheckedChangeListener

*/
package kr.android.check;


import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener{

	CheckBox cb;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb = (CheckBox)findViewById(R.id.checkBox1);
        
        //이벤트 소스와 이벤트 리스너 연결
        cb.setOnCheckedChangeListener(this);        
    }
    
    //이벤트 핸들러
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		if( isChecked){
			cb.setText("체크된 상태");
		}else{
			cb.setText("체크되지 않은 상태");
		}
	}   
}