package kr.android.list.custom;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//객체 선언
	ArrayList<MyItem> myItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//데이터 생성 및 저장
		myItems = new ArrayList<MyItem>();
		myItems.add(new MyItem(R.drawable.ic_launcher, "Sony VAIO"));
		myItems.add(new MyItem(R.drawable.ic_launcher, "IBM ThinkPad"));
		myItems.add(new MyItem(R.drawable.ic_launcher, "Apple MacBookpro"));
		
		//어댑터 객체 생성
		MyListAdapter myListAdapter = new MyListAdapter(this, R.layout.widget_icontext, myItems);
		
		//ListView와 매칭
		ListView lvList = (ListView)findViewById(R.id.lvList);
		lvList.setAdapter(myListAdapter);
	}
	
	//커스텀 어탭터 클래스
	class MyListAdapter extends BaseAdapter{
		
		//컨텍트 정보를 처리하기 위해 컨텍트 정보 명시
		Context context;
		ArrayList<MyItem> myItems;
		int layout;
		LayoutInflater inflater;
		
		public MyListAdapter(Context context, int layout, ArrayList<MyItem> myItems){
			this.context = context;
			this.layout = layout;
			this.myItems = myItems;
			
			//ListView에서 사용할 View를 정의한 XML을 읽어오기 위해
			inflater = LayoutInflater.from(context);
		}		
		
		//전체 데이터의 개수 반환
		@Override
		public int getCount() {
			return myItems.size();
		}
		
		//전달된 포지션값에 해당하는 데이터 반환 
		@Override
		public Object getItem(int position) {
			return myItems.get(position).name;
		}

		//포시션값 반환
		@Override
		public long getItemId(int position) {
			return position;
		}

		//실제 데이터 반환 (아이템으로 보여질 뷰 객체 리턴)
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			
			//convertView객체가 null인지 유무 체크
			if(convertView==null){
				//null값이면 뷰 생성
				convertView = inflater.inflate(layout, null);
			}//null이 아니면 convertView에 데이터를 설정해서 리턴
			
			//리소스 참조
			ImageView ivIcon = (ImageView)convertView.findViewById(R.id.ivIcon);
			ivIcon.setImageResource(myItems.get(position).icon);
			TextView tvName =(TextView)convertView.findViewById(R.id.tvName);
			tvName.setText(myItems.get(position).name);
			Button btnSelect = (Button)convertView.findViewById(R.id.btnSelect);
			//이벤트 처리
			btnSelect.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//상품정보 읽어오기
					String str = myItems.get(pos).name + "를 주문합니다";
					
					Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
				}
			});
			
			return convertView;
		}		
	}

	//리스트뷰에 출력할 항목 정의
	class MyItem{
		int icon;
		String name;
		public MyItem(int icon, String name){
			this.icon = icon;
			this.name = name;
		}
	}
}
