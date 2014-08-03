/* WebView

1) URL 링크
2) HTML 직접 생성 (로컬파일 사용)
3) HTML 파일 사용 (asset폴더의 로컬파일 사용)
4) 자바 스크립트 허용
- 자바 스크립의 alert 대체 코드 작성

*/

package kr.adroid.browser;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MainActivity extends Activity {
	
	WebView browser;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		browser =(WebView)findViewById(R.id.wvWeb);
		
		//URL 링크
		//browser.loadUrl("http://m.naver.com");
		
		
		//HTML 직접 생성
/*		String msg ="<html><body>Hellow Android World!!!</body></html>";		
												//mimeType	:문서타입
		browser.loadDataWithBaseURL(null, msg, "text/html", "UTF-8", null);*/
		
		
		//assets의 HTML파일 이용
		browser.loadUrl("file:///android_asset/hello.html");
		
		//자바 스크립트 허용
		browser.getSettings().setJavaScriptEnabled(true);		
		
		MyWebChromeClient myWebChromeClient = new MyWebChromeClient();		
		//브라우저에 등록
		browser.setWebChromeClient(myWebChromeClient);
	}
	//자바 스크립트의 alert 대체 코드 작성
	//자바 스크립트의 alert 경고창을 대체하는 안드로이드  경고창 구현
	private class MyWebChromeClient extends WebChromeClient{
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result){
			//message	: '경고창'
			new AlertDialog.Builder(MainActivity.this).setTitle("경고").setCancelable(false).setNeutralButton("확인", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).show();
			
			result.confirm();
			
			return true;
		}
	}
}
