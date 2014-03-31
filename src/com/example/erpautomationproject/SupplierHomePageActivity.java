package com.example.erpautomationproject;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SupplierHomePageActivity extends Activity {
	ArrayList<ParseObject> unavailableItemsList=new ArrayList<ParseObject>();
	ArrayList<ParseObject> availableItemsList=new ArrayList<ParseObject>();
	String userId=null;
	String Id=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_home_page);

		Intent intent=getIntent();
		String username=intent.getStringExtra("name");

		ParseQuery<ParseObject> userQuery=new ParseQuery<ParseObject>("Users");
		userQuery.whereEqualTo("User_UserName", username);
		String userId=null;
		try {
			userId=userQuery.getFirst().getObjectId();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		ParseQuery<ParseObject> supplierQuery=new ParseQuery<ParseObject>("Suppliers");
		supplierQuery.whereEqualTo("User_Id", userId);
		String supplierId=null;
		try {
			supplierId=supplierQuery.getFirst().getObjectId();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Id=supplierId; 
		ParseQuery<ParseObject> supplierAvailableItemsQuery=new ParseQuery<ParseObject>("Supplier_Items");
		supplierAvailableItemsQuery.whereEqualTo("Supplier_Id", supplierId);
		supplierAvailableItemsQuery.whereGreaterThan("Item_Qty", 0);

		try {
			System.out.println("Count"+supplierAvailableItemsQuery.count());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ParseQuery<ParseObject> supplierUnavailableItemsQuery=new ParseQuery<ParseObject>("Supplier_Items");
		supplierUnavailableItemsQuery.whereEqualTo("Supplier_Id", supplierId);
		supplierUnavailableItemsQuery.whereEqualTo("Item_Qty", 0);

		ParseQuery<ParseObject> activeTransporterQuery=new ParseQuery<ParseObject>("Transporters");
		activeTransporterQuery.whereEqualTo("Transporter_Status", "Active");

		ParseQuery<ParseObject> inactiveTransporterQuery=new ParseQuery<ParseObject>("Transporters");
		inactiveTransporterQuery.whereEqualTo("Transporter_Status", "Inactive");

		try {
			availableItemsList=(ArrayList<ParseObject>)supplierAvailableItemsQuery.find();
			unavailableItemsList=(ArrayList<ParseObject>)supplierUnavailableItemsQuery.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextView availableItemsText=(TextView)findViewById(R.id.supplierAvailableItems);
		TextView unavailableItemsText=(TextView)findViewById(R.id.supplierUnavailableItems);
		TextView activeTransportersText=(TextView)findViewById(R.id.supplierActiveTransporters);
		TextView inactiveTransportersText=(TextView)findViewById(R.id.supplierInactiveTransporters);


		availableItemsText.setText("Available Items:"+String.valueOf(availableItemsList.size()));
		unavailableItemsText.setText("Unavailable Items:"+String.valueOf(unavailableItemsList.size()));
		try {
			activeTransportersText.setText("Active:"+String.valueOf(activeTransporterQuery.count()));
			inactiveTransportersText.setText("Inactive:"+String.valueOf(inactiveTransporterQuery.count()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		availableItemsText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SupplierHomePageActivity.this,SupplierItemDisplayActivity.class);
				intent.putExtra("supplierId",Id);
				intent.putExtra("status", "available");
				startActivity(intent);


			}
		});
		unavailableItemsText.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SupplierHomePageActivity.this,SupplierItemDisplayActivity.class);
				intent.putExtra("supplierId",Id);
				intent.putExtra("status", "unavailable");
				startActivity(intent);
			}
		});
		
		
		ParseQuery<ParseObject> order_items_inwarehouse=new ParseQuery<ParseObject>("Order_Items");
		order_items_inwarehouse.whereEqualTo("Supplier_Id", Id);
		order_items_inwarehouse.whereEqualTo("OrderItem_Status","InWarehouse");
		
		ParseQuery<ParseObject> order_items_suppliers=new ParseQuery<ParseObject>("Order_Items");
		order_items_suppliers.whereEqualTo("Supplier_Id", Id);
		order_items_suppliers.whereEqualTo("OrderItem_Status","Shipped");
		
		TextView inWarehouseOrders=(TextView)findViewById(R.id.supplierOrdersInwarehouse);
		TextView ShippedOrders=(TextView)findViewById(R.id.supplierOrderItemsShipped);
		
		try {
			inWarehouseOrders.setText("InWarehouseOrders("+order_items_inwarehouse.count()+")");
			ShippedOrders.setText("Shipped("+order_items_suppliers.count()+")");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.supplier_home_page, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection

		if(item.getItemId()==R.id.SupplierSignOut){

			this.finish();
		}
//		if(item.getItemId()==R.id.SupplierCreateItem){
//			Intent intent=new Intent(this,SupplierCreateItemActivity.class);
//			//intent.putExtra("usertype", "Transporter");
//			intent.putExtra("id", Id);
//			startActivity(intent);
//		}
		return true;
	}
	
	
	

}
