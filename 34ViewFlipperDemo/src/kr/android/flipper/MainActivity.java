package kr.android.flipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener{
	
	//ViewFlipper객체 생성
	ViewFlipper myViewFlipper;
	Button btnPrev, btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myViewFlipper = (ViewFlipper)findViewById(R.id.vfDetails);
		btnPrev = (Button)findViewById(R.id.btnPrev);
		btnNext =(Button)findViewById(R.id.btnNext);
		
		//이벤트 연결
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		
	}

	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnNext){	//다음 뷰 호출
			myViewFlipper.showNext();			
		}else if(v.getId()==R.id.btnPrev){	//이전 뷰 호출
			myViewFlipper.showPrevious();					
		}		
	}
}