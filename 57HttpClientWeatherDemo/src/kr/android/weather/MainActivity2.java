/*XML PullPaer ����2
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
	//���û ���� ����
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
	
	
	//������ ȣ��
	public void updateForeCast(){
		//ProgressBar�� �������� ó��
		progressBar.setVisibility(View.VISIBLE);
		
		new Thread(){
			@Override
			public void run(){
				//DOM Parser�̿�
				//buildForeCastsbyDOM(getStreamFromURL());				
				
				//XMLPullParser�̿�
				buildForeCastsbyXMLPullParser(getStreamFromURL());

				handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						//HTML�ۼ�
						String page = generatePage();
						
						wvView.loadDataWithBaseURL(null, page, "text/html", "UTF-8", null);
						//Toast.makeText(MainActivity.this, "����", Toast.LENGTH_SHORT).show();
						//ProgressBar �� �������� ó��
						progressBar.setVisibility(View.GONE);
					}
				});
				
			}
		}.start();
	}
	
	
	//XML������ �о�鿩 XmlPullParser�� �Ľ�
	public void buildForeCastsbyXMLPullParser(InputStream input){
		try {
			// uri�� ����� ����Ʈ�� ����
			// weatherUrl = new URL(WEATHER_URL);

			// xml�����͸� �о inpuitstream�� ����
			// InputStream in = weatherUrl.openStream(); // getStreamFromURL();

			// XmlPullParser�� ����ϱ� ���ؼ�
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

			// ���ӽ����̽� ��뿩��
			factory.setNamespaceAware(true);
			// xml������ �̺�Ʈ�� �̿��ؼ� �����͸� �������ִ� ��ü
			XmlPullParser xpp = factory.newPullParser();

			// XmlPullParser xml�����͸� ����
			xpp.setInput(input, "UTF-8");

			// �̺�Ʈ ������ ��������
			int eventType = xpp.getEventType();
			// �̺�Ʈ ���¿� ���� ��Ʈ�� �ϴ� ��쵵 �ִٴ���.. �̹� ���������� ����.
			boolean isOk = false;
			// ForeCast forecast = new ForeCast();
			String tmpDesc = "";
			String tmpTa = "";
			String tagName = "";

			ForeCast forecast; 
			// xml�� �������� ������ ���鼭 ���ϴ� �����͸� ����
			// eventType=xpp.next();//�Ľ��� �ڷῡ�� ���� �������� �̵�
			// xmlParserEvent = xmlPullParser.next(); //������������ ����
			// <tagName attribute="value"> �� �κп��� '>' �κе� �̺�Ʈ���� ����.(�� ����/�� 2����
			// �̺�Ʈ)
			// eventType=xpp.next(); �� xpp.nextToken(); ����
			// if(xpp.getAttributeName(eventType)==) .. �ʿ��ϸ� �� ������ ���
			while (eventType != XmlPullParser.END_DOCUMENT) {

				forecast = new ForeCast();
//				ForeCast forecast = new ForeCast();
				// ���� forecast.local = ;
				// ���� forecast.desc = ;
				// �µ� forecast.ta = ;
				
					if (eventType == XmlPullParser.START_TAG) { // ���� �±׸� ��������.
						// �±׸��� ����
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
				eventType = xpp.next(); // ���� �̺�Ʈ Ÿ��
			}
		} catch (Exception e) {
			Log.e(TAG, "���� �߻�", e);
		}
		
	}
	
	
	//XML������ DOMƮ���� �����ؼ� �Ľ�
	public void buildForeCastsbyDOM(InputStream input){
		
		try{
			//DOM �ļ� ����
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//DOM tree ����
			Document doc = builder.parse(input);
			
			NodeList weather = doc.getElementsByTagName("local");
			
			for(int i=0; i< weather.getLength();i++){
				//import org.w3c.dom.Element;
				Element local = (Element)weather.item(i);
				
				ForeCast forecast = new ForeCast();
				//����(���ø�)
				forecast.local = local.getFirstChild().getNodeValue();
				//����
				forecast.desc = local.getAttribute("desc");
				//�µ�
				forecast.ta = local.getAttribute("ta");			
				
				arrayList.add(forecast);
						
			}						
			
		}catch(Exception e){
			Log.e(TAG, "�Ľ� ����", e);
		}		
	}
	
	//������ �����ؼ� XML������ ��û
	public InputStream getStreamFromURL(){
		InputStream input = null;

		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(WEATHER_URL);

			//������ ���� ��ü
			HttpResponse httpResponse = (HttpResponse)httpClient.execute(httpGet);

			//���� ���� ó��
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
			input = bufferedHttpEntity.getContent();

		}catch(Exception e){
			Log.e(TAG, "���� ����", e);

		}

		return input;
	}

	
	//UI�۾�(�����͸� ǥ���ϱ� ���� HTML)
	public String generatePage(){	
		
		StringBuffer result = new StringBuffer("<html><body><table width=100%>");
			result.append("<tr><th width=30%>����</th>");
			result.append("<th width=50%>����</th>");
			result.append("<th width=20%>�µ�</th></tr>");
		
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
	
	
	//��������(����, ����, �µ�)�� ������ Ŭ���� ��ü ����
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
