package kr.android.framelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	Button btn;
	ImageView ivImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn = (Button)findViewById(R.id.btnPush);
		ivImage = (ImageView)findViewById(R.id.ivImage);
		
		//imgv.setVisibility(View.INVISIBLE);
		
		//�̺�Ʈ ����
		btn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				if( ivImage.getVisibility() == View.VISIBLE){
					//View�� �������� ���� ��
					ivImage.setVisibility(View.INVISIBLE);
				}else{
					//View�� �Ⱥ������� ���� ��
					ivImage.setVisibility(View.VISIBLE);
				}				
			}
		});		
	}
}
