package com.example.erpautomationproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TransporterHomePageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transporter_home_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transporter_home_page, menu);
		return true;
	}

}
