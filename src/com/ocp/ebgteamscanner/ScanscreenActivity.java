package com.ocp.ebgteamscanner;

import com.ocp.ebgteamscanner.util.SystemUiHider;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class ScanscreenActivity extends Activity implements OnClickListener {
	private Button scanBtn;
	private TextView formatTxt, contentTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Aktivität als Content setzen
		setContentView(R.layout.activity_scanscreen);
		
		// Initialisiere Objekte
		scanBtn = (Button) findViewById(R.id.scan_button);
		formatTxt = (TextView) findViewById(R.id.scan_format);
		contentTxt = (TextView) findViewById(R.id.scan_content);
	}
	
	/***
	 * ClickEvent-Listener für alle Items
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.scan_button) {
			// scan
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		}
	}
	
	/***
	 * Callback für die QR-Code API
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// Parsen des Ergebnisses
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		
		if (scanningResult != null) {
			// Yeah, das Ergebnis ist da!
			// Auslesen was drin steht
			String scanContent = scanningResult.getContents();
			
			// Code abfragen
			String scanCode = scanningResult.getFormatName();
			
			// Und anzeigen
			formatTxt.setText(scanCode);
			contentTxt.setText(scanContent);
		} else {
			// Kein Ergebnis - Meldung machen
			Toast myToast = Toast.makeText(getApplicationContext(), "Scan failed", Toast.LENGTH_SHORT);
			myToast.show();
		}
	}
}
