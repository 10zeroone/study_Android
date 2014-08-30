package kr.android.post;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	static final String POST_URL = "http://192.168.0.2:8080/HellowWeb/postTest.jsp";
	static final String TAG = "HttpClientPostDemo";
	
	EditText etName, etAddress;
	Button btnSend;	
	ProgressDialog progressDialog;
	
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etName = (EditText)findViewById(R.id.etName);
		etAddress = (EditText)findViewById(R.id.etAddress);
		btnSend = (Button)findViewById(R.id.btnSend);
				
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = etName.getText().toString();
				String address = etAddress.getText().toString();
				
				if(name.length()<=0){
					Toast.makeText(MainActivity.this, "이름입력 필수", Toast.LENGTH_SHORT).show();
					etName.requestFocus();
					return;
				}
				if(address.length()<=0){
					Toast.makeText(MainActivity.this, "주소입력 필수", Toast.LENGTH_SHORT).show();
					etAddress.requestFocus();
					return;
				}
				
				progressDialog = ProgressDialog.show(MainActivity.this, "사이트 접속중", "데이터 전송 중입니다.");
								
				new Thread(){
					@Override
					public void run(){
						final InputStream input = getStreamFromURL(POST_URL);
						
						//UI작업
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								parseXML(input);								
							}
						});						
					}
				}.start();				
			}
		});	
	}
	
		
	
	//InputStream -> DOM tree 생성
	public void parseXML(InputStream input){
		try{
			//DOM파서 생성
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//import org.w3c.dom.Document;
			//DOM tree생성
			Document doc = builder.parse(input);
			NodeList node = doc.getElementsByTagName("code");
			
			String result = node.item(0).getFirstChild().getNodeValue();
			
			//프로그레스 창 중지
			progressDialog.dismiss();
			
			if(result.equals("success")){
				Toast.makeText(this, "전송성공", Toast.LENGTH_LONG).show();
				
				//EditText초기화
				etName.setText("");;
				etAddress.setText("");
			}else{
				Toast.makeText(this, "전송실패", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			Log.e(TAG, "파싱 에러", e);
			progressDialog.dismiss();
			Toast.makeText(this, "오류발생" + e, Toast.LENGTH_LONG).show();
		}		
		Log.i(TAG,"성공");		
	}
	
	//서버에 접속해서 POST방식으로 데이터를 전달하고 서버에서 응답한 정보를 처리
	public InputStream getStreamFromURL(String url){
		
		InputStream input = null;
		
		try{			
			HttpClient httpClient = new DefaultHttpClient();
			
			//POST방식
//						자료형으로 전달하기 위해 ArrayList 객체생성하고
//						NameValuePair 사용하여 key, value 쌍으로 자료형구조를 생성			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			//이름 담기
			params.add(new BasicNameValuePair("name", etName.getText().toString()));
			//주소 담기
			params.add(new BasicNameValuePair("address", etAddress.getText().toString()));
			//HttpPost
			HttpPost postMethod = new HttpPost(url);
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			//응답을 받을 객체
			HttpResponse httpResponse = (HttpResponse)httpClient.execute(postMethod);					
			
			//응답수신 처리
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
			input = bufferedHttpEntity.getContent();			
			
		}catch(Exception e){
			Log.e(TAG, "네트워크 에러 발생", e);
		}
		
		return input;
	}
}