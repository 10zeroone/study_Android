package kr.android.html;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	static final String TAG ="NetworkHTMLDEMO";
	static final String URL ="http://www.naver.com/index.html";
	
	EditText etView;
	Button btnGo;
	ProgressDialog progressDialog;
	
	//import android.os.Handler;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etView =(EditText)findViewById(R.id.etView);
		btnGo = (Button)findViewById(R.id.btnCall);
		btnGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 다이어로그 창 표시
				progressDialog = ProgressDialog.show(MainActivity.this, "사이트 접속 중", "잠시만 기다려주세요");
				
				processData();
			}
		});		
	}
	
	//스레드를 구성해서 서버에 접근해서 HTML호출 후 HTML문서 출력
	public void processData(){
		//초기화
		//버튼을 여러번 누를 수 있기 때문에 누를때마다 기존것 지우고 보여주기 위해
		etView.setText("");
		
		//스레드 생성
		new Thread(){
			
			@Override
			public void run(){
				//서버에 접근해서 HTML호출
				//String str = getStringFromURL();
				final String str = getStringFromURL();
				
				//정보를 UI에 반영
				handler.post(new Runnable() {
					
					@Override
					public void run() {	//실질적인 UI작업
						// 내부클래스에서 지역변수 읽을 수 없으므로 변수에 final붙여주어야 함
						etView.setText(str);						
						//창 중지
						progressDialog.dismiss();
						
					}
				});
			}
		}.start();
	}
	
	//서버에 접근해 원하는 HTML를 호출한 후 파일을 반환 받음
	public String getStringFromURL(){
		String responseBody = null;
		
		try{
			
			HttpClient httpClient = new DefaultHttpClient();
			
			//핸들러에 정보 담음
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			//get방식으로 핸들러에 전달하여 가공한 정보 매칭해서 responseBody에 저장
												//요청처리, 응답처리
			responseBody = httpClient.execute(new HttpGet(URL), responseHandler);
			
		}catch(Exception e){
			Log.e(TAG, "네트워크 오류", e);			
		}		
		return responseBody;
	}
}
