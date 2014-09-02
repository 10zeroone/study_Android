package kr.android.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class SMSReciever extends BroadcastReceiver{

	//알림창이 만들어지면 알림창을 제어할수 있는 ID정의
	private static final int NOTIFY_ID =1234;
	
	//브로드캐스트 메시지 수신시 자동 호출되는 onReceive() 재정의
	@Override
	public void onReceive(Context context, Intent intent) {
		//context	: 어플케이션의 정보를 가짐 
		//intent	: 메시지의 정보를 가짐
		
		Bundle bundle = intent.getExtras();
		
		//import android.telephony.SmsMessage;
		SmsMessage[] smsMessages = null;
		String address ="";
		String content ="";
		
		if(bundle!=null){
			//PDU: SMS메세지의 산업포멧
			Object[] pdus =(Object[])bundle.get("pdus");
			
			//가져온 Object(pdu)만큼 SmsMessage객체 생성
			smsMessages = new SmsMessage[pdus.length];
			
			for(int i=0;i< smsMessages.length;i++){
				//Object 배열(pdu)에 담겨있는 메시지를 byte[]로 캐스팅하여 SmsMesage객체에 담기
				smsMessages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				//전화번호 추출
				address += smsMessages[i].getOriginatingAddress();
				//메시지 추출
				content += smsMessages[i].getMessageBody();
				content += "\n";				
			}
			Toast.makeText(context, address + ":"+content, Toast.LENGTH_SHORT).show();
			//Notification Status bar 에 등록
			addNotificationStatusBar(context, content, content);
		}		
	}	
	
	
	//Notification Status Bar에 표시될 Notification 생성
	//Context: 어플리케이션의 정보를 가짐
	private void addNotificationStatusBar(Context context, String address, String message){
		//1. NotificationManager 얻기
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		//2. Notification.Builder 객체 생성
		Notification.Builder builder = new Notification.Builder(context);
		
		//3.알림 메시지 클릭시 이동할 Activity지정
		Intent intent = new Intent(context, NotificationMessage.class);
		
		//4.Activity 영역에 있는 것이 아니므로  Intent로 정보 전송할 수 없기 대문에 PendingIntent로 전달해야 됨
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		//context		: 브로드 캐스트 실행할 PendingIntent의 Context
		//requestCode	: sender에게 요청 코드(현재 사용되지 않음)
		//intent		: 브로드캐스트 할 Intent
		//flags			: 
		/* 사용가능한 flags:
		 * FLAG_CANCEL_CURRENT	: 이전에 생성한 PendingIntent는 취소하고, 새롭게 하나를 만듬
		 * FLAG_NO_CREATE		: 현재 생성된 PendingIntent를 반환
		 * FLAG_ONE_SHOT		: 이 플래그를 이용해 생성된 PendingIntent는 단 한번 밖에 사용될 수 없음
		 * FLAG_UPDATE_CURRENT	: 만일 이미 생성된 PendingIntent가 존재한다면 해당 intent의 내용을 변경	
		*/
		//5.builder객체 설정
		//알림창 최상단의 아이콘 옆에 보여지는 알림메시지 제일 상단에 표시		
		builder.setTicker(address + " : " + message);
		//알림메시지를 클릭해서 Activity를 호출하면 자동으로 알림메시지 제거(true:제거, false:미제거)
		builder.setAutoCancel(true);
		//알림메시지 아이콘 설정
		builder.setSmallIcon(R.drawable.ic_launcher);
		//알림메시지 컨텐트 항목의 주소 표시
		builder.setContentTitle(address);
		//알림메시지 컨텐트의 문자 내용
		builder.setContentText(message);
		//이동할 Activity정보를 갖고 잇는 PendingIntent등록 
		builder.setContentIntent(pendingIntent);
		
		//6.생성된 알림메시지에  ID를 부여해서 NotificationManager에 등록
													//minSDK version 16으로 지정
		notificationManager.notify(NOTIFY_ID, builder.build());
	}
}
