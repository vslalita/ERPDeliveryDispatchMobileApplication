package com.example.erpautomationproject;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationActivity extends Activity {
	
	private static final int MY_NOTIFICATION_ID=1;
	 NotificationManager notificationManager;
	 Notification myNotification;
	 private final String myBlog = "http://android-er.blogspot.com/";
	 Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
	 PendingIntent pendingIntent = PendingIntent.getActivity(
		      this, 
		      0, 
		      myIntent, 
		      Intent.FLAG_ACTIVITY_NEW_TASK);
	 @SuppressLint("NewApi")
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_notification);
	  Button buttonSend = (Button)findViewById(R.id.send);
	  buttonSend.setOnClickListener(new OnClickListener(){

	   @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	   public void onClick(View arg0) {
	      
	    myNotification = new Notification.Builder(getBaseContext())
	          .setContentTitle("Exercise of Notification!")
	          .setContentText("http://android-er.blogspot.com/")
	          .setTicker("Notification!")
	          .setWhen(System.currentTimeMillis())
	          .setContentIntent(pendingIntent)
	          .setDefaults(Notification.DEFAULT_SOUND)
	          .setAutoCancel(true)
	          .setSmallIcon(R.drawable.ic_launcher)
	          .build();
	    
	    notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

	   }});

	 }


}
