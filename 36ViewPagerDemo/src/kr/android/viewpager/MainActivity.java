package kr.android.viewpager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//변수 선언
	Button btnYellow, btnPink, btnRed;
	ViewPager myViewPager;
	int[] btn ={R.id.btnP, R.id.btnR, R.id.btnY};
	
	//이벤트 처리
	private View.OnClickListener myListener = new View.OnClickListener() {
		//이벤트 핸들러
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnY:			
				myViewPager.setCurrentItem(0);
				break;
			case R.id.btnP:
				myViewPager.setCurrentItem(1);
				break;
			case R.id.btnR:
				myViewPager.setCurrentItem(2);
				break;
			
			case R.id.btnYellow:				
			case R.id.btnPink:				
			case R.id.btnRed:
				String text = ((Button)v).getText().toString();
				Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();					
			}			
		}
	};	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//버튼을 읽어와서 바로 이벤트와 연결
		for(int id:btn){
			findViewById(id).setOnClickListener(myListener);			
		}
		
		//ViewPager 참조
		myViewPager = (ViewPager)findViewById(R.id.vpView);
		
		//ViewPager에 어댑터 클래스를 등록
		myViewPager.setAdapter(new MyPagerAdapter(this));
	}
	
	//추상클래스인 PagerAdapter상속하여 커스텀페이저어댑터 정의
	private class MyPagerAdapter extends PagerAdapter{		
		//XML을 읽어들여서 XML에 표시된 클래스 정보를 토대로 하여 객체를 생성해주는 객체 선언
		LayoutInflater myLayoutInflater;
		
		public MyPagerAdapter(Context context){
			myLayoutInflater = LayoutInflater.from(context);
		}
		
		//ViewPager에서 사용할 View객체 생성/등록 하도록 instantiateItem() 재정의
		//pager		: ViewPager
		//position	: 몇번째 화면을 구성할지에 대한  index
		@Override
		public Object instantiateItem(View pager, int position){			
			View v = null;
			switch(position){
			case 0:
				
				//지정된 XML파일을 읽어들여 객체를 생성하고 XML의 상에 있는 View를 반환
				v = myLayoutInflater.inflate(R.layout.page_yellow, null);
				//RelativeLayout의 하위객체인 Button을 호출하여 이벤트 연결				
				v.findViewById(R.id.btnYellow).setOnClickListener(myListener);
				break;
			case 1:
				v = myLayoutInflater.inflate(R.layout.page_pink,  null);
				v.findViewById(R.id.btnPink).setOnClickListener(myListener);
				break;
			case 2:
				v= myLayoutInflater.inflate(R.layout.page_red,  null);
				v.findViewById(R.id.btnRed).setOnClickListener(myListener);
				break;			
			}
			
			//생성된 View객체 등록
			((ViewPager)pager).addView(v, 0);	//XML에서 생성한 view, index
			
			return v;
		}
		
		//View 객체 삭제
		@Override
		public void destroyItem(View pager, int position, Object object){
			
			((ViewPager)pager).removeView((View)object);
			
		}
		
		//현재 PagerAdapter에서 관리할 페이지 갯수를 반환
		@Override
		public int getCount() {			
			return btn.length;
		}

		//instantiateItem메서드에서 생성된 객체를 이용할 것인지를 체크
		//true	:화면 출력, false	: 화면 미출력
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}		
	}	
}
