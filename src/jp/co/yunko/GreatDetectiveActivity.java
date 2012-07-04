package jp.co.yunko;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class GreatDetectiveActivity extends Activity {
	LocationGeneral locationGeneral;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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