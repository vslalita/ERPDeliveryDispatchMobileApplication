package com.example.erpautomationproject;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AdminSupplierDisplayActivity extends AdminPageActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_supplier_display);

		Intent intent=getIntent();
		String status=intent.getStringExtra("status");

		ParseQuery<ParseObject> suppliersQuery=new ParseQuery<ParseObject>("Suppliers");
		suppliersQuery.whereEqualTo("Supplier_Status", status);
		ArrayList<ParseObject> suppliers=new ArrayList<ParseObject>();
		try {
			suppliers=(ArrayList<ParseObject>)suppliersQuery.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(suppliers.size()>0){
			ListView adminSupplierListView=(ListView)findViewById(R.id.adminsupplierslistview);
			adminSupplierListView.setAdapter(new SupplierListDisplayAdapter(this,suppliers,"Admin"));

			adminSupplierListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					arg0.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							System.out.println("touched");

							float endX=0,startX=0;

							switch(event.getAction()){

							case (MotionEvent.ACTION_DOWN) :
								startX=event.getX();
							System.out.println(startX);
							Log.d("THis","Action was DOWN");
							return true;
							case (MotionEvent.ACTION_MOVE) :
								Log.d("THis","Action was MOVE");
							return true;
							case (MotionEvent.ACTION_UP) :
								endX=event.getX();
							System.out.println(endX);
							if(startX<endX){
								System.out.println("Moved Right");
								//LayoutInflater supplierLayoutView=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);                  
								// parent.removeAllViews();
								// LayoutInflater.from(this).inflate(R.layout.deletelayout,null);

							}

							Log.d("THis","Action was UP");
							return true;
							case (MotionEvent.ACTION_CANCEL) :
								Log.d("THis","Action was CANCEL");
							return true;
							case (MotionEvent.ACTION_OUTSIDE) :
								Log.d("THis","Movement occurred outside bounds " +
										"of current screen element");
							return true;      
							}
							return false;
						}
					});
				}
			});		
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_supplier_display, menu);
		return true;
	}

}
