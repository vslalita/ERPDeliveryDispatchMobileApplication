package com.example.erpautomationproject;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.GridView;

public class CustomerSupplierDisplayActivity extends HomePageActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_supplier_display);
		
		Intent intent=getIntent();
		String orderid=intent.getStringExtra("OrderNumber");
		System.out.println("Order_Id:"+orderid);
		ParseQuery<ParseObject> itemsListQuery=new ParseQuery<ParseObject>("Order_Items");
		itemsListQuery.whereEqualTo("Order_Id", orderid);
		ArrayList<ParseObject> itemsList=new ArrayList<ParseObject>();
		try {
			itemsList = (ArrayList<ParseObject>) itemsListQuery.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GridView itemGrid=(GridView)findViewById(R.id.itemsGrid);
		itemGrid.setAdapter(new ItemListDisplayAdapter(itemsList,getBaseContext(),"ShowItemsSupplier"));
		
	}
}
