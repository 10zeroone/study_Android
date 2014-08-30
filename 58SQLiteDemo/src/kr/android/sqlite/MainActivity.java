package kr.android.sqlite;


import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
												//import android.view.View.OnClickListener;
public class MainActivity extends ListActivity implements OnClickListener {
	
	//데이터베이스 접근을 위한 객체 선언
	DatabaseAdapter databaseAdapter;
	static final String TAG = "SQLiteDemo";
	TextView tv_mCurrentID;
	EditText etEditMemo;
	//import android.widget.SimpleCursorAdapter;
	SimpleCursorAdapter simpleCursorAdapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv_mCurrentID= (TextView)findViewById(R.id.tvId);
		etEditMemo =(EditText)findViewById(R.id.etEditMemo);

		//버튼 등록
		int buttons[] = {R.id.btnAdd, R.id.btnDelete, R.id.btnModify};
		
		//Button 객체 호출 및 이벤트 연결
		for(int id:buttons){
			Button button = (Button)findViewById(id);
			button.setOnClickListener(this);
		}
		
		
		
		//삭제, 수정버튼 비활성화
		setEnabled(false);
	}

	
	
	//데이터 읽어오기
	@Override
	protected void onResume(){
		super.onResume();
		
		//DatabaseAdapter 호출
		databaseAdapter = new DatabaseAdapter(this);
		//SQLite Database객체 생성
		databaseAdapter.open();
		
		//목록 데이터를 갖고 있는 커서 객체 호출
		cursor = databaseAdapter.fetchAllMemo();
		
		//데이블의 컬럼과 뷰를 연결
		String[] from = DatabaseAdapter.PROJECTION;
		int[] to = new int[]{R.id.tv_ID, R.id.tv_Cotent};
		
		//Table에 있는 데이터를 매칭시킬수 있도록 하기 위해 simpleCursorAdapter객체 생성
		//min SDK version이 11이상이어야 함													// SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER: 데이터변동을 감시할 수 있도록 설정(의무사항)
		//simpleCursorAdapter = new SimpleCursorAdapter(context, layout, c, from, to, flags)
		simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.memo_row, cursor, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		//ListView에 SimpleCursorAdapter등록
		setListAdapter(simpleCursorAdapter);
	}
	
	
	//데이터베이스 사용 완료
	@Override
	protected void onPause(){
		super.onPause();
		//Cursor 종료
		cursor.close();
		//SQLiteDatabase 종료
		databaseAdapter.close();
	}

	//검색된 내용 표시
	private void toastMemo(String str){
		if(str.length() ==0){
			return;
		}
		String memo = databaseAdapter.searchMemo(str);
		Toast.makeText(this, memo, Toast.LENGTH_LONG).show();
	}

	
	//이벤트 처리
	@Override
	public void onClick(View v) {			
		
		// 입력한 ID값을 DB에 INSERT
		String content = etEditMemo.getText().toString();
		String id = tv_mCurrentID.getText().toString();
		
		if(v.getId()==R.id.btnAdd){	//저장
			if(content.length()!=0){
				databaseAdapter.addMemo(content);
				toastMemo(content); 	//서치
			}
		}else if(v.getId()==R.id.btnModify){	//수정
			if(content.length()!=0){
				databaseAdapter.setMemo(id, content);
				toastMemo(content); 	//서치
			}			
		}else if(v.getId()==R.id.btnDelete){	//삭제
			databaseAdapter.deleteMemo(id);
		}
		//EditText 초기화
		etEditMemo.setText("");
		//TextView 초기화
		tv_mCurrentID.setText("ID");
		
		
		
		//삭제, 수정버튼 비활성화
		setEnabled(false);
		
		
		//추가된 데이터를 읽어옴
		cursor = databaseAdapter.fetchAllMemo();
		//SimpleCursorAdapter의 Cursor교체하게 되면 결과적으로 ListView 를 갱신하는 효과
		simpleCursorAdapter.changeCursor(cursor);
	}
	
	
	//ListView 이벤트 핸들러
	@Override
	protected void onListItemClick(ListView parent, View view, int postion, long id){		
		
		
		//TAP된 행의 ID와 Content정보를 갖고 있는 레이아웃 호출
		LinearLayout layout   =(LinearLayout)view;
		TextView tv_Content = (TextView)layout.findViewById(R.id.tv_Cotent);
		etEditMemo.setText(tv_Content.getText());
		tv_mCurrentID.setText(Long.toString(id));
		
		
		//삭제, 수정버튼 활성화
		setEnabled(true);
		
		toastMemo(tv_Content.getText().toString()); 	//서치
		
	}
	
	
	
	
	
	//삭제, 수정버튼 상태 변경
	private void setEnabled(boolean enabled){
		int buttons[] = {R.id.btnDelete, R.id.btnModify};
		
		for(int id: buttons){
			Button button = (Button)findViewById(id);
			button.setEnabled(enabled);
		}
	}
	
}