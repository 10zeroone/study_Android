package kr.android.dom2;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

	EditText etResult;
	Button btnParseXML;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etResult = (EditText)findViewById(R.id.etResult);
		btnParseXML = (Button)findViewById(R.id.btnParseXML);
		btnParseXML.setOnClickListener(this);	
	}

	@Override
	public void onClick(View v) {

		InputStream in = null;
		
		try{
			//1)XML을 읽어서 InputStream생성
			in = getResources().openRawResource(R.raw.product);
			
			//2)DOM파서 생성
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//3)DOM트리 생성
			Document doc = builder.parse(in);
			
			//4)<item>태그를 가진 노드 리스트 확인
			NodeList orders = doc.getElementsByTagName("item");
			//StringBuffer생성
			StringBuffer sb = new StringBuffer();
			sb.append("주문항목\n");			 
			
			for(int i=0; i<orders.getLength(); i++){
//				Node n1 = (Node)orders.item(i);						
//				Node n2 = n1.getFirstChild();
//				String s = n2.getNodeValue();				
				
//				Node n1 = (Node)orders.item(i);
//				String s = n1.getFirstChild().getNodeValue();
				
				String s = orders.item(i).getFirstChild().getNodeValue();				
				
				sb.append((i+1)+": " + s +"\n");
				
			}
			
			etResult.setText(sb.toString());			
			
		}catch(Exception e){
			Toast.makeText(this, "예외: " + e.toString(), Toast.LENGTH_LONG).show();
			
		}finally{
			if(in!=null){ try{in.close();}catch(IOException e){e.printStackTrace();} }
		}		
	}	
}
