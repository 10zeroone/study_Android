package kr.android.network;

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
		ConnectivityManager connectivityManager = 
				(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		
		//모든 정보의 객체저장
		NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
		
		//네트워크 정보 출력
		for(int i=0; i<networkInfos.length; i++){
			NetworkInfo networkInfo = networkInfos[i];
			tvInfo.append("typeName: " + networkInfo.getTypeName() + "\n");
			tvInfo.append("available: " + networkInfo.isAvailable() + "\n");
			tvInfo.append("connected: " + networkInfo.isConnected() + "\n");
			tvInfo.append("=================================\n");
		}

	}
}
