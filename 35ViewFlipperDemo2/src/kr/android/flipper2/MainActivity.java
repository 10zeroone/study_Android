package kr.android.flipper2;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class MainActivity extends Activity implements View.OnTouchListener{
	
	ViewFlipper myViewFlipper;
	
	int[] imageItems;
	ImageView iv1, iv2;
	int num;
	int cnt;

	//터치 이벤트 발생 지점의 x좌표 저장
	float down_x;
	float up_x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//배열 생성
		imageItems = new int[]{R.drawable.img01,R.drawable.img02,R.drawable.img03, R.drawable.img04, 
				R.drawable.img05, R.drawable.img06, R.drawable.img07, R.drawable.img08, R.drawable.img09};
		
		myViewFlipper = (ViewFlipper)findViewById(R.id.vfView);
		
		//이미지를 ViewFlipper에 등록
		for(int i : imageItems){
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(i);
			myViewFlipper.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
					ViewGroup.LayoutParams.MATCH_PARENT));
		}
		
		//이벤트 연결
		myViewFlipper.setOnTouchListener(this);		
	}

	//
	//이벤트 핸들러
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//터치 이벤트가 일어난 View가 ViewFlipper가 아니면 return
		if(v !=myViewFlipper){
			return false;
		}
		
		//좌표 체크
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			//터치 시작 지점의 x좌표 저장
			down_x = event.getX();
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			//터치가 끝나고 손을 띤 지점의 x좌표 저장
			up_x = event.getX();
			
			if(up_x < down_x){
				//터치할 때 왼쪽 방향으로 진행				
				myViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
				myViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
				myViewFlipper.showNext();
			}else if(up_x > down_x){
				//터치할 때 오른쪽 방향으로 진행
				myViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
				myViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
				myViewFlipper.showPrevious();
			}			
		}		
		return true;
	}
}
