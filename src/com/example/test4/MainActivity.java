package com.example.test4;

import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends Activity {

	private BaiduMap baiduMap;
	private LocationManager locationManager;
	private String provider;
	private MapView mapview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mapview=(MapView)findViewById(R.id.map_view);
		baiduMap=mapview.getMap();
		baiduMap.setMyLocationEnabled(true);
		locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
		List<String> s=locationManager.getProviders(true);
		if(s.contains(locationManager.GPS_PROVIDER))
			provider=LocationManager.GPS_PROVIDER;
		else
			provider=LocationManager.NETWORK_PROVIDER;
		Location location=locationManager.getLastKnownLocation(provider);
		locationManager.requestLocationUpdates(provider, 1000,(float) 0.3,locationListener);
		to(location);
	}
	protected void onDestory()
	{
		super.onDestroy();
		mapview.onDestroy();
		baiduMap.setMyLocationEnabled(false);
		if(locationManager!=null)
			locationManager.removeUpdates(locationListener);
	}
	LocationListener locationListener=new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			locationchange(location);
		}
	};
	private void to(Location location)
	{
		LatLng ll=new LatLng(location.getLatitude(), location.getLongitude());
		MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(ll);
		baiduMap.animateMapStatus(update);
		update=MapStatusUpdateFactory.zoomTo(16f);
		baiduMap.animateMapStatus(update);
	}
	private void locationchange(Location location)
	{
		MyLocationData.Builder builder=new MyLocationData.Builder();
		builder.latitude(location.getLatitude());
		builder.longitude(location.getLongitude());
		MyLocationData data=builder.build();
		baiduMap.setMyLocationData(data);
	}
	
}
