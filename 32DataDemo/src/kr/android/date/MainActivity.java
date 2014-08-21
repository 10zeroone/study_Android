package kr.android.date;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity implements OnClickListener{
	
	//Calendar객체생성
	Calendar dateAndTime = Calendar.getInstance();
	
	//기본 포멧 생성
	DateFormat dfDateNadTime = DateFormat.getDateTimeInstance();
	TextView tvDateAndTime;
	Button btnDateSelect, btnTimeSelect;	
	
	//DatePickerDiaglog 사용시 발생하는 이벤트를 처리하는 객체 생성
	DatePickerDialog.OnDateSetListener datepickerListener = new DatePickerDialog.OnDateSetListener() {

		//이벤트 핸들러
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// DatePickerDialog에서 변경한 년,월,일을 Calendar객체에 설정
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			
			//설정된 정보가 반영되도록 메소드 호출
			updateDateAndTime();
		}
	};
	
	//TimePickerDialog 사용시 발생하는 이벤트를 처리하는 객체 생성
	TimePickerDialog.OnTimeSetListener timepickerListener = new TimePickerDialog.OnTimeSetListener() {
		
		//이벤트 핸들러
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TimePickerDialog에서 변경한 시,분을 Calendar객체에 설정
			dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTime.set(Calendar.MINUTE, minute);
			
			//설정된 정보가 반영되도록 메소드 호출
			updateDateAndTime();			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvDateAndTime = (TextView)findViewById(R.id.tvText);
		btnDateSelect = (Button)findViewById(R.id.btnDateSelect);
		btnTimeSelect = (Button)findViewById(R.id.btnTimeSelect);		
		
		//이벤트소스와 이벤트 리스너 연결
		btnDateSelect.setOnClickListener(this);
		btnTimeSelect.setOnClickListener(this);
		
		//초기데이터 설정
		updateDateAndTime();
	}
	
	
	//날짜와 시간 표시 메소드
	private void updateDateAndTime(){
		//포멧 생성해서, TextView에 표시
		tvDateAndTime.setText(dfDateNadTime.format(dateAndTime.getTime()));
	}
	
	
	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnDateSelect){	//날짜 설정
			new DatePickerDialog(this, datepickerListener, dateAndTime.get(Calendar.YEAR), 
					dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();		
			
		}else if(v.getId()==R.id.btnTimeSelect){	//시간 설정
			new TimePickerDialog(this, timepickerListener, dateAndTime.get(Calendar.HOUR_OF_DAY), 
					dateAndTime.get(Calendar.MINUTE), true).show();
		}
	}
}
