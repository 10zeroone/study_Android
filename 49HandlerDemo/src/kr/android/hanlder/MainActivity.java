/* Thread 
 * 
 * Thread -> Handle -> UI

*/
package kr.android.hanlder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	//객체 선언
	ProgressBar progressBar;	
	//Android.os.Handler
	Handler handler = new Handler();
	boolean isRunning = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		progressBar = (ProgressBar)findViewById(R.id.pbProgress);
	}
	
	//포그라운로 가기전 실행됨
	//스레드로 데이터 처리
	@Override
	public void  onStart(){
		super.onStart();
		
		//ProgressBar초기화
		progressBar.setProgress(0);
		
		//화면 인터페이스와 별도의 백그라운드 작업을 위해 스레드 생성
		Thread background = new Thread(new Runnable() {
			
			@Override
			public void run() {				
				//웹에서 데이터 읽어오는 효과를 주기 위해 for문 구현
				try{
					for(int i=0; i<20 && isRunning; i++){
						Thread.sleep(1000);
						//웹에서 데이터를 읽어온다고 가정
						//스레드에서 UI에 접근하려면 Handler객체를 이용해서 접근함
						//post()는  Runnable객체를 인수로 넣어야 함(Runnable객체를 받아 메시지 큐에 넣어 실행)
						handler.post(new Runnable() {
							@Override
							public void run() {
								// UI에 접근해서 화면 갱신
								progressBar.incrementProgressBy(5);							
							}
						});
					}
				}catch(Exception e){
					Log.e("핸들러 데모", e.toString());					
				}
			}
		});
		
		isRunning = true;
		background.start();
	}
	
	//스레드 중지
	@Override
	public void onStop(){
		super.onStop();
		
		isRunning = false;
	}
}
