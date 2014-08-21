package kr.android.message;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

	Button btnAlert, btnToast, btnProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnAlert = (Button)findViewById(R.id.btnAlert);
		btnToast = (Button)findViewById(R.id.btnToast);
		btnProgress = (Button)findViewById(R.id.btnProgress);
		
		//이벤트 소스와 이벤트 리스너 연결
		btnAlert.setOnClickListener(this);
		btnToast.setOnClickListener(this);
		btnProgress.setOnClickListener(this);	
	}

	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btnAlert:
			//경고창 생성
			//체인걸어서 속성 호출
																								//setCancelable(true)	: 백버튼으로 창을 닫을 것인지 설정
			//new AlertDialog.Builder(this).setTitle("대화상자").setMessage("경고창을 오픈하였습니다!").setCancelable(true).setNeutralButton("닫기", new DialogInterface.OnClickListener() {
			new AlertDialog.Builder(this).setTitle("대화상자")
				.setMessage("경고창을 오픈하였습니다!").setCancelable(false)
				.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 단순히 창을 달을 경우는 명시할 코드가 없음
					// 창이 닫힐 때 부가적인 작업을 할 경우는  onClick()메서도 안에 기재					
				}
			}).show();			
			break;
		case R.id.btnToast:
			Toast.makeText(this, "토스트메시지가 오픈되었습니다!",  Toast.LENGTH_SHORT).show();
			break;			
		case R.id.btnProgress:
			final ProgressDialog progressDialog = 
									ProgressDialog.show(this, "프로그레스 바 작동중", "잠시만 기다려주세요!");
			
			//익명내부 클래스로 스레드 생성하여 백그라운드 작업
			new Thread(){
				@Override
				public void run(){
					try{
						sleep(5000);
					}catch(Exception e){
						Log.e("Message Demo", e.toString());
					}
					//창 닫기
					//로컬내부 클래스이므로 메소드안에 있는 지역변수를 호출할 수 없으므로 progressDialog객체 final을 붙여주여야 함 
					progressDialog.dismiss();					
				}
			}.start();
			
			break;
		}		
	}
}
