package kr.android.weather;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	//기상청 날씨 정보
	static final String WEATHER_URL ="http://www.kma.go.kr/XML/weather/sfc_web_map.xml";
	static final String TAG="HttpClientWeatherDemo";
	
	WebView wvView;
	ArrayList<ForeCast> arrayList = new ArrayList<ForeCast>();
	ProgressBar progressBar;
	
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wvView = (WebView)findViewById(R.id.wvView);
		progressBar = (ProgressBar)findViewById(R.id.pbProgress);		
		
		updateForeCast();
	}
	
	
	//(5) 스레드 호출
	public void updateForeCast(){
		//ProgressBar를 보여지게 처리
		progressBar.setVisibility(View.VISIBLE);
		
		new Thread(){
			@Override
			public void run(){
				//DOM Parser이용
				buildForeCastsbyDOM(getStreamFromURL());				
				
				//XMLPullParser이용
				//buildForeCastsbyXMLPullParser(getStreamFromURL());

				handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						//HTML작성
						String page = generatePage();
						
						wvView.loadDataWithBaseURL(null, page, "text/html", "UTF-8", null);
						//Toast.makeText(MainActivity.this, "성공", Toast.LENGTH_SHORT).show();
						//ProgressBar 안 보여지게 처리
						progressBar.setVisibility(View.GONE);
					}
				});
				
			}
		}.start();
	}
	
	
	//(4-2) XML파일을 읽어들여 XmlPullParser가 파싱
	public void buildForeCastsbyXMLPullParser(InputStream input){
		String local = null;
		String desc= null;
		String ta = null;
		
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			
			//파서에 스트림 연결
			parser.setInput(input, "UTF-8");
			
			while(parser.getEventType()!=XmlPullParser.END_DOCUMENT){
				if(parser.getEventType()==XmlPullParser.START_TAG){
					if(parser.getName().equals("local")){
						//날씨 처리
						desc = parser.getAttributeValue(2);

						//온도 처리
						ta = parser.getAttributeValue(3);
					}

				}else if(parser.getEventType()==XmlPullParser.TEXT){
					//지역정보
					local = parser.getText();
				}else if(parser.getEventType()==XmlPullParser.END_TAG){
					if(parser.getName().equals("local")){
						arrayList.add(new ForeCast(local, desc, ta));
					}
				}
				//XMLPullParsr의 커서를 다음 요소(텍스트)로 이동
				parser.next();
			}
			
		}catch(Exception e){
			
		}
		
	}
	
	
	//(4-1) XML파일을 DOM트리를 생성해서 파싱 (DOM Parser)
	public void buildForeCastsbyDOM(InputStream input){
		
		try{
			//DOM 파서 생성
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//DOM tree 생성
			Document doc = builder.parse(input);
			
			NodeList weather = doc.getElementsByTagName("local");
			
			for(int i=0; i< weather.getLength();i++){
				//import org.w3c.dom.Element;
				Element local = (Element)weather.item(i);
				
				ForeCast forecast = new ForeCast();
				//지역(도시명)
				forecast.local = local.getFirstChild().getNodeValue();
				//날씨
				forecast.desc = local.getAttribute("desc");
				//온도
				forecast.ta = local.getAttribute("ta");			
				
				arrayList.add(forecast);
						
			}						
			
		}catch(Exception e){
			Log.e(TAG, "파싱 에러", e);
		}		
	}
	
	//(3) 서버에 접근해서 XML데이터 요청
	public InputStream getStreamFromURL(){
		InputStream input = null;

		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(WEATHER_URL);

			//응답을 받을 객체
			HttpResponse httpResponse = (HttpResponse)httpClient.execute(httpGet);

			//응답 수신 처리
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
			input = bufferedHttpEntity.getContent();

		}catch(Exception e){
			Log.e(TAG, "접속 오류", e);

		}

		return input;
	}

	
	//(2) UI작업(데이터를 표시하기 위한 HTML)
	public String generatePage(){	
		
		StringBuffer result = new StringBuffer("<html><body><table width=100%>");
			result.append("<tr><th width=30%>지역</th>");
			result.append("<th width=50%>날씨</th>");
			result.append("<th width=20%>온도</th></tr>");
		
			for(ForeCast foreCast: arrayList){
				result.append("<tr><td align=center>");
				result.append(foreCast.local);
				result.append("</td><td align=center>");
				result.append(foreCast.desc);
				result.append("</td><td align=center>");
				result.append(foreCast.ta);
				result.append("</td></tr>");
			}
		result.append("</table></body></html>");
		
		
		return result.toString();
	}
	
	//(1) 날씨정보(지역, 날씨, 온도)를 저장할 클래스 객체 생성
	class ForeCast{
		String local;	//지역
		String desc;	//날씨
		String ta;		//온도
		
		public ForeCast(){}
		
		public ForeCast(String local, String desc, String ta){
			this.local = local;
			this.desc = desc;
			this.ta = ta;
		}
	}	
}
