package kr.android.gallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{	
	
	ImageView ivPhoto;
	TextView tvFileInfo;
	Button btnPhotoSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
		tvFileInfo = (TextView)findViewById(R.id.tvFileInfo);
		btnPhotoSelect = (Button)findViewById(R.id.btnPhotoSelect);
		//버튼을 이벤트 리스너와 연결
		btnPhotoSelect.setOnClickListener(this);
		
	}

	//이벤트 핸들러
	@Override
	public void onClick(View v) {
		// 갤러리를 호출해서 휴대폰에 저장된 이미지를 읽어들임
		
/*
  		킷캣이전에 명시하던 방법
		Intent intent = new Intent();
		//여러 리소스들의 목록들을 ACTION_GET_CONTENT 값 가져올 수 있도록 설정
		intent.setAction(Intent.ACTION_GET_CONTENT);
*/
		//킷캣 호환성을 위해 명시하는 방법
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		
		//image/jpeg, image/png, image/gif등 이미지파일을 타입으로 지정
		intent.setType("image/*");
		startActivityForResult(intent, 0);		
	}
	
	//Activity호출해서 만들어진 데이터를 전달받는 메소드
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data){
			super.onActivityResult(requestCode, resultCode, data);
			
			if(data!=null){
				FileOutputStream fileOutputStream =null;
				try{
					Uri uri = data.getData();
					//이미지 뷰에 이미지 보여주기
														//getContentResolver()가 매개역할을 하여 uri를 통해 데이터를 호출할 수 있도록 해주는 객체
					Bitmap image = Images.Media.getBitmap(getContentResolver(), uri);
					ivPhoto.setImageBitmap(image);
					ivPhoto.setVisibility(View.VISIBLE);
					
					//이미지 정보 추출 및 표시
					tvFileInfo.setText("========이미지 정보===========\n");
					tvFileInfo.append("URI: " + uri.toString()+"\n");
					tvFileInfo.append("Last Path Segement: "+uri.getLastPathSegment()+"\n");
					
					//Cursor 데이터를 쿼리하여 가져올때사용
//					uri			:'content://' scheme를 가지고 가져올 Content Provider를 결정합니다.
//					projection	: 리턴 받아야 하는 데이터 column의 이름목록입니다.
//								  null 지정 시 모든 column을 가져옵니다.
//					selection	:SQLite의 WHERE 구문의 내용을 결정합니다.
//					selectionArgs	:selection의 arguments 들을 나열합니다.
//					sortOrder		:SQLite의 ORDER BY와 같은 정렬방식을 결정합니다.
					Cursor cursor = getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, null, Images.Media._ID+"=?", new String[]{uri.getLastPathSegment()}, null);
					if(cursor.moveToFirst()){
						String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(Images.Media.DATA));
						tvFileInfo.append("원본이미지경로: "+imagePath + "\n");
						File file = new File(imagePath);
						tvFileInfo.append("이미지 용량: " + file.length()+"\n");
					}
					tvFileInfo.append("크기: " + image.getWidth() + "*" + image.getHeight());
					
					tvFileInfo.setVisibility(View.VISIBLE);
					
					//선택된 SD Card에 저장된(\storage\sdcard\) 파일을 읽어들여 내부 어플리케이션 내부 공간(\data\data\kr.android.gallery\files\test.jpg)에 저장하는 것
					fileOutputStream = openFileOutput("test.jpg",  MODE_PRIVATE);
									//포멧, 컬리티, 스트림				
					image.compress(CompressFormat.JPEG, 100, fileOutputStream);
					
				}catch(Exception e){
					Log.e("FileSearchByGallery", "IO Error", e);					
					
				}finally{
					if(fileOutputStream!=null){ try{fileOutputStream.close();}catch(IOException e){e.printStackTrace();} };					
				}
			}
		}
}
