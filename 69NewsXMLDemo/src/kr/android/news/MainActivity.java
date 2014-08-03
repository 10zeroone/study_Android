package kr.android.news;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends ListActivity {
	
	//XML정보 경로
	public static final String  ARTICLE_URL = "https://news.google.co.kr/news/feeds?pz=1&cf=all&ned=kr&h1=ko&topic=e&output=rss";

	//목록에 필요한 데이터 저장 객체 생성
	ArrayList<MyNews> myRSS = new ArrayList<MyNews>();
	
	TextView tvTitle, tvDate, tvDescription;	
	
	Handler handler = new Handler();
	MyListAdapter adapter;
	ProgressBar progressBar;
	
	//Sun, 15 Jun 2014 10:50:18 GMT -> 2014-06-15 10:50:18
	//영문 형식의 날짜인 String데이터를 -> Date 데이터로 변형
	SimpleDateFormat origin_Format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	SimpleDateFormat new_Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		progressBar = (ProgressBar)findViewById(R.id.progressBar);		
		
		adapter = new MyListAdapter(this, R.layout.news_list);
		
		//ListView에 MyListAdapter등록
		setListAdapter(adapter);
		
		//데이터 읽기
		progressBar.setVisibility(View.VISIBLE);
		
		new Thread(){
			@Override
			public void run() {
				final InputStream inputStream = getStreamFromURL();
				
				//UI 설정
				handler.post(new Runnable() {
					
					@Override
					public void run() {
							parseXML(inputStream);
							//ProgressBar 중지
							progressBar.setVisibility(View.GONE);
						}
				});
				super.run();
			}			
		}.start();
	}
	
	/** 
	 * XML파싱
	 * 
	 */
	private void parseXML(InputStream in){
		
		try{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			
			//XML 태그사이의 공백을 제거하는 역할 (JDK 6.0이상부터 사용)
			documentBuilderFactory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			//DOM 트리 생성
			//import org.w3c.dom.Document;
			Document document = documentBuilder.parse(in);
			
			//기사 추출
			NodeList articles = document.getElementsByTagName("item");
			for(int i=0; i<articles.getLength(); i++){
				MyNews myNews = new MyNews();
				
				//<item> 태그의 자식 태그 호출
				NodeList article = articles.item(i).getChildNodes();
				for(int j=0; j<article.getLength(); j++){
					Node node = article.item(j);
					if(node.getNodeName().equals("title")){
						myNews.title = node.getFirstChild().getNodeValue();
					}else if(node.getNodeName().equals("pubDate")){		
						
//						myNews.pubDate = node.getFirstChild().getNodeValue();
						
						//날짜 표기을 원하는 형식으로 변형하기
										//String -> Date
						//import java.util.Date;						
						Date date = origin_Format.parse(node.getFirstChild().getNodeValue());
										//Date -> String
						myNews.pubDate = new_Format.format(date);
						
					}else if(node.getNodeName().equals("description")){
						myNews.description = node.getFirstChild().getNodeValue();
					}
				}
				//데이터 담기
				myRSS.add(myNews);
			}
			
			//어탭터에 매핑된 데이터를 읽어들여 ListView 갱신
			adapter.notifyDataSetChanged();
			
		}catch(Exception e){
			Log.e("NewsXMLDemo", "Parsing Error", e);
		}finally{
			if( in!=null){ try{in.close();}catch(IOException e){e.printStackTrace();} }
		}
		
	}
	
	/** 
	 * 서버에서 XML 읽기
	*/
	private InputStream getStreamFromURL(){
		
		InputStream input = null;
		
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(ARTICLE_URL);
			
			//응답을 받을 객체
			HttpResponse httpResponse = (HttpResponse)httpClient.execute(getMethod);
			
			//응답 수신처리
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
			input = bufferedHttpEntity.getContent();
			
		}catch(Exception e){
			Log.e("NewsXMLDemo", "Network Error", e);
		}
		
		return input;
	}
	
	
	//이벤트 핸들러
	@Override
	protected void onListItemClick(ListView list, View v, int position, long id){
		Intent intent = new Intent(this, NewsRSSDetail.class);
		//데이터 설정
		intent.putExtra("title", myRSS.get(position).title);
		intent.putExtra("pubDate", myRSS.get(position).pubDate);
		intent.putExtra("description", myRSS.get(position).description);
		
		startActivity(intent);	
	}	
	
	
	/**
	 * 커스텀 어탭터
	 *
	 */
	class MyListAdapter extends BaseAdapter{
		
		Context context;
		LayoutInflater inflater;
		//리스트에 들어가는 XML파일
		int layout;
		
		public MyListAdapter(Context context, int layout){
			this.context = context;
			this.layout = layout;
			
			//LayoutInflater생성
			inflater = LayoutInflater.from(context);
		}

		//전체 데이터의 개수 반환
		@Override
		public int getCount() {
			
			return myRSS.size();
		}

		//전달된 포지션값에 해당하는 데이터 반환 
		@Override
		public Object getItem(int position) {
			return myRSS.get(position).title;
		}

		//포시션값 반환
		@Override
		public long getItemId(int position) {
			return position;
		}

		//실제 데이터 반환 (아이템으로 보여질 뷰 객체 리턴)
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView==null){
				//직접 매칭 시킬것을 것이기 때문에 parent, false설정
				convertView = inflater.inflate(layout, parent, false);
			}
			tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
			tvTitle.setText(myRSS.get(position).title);
			
			
			tvDate =(TextView)convertView.findViewById(R.id.tvDate);
			tvDate.setText(myRSS.get(position).pubDate);
			
			return convertView;
		}
	}}

/**
 * 데이터 저장
 *
 */
class MyNews{
	String title;
	String pubDate;
	String description;
	
}