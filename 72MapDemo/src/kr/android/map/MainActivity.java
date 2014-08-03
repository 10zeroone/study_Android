package kr.android.map;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class MainActivity extends Activity implements OnMarkerClickListener, OnInfoWindowClickListener{

	GoogleMap map;
	static final LatLng ADDRESS_GANGNAMSTATION = new LatLng(37.498155, 127.027642);
	static final LatLng ADDRESS_STUDY = new LatLng(37.538683, 127.135346);
	static final LatLng ADDR_GANGNAMSTYLE = new LatLng(37.498751, 127.027675);

	static final LatLng ADDR_GANGNAMSTYLE2 = new LatLng(37.498691, 127.026580);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(map==null){
			//layout에 정의한 map객체를 참조
								//minSdkVersion="11"
			map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			if(map!=null){
				//기본 마커표시
				addBasicMarker();
				
				//화면에 지정한 위도 경도 표시
				CameraPosition cameraPosition = new CameraPosition.Builder().target(ADDRESS_GANGNAMSTATION).zoom(17).build();
				//맵에 원하는 위치 표시
				map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				
				//맵에 이벤트 연결
				map.setOnMarkerClickListener(this);
				map.setOnInfoWindowClickListener(this);
				
				
				//나의 현재 위치 읽어오기
				map.setMyLocationEnabled(true);
								
				//나의 현재 위치로 이동할 수 있는 아이콘 표시
				UiSettings uiSettings = map.getUiSettings();
				uiSettings.setMyLocationButtonEnabled(true);				
			}
		}		
	}
	
	//마커 표시
	private void addBasicMarker(){
		map.addMarker(new MarkerOptions().position(ADDRESS_GANGNAMSTATION).title("강남역").snippet("나는 강남스타일~").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		//map.addMarker(new MarkerOptions().position(ADDR_GANGNAMSTYLE).title("강남스테이지").snippet("여기서 사진 찍어요~").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		//마커 모양 변경
		map.addMarker(new MarkerOptions().position(ADDR_GANGNAMSTYLE).title("강남스테이지").snippet("여기서 사진 찍어요~").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
		//map.addMarker(new MarkerOptions().position(ADDR_GANGNAMSTYLE2).title("강남스테이지").snippet("여기서 사진 찍어요~").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_flag)));
		
		map.addMarker(new MarkerOptions().position(ADDRESS_STUDY).title("미래인재").snippet("열공 중~").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_flag)));
		
	}

	//마커 클릭시 이벤트 핸들러
	@Override
	public boolean onMarkerClick(Marker m) {
			Toast.makeText(this, m.getTitle()+",마커 클릭", Toast.LENGTH_SHORT).show();
			
			//이벤트를 여러개 연결해서 사용시 다른 이벤트 방해하지 않으려면 false로 지정
			//true명시하면 마커 클릭시 이벤트만 처리됨
		return false;
	}

	//말풍선 클릭시 이벤트 핸들러
	@Override
	public void onInfoWindowClick(Marker m) {
			Toast.makeText(this, m.getTitle()+",말풍선 클릭", Toast.LENGTH_SHORT).show();
	}
}
