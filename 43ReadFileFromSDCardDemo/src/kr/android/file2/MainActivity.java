package kr.android.file2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//파일명 지정
	String filename = "test" + System.currentTimeMillis() +".txt";
	
	//SD카드 경로 지정
	File sdcard_path = Environment.getExternalStorageDirectory();
	
	TextView tvOutput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvOutput =(TextView)findViewById(R.id.tvOutput);
		
		//파일 생성 호출
		writeFileToSDCard();
		
		//파일 읽기 호출
		readFileFromSDCard();		
		
	}
	
	//파일 생성
	private void writeFileToSDCard(){
		tvOutput.setText("[파일쓰기]\n");
		
		//SDCard가 존재하고 파일을 생성할 수 있는지 여부 체크
		if(sdcard_path.exists() && sdcard_path.canWrite()){
			//폴더 생성
			//폴더 생성을 위한 경로 지정
			File uadDir = new File(sdcard_path.getAbsolutePath() + "/filetest");
			//사용하고자 하는 폴더(디렉토리) 생성
			uadDir.mkdir();
			
			//파일 생성
			FileOutputStream fos = null;
			try{
				fos = new FileOutputStream(uadDir.getAbsolutePath()+"/"+ filename);
				
				String msg="SD카드의 파일 내용";
				//String -> byte[]
				fos.write(msg.getBytes());
				tvOutput.append("파일이 생성되었습니다.\n");
			}catch(Exception e){
				Toast.makeText(this, "예외: "+ e.toString(), Toast.LENGTH_SHORT).show();
				
			}finally{
				if(fos!=null){ try{fos.close();}catch(Exception e){e.printStackTrace();} }
			}			
		}
	}
	
	//파일로부터 데이터 읽기
	private void readFileFromSDCard(){
		
		tvOutput.append("=====================\n");
		tvOutput.append("[파일읽기]\n");
		File rFile = new File(sdcard_path.getAbsolutePath() + "/filetest/"+filename);
		
		if(rFile.canRead()){
			FileInputStream fis = null;
			
			try{
				fis = new FileInputStream(rFile);
				byte[] reader = new byte[fis.available()];
				fis.read(reader);
				
				//byte[] -> String
				tvOutput.append(new String(reader));				
				
			}catch(IOException e){
				Toast.makeText(this, "예외: "+e.toString(), Toast.LENGTH_SHORT).show();
			}finally{
				if(fis!=null){ try{fis.close();}catch(IOException e){e.printStackTrace();} }
			}
		}		
	}
}
