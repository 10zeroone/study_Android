package kr.android.video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
	
	VideoView vwVideo;
	File sd_path = Environment.getExternalStorageDirectory();
	final String VIDEO_path = "http://192.168.0.2:8080/HellowWeb/movie03.mp4";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		vwVideo = (VideoView)findViewById(R.id.vwVideo);
		vwVideo.requestFocus();
		
		//미디어 컨트롤러 생성 후 등록
		vwVideo.setMediaController(new MediaController(this));
		
		try{
			
			//raw의 파일을 내장영역에 저장
			rawToFile(this, R.raw.movie01, "movie01.mp4");
			
			//내장영역의 동영상 호출
			//외부에서 호출할 수 있도록 경로 지정
			String path = getFilesDir().getAbsolutePath()+"/movie01.mp4";
			//VideoView객체에서 재상할 동영상 설정
			vwVideo.setVideoPath(path);
			Toast.makeText(MainActivity.this, getFilesDir().getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
						
			
			
			//SD카드에서 동영상 호출
			vwVideo.setVideoPath(sd_path.getAbsolutePath()+ "/movie02.mp4");
			Toast.makeText(MainActivity.this, sd_path.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
			
			
			
			
			//서버에서 동영상 호출			
			//VideoView객체에서 재상할 동영상 설정
			vwVideo.setVideoURI(Uri.parse(VIDEO_path));
			Toast.makeText(MainActivity.this, VIDEO_path, Toast.LENGTH_LONG).show();
			
			
		}catch(Exception e){
			Log.e("VideoDemo", "Play Error", e);
		}		
	}
	
	
	
	//InputStream을 내장영역의 파일로 저장
	private void intoFile(Context context, InputStream inputStream, String fileName) throws Exception{
		int count;
		byte[] size = new byte[1024];
		OutputStream outputStream = null;
		
		try{
			//MODE_WORLD_READABLE: 경로를 지정해서 파일 접근 가능
			//deprecated 됨
			outputStream = openFileOutput(fileName, MODE_WORLD_READABLE);
			
			while(true){
				count = inputStream.read(size);
				if(count<=0) break;
				outputStream.write(size, 0, count);
			}
		}catch(Exception e){
			Log.e("VideoDemo", "IO Error", e);
		}finally{
			if(outputStream!=null){ try{outputStream.close();}catch(IOException e){e.printStackTrace();} }
			if(inputStream!=null){ try{inputStream.close();}catch(IOException e){e.printStackTrace();} }
		}
	}
	
	
	
	//raw 폴더에서 파일을 호출해서 InputStream으로 가공
	private void rawToFile(Context context, int resID, String fileName) throws Exception{
		InputStream inputStream = context.getResources().openRawResource(resID);

		intoFile(getApplication(), inputStream, fileName);
	}
}
