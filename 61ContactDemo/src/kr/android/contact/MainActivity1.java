
package kr.android.contact;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity1 extends Activity implements OnClickListener{

	Button btnPick, btnView;
	Intent intent;

	static final int PICK_CONTACT_REQUEST =1;
	
	String id = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnPick = (Button)findViewById(R.id.btnPick);
		btnView = (Button)findViewById(R.id.btnView);		
		btnPick.setOnClickListener(this);
		btnView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnPick){			
			
			//호출방식 설정하여 인텐트 생성
			//people정보 다 가져오는 것
			intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
			
			startActivityForResult(intent, PICK_CONTACT_REQUEST);
			
		}else if(v.getId()==R.id.btnView){
			//primary key에 해당되는 번호의 people 정보를 가져옴
			//content	 : 스키마
			Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
			
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));

			startActivity(intent);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//startActivityForResult에서 넘긴 requestCode확인
		if(requestCode == PICK_CONTACT_REQUEST){
			if( resultCode== RESULT_OK){
				// 선택한 결과는 Uri 리턴되며 해당 Uri 를 쿼리하여 정보를 얻어오게 됩니다.
				Uri contactUri = data.getData();
				
				id = contactUri.toString();
				
				Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
			}
		}
	}
}

