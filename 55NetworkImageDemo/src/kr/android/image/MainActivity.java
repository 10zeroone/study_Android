package kr.android.image;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	static final String TAG = "NetworkImageDemo";
	static final String IMAGE_URL ="http://img.naver.net/static/www/u/2013/0731/nmms_224940510.gif";
	
	ImageView imageView;
	ProgressDialog progressDialog;
	Button btnGo;	
	
	//import android.os.Handler;
	Handler handler = new Handler();	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		imageView = (ImageView)findViewById(R.id.ivImage);
		btnGo = (Button)findViewById(R.id.btnCall);
		btnGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progressDialog = ProgressDialog.show(MainActivity.this, "서버에 접속중", "이미지 불러오는 중");		
				
				processData();
			}
		});		
	}
	
	//스레드를 생성해서 서버에 접속
	public void processData(){
		//thread객체 생성
		new Thread(){
			@Override
			public void run(){
				//읽어온 이미지의 InputStream을 getRemoteImage메소드에 전달하여 Bitmap으로 저장
				final Bitmap iBitmap = getRemoteImage(getStreamFromURL());
				
				//UI작업
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						//ImageView에 Bitmap전달
						imageView.setImageBitmap(iBitmap);
						//창 중지
						progressDialog.dismiss();						
					}
				});				
			}
		}.start();
	}	
		

	//읽어온 InputStream을 Bitmap으로 전달
	public Bitmap getRemoteImage(InputStream input){
		Bitmap bitmap = null;
		
		try{
			BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
			bitmap = BitmapFactory.decodeStream(bufferedInputStream);
			bufferedInputStream.close();					
			
		}catch(IOException e){
			Log.e(TAG,  "파싱 에러 발생", e);
		}

		return bitmap;
	}
	
	//서버에 접속해서 Image를 읽기
	public InputStream getStreamFromURL(){
		InputStream input = null;

		try{
			HttpClient httpClient = new DefaultHttpClient();
			//요청담당
			HttpGet getMethod = new HttpGet(IMAGE_URL);

			//응답을 받을 객체
			HttpResponse httpResponse = (HttpResponse)httpClient.execute(getMethod);

			//응답 수신 처리
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
			input = bufferedHttpEntity.getContent();


		}catch(Exception e){
			Log.e(TAG, "네트워크 에러 발생", e);			
		}

		return input;
	}
}
