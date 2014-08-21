package kr.android.seekbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends Activity {
	
	ImageView imageView;
	SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageView = (ImageView)findViewById(R.id.ivAndroid);
        seekBar = (SeekBar)findViewById(R.id.sbSeekBar);
        
        //seekBar 초기화
        seekBar.setMax(255);		//SeekBar 최대길이	: 255
        seekBar.setProgress(225);	//SeekBar 초기값	: 255	
        
        //익명내부클래스로 이벤트 핸들러 구현
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

        	//seekBar 값이 변경 될 때마다 ImageView의 투명도 조절
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
										//minSDKVersion = 16
				//ImageView의 투명도 설정
				imageView.setImageAlpha(seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
        	
        });
    }
}
