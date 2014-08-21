/*
MainActivity.java���� nullüũ
���� 100�̻�  �ʰ����� üũ
 
* file info:
* MainActivity1.java
* SecondActivity.java
* 
*  ȭ���̵� �� ���޹��� ������ �����ϱ�
*  activity_main.xml
*  	- TextView, EditText ��Ʈ�� 4��Ʈ �߰�, Button(����)�߰�
*  second_main.xml
*  	- LinearLayout(Vertical) �߰�, TextView �߰� 
*  AndroidManifest.xml
*  	- <activity android:name="SecondActivity"></activity>
*  MainActivity.java
*  	- ID�� �ش�Ǵ� ��ü�� ������ ��ȯ, �̺�Ʈ �ҽ��� ������ ����, �̺�Ʈ �ڵ鷯
*  SecondActivity.java
*  	- ������ ��������,  �հ�/��� ���ϱ�
*/
package kr.android.move2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity1 extends Activity {
	
	EditText etName, etKorean, etMath, etEnglish;
	Button btnSave;
	
	String etN,  etK, etM, etE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSave = (Button)findViewById(R.id.btnSave);
			
		etName = (EditText) findViewById(R.id.etName);
		etKorean = (EditText) findViewById(R.id.etKorean);
		etMath = (EditText) findViewById(R.id.etMath);
		etEnglish = (EditText) findViewById(R.id.etEnglish);
		
		btnSave.setOnClickListener(new OnClickListener() {				
			
			//�̺�Ʈ �ڵ鷯
			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent(MainActivity1.this, SecondActivity.class);	
				// toString()�� ��ȯ�ϴ� String �����ʹ� �ش� ��ü�� ĳ���� ���� ǥ��
				// getClass(). getName() + '@' + Integer.toHexString(hashCode())
				// �� ��Ʈ���� �����Ͽ� �߰������� �����Ѵ�.
				etN = "" + etName.getText().toString().trim();
				etK = "" + etKorean.getText().toString();
				etM = "" + etMath.getText().toString();
				etE = "" + etEnglish.getText().toString();
				
				// ���̰�(str.length==0)�� üũ�Ͽ�, �Է´���(null ��)�� �����Ѵ�.
				if(etN.toString()=="" || etK.toString()=="" || etM.toString()=="" || etE.toString()==""){
					Toast.makeText(MainActivity1.this,
							"�� �׸��� ������ �ȵ˴ϴ�.\n�ٸ��� �Է��� �ּ���.",
							Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				// inputType�� "number"�� ���������Ƿ�.. "100" �ʰ��� üũ�Ѵ�.
				// name ���� ������ ���̰�("0")�� üũ�Ѵ�. �� �ʿ��ϸ� ����ǥ����? 
				if(Integer.parseInt(etK.toString())>100 || Integer.parseInt(etM.toString())>100 || Integer.parseInt(etE.toString())>100){
					Toast.makeText(MainActivity1.this,
							"100������ ���� ���� �����ϴ�.\n�ٸ��� �Է��� �ּ���.",
							Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				intent.putExtra("name", etN);				
				intent.putExtra("korean", etK);
				intent.putExtra("math", etM);
				intent.putExtra("english", etE);
				
				startActivity(intent);
			}
		});
	}
}
