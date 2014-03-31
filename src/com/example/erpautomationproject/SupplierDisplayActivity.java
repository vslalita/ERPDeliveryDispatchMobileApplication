package com.example.erpautomationproject;

import java.util.ArrayList;
import java.util.Arrays;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class SupplierDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_display);
		Parse.initialize(SupplierDisplayActivity.this, "oHrb9b5ygGjT8PmEYb5Cdi1xTPAczZ1GNVQhaFJ0", "oOr5dAYiJiLhT1p5Evmi3QZiI7JQihoNAleWCHkD");


		Intent intent=getIntent();
		String itemId=intent.getStringExtra("ItemId");
		final String orderitemId=intent.getStringExtra("OrderItemId");
		ParseQuery<ParseObject> supplierItemList=new ParseQuery<ParseObject>("Supplier_Items");
		supplierItemList.selectKeys(Arrays.asList("Supplier_Id"));
		supplierItemList.whereEqualTo("Item_id",itemId);
		ArrayList<ParseObject> supplierIds=new ArrayList<ParseObject>();
		try {
			supplierIds = (ArrayList<ParseObject>)supplierItemList.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ParseQuery <ParseObject> supplierListQuery=new ParseQuery<ParseObject>("Suppliers");
		ArrayList<ParseObject> suppliers =new ArrayList<ParseObject>();
		try {
			suppliers=(ArrayList<ParseObject>)supplierListQuery.find();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		ArrayList<ParseObject> supplierList=new ArrayList<ParseObject>();
		for(int i=0;i<suppliers.size();i++){
			for(int j=0;j<supplierIds.size();j++){
				if(suppliers.get(i).getObjectId().equals(supplierIds.get(j).getString("Supplier_Id"))){
					supplierList.add(suppliers.get(i));
				}

			}
		}

		ListView supplierListView=(ListView)findViewById(R.id.supplierListView);
		supplierListView.setAdapter(new SupplierListDisplayAdapter(this,supplierList,null));

		Button selectSupplierButton=new Button(this);
		supplierListView.addFooterView(selectSupplierButton);
		selectSupplierButton.setText("Select Supplier");
		
		selectSupplierButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                ParseObject supplier=SupplierListDisplayAdapter.selectedSupplier;
                ParseQuery<ParseObject> itemUpdate=new ParseQuery<ParseObject>("Order_Items");
                try {
					itemUpdate.get(orderitemId);
					ParseObject p=itemUpdate.getFirst();
					p.put("Supplier_Id",supplier.getObjectId());
					p.put("OrderItem_Status", "InWarehouse");
					p.save();
					ParseQuery<ParseObject> orderUpdate=new ParseQuery<ParseObject>("Orders");
					String OrderId=p.getString("Order_Id");
					ParseObject order=orderUpdate.get(OrderId);
					ParseQuery<ParseObject> orderitems=ParseQuery.getQuery("Order_Items");
					orderitems.whereEqualTo("Order_Id",order.getObjectId());
					int orderitemscount=orderitems.count();
					ArrayList<ParseObject> items=new ArrayList<ParseObject>();
					items=(ArrayList<ParseObject>)orderitems.find();
					orderitemscount=items.size();
					int j=0;
					for(int i=0;i<items.size();i++){
						if(items.get(i).getString("OrderItem_Status").equals("InWarehouse")){
							j=j+1;
						}
					}
					if(j==items.size()){
						order.put("Order_Status", "InWarehouse");
						order.save();
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
			}
		});
		
		
	}

}
