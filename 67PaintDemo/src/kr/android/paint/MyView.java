package kr.android.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View{
	//Activity에서 커스텀 뷰를 사용하기 위해
	//Context객체 하나를 파라미터로 가지는 생성자 정의
	public MyView(Context context){
		super(context);
	}
	
	//XML에 element로 지정시 반드시 아래 생성자 정의
	public MyView(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
	}
	
	//그림 그리기
	//onDraw()메소드는 뷰가 화면에 디스플레이될 때 자동으로 호출됩니다.
	public void onDraw(Canvas canvas){
		//백그라운드 색깔 지정
		canvas.drawColor(Color.BLACK);
		//그리기 옵션 지정
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		//drawLine
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(10);
					//시작x, 시작y, 끝x, 끝y, Paint객체
		canvas.drawLine(50, 0, 50, 100, paint);
		
		
		paint.setColor(Color.RED);
		paint.setStrokeWidth(5);
		for(int y=30, alpha=255; alpha>2; alpha-=50, y+=10){
			//알파값 설정
			paint.setAlpha(alpha);
			//그리기
			canvas.drawLine(0, y, 100, y, paint);			
		}
		
		
		
		//drawRect
		paint.setColor(Color.WHITE);
		//테두리 선만 그리기
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
				//왼쪽, 상단, 오른쪽, 하단, Paint
		canvas.drawRect(120, 10, 120+80, 10+80, paint);
		
		
		paint.setColor(Color.MAGENTA);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(220, 10, 220+80, 10+80, paint);
	
		
		
		//drawArc
		paint.setColor(Color.MAGENTA);
					//
		canvas.drawArc(new RectF(150,120,150+100, 120+100), 	//넓이 지정
				0, 			//시작각도
				50, 		//끝각도
				true, 		//중심사용여부
				paint);
		
		
		//drawOval
		paint.setColor(Color.YELLOW);
		canvas.drawOval(new RectF(20, 250, 20+100, 250+50), paint);
		
		
		//drawRoundRect
		paint.setColor(Color.GREEN);
		canvas.drawRoundRect(new RectF(150, 250, 150+100, 250+50), 
				10, 10,	//모서리가 둥근 정도 
				paint);
		
		
		//drawPath
		paint.setColor(Color.BLUE);
		//테두리만 그리기
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);		
		//Path지정
		Path path = new Path();
		path.moveTo(0, 0);
		path.lineTo(30, 60);
		path.lineTo(-30, 60);
		//그릴 좌표설정이 완료되면 close( )
		path.close();
		//실제 도형이 만들어 지는 위치
		path.offset(150, 340);
		canvas.drawPath(path, paint);
		
	}

}
