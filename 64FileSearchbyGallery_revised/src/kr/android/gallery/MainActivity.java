package kr.android.gallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
     
public class MainActivity extends ActionBarActivity 
                          implements OnClickListener{

	ImageView photo;
	Button btn;
	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		photo = (ImageView)findViewById(R.id.image);
		text = (TextView)findViewById(R.id.text);
		btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		//갤러리를 호출해서 휴대폰에 저장된 이미지를 읽어들임
		
		//킷캣이전에 명시하는 방법
		//Intent intent = new Intent();
		//intent.setAction(Intent.ACTION_GET_CONTENT);
		
		//킷캣 호환성을 위한 명시 방법
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		//image/jpeg,image/png,image/gif ...
		intent.setType("image/*");
		startActivityForResult(intent, 0);
	}
	
	//Activity 호출해서 만들어진 데이터를 전달받는 메서드
	@Override
	protected void onActivityResult(int requestCode,
			              int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, 
				data);
		if(data!=null){
			FileOutputStream fileOut = null;
			try{
				Uri uri = data.getData();
				
				//ImageView에 이미지 보여주기
				//킷캣 호환성을 위한 권한 명시 : 
				//READ_EXTERNAL_STORAGE 추가
				Bitmap image = 
				Images.Media.getBitmap(getContentResolver(), uri);
				
				photo.setImageBitmap(image);
				photo.setVisibility(View.VISIBLE);
				
				//이미지 정보 추출 및 표시
				text.setText("===이미지정보===\n");
				text.append("URI:"+uri.toString()+"\n");
				text.append("Last Path Segment:"+
				                uri.getLastPathSegment()+"\n");
				  
				Cursor c = getContentResolver().query(
						   Images.Media.EXTERNAL_CONTENT_URI, 
						   null, 
						   Images.Media._ID+"=?", 
			           new String[]{uri.getLastPathSegment()}, 
			                                              null);
				if(c.moveToFirst()){
					String imagePath = c.getString(
					c.getColumnIndexOrThrow(Images.Media.DATA));
				
					text.append("원본이미지경로:"+imagePath+"\n");
					
					File f = new File(imagePath);
					text.append("이미지용량:"+f.length()+"\n");
				}
				
				text.append("크기:"+image.getWidth()+"*"+
				                    image.getHeight());
				
				text.setVisibility(View.VISIBLE);
				
				//SDCard에 저장된 파일을 읽어들여
				//어플리케이션 내부 공간에 저장
				fileOut = openFileOutput("test.jpg",MODE_PRIVATE);
				              //포멧                퀄리티  스트림
				image.compress(CompressFormat.JPEG, 100, fileOut);
				         
			}catch(Exception e){
				Log.e("FileSearchbyGallery","oi error",e);
			}finally{
				if(fileOut!=null)try{fileOut.close();}catch(IOException e){}
			}
		}
	}
}
