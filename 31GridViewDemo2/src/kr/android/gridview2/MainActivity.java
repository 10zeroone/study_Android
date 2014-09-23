package kr.android.gridview2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	//이미지 정보를 받는 배열
	Integer[] images = {R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany,
			R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg,
			R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia,
			R.drawable.slovenia, R.drawable.spain, R.drawable.sweden, R.drawable.united_kingdom, R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany,
			R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg,
			R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia,
			R.drawable.slovenia, R.drawable.spain, R.drawable.sweden, R.drawable.united_kingdom, R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany,
			R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg,
			R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia,
			R.drawable.slovenia, R.drawable.spain, R.drawable.sweden, R.drawable.united_kingdom};

	GridView myGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		myGridView = (GridView)findViewById(R.id.gvView);
		
		//GridView에 이미지를 보여주기 위해 ImageAdapter객체를 등록
		myGridView.setAdapter(new ImageAdapter(this));		

		
	}
	
	
	//추상 클래스 BaseAdapter를 상속하여 GridView에 이미지를 보여줄 수 있는 기능을 하는 내부 클래스 구현
	public class ImageAdapter extends BaseAdapter{

		//변수 선언
		//Activity의 부모가 Context
		private Context context;
		
		//뷰의 생성자에는 항상 Context객체가 전달되어져야 합니다.
		public ImageAdapter(Context context){
			this.context = context;
		}		
		
		//전체 데이터의 갯수
		@Override
		public int getCount() {						
			return images.length;
		}

		//전달된 포지션값에 해당하는 데이터 반환 
		@Override
		public Object getItem(int position) {
			return images[position];
		}
		
		@Override
		public long getItemId(int position) {
			//포시션값 반환
			return position;
		}

		//실제 데이터 반환 (아이템으로 보여질 뷰 객체 리턴)
		//int position: 위치값
		//View convertView: 실제 이미지를 넣을 수 있는 뷰(표시 될 뷰)
		//ViewGroup parent: 부모그룹, GridView객체 의미		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//재사용할 수 있는 ImageView가 있다면 ImageView를 재활용하고 
			//생성된 ImageView가 없으면 객체 생성하여 작업 수행  
			ImageView myImageView = (ImageView)convertView;			
			if(convertView == null){	//생성된 ImageView가 없으면, 최초인 경우
				myImageView = new ImageView(context);
			}
			
			//ImageView의 크기와 이미지 크기 그리고 공백 설정
			myImageView.setLayoutParams(new GridView.LayoutParams(80,80));			
			myImageView.setPadding(10,  10, 10, 10);
			//이미지를 인자로 넘어온 position에 맞게 설정
			myImageView.setImageResource(images[position]);			
			
			return myImageView;
		}		
	}
}
