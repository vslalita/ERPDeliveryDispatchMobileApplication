package com.erp.erpautomationproject;

import java.util.ArrayList;

import com.example.erpautomationproject.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class AdminOrderDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_order_display);
		
		Intent intent=getIntent();
		String status=intent.getStringExtra("status");
		System.out.println(status);

		ParseQuery<ParseObject> ordersQuery=new ParseQuery<ParseObject>("Orders");
		ordersQuery.whereEqualTo("Order_Status", status);
		try {
			System.out.println(ordersQuery.count());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<ParseObject> orders=new ArrayList<ParseObject>();
		try {
			 orders=(ArrayList<ParseObject>)ordersQuery.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(orders.size());
		ListView ordersListView=(ListView)findViewById(R.id.adminorderlistview);
		ordersListView.setAdapter(new OrderListAdapter(this,orders));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_order_display, menu);
		return true;
	}

}
