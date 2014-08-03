package kr.android.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class NewsRSSDetail extends Activity{
	
	String title, description, pubDate;
	WebView wvWeb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail);
		
		//넘어온 데이터 저장
		Intent intent = getIntent();
		
		title = intent.getExtras().getString("title");
		pubDate = intent.getExtras().getString("pubDate");
		description = intent.getExtras().getString("description");
		
		//img태그에서 https:가 생략되어 있어서 이미지를 호출하지 못하는 부분 처리
		//img 태그를 검색해서 https: 삽입 하도록 처리
		description = description.replaceAll("img src=\"//", "img src=\"https://");
		
		//HTML문서 작성
		StringBuffer text = new StringBuffer();
		text.append("<html><body><font size=4>");
		text.append(title);
		text.append("</font><hr size=2 width=100% noshade>");
		text.append(description);
		text.append("<br>입력날짜: ");
		text.append(pubDate);		
		text.append("</body></html>");
		
		//WebView에 출력
		wvWeb = (WebView)findViewById(R.id.wvWeb);
		WebSettings webSettings = wvWeb.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		
		wvWeb.loadDataWithBaseURL(null, text.toString(), "text/html", "UTF-8", null);
	}
}
