package com.erp.erpautomationproject;

import com.example.erpautomationproject.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AdminPageActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_page);
		Parse.initialize(this, "oHrb9b5ygGjT8PmEYb5Cdi1xTPAczZ1GNVQhaFJ0", "oOr5dAYiJiLhT1p5Evmi3QZiI7JQihoNAleWCHkD");
		
		LinearLayout supplierLayout=(LinearLayout)findViewById(R.id.adminsupplierlayout);
		supplierLayout.setBackgroundColor(Color.WHITE);
		supplierLayout.setBackgroundResource(R.drawable.rectangle);
		LinearLayout ordersLayout=(LinearLayout)findViewById(R.id.adminorderslayout);

		ParseQuery<ParseObject> activeSupplierquery=new ParseQuery<ParseObject>("Suppliers");
		activeSupplierquery.whereEqualTo("Supplier_Status", "Active");

		ParseQuery<ParseObject> inactiveSupplierquery=new ParseQuery<ParseObject>("Suppliers");
		inactiveSupplierquery.whereEqualTo("Supplier_Status", "Inactive");

		ParseQuery<ParseObject> shippedOrderquery=new ParseQuery<ParseObject>("Orders");
		shippedOrderquery.whereEqualTo("Order_Status", "Shipped");

		ParseQuery<ParseObject> bookedOrderquery=new ParseQuery<ParseObject>("Orders");
		bookedOrderquery.whereEqualTo("Order_Status", "Booked");

		ParseQuery<ParseObject> inWarehouseOrderquery=new ParseQuery<ParseObject>("Orders");
		inWarehouseOrderquery.whereEqualTo("Order_Status", "InWarehouse");

		ParseQuery<ParseObject> deliveredOrderquery=new ParseQuery<ParseObject>("Orders");
		deliveredOrderquery.whereEqualTo("Order_Status", "Delivered");

		TextView supplierActiveCountText=(TextView)findViewById(R.id.supplieractivecount);
		TextView supplierInactiveCountText=(TextView)findViewById(R.id.supplierinactivecount);
        TextView shippedOrderCountText=(TextView)findViewById(R.id.shippedordercount);
        TextView bookedOrderCountText=(TextView)findViewById(R.id.bookedordercount);
        TextView deliveredOrderCountText=(TextView)findViewById(R.id.deliveredordercount);
        TextView inWarehouseOrderCountText=(TextView)findViewById(R.id.inwarehouseordercount);

        
		try {
			supplierActiveCountText.setText("Active: "+String.valueOf(activeSupplierquery.count()));
			supplierInactiveCountText.setText("Inactive: "+String.valueOf(inactiveSupplierquery.count()));
			shippedOrderCountText.setText("ShippedOrders: "+String.valueOf(shippedOrderquery.count()));
			bookedOrderCountText.setText("BookedOrders: "+String.valueOf(bookedOrderquery.count()));
			deliveredOrderCountText.setText("Delivered Orders: "+String.valueOf(deliveredOrderquery.count()));
			inWarehouseOrderCountText.setText("inWarehouse Orders: "+String.valueOf(inWarehouseOrderquery.count()));

			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		supplierActiveCountText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AdminPageActivity.this,AdminSupplierDisplayActivity.class);
				intent.putExtra("status", "Active");
				startActivity(intent);
				System.out.println("clicked active");
			}
		});
		supplierInactiveCountText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AdminPageActivity.this,AdminSupplierDisplayActivity.class);
				intent.putExtra("status", "Inactive");
				startActivity(intent);
				System.out.println("clicked");
			}
		});
		
		shippedOrderCountText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AdminPageActivity.this,AdminOrderDisplayActivity.class);
				intent.putExtra("status", "Shipped");
				startActivity(intent);
				System.out.println("clicked");
			}
		});
		
		
		bookedOrderCountText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AdminPageActivity.this,AdminOrderDisplayActivity.class);
				intent.putExtra("status", "Booked");
				startActivity(intent);
			}
		});
		
	    inWarehouseOrderCountText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AdminPageActivity.this,AdminOrderDisplayActivity.class);
				intent.putExtra("status", "In Warehouse");
				startActivity(intent);
				System.out.println("clicked");
			}
		});
		
		deliveredOrderCountText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AdminPageActivity.this,AdminOrderDisplayActivity.class);
				intent.putExtra("status", "Delivered");
				startActivity(intent);
				System.out.println("clicked");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection

		if(item.getItemId()==R.id.CreateUsersAdmin){
			final Dialog d=new Dialog(this);
			d.setContentView(R.layout.admincreateuserchioce_layout);
			d.setTitle("Select User");
			d.show();

			Button okbutton=(Button)d.findViewById(R.id.adminChoiceOk);
			okbutton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					RadioGroup adminChoiceOptions=(RadioGroup)d.findViewById(R.id.adminUserCreationChoice);
					int id=adminChoiceOptions.getCheckedRadioButtonId();
					RadioButton adminChoice=(RadioButton)d.findViewById(id);
					if(adminChoice.getText().toString().equals("Customer")){
						Intent intent=new Intent(AdminPageActivity.this,AdminCreateUserActivity.class);
						intent.putExtra("usertype", "Customer");
						startActivity(intent);
						System.out.println("Customer");
					}
					if(adminChoice.getText().toString().equals("Supplier")){
						Intent intent=new Intent(AdminPageActivity.this,AdminCreateUserActivity.class);
						intent.putExtra("usertype", "Supplier");
						startActivity(intent);
						System.out.println("Supplier");
					}

					if(adminChoice.getText().toString().equals("Both")){
						Intent intent=new Intent(AdminPageActivity.this,AdminCreateUserActivity.class);
						intent.putExtra("usertype", "Both");
						startActivity(intent);
					}
				}
			});
		}

		if(item.getItemId()==R.id.SignOutAdmin){
			this.finish();
		}
		return true;
	}

}
