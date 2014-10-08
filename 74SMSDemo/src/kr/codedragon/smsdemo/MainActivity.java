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
        //1. 기본 API로 문자 보내기
        //새로운 Intent 생성
        Intent intent = new Intent(Intent.ACTION_VIEW); 
        
        //intentSendSMS의 "sms_body"영역에 "content" 대입
        intent.putExtra("sms_body", "content"); 		   
        
        //intentSendSMS의 타입 설정 ("vnd.android-dir/mms-sms"는 문자 전송 API)
        intent.setType("vnd.android-dir/mms-sms");	   
        
        // intentSendSMS 호출
        startActivity(intent); 
*/        
        
        
        // 2. 직접 구현하기
        //UI 오브젝트 인스턴스 생성
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        etContents = (EditText)findViewById(R.id.etContents);
        btnSendSMS = (Button)findViewById(R.id.btnSendSMS);
        
        //이벤트 핸들러
        btnSendSMS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//strPhoneNumber에 etPhoneNumber의 문자열을 저장
				String strPhoneNumber = etPhoneNumber.getText().toString(); 
				//strContents에 etContents의 문자열을 긁어온다.
				String strContents = etContents.getText().toString();    
				//sendSMS() 메소드 호출
				sendSMS(strPhoneNumber, strContents); 
			}
		});
        
    }
    
    //메세지를 보낼 메소드
    public void sendSMS(String _strPhoneNumber, String _strContents) 
	{
    	// PendingIntentd의 id값 설정
		String strSend = "pendingIntentSend"; 
		
		// pendingIntent 생성 및 초기화
		PendingIntent pendingIntentSend = PendingIntent.getBroadcast(this, 0, new Intent(strSend), 0); 
		
		
		
		registerReceiver(new BroadcastReceiver() // 브로드 캐스트 등록
		{	//브로드 케스트 리시버에서 특정 이벤트 호출할 때 사용되는 함수			
			public void onReceive(Context _Context, Intent _Intent) // 보로드 캐스트가 메세지를 받았을 때
			{
				switch (getResultCode()) // 결과 값을 기준으로
				{
					case Activity.RESULT_OK: // 정상적으로 발신일 경우
					{
						//getBaseContext(): Activity의 Context 생성자나 Context에서 기본 설정 된 Context
						Toast.makeText(getBaseContext(), "RESULT_OK", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // 불특정한 오류일 경우
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_GENERIC_FAILURE", Toast.LENGTH_SHORT).show();
						
						break;
					}
					case SmsManager.RESULT_ERROR_NO_SERVICE: // 서비스 연결 불가능일 경우 (서비스 가능지역이 아니거나 문자 전송을 못할 때)
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_NO_SERVICE", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					
					case SmsManager.RESULT_ERROR_NULL_PDU: // 통신 프로토콜 오류일 경우
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_NULL_PDU", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					case SmsManager.RESULT_ERROR_RADIO_OFF: // 라디오가(3G 통신) 꺼져 있을 경우(문자 전송기능이 꺼져있을 때)
					{
						Toast.makeText(getBaseContext(), "RESULT_ERROR_RADIO_OFF", Toast.LENGTH_SHORT).show(); 
						
						break;
					}
					default : // 이 외에 상황
						Toast.makeText(getBaseContext(), "DEFAULT", Toast.LENGTH_SHORT).show(); 
				}
				
			}
		}, new IntentFilter(strSend));
		
		//문자를 전성하기 위한 SmsManager를 생성하고 초기화
		SmsManager smsManager = SmsManager.getDefault(); 
		//smsManager를 이용하여 메세지를 전송
		smsManager.sendTextMessage(_strPhoneNumber, null, _strContents, pendingIntentSend, null);
	}
}
