package kr.android.network2;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView tvInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		
		//네트워크 정보를 얻기위해서 ConnectivityManager호출
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		//WiFi정보 호출
		NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWiFiAvailable = networkInfo.isAvailable();
		boolean isWiFiConn = networkInfo.isConnected();
		
		tvInfo.append("WiFi 연결 가능: " + isWiFiAvailable + "\n");
		tvInfo.append("WiFi 연결 상태: " + isWiFiConn + "\n");
		tvInfo.append("\n");
		
		//모바일 정보 호출
		networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileAvailable = networkInfo.isAvailable();
		boolean isMobileconn = networkInfo.isConnected();
		tvInfo.append("모바일 연결 가능: " + isMobileAvailable + "\n");
		tvInfo.append("모바일 연결 상태: " + isMobileconn + "\n");		
	}
}
