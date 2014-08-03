/*
 * 생명주기 상태변화가 있을 때 마다 로그로 확인하기
 * ---------------------------------------------------------------
 * eclipse의 콘솔: Console 창
 * 폰안에서 보여지는 콘솔: LogCat 창
*/
package kr.android.cycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	public static final String TAG ="Lifecycle";

	//액티비티가 생성된 이후에 호출
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i(TAG, "onCreate 실행됨");		
	}
	
	//onCreate가 호출된 이후 또는 onRestart가 호출된 이후에 연속적으로 호출됨 
	@Override
	protected void onStart(){
		//메소드 오버라드시 부모메소드를 호출해서 초기화작업을 반드시 해야함
		super.onStart();
		Log.i(TAG, "onStart 실행됨");
	}
	
	//액티비티가 백그라운드 상태에서 포그라운드상태로 되려고 할 때  호출 
	@Override
	protected void onRestart(){
		super.onRestart();
		Log.i(TAG, "onRestart 실행됨");
	}
	
	//액티비티가 포그라운드 상태가 되기 직전에 호출
	@Override
	protected void onResume(){
		super.onResume();
		Log.i(TAG, "onResume 실행됨");
	}
	
	//액티비티가 백그라운드 상태가 되기 전에 호출
	@Override
	protected void onPause(){
		super.onPause();
		Log.i(TAG, "onPause 실행됨");
	}
	
	//액티비티가 백그라운드 상태가 되면 호출
	@Override
	protected void onStop(){
		super.onStop();
		Log.i(TAG,  "onStop 실행됨");
	}
	
	//액티비티가 종료될 때 호출
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.i(TAG, "onDestroy 실행됨");
	}
}
