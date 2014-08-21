package kr.android.audio;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	//객체 선언
	MediaPlayer mediaPlayer;
	
	Button btnPlay, btnPause, btnReplay;
	int playbackPosition = 0;
	File sd_path = Environment.getExternalStorageDirectory();
	final String AUDIO_PATH ="http://192.168.0.2:8080/HellowWeb/audio03.mp3";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnPlay =(Button)findViewById(R.id.btnPlay);
		btnPause =(Button)findViewById(R.id.btnPause);
		btnReplay =(Button)findViewById(R.id.btnReplay);
		
		btnPlay.setOnClickListener(this);
		btnPause.setOnClickListener(this);
		btnReplay.setOnClickListener(this);	
	}
	
	//로컬영역(내장되어) 있는 데이터 호출
	private void playAudioLocal() throws Exception{
		
		killMediaPlayer();
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer = MediaPlayer.create(this, R.raw.audio01);
		mediaPlayer.start();
		Toast.makeText(this, "로컬저장 mp3 재생", Toast.LENGTH_SHORT).show();
	}
	
	
	//SDCard에서 데이터 호출
	private void playAudioSD() throws Exception{
		killMediaPlayer();
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(sd_path.getAbsolutePath()+"/audio02.mp3");
		mediaPlayer.prepare();
		mediaPlayer.start();	
		Toast.makeText(this, "SDCard에서 mp3 재생", Toast.LENGTH_SHORT).show();
	}
	
	
	//서버에서 데이터 받기
	private void playAudioServer(String url) throws Exception{
		killMediaPlayer();

		mediaPlayer = new MediaPlayer();
		//대상 파일 지정
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepare();
		mediaPlayer.start();
		Toast.makeText(this, "서버에서 mp3 재생", Toast.LENGTH_SHORT).show();
	}
		
	
	

	//MediaPlayer 자원 정리
	private void killMediaPlayer(){
		if(mediaPlayer!=null){
			try{
				//리소스 해제
				mediaPlayer.release();
			}catch(Exception e){
				Log.e("AudioDemo", "Release Error", e);
			}
		}
	}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		killMediaPlayer();
	}
	

	//이벤트 핸들러
	@Override
	public void onClick(View v) {

		if(v.getId()==R.id.btnPlay){			//재생시작
			
			try {
				//로컬 영역 재생
				playAudioLocal();

				
				//SDCard영역 재생
				//playAudioSD();
				
				
				//서버영역 재생
				//playAudioServer(AUDIO_PATH);
				
			} catch (Exception e) {
				Log.e("AudioDemo", "Play Error", e);
			}
			
		}else if(v.getId()==R.id.btnReplay){	//재생 재개
			if(mediaPlayer!=null && !mediaPlayer.isPlaying()){
				//중지시점의 재생 위치로 이동
				mediaPlayer.seekTo(playbackPosition);
				//재생
				mediaPlayer.start();		
				Toast.makeText(this, "다시 재생", Toast.LENGTH_SHORT).show();
			}			
			
		}else if(v.getId()==R.id.btnPause){		//재생 일시중지
			
			if(mediaPlayer!=null){
				//일시정지 시점의 재생 위치 저장
				playbackPosition = mediaPlayer.getCurrentPosition();
				//일시정지
				mediaPlayer.pause();	
				Toast.makeText(this, "재생 일시 중지", Toast.LENGTH_SHORT).show();
			}			
		}
	}
}
