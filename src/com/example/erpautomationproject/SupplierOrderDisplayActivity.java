package com.example.erpautomationproject;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class SupplierOrderDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_order_display);
		
		Intent intent=getIntent();
		String id=intent.getStringExtra("supplierId");
		String status=intent.getStringExtra("status");
		
		ParseQuery<ParseObject> orderItems=ParseQuery.getQuery("Order_Items");//;("order_items");
		orderItems.whereEqualTo("Supplier_Id", id);
		orderItems.whereEqualTo("OrderItem_Status", status);
		try {
			System.out.println("orderitemscount"+orderItems.count());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<ParseObject> orderItemsList=new ArrayList<ParseObject>();
		try {
			orderItemsList = (ArrayList<ParseObject>) orderItems.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("orderitemscount"+orderItemsList.size());
		ListView orderListView=(ListView)findViewById(R.id.adminsupplierslistview);
		orderListView.setAdapter(new SupplierOrderItemsview(orderItemsList,getBaseContext()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.supplier_order_display, menu);
		return true;
	}

}
