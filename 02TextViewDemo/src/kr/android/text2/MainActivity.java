/*  layout�� ��ü ����

* �ڵ�� UI�� ö���� �и��Ǿ� ����
-java �ڵ�
-xml UI

* R.class
-��ü�� �ĺ��ϰ� ������ �� �ֵ��� ���ִ� Ŭ����
-acitivity_main.xml ���� ID�ο��ϰ� android:id="@+id/textView1" �� �ĺ�
											@: R.class�ǹ�
-�ȵ���̵�� ID�� �����ϴ� ��ü�� ���� �����ϸ�
-R.java(gen\kr.android.text2.R.java)Ŭ�������� ID ����

*findViewById()
-R.java�� ��ϵ� UI������Ʈ ID���� �����Ͽ� TextView������ �ν��Ͻ��� �ش� TextView�� �������� ���Խ��� �ִ� �Լ�

 * 
 * 
*/
package kr.android.text2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //��ü ����
        setContentView(R.layout.activity_main);
        
        
        
        //View -> TextView
        //TextView			: UI������Ʈ ����
        //text				: �ν��Ͻ� ��
        //=					: ���� ������
        //(TextView)		: ������ �ν��Ͻ� UI������Ʈ �������� ����ȯ
        //findViewById()	: ������ �����  ID�� ���� TextView��ü ȣ��
        //					    ����� ��Ī�� TextView��ü ȣ��
        TextView text = (TextView)findViewById(R.id.textView1);
        //���� ����
        text.setBackgroundColor(Color.BLUE);
        //�۲û� ����
        text.setTextColor(Color.CYAN);        
    }
}
