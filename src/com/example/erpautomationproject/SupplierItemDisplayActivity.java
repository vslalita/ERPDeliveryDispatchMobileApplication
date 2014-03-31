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
import android.widget.ListView;

public class SupplierItemDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_item_display);

		Intent intent=getIntent();
		String id=intent.getStringExtra("supplierId");
		String status=intent.getStringExtra("status");
		ParseQuery<ParseObject> supplierQuery=new ParseQuery<ParseObject>("Supplier_Items");
		supplierQuery.whereEqualTo("Supplier_Id", id);
		ParseQuery<ParseObject> supplierItemsQuery=new ParseQuery<ParseObject>("Items");


		if(status.equals("available")){
			supplierQuery.whereGreaterThan("Item_Qty", 0);
		}
		else{
			supplierQuery.whereEqualTo("Item_Qty", 0);
		}
		try {
			ArrayList<ParseObject> supplierItems=(ArrayList<ParseObject>) supplierQuery.find();
			ArrayList<ParseObject> Items=(ArrayList<ParseObject>) supplierItemsQuery.find();
			ArrayList<ParseObject> ItemsList=new ArrayList<ParseObject>();
			for(int i=0;i<supplierItems.size();i++){
				for(int j=0;j<Items.size();j++){
					if(supplierItems.get(i).getString("Item_id").equals(Items.get(j).getObjectId())){
						ItemsList.add(Items.get(j));
					}
				}

			}

			ListView supplierItemsListView=(ListView)findViewById(R.id.supplierItemDisplay);
			supplierItemsListView.setAdapter(new ItemListDisplayAdapter(ItemsList, this, "ShowItem"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.supplier_item_display, menu);
		return true;
	}

}
