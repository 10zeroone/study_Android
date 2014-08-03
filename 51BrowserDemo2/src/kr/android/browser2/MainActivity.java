package kr.android.browser2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	
	WebView wvWeb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wvWeb = (WebView)findViewById(R.id.wvWeb);
		
		//이벤트 연결하기 위해 등록
		wvWeb.setWebViewClient(new MyWebClient());
		
		//자바스크립트 허용
		WebSettings webSettings = wvWeb.getSettings();
		webSettings.setJavaScriptEnabled(true);
		//ZOOM기능 사용
		webSettings.setBuiltInZoomControls(true);
		
		//기본URL설정
		//wvWeb.loadUrl("http://m.naver.com");		
		wvWeb.loadUrl("http://m.daum.net");
		
		//ZOOM기능 확인하기위해 PC버전의 URL설정
		//wvWeb.loadUrl("http://www.korea.com");
	}
	
	//Web의 이벤트 처리 리스너역할을 하는 클래스 생성
	//private: 내부클래스로 만들 경우에만 사용가능
	private class MyWebClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url){
			//클릭한 링크주소를 WebView에 설정
			view.loadUrl(url);
			return true;
		}
	}
	
	//BackKey에 대한 메소드 재정의
	//public: 일반적으로 사용시
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		//화면 히스토리를 읽어들여 이전 페이지가 존재하는지 확인
		if(keyCode==KeyEvent.KEYCODE_BACK && wvWeb.canGoBack()){
			//이전 페이지로 이동
			wvWeb.goBack();
		}else if(keyCode==KeyEvent.KEYCODE_BACK && !wvWeb.canGoBack()){
			//이전 페이지가 없는 경우
			new AlertDialog.Builder(this).setTitle("종료 확인").setMessage("종료하시겠습니까?").setCancelable(false).setPositiveButton("예", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Activity 종료
					finish();					
				}				
			
			}).setNegativeButton("아니오",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//창 닫기
				}
			}).show();			
			
		}		
		return true;
	}
}
