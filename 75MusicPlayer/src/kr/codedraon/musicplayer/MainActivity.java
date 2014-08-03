package kr.codedraon.musicplayer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	final String TAG="MusicPlayer";
	
	//������ ��� ������ ����� ����
	int nSequence = 0;
	//������ �Ͻ����� ������ ���� ���� �ð�
	int nPlayPosition = 0;
	//CountDownTimer�� �̿��� ���� �ð��� ����� �Լ�
	int nRemainTime = 0;

	//������ ���/�Ͻ��������� �˷��ִ� ����
	Boolean bPaused = false;

	//���� ��� ��ü
	MediaPlayer mediaPlayer;
	//���� �ð��� ���������� �Ѿ�� ���ִ� ��ü
	CountDownTimer countDownTimer;

	ArrayList<String> aAlbum;
	ArrayList<Bitmap> aAlbumArt;
	ArrayList<String> aData;

	TextView textViewAuthor;
	ImageView imageViewAlbumArt;

	ProgressBar progressBar;
	TextView textViewSeconds;

	Button buttonPrevious;
	Button buttonPlayAndPause;
	Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //�������Ͽ��� ������ �� ���� ����� ����
        String [] aMideaProperties = new String[]
    			{
    			MediaStore.Audio.Media._ID,		//���� ������ ID��
    			//MediaStore.Audio.Media.ALBUM_ID,
    			MediaStore.Audio.Media.ARTIST,	//���� ������ ���ǰ���
    			MediaStore.Audio.Media.ALBUM,	//���� ������ �ٹ����� ��
    			MediaStore.Audio.Media.TITLE,	//���� ������ ���� ���� ��
    			MediaStore.Audio.Media.DATA		//���� ������ DATA����
    			};
    	
        //UI������Ʈ�� �������� �޾ƿͼ� �ʱ�ȭ
    	textViewAuthor = (TextView) findViewById(R.id.tvInfo );
        imageViewAlbumArt = (ImageView) findViewById(R.id.ivAlbumArt);
        
        progressBar = (ProgressBar) findViewById(R.id.pbProgressBar);
        textViewSeconds = (TextView) findViewById(R.id.tvSeconds);
        
        buttonPrevious = (Button) findViewById(R.id.btnPrevious);
        buttonPlayAndPause = (Button) findViewById(R.id.btnPlayAndPause);
        buttonNext = (Button) findViewById(R.id.btnNext);
        
        //�������Ͽ��� �ҷ��� ������ ���� ArrayList�ʱ�ȭ
        aAlbum = new ArrayList<String>();
        aAlbumArt = new ArrayList<Bitmap>();
        aData = new ArrayList<String>();
        
        //���� ���ϵ鿡 ���� ������ �ҷ��� Cursor�� �̿��Ͽ� �� ĭ�� �̵�
        Cursor cursor = getContentResolver().
        		query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, aMideaProperties, null, null, null);
        		//query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, aMideaProperties, null, null, null);
        
        if (cursor.moveToFirst()) {
        	do {
        		String strID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        		//String strID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        		String strArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        		String strAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
        		String strTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        		String strData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        		
        		//���������� ID������ �ٹ���Ʈ�� ���� �������� Uri����
        		Uri uriAlbumArt = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), Integer.parseInt(strID));
        		
        		
        		//����̽��� ����� ���̳ʸ������� �����ͷ� ��ȯ�� �ִ� ��ü ����
        		ContentResolver contentResolver = this.getContentResolver();
        		
        		InputStream inputStream = null;
        		
        		try {
        			//�������� ��������
        			inputStream = contentResolver.openInputStream(uriAlbumArt);
        		}
        		catch (FileNotFoundException e) {
        			//�������� ������ ���� �� ���� �߻��� ������ ����κ��� ���
        			e.printStackTrace();
        		}
        		
        		//�о�� inputStream���� �ٹ���Ʈ�� �ؼ�
        		Bitmap bitmapAlbumArt = BitmapFactory.decodeStream(inputStream);
        		
        		
        		//ArrayList�迭�� ������ �߰�
        		aAlbum.add(strTitle + " - " + strAlbum + " - " + strArtist);
        		aAlbumArt.add(bitmapAlbumArt);
        		aData.add(strData);
        		
        		//�α� ���
        		Log.d(TAG, "Album : " + aAlbum.get(aAlbum.size() - 1));
        		Log.d(TAG, "Data  : " + aData.get(aData.size() - 1));
        	} while (cursor.moveToNext());	//Cursor�� ���� ����ų ������ ������ �ݺ�����
        }
        
        onPlayButtonPressed();
        
        buttonPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onPreviousButtonPressed();
			}
		});
        
        buttonPlayAndPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()){
					//������ ��� ���̶�� �Ͻ� ���� ��Ŵ
					onPauseButtonPressed();
				}
				else{
					//������ �Ͻ� ���� �����̸� �����Ŵ
					onPlayButtonPressed();
				}
			}
		});
        
        buttonNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//���� ������ �̵�
				onNextButtonPressed();
			}
		});
    }

    public void onPreviousButtonPressed() {
    	//���� ���� �Ͻ� ���� ���°� �ƴ϶�� ǥ��
    	bPaused = false;
    	//����ǰ� �ִ� ���� ����
    	mediaPlayer.stop();
    	//ī��ƮŸ�̸� ����
    	countDownTimer.cancel();
    	
    	if (nSequence == 0){
    		//���� ��� ������ 0�̸�, aData�� ũ��(������ ����)�� ��������°�� �̵�(�ݺ����)
    		nSequence = aData.size() - 1;
    	}
    		
    	else{
    		//���� ��� ������ 0�� �ƴϸ�, �������� 1�� ���ϴ�.
    		nSequence--;
    	}
    		
    	//���� ���
    	onPlayButtonPressed();
    }
    
    //�Ͻ�����/��� ���¿� ���� ���� ������ �ܿ� �ð��� ����ϱ� ���� �Լ�
    public void onPlayButtonPressed() {
    	if (bPaused){
    		//������ �Ͻ� �������¿��ٸ� �Ͻ� ������ ��ġ�� �̵�
    		mediaPlayer.seekTo(nPlayPosition);
    	}
    	else {
    		//������ �Ͻ� ���� ���°� �ƴ϶�� ������ ��� ��ų �غ� ��
    		mediaPlayer = new MediaPlayer();
    		mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(aData.get(nSequence)));
    		nRemainTime = mediaPlayer.getDuration() / 1000;
    	}
    	
    	//���� ���
    	mediaPlayer.start();
    	
    	
    	//�ٹ���Ʈ�� ImageView�� ����
    	//imageViewAlbumArt.setImageBitmap(aAlbumArt.get(nSequence));
    	Bitmap bm = aAlbumArt.get(nSequence);
    	imageViewAlbumArt.setImageBitmap(bm);
    	
    	
    	
    	//debugging
    	Toast.makeText(this, Integer.toString(nSequence), Toast.LENGTH_LONG).show();
    	//������ ���ǰ���, ���Ǹ�, �ٹ����� TextView�� ���
    	textViewAuthor.setText(aAlbum.get(nSequence));
    	//ProgressBar�� �ִ밪�� ������ ���� �ð� ��ŭ ����
    	progressBar.setMax(nRemainTime);
    	
    	//CountDownTimer�� ���ڰ� �Է�
    	//mediaPlayer.getDuration()	: ���� ����ǰ� �ִ� ������ ���� �ð�
    	//1000						: 1�ʸ��� �ݺ�
    	countDownTimer = new CountDownTimer(mediaPlayer.getDuration(), 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				//���� �ð��� 1�� ����
				nRemainTime--;
				
				//���� �ð��� �������� �а� �ʸ� ����, TextView�� ���
				String strSeconds = String.format("%02d", nRemainTime % 60);
				String strMinutes = String.format("%02d", nRemainTime / 60);
				textViewSeconds.setText(strMinutes + ":" + strSeconds);
			}
			
			@Override
			public void onFinish() {
				//���� �� �̵�
				onNextButtonPressed();
			}
		};
		
		countDownTimer.start();
		buttonPlayAndPause.setText("�Ͻ�����");
    }
    
    //���� ����� �Ͻ� �����ϴ� �Լ�
    public void onPauseButtonPressed() {
    	//�Ͻ����� �Ǿ����Ƿ� ��ư�� ���ڿ��� ������� ����
    	buttonPlayAndPause.setText("���");
    	
    	//�Ͻ����� ������ ���� ������ ����
    	bPaused = true;
    	
    	//�Ͻ������� �� �� ������ �÷��̵Ǿ��� ������ ����
    	nPlayPosition = mediaPlayer.getCurrentPosition();
    	
    	//���� �Ͻ�����
    	mediaPlayer.pause();
    	//CountDownTimer ���
    	countDownTimer.cancel();
    }
    
    //���� �� ���
    public void onNextButtonPressed() {
    	//���� ���� �Ͻ����� ���°� �ƴ϶�� ǥ��
    	bPaused = false;
    	//������� ���� ����
    	mediaPlayer.stop();
    	//CounTDownTimer ���
    	countDownTimer.cancel();
    	
    	if (nSequence == aData.size() - 1){
    		//���� ��� ������ 0�̸� aData�� ũ��(������ ����)�� ������ ��°�� �̵�(�ݺ� ���)
    		nSequence = 0;
    	}
    	else{
    		//���� ���� ��������� 0�� �ƴ϶��, �������� 1�� ����(���� ������ �� ���)
    		nSequence++;
    	}
    	//���� ���
    	onPlayButtonPressed();
    }

}




