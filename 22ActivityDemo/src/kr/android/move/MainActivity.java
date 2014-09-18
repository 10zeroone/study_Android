/* ȭ�� �̵�

����Ʈ ����:
��Ƽ��Ƽ�� ��Ƽ��Ƽ ȣ��(�Ű�����)
� ����� ������ ���� �����ϱ� ���� ���

AndroidManifest.xml
��Ƽ��Ƽ �߰��� ���⿡ �ݵ�� ���
-android:versionCode="1"	���ۿ��� �������� ��������
-android:versionName="1.0" 	�Ϲ� ����ڿ��� �������� �������� 

*/
package kr.android.move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;


public class MainActivity extends Activity implements View.OnClickListener{
	
	Button btnMove, btnEnter;
	EditText etText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		//ID�� �ش�Ǵ� ��ü�� ������ ��ȯ
		btnMove = (Button)findViewById(R.id.btnMove);
		btnEnter = (Button)findViewById(R.id.btnEnter);		
		etText = (EditText)findViewById(R.id.etText);
		
		//�̺�Ʈ �ҽ��� �̺�Ʈ ������ ����
		btnMove.setOnClickListener(this);
		btnEnter.setOnClickListener(this);
	}

	//�̺�Ʈ �ڵ鷯
	@Override
	public void onClick(View v) {
		// ȭ�� �̵�
		// ����Ʈ ��ü ����
		Intent intent = null;
		if(v.getId()==R.id.btnMove){
								//���� ��Ƽ��Ƽ, �̵��� ��Ƽ��Ƽ
			intent = new Intent(this, SecondActivity.class);
		}else if(v.getId()==R.id.btnEnter){
			intent = new Intent(this, SecondActivity.class);
			//Editable -> String
			//ȭ�� �̵��ÿ� �̵��� ȭ�鿡�� ȣ���� �����͸� ����
			intent.putExtra("msg", etText.getText().toString());
		}
		
		//����Ʈ�� �Ű������� ���� ��Ƽ��Ƽ ����
		startActivity(intent);
	}
}
