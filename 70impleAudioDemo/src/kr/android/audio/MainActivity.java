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

	//��ü ����
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
	
	//���ÿ���(����Ǿ�) �ִ� ������ ȣ��
	private void playAudioLocal() throws Exception{
		
		killMediaPlayer();
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer = MediaPlayer.create(this, R.raw.audio01);
		mediaPlayer.start();
		Toast.makeText(this, "�������� mp3 ���", Toast.LENGTH_SHORT).show();
	}
	
	
	//SDCard���� ������ ȣ��
	private void playAudioSD() throws Exception{
		killMediaPlayer();
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(sd_path.getAbsolutePath()+"/audio02.mp3");
		mediaPlayer.prepare();
		mediaPlayer.start();	
		Toast.makeText(this, "SDCard���� mp3 ���", Toast.LENGTH_SHORT).show();
	}
	
	
	//�������� ������ �ޱ�
	private void playAudioServer(String url) throws Exception{
		killMediaPlayer();

		mediaPlayer = new MediaPlayer();
		//��� ���� ����
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepare();
		mediaPlayer.start();
		Toast.makeText(this, "�������� mp3 ���", Toast.LENGTH_SHORT).show();
	}
		
	
	

	//MediaPlayer �ڿ� ����
	private void killMediaPlayer(){
		if(mediaPlayer!=null){
			try{
				//���ҽ� ����
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
	

	//�̺�Ʈ �ڵ鷯
	@Override
	public void onClick(View v) {

		if(v.getId()==R.id.btnPlay){			//�������
			
			try {
				//���� ���� ���
				playAudioLocal();

				
				//SDCard���� ���
				//playAudioSD();
				
				
				//�������� ���
				//playAudioServer(AUDIO_PATH);
				
			} catch (Exception e) {
				Log.e("AudioDemo", "Play Error", e);
			}
			
		}else if(v.getId()==R.id.btnReplay){	//��� �簳
			if(mediaPlayer!=null && !mediaPlayer.isPlaying()){
				//���������� ��� ��ġ�� �̵�
				mediaPlayer.seekTo(playbackPosition);
				//���
				mediaPlayer.start();		
				Toast.makeText(this, "�ٽ� ���", Toast.LENGTH_SHORT).show();
			}			
			
		}else if(v.getId()==R.id.btnPause){		//��� �Ͻ�����
			
			if(mediaPlayer!=null){
				//�Ͻ����� ������ ��� ��ġ ����
				playbackPosition = mediaPlayer.getCurrentPosition();
				//�Ͻ�����
				mediaPlayer.pause();	
				Toast.makeText(this, "��� �Ͻ� ����", Toast.LENGTH_SHORT).show();
			}			
		}
	}
}
