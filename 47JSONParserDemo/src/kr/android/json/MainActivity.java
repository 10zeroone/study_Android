package kr.android.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.AssetManager.AssetInputStream;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView)findViewById(R.id.tvText);
		
		//assets폴더의 정보 처리를 위해 AssetManager 생성
		AssetManager assetManager = getResources().getAssets();
		
		try{
			//JSON파일 읽기
			AssetInputStream ais = (AssetInputStream)assetManager.open("sample.json");
			
			//byte스트림을 문자스트림으로 변환
			BufferedReader br = new BufferedReader(new InputStreamReader(ais, "UTF-8"));
			
			StringBuffer sb = new StringBuffer();
			String result = null;
			
			while((result=br.readLine()) != null){
				sb.append(result);
			}
			
			//JSON데이터
			String msg = sb.toString();
			
			//JSONObject  생성
			JSONObject jsonObject = new JSONObject(msg);
			
			String menu=jsonObject.getString("menu");
			text.setText(menu+"\n");
			
			//JSONArray 생성
			JSONArray jsonArray = new JSONArray(jsonObject.getString("member"));
			
			for( int i=0; i<jsonArray.length(); i++){
				text.append("===================\n");
				text.append(jsonArray.getJSONObject(i).getString("id")+"\n");
				text.append(jsonArray.getJSONObject(i).getString("name")+"\n");
				text.append(jsonArray.getJSONObject(i).getString("address")+"\n");
				text.append(jsonArray.getJSONObject(i).getString("job")+"\n");			
			}			
			
		}catch(Exception e){
			Log.e("JSON Deomo", e.toString());
			
		}		
	}	
}
