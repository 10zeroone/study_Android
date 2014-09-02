package kr.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NewsService extends Service{
	
	Handler handler = new Handler();

	//스레드 종료처리를 위한 변수
	boolean bQuit;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	//서비스 실행
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		//스레드 생성
		NewsThread newsThread = new NewsThread();
		newsThread.start();
		
		return super.onStartCommand(intent, flags, startId);
	}

	//서비스 중지
	@Override
	public void onDestroy() {
		// 부모의 onDestroy초기화
		super.onDestroy();
		
		Toast.makeText(this, "서비스 종료", Toast.LENGTH_SHORT).show();
		
		//스레드 종료
		bQuit=true;
	}
	
	//스레드 정의: 원격지에서 데이터를 전송받아서 표시하는 역할	
	class NewsThread extends Thread{
		String[] enuNews = {
				"사과는 빨게 빨간것은 빨간것은 원숭이 엉덩이",
				"내일 지구가 멸망해도 사과나무 심기 캠페인",
				"사과, 변과와 혁신의 아이콘 ?",
				"미래에 대한 한걸음",
				"고질라 뉴욕, 홍콩, 일본을 파괴",
				"고질라 변종 출현",
				"고질라 어벤저스 삼키다!"
		};

		@Override
		public void run() {

			for(int idx=0; bQuit==false; idx++){
				
				final String msg = enuNews[idx%enuNews.length];
				
				//UI에 데이터 설정
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(NewsService.this, msg, Toast.LENGTH_LONG).show();
					}
				});
				
				//7초 단위로 데이터 읽어오기
				try{
					Thread.sleep(3000);
				}catch(Exception e){
					
				}
			}
			//super.run();
		}
	}
}
