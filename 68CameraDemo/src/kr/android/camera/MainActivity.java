package kr.android.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	MyCameraSurface myCameraSurface;
	Button btnShutter;
	ImageView ivRawImage;
	File sd_path = Environment.getExternalStorageDirectory();
	
	//파일 포멧 설정
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myCameraSurface = (MyCameraSurface)findViewById(R.id.svPreview);		
		ivRawImage = (ImageView)findViewById(R.id.ivRawImage);
		
		btnShutter = (Button)findViewById(R.id.btnShutter);
		btnShutter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 사진 촬영은 Camera객체의 takePickture메소드 호출을 통해 이뤄짐
				// takePicture는 ShutterCallback 구현 하나와 PictureCallback구현 두개
				// (Raw 이미지용 하나와 JPEG으로 인코딩 이미지 용 하나)를 매개변수로 받음
				myCameraSurface.mCamera.takePicture(shutterCallback, rawCallback, picture);				
			}
		});
	}
	
	ShutterCallback shutterCallback = new ShutterCallback() {
		
		@Override
		public void onShutter() {
			// Shutter가 닫힐 때 필요한 작업을 수행
			// 디폴트로 기본 Shutter 음 재생됨			
		}
	};
	
	PictureCallback rawCallback = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// Raw이미지 데이터를 가지고 필요한 작업을 수행
			// 거의 사용 안하고 보통 null처리함			
		}
	};
	
	
	//사진 저장
	PictureCallback picture = new PictureCallback() {
		
								//byte[] data	: 영상데이터
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			if(data!=null){
				//촬영한 이미지 표시
				BitmapFactory.Options opts = new BitmapFactory.Options();
				
				//원본은 건드리지 않고, 이미지 크기만 '1/입력숫자'로 줄여서 로딩함
				//비트맵이 메모리를 너무 많이 차지하는 문제를 해결하기 위한 옵션
				//1/16로 줄임으로써 해상도를 낮춤
				opts.inSampleSize = 16;
				
				//이미지, 이미지의 offset, 길이, 디코딩시 옵션
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
				ivRawImage.setImageBitmap(bitmap);
				
				//촬영된 영상을 파일에 저장
				//파일명 지정
															//import java.util.Date;
				String filename = simpleDateFormat.format(new Date());
				String path = sd_path.getAbsolutePath()+"/"+filename+".jpg";
				FileOutputStream fos = null;
				try{
					fos = new FileOutputStream(path);
					fos.write(data);
				}catch(Exception e){
					Toast.makeText(MainActivity.this, "파일 저장 중 에러 발생", Toast.LENGTH_SHORT).show();
					Log.e("CameraDemo", "File Error", e);
					
					//(중요) 오류 발생시 메소드 종료하기 위해 return;
					return;
				}finally{
					
					if( fos!=null){ try{fos.close();}catch(IOException e){e.printStackTrace();} }
					
				}
				
				//미디어 파일을 추가한 후
				//MediaScanner를 이용하여 해당 파일을 미디어 DB에 추가
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				
				Uri uri = Uri.parse("file://"+path);
				intent.setData(uri);
				
				//브로드캐스트 인텐트를 이용해 해당 미디어 파일이 삽입되어 사용 가능함을 알려야 함(미디어 DB에 등록)
				sendBroadcast(intent);
				
				Toast.makeText(MainActivity.this, "사진 저장 완료", Toast.LENGTH_SHORT).show();
				myCameraSurface.mCamera.startPreview();
				
			}
		}
	};	

	
	//메뉴 등록
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuItem menuItem = menu.add(0, 1, 0, "갤러리 보기");
			
		//메뉴 이벤트 처리
		menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				
				Intent intent = new Intent(Intent.ACTION_VIEW, Media.EXTERNAL_CONTENT_URI);
				startActivity(intent);
				
				return true;
			}
		});
		return true;
	}
}	//End of MainActivity


/*
 * View에 동영상 또는 카메라 프리뷰와 같이 빠른 화면 변화 또는 그려지는 양이 많을 경우 SurfaceView를 사용해 처리
 * 
 * Surface는 그래픽버퍼 (데이터 제공)
 * SurfaceView에서 보여지는 화면 제어를 하기 위해서 SurfaceHolder생성
 * 
 * 
 * SurfaceView			: 화면 제공
 * -------------------
 * Surface				: 데이터 제공
 * -------------------
 * SurfaceHolder		: 화면 제어
 * -> SurfaceHolder를 이용해서 Surface(버퍼)에 그림을 그리면 SurfaceView에 반영
 * 
 * 
 * 이벤트 처리:
 * Callback Interface는 
 * SurfaceHolder를 통해서 작성한 Surface와 SurfaceView를 연결하기 위해서 
 * Surface의 생성, 변경, 종료에 대한 이벤트 처리 
 * 
*/
//고속영상을 제공하는 뷰 정의 (고속영상 처리 위해 SurfaceView상속한 클래스 정의)
class MyCameraSurface extends SurfaceView implements SurfaceHolder.Callback{
	
	//객체 선언
	//버퍼에 있는 데이터를 SurfaceView에 전달하는 객체정의
	SurfaceHolder mholder;
	//import android.hardware.Camera;
	Camera mCamera;

	//생성자를 통해 XML에 등록
	public MyCameraSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//SurfaceHolder 객체 생성 및 SurfaceHolder초기화
		mholder = getHolder();
		//SurfaceHolder와 Callback연결
		mholder.addCallback(this);
		
	}
	
	//표면 생성시 카메라 오픈하고 미리보기 설정
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//카메라 초기화
		mCamera = Camera.open();

		//SurfaceView가 만들어질 때 카메라 객체를 참조한 후 미리보기 화면으로 홀더 객체 생성
		try{
			//카메라 객체 홀더로 셋팅
			mCamera.setPreviewDisplay(mholder);

		}catch(IOException e){
			mCamera.release();
			mCamera = null;			
		}
	}

	//SurfaceView의 화면 크기가 바뀌는 등의 변경 시점에 미리보기 시작
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//카메라로 미리보기 구현하는 이벤트 처리
		mCamera.startPreview();		
	}	

	//Surface파괴시 카메라도 자원정리
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//카메라로 보기 구현하는 이벤트 정지 처리
		if(mCamera!=null){ 
			//View정지
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;			
		} 
	}	
}

