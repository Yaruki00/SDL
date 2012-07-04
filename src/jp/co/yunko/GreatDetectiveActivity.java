package jp.co.yunko;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

public class GreatDetectiveActivity extends Activity {
	LocationGeneral locationGeneral;
	public TextView availableView;
	public TextView providerView;
	public TextView timeView;
	public TextView latitudeView;
	public TextView longitudeView;
	public TextView altitudeView;
	public TextView accuracyView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        availableView = (TextView) findViewById(R.id.available_view);
		providerView = (TextView) findViewById(R.id.provider_view);
		timeView = (TextView) findViewById(R.id.time_view);
		latitudeView = (TextView) findViewById(R.id.latitude_view);
		longitudeView = (TextView) findViewById(R.id.longitude_view);
		altitudeView = (TextView) findViewById(R.id.altitude_view);
		accuracyView = (TextView) findViewById(R.id.accuracy_view);
        locationGeneral = new LocationGeneral(this);
        locationGeneral.startLocationService();
    }
	// 警告->終了ダイアログ
    public void showFinishDialog(String message) {
    	new AlertDialog.Builder(this)
    	    .setTitle("警告")
    	    .setMessage(message)
    	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	    	@Override
    	    	public void onClick(DialogInterface dialog, int which) {
    	    		finish();
    	    	}
    	    })
    	    .setCancelable(false)
    	    .show();
    }
}