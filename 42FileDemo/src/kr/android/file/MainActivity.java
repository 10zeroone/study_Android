/* 데이터 저장 - 내장영역에 데이터 저장
 * 
 * Activity가 가지고 있는 내장 메소드를 사용하여 데이터 저장
 * 생명주기에 의해 내용 저장하도록 구현
 * 내장영역이므로 경로 없이 파일 호출가능
 * 
*/
package kr.android.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//파일명 지정
	static final String NOTES = "notes.txt";
	
	EditText etEditor;
	Button btnClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etEditor = (EditText)findViewById(R.id.etEditor);
		btnClose = (Button)findViewById(R.id.btnClose);
		
		//이벤트 처리
		btnClose.setOnClickListener(new OnClickListener() {
			
			//이벤트 핸들러
			@Override
			public void onClick(View v) {
				//Activity 종료
				finish();
			}
		});		
	}
	
	//파일로 부터 Data를 읽어옴
	@Override
	public void onResume(){
		//초기화 시킨다음에 반드시 작업해 주어야 함
		super.onResume();
		
		InputStream in = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		
		//파일 유무에 대한 예외 처리
		try{
			//파일로 부터 데이터를 읽어서 InputStream 으로 반환
			//파일이 존재하지 않는 경우 null반환 및 FileNotFoundException발생
			in = openFileInput(NOTES);			
			
			if(in !=null){	//파일이 존재하는 경우
				//문자 스트림으로 변환
				inputStreamReader = new InputStreamReader(in);
				reader = new BufferedReader(inputStreamReader);

				String str;
				StringBuffer buf = new StringBuffer();				
				 while( (str=reader.readLine()) != null){
					 buf.append(str + '\n');
				 }				 
				 etEditor.setText(buf.toString());				
			}			
		}catch(FileNotFoundException e){
			//최초앱 실행시에는 파일이 존재하지 않기 때문에 1회 예외가 발생함
			//아무처리 안함
		}catch(Exception e){
			//그외 예외시 예외내용 알려줌
			Toast.makeText(this, "예외: " + e.toString(), Toast.LENGTH_SHORT).show();
		}finally{
			if( reader!=null ){ try{reader.close();}catch(IOException e){e.printStackTrace();} }
			if( inputStreamReader!=null ){ try{inputStreamReader.close();}catch(IOException e){e.printStackTrace();} }
			if( in!=null ){ try{in.close();}catch(IOException e){e.printStackTrace();} }
		}
	}
	
	//파일에 Data를 저장함
	@Override
	public void onPause(){
		super.onPause();
		
		OutputStreamWriter out = null;
		
		try{
			//파일 생성(ByteStream을 문자스트림으로 변환), 
			//파일 생성시 생성방식 속성 지정(
			//	MODE_PRIVATE: 덮어쓰기
			//	MODE_APPEND	: 이어쓰기)			
			out = new OutputStreamWriter(openFileOutput(NOTES, MODE_PRIVATE));
			out.write(etEditor.getText().toString());
			
			Toast.makeText(this, "데이터를 저장합니다!",  Toast.LENGTH_SHORT).show();
			
		}catch(Exception e){
			e.printStackTrace();			
		}finally{
			if( out!=null){ try{out.close();}catch(IOException e){e.printStackTrace();} }
			
		}
	}	
}
