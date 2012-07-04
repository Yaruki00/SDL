package jp.co.yunko;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;

public class LocationGeneral {
	
	GreatDetectiveActivity activity;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private Timer locationTimer;
	long time;
	
	LocationGeneral(GreatDetectiveActivity activity) {
		this.activity = activity;
	}
	// 位置情報を取得を始める
	public void startLocationService() {
		stopLocationService();
		locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
		// 位置情報機能非搭載端末の場合
		if (locationManager == null) {
			// アラートを出して終了
			activity.showFinishDialog("位置情報機能が搭載されていない端末です");
			return;
		}
		// Criteriaに現在使用できるプロバイダーで一番いいのを選んでもらう
		final Criteria criteria = new Criteria();
		final String provider = locationManager.getBestProvider(criteria, true);
		// 位置情報が有効になっていない場合
		if (provider == null) {
			activity.showFinishDialog("位置情報が有効になっていません");
		}
		// 最後に取得できた位置が５分以内に得られたものなら処理を行う
		final Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
		if (lastKnownLocation != null && (new Date().getTime() - lastKnownLocation.getTime()) <= (5 * 60 * 1000L)) {
			setLocation(lastKnownLocation);
			return;
		}
		// LocationListener の生存時間を決定するタイマーを起動します。
		locationTimer = new Timer(true);
		time = 0L;
		final Handler handler = new Handler();
		locationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (time >= (30 * 1000L)) {
							stopLocationService();
						}
						time = time + 1000L;
					}
				});
			}
		}, 0L, 1000L);
		// 位置情報を取得
		locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(final Location location) {
				setLocation(location);
			}
			@Override public void onProviderDisabled(final String provider) {}
			@Override public void onProviderEnabled(final String provider) {}
			@Override public void onStatusChanged(final String provider, final int status, final Bundle extras) {}
		};
		locationManager.requestLocationUpdates(provider, 60000, 0, locationListener);
		
	}
	// 位置情報取得をやめる
	void stopLocationService() {
		if (locationTimer != null) {
			locationTimer.cancel();
			locationTimer.purge();
			locationTimer = null;
		}
		if (locationManager != null) {
			if (locationListener != null) {
				locationManager.removeUpdates(locationListener);
				locationListener = null;
			}
			locationManager = null;
		}
	}
	// 位置情報が取得できた場合の処理
	void setLocation(final Location location) {
		stopLocationService();
		
	}
}
