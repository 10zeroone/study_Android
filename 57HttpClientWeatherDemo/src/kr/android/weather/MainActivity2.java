/*XML PullPaer 과제2
 * 
 * buildForeCastsbyXMLPullParser()
 * 
 * */

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

public class MainActivity2 extends Activity {
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
	
	
	//스레드 호출
	public void updateForeCast(){
		//ProgressBar를 보여지게 처리
		progressBar.setVisibility(View.VISIBLE);
		
		new Thread(){
			@Override
			public void run(){
				//DOM Parser이용
				//buildForeCastsbyDOM(getStreamFromURL());				
				
				//XMLPullParser이용
				buildForeCastsbyXMLPullParser(getStreamFromURL());

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
	
	
	//XML파일을 읽어들여 XmlPullParser가 파싱
	public void buildForeCastsbyXMLPullParser(InputStream input){
		try {
			// uri에 저장된 사이트에 접속
			// weatherUrl = new URL(WEATHER_URL);

			// xml데이터를 읽어서 inpuitstream에 저장
			// InputStream in = weatherUrl.openStream(); // getStreamFromURL();

			// XmlPullParser를 사용하기 위해서
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

			// 네임스페이스 사용여부
			factory.setNamespaceAware(true);
			// xml문서를 이벤트를 이용해서 데이터를 추출해주는 객체
			XmlPullParser xpp = factory.newPullParser();

			// XmlPullParser xml데이터를 저장
			xpp.setInput(input, "UTF-8");

			// 이벤트 저장할 변수선언
			int eventType = xpp.getEventType();
			// 이벤트 상태에 따라 컨트롤 하는 경우도 있다던데.. 이번 과제에서는 없음.
			boolean isOk = false;
			// ForeCast forecast = new ForeCast();
			String tmpDesc = "";
			String tmpTa = "";
			String tagName = "";

			ForeCast forecast; 
			// xml의 데이터의 끝까지 돌면서 원하는 데이터를 얻어옴
			// eventType=xpp.next();//파싱한 자료에서 다음 라인으로 이동
			// xmlParserEvent = xmlPullParser.next(); //루프돌기전에 개행
			// <tagName attribute="value"> 이 부분에서 '>' 부분도 이벤트값을 가짐.(즉 시작/끝 2번의
			// 이벤트)
			// eventType=xpp.next(); 와 xpp.nextToken(); 같음
			// if(xpp.getAttributeName(eventType)==) .. 필요하면 이 구문도 사용
			while (eventType != XmlPullParser.END_DOCUMENT) {

				forecast = new ForeCast();
//				ForeCast forecast = new ForeCast();
				// 지역 forecast.local = ;
				// 날씨 forecast.desc = ;
				// 온도 forecast.ta = ;
				
					if (eventType == XmlPullParser.START_TAG) { // 시작 태그를 만났을때.
						// 태그명을 저장
						tagName = xpp.getName();
						if (tagName.equals("local")) {
							//forecast.desc = xpp.getAttributeValue(2);
							tmpDesc = xpp.getAttributeValue(2);
							//forecast.ta = xpp.getAttributeValue(3);
							tmpTa = xpp.getAttributeValue(3);
							isOk = true;
						}
					} else if (eventType == XmlPullParser.END_TAG) {
						// isOk = !isOk;
					} else if (isOk && (eventType == XmlPullParser.TEXT)) {
						forecast.desc = tmpDesc;
						forecast.ta = tmpTa;
						forecast.local = xpp.getText();
						arrayList.add(forecast);
						isOk = false;
					}
				eventType = xpp.next(); // 다음 이벤트 타입
			}
		} catch (Exception e) {
			Log.e(TAG, "예외 발생", e);
		}
		
	}
	
	
	//XML파일을 DOM트리를 생성해서 파싱
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
	
	//서버에 접근해서 XML데이터 요청
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

	
	//UI작업(데이터를 표시하기 위한 HTML)
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
	
	
	//날씨정보(지역, 날씨, 온도)를 저장할 클래스 객체 생성
	class ForeCast{
		String local;
		String desc;
		String ta;
		
		public ForeCast(){}
		
		public ForeCast(String local, String desc, String ta){
			this.local = local;
			this.desc = desc;
			this.ta = ta;
		}
	}	
}
