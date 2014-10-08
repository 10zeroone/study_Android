package kr.codedragon.smsdemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	EditText etPhoneNumber;
	EditText etContents;
	Button btnSendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
/*        
        //1. �⺻ API�� ���� ������
        //���ο� Intent ����
        Intent intent = new Intent(Intent.ACTION_VIEW); 
        
        //intentSendSMS�� "sms_body"������ "content" ����
        intent.putExtra("sms_body", "content"); 		   
        
        //intentSendSMS�� Ÿ�� ���� ("vnd.android-dir/mms-sms"�� ���� ���� API)
        intent.setType("vnd.android-dir/mms-sms");	   
        
        // intentSendSMS ȣ��
        startActivity(intent); 
*/        
        
        
        // 2. ���� �����ϱ�
        //UI ������Ʈ �ν��Ͻ� ����
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        etContents = (EditText)findViewById(R.id.etContents);
        btnSendSMS = (Button)findViewById(R.id.btnSendSMS);
        
        //�̺�Ʈ �ڵ鷯
        btnSendSMS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//strPhoneNumber�� etPhoneNumber�� ���ڿ��� ����
				String strPhoneNumber = etPhoneNumber.getText().toString(); 
				//strContents�� etContents�� ���ڿ��� �ܾ�´�.
				String strContents = etContents.getText().toString();    
				//sendSMS() �޼ҵ� ȣ��
				sendSMS(strPhoneNumber, strContents); 
			}
		});
        
    }
    
    //�޼����� ���� �޼ҵ�
    public void sendSMS(String _strPhoneNumber, String _strContents) 
	{
    	// PendingIntentd�� id�� ����
		String strSend = "pendingIntentSend"; 
		
		// pendingIntent ���� �� �ʱ�ȭ
		PendingIntent pendingIntentSend = PendingIntent.getBroadcast(this, 0, new Intent(strSend), 0); 
		
		
		
		registerReceiver(new BroadcastReceiver() // ��ε� ĳ��Ʈ ���
		{	//��ε� �ɽ�Ʈ ���ù����� Ư�� �̺�Ʈ ȣ���� �� ���Ǵ� �Լ�			
			public void onReceive(Context _Context, Intent _Intent) // ���ε� ĳ��Ʈ�� �޼����� �޾��� ��
			{
				switch (getResultCode()) // ��� ���� ��������
				{
					case Activity.RESULT_OK: // ���������� �߽��� ���
					{
						//getBaseContext(): Activity�� Context �����ڳ� Context���� �⺻ ���� �� Context
						Toast.makeText(getBaseContext(), "RESULT_OK", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // ��Ư���� ������ ���
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_GENERIC_FAILURE", Toast.LENGTH_SHORT).show();
						
						break;
					}
					case SmsManager.RESULT_ERROR_NO_SERVICE: // ���� ���� �Ұ����� ��� (���� ���������� �ƴϰų� ���� ������ ���� ��)
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_NO_SERVICE", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					
					case SmsManager.RESULT_ERROR_NULL_PDU: // ��� �������� ������ ���
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_NULL_PDU", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					case SmsManager.RESULT_ERROR_RADIO_OFF: // ������(3G ���) ���� ���� ���(���� ���۱���� �������� ��)
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_RADIO_OFF", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					default : // �� �ܿ� ��Ȳ
						Toast.makeText(getBaseContext(), "DEFAULT", Toast.LENGTH_SHORT).show(); 
				}
				
			}
		}, new IntentFilter(strSend));
		
		//���ڸ� �����ϱ� ���� SmsManager�� �����ϰ� �ʱ�ȭ
		SmsManager smsManager = SmsManager.getDefault(); 
		//smsManager�� �̿��Ͽ� �޼����� ����
		smsManager.sendTextMessage(_strPhoneNumber, null, _strContents, pendingIntentSend, null);
	}
}
