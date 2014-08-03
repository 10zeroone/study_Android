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
	
	//음악의 재생 순번을 기억할 변수
	int nSequence = 0;
	//음악을 일시중지 시켰을 때의 남은 시간
	int nPlayPosition = 0;
	//CountDownTimer를 이용해 남은 시간을 계산할 함수
	int nRemainTime = 0;

	//음악이 재생/일시중지인지 알려주는 변수
	Boolean bPaused = false;

	//음악 재생 객체
	MediaPlayer mediaPlayer;
	//남은 시간과 다음곡으로 넘어가게 해주는 객체
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
        
        //음악파일에서 가지고 올 정보 목록을 지정
        String [] aMideaProperties = new String[]
    			{
    			MediaStore.Audio.Media._ID,		//음악 파일의 ID값
    			//MediaStore.Audio.Media.ALBUM_ID,
    			MediaStore.Audio.Media.ARTIST,	//음악 파일의 음악가값
    			MediaStore.Audio.Media.ALBUM,	//음악 파일의 앨범제목 값
    			MediaStore.Audio.Media.TITLE,	//음악 파일의 음악 제목 값
    			MediaStore.Audio.Media.DATA		//음악 파일의 DATA역역
    			};
    	
        //UI오브젝트의 참조값을 받아와서 초기화
    	textViewAuthor = (TextView) findViewById(R.id.tvInfo );
        imageViewAlbumArt = (ImageView) findViewById(R.id.ivAlbumArt);
        
        progressBar = (ProgressBar) findViewById(R.id.pbProgressBar);
        textViewSeconds = (TextView) findViewById(R.id.tvSeconds);
        
        buttonPrevious = (Button) findViewById(R.id.btnPrevious);
        buttonPlayAndPause = (Button) findViewById(R.id.btnPlayAndPause);
        buttonNext = (Button) findViewById(R.id.btnNext);
        
        //음악파일에서 불러올 정보를 담을 ArrayList초기화
        aAlbum = new ArrayList<String>();
        aAlbumArt = new ArrayList<Bitmap>();
        aData = new ArrayList<String>();
        
        //음악 파일들에 대한 정보를 불러와 Cursor를 이용하여 한 칸씩 이동
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
        		
        		//음악파일의 ID값으로 앨범아트를 갖고 오기위한 Uri설정
        		Uri uriAlbumArt = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), Integer.parseInt(strID));
        		
        		
        		//디바이스에 저장된 바이너리파일을 데이터로 변환해 주는 객체 생성
        		ContentResolver contentResolver = this.getContentResolver();
        		
        		InputStream inputStream = null;
        		
        		try {
        			//음악파일 가져오기
        			inputStream = contentResolver.openInputStream(uriAlbumArt);
        		}
        		catch (FileNotFoundException e) {
        			//음악파일 가지고 오는 중 문제 발생시 문제가 생긴부분을 출력
        			e.printStackTrace();
        		}
        		
        		//읽어온 inputStream으로 앨범아트를 해석
        		Bitmap bitmapAlbumArt = BitmapFactory.decodeStream(inputStream);
        		
        		
        		//ArrayList배열에 데이터 추가
        		aAlbum.add(strTitle + " - " + strAlbum + " - " + strArtist);
        		aAlbumArt.add(bitmapAlbumArt);
        		aData.add(strData);
        		
        		//로그 출력
        		Log.d(TAG, "Album : " + aAlbum.get(aAlbum.size() - 1));
        		Log.d(TAG, "Data  : " + aData.get(aData.size() - 1));
        	} while (cursor.moveToNext());	//Cursor가 다음 가리킬 영역이 있으면 반복수행
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
					//음악이 재생 중이라면 일시 중지 시킴
					onPauseButtonPressed();
				}
				else{
					//음악이 일시 중지 상태이면 재생시킴
					onPlayButtonPressed();
				}
			}
		});
        
        buttonNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//다음 곡으로 이동
				onNextButtonPressed();
			}
		});
    }

    public void onPreviousButtonPressed() {
    	//현재 음악 일시 중지 상태가 아니라는 표시
    	bPaused = false;
    	//재생되고 있던 음악 중지
    	mediaPlayer.stop();
    	//카운트타이머 종료
    	countDownTimer.cancel();
    	
    	if (nSequence == 0){
    		//현재 재생 순번이 0이면, aData의 크기(음악의 개수)의 마지막번째로 이동(반복재생)
    		nSequence = aData.size() - 1;
    	}
    		
    	else{
    		//현재 재생 순번이 0이 아니면, 순번에서 1을 뺍니다.
    		nSequence--;
    	}
    		
    	//음악 재생
    	onPlayButtonPressed();
    }
    
    //일시정지/재생 상태에 따라 음악 파일의 잔여 시간을 계산하기 위한 함수
    public void onPlayButtonPressed() {
    	if (bPaused){
    		//음악이 일시 중지상태였다면 일시 중지한 위치로 이동
    		mediaPlayer.seekTo(nPlayPosition);
    	}
    	else {
    		//음악이 일시 중지 상태가 아니라면 음악을 재생 시킬 준비를 함
    		mediaPlayer = new MediaPlayer();
    		mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(aData.get(nSequence)));
    		nRemainTime = mediaPlayer.getDuration() / 1000;
    	}
    	
    	//음악 재생
    	mediaPlayer.start();
    	
    	
    	//앨범아트를 ImageView에 대입
    	//imageViewAlbumArt.setImageBitmap(aAlbumArt.get(nSequence));
    	Bitmap bm = aAlbumArt.get(nSequence);
    	imageViewAlbumArt.setImageBitmap(bm);
    	
    	
    	
    	//debugging
    	Toast.makeText(this, Integer.toString(nSequence), Toast.LENGTH_LONG).show();
    	//음악의 음악가명, 음악명, 앨범명을 TextView에 출력
    	textViewAuthor.setText(aAlbum.get(nSequence));
    	//ProgressBar의 최대값을 음악의 남은 시간 만큼 설정
    	progressBar.setMax(nRemainTime);
    	
    	//CountDownTimer의 인자값 입력
    	//mediaPlayer.getDuration()	: 현재 재생되고 있는 음악의 남은 시간
    	//1000						: 1초마다 반복
    	countDownTimer = new CountDownTimer(mediaPlayer.getDuration(), 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				//남은 시간을 1씩 차감
				nRemainTime--;
				
				//남은 시간을 기준으로 분과 초를 구해, TextView에 출력
				String strSeconds = String.format("%02d", nRemainTime % 60);
				String strMinutes = String.format("%02d", nRemainTime / 60);
				textViewSeconds.setText(strMinutes + ":" + strSeconds);
			}
			
			@Override
			public void onFinish() {
				//다음 곡 이동
				onNextButtonPressed();
			}
		};
		
		countDownTimer.start();
		buttonPlayAndPause.setText("일시중지");
    }
    
    //음악 재생을 일시 중지하는 함수
    public void onPauseButtonPressed() {
    	//일시중지 되었으므로 버튼의 문자열을 재생으로 설정
    	buttonPlayAndPause.setText("재생");
    	
    	//일시중지 상태인 것을 변수에 저장
    	bPaused = true;
    	
    	//일시중지가 될 때 음악이 플레이되었던 지점을 저장
    	nPlayPosition = mediaPlayer.getCurrentPosition();
    	
    	//음악 일시중지
    	mediaPlayer.pause();
    	//CountDownTimer 취소
    	countDownTimer.cancel();
    }
    
    //다음 곡 재생
    public void onNextButtonPressed() {
    	//현재 음악 일시중지 상태가 아니라는 표시
    	bPaused = false;
    	//재생중인 음악 중지
    	mediaPlayer.stop();
    	//CounTDownTimer 취소
    	countDownTimer.cancel();
    	
    	if (nSequence == aData.size() - 1){
    		//현재 재생 순번이 0이면 aData의 크기(음악의 개수)의 마지막 번째로 이동(반복 재생)
    		nSequence = 0;
    	}
    	else{
    		//만약 현재 재생순번이 0이 아니라면, 순번에서 1을 더함(다음 순번의 곡 재생)
    		nSequence++;
    	}
    	//음악 재생
    	onPlayButtonPressed();
    }

}




