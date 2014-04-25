package com.erp.erpautomationproject;

import com.example.erpautomationproject.R;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;

public class SupplierCreateItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_create_item);

		Intent intent=getIntent();
		String id=intent.getStringExtra("id");
		
		EditText supplierItemName=(EditText)findViewById(R.id.SupplierItemName);
		EditText supplierItemCategory=(EditText)findViewById(R.id.SupplierItemCategory);
		EditText supplierItemPrice=(EditText)findViewById(R.id.SItemPrice);
		//EditText supplierShippingPrice=(EditText)findViewById(R.id.SupplierShippingPrice);
		EditText supplierItemQty=(EditText)findViewById(R.id.SupplierItemQty);

		ParseObject itemObject=new ParseObject("Items");
		itemObject.put("Item_Name",supplierItemName.getText().toString());
		itemObject.put("Item_Category",supplierItemCategory.getText().toString());
	    itemObject.put("Item_Price", supplierItemPrice.getText().toString());
		//itemObject.put("Item_Price",Integer.parseInt(supplierItemPrice.getText().toString()));
        try {
			itemObject.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ParseObject supplierItemObject=new ParseObject("Supplier_Items");
		supplierItemObject.put("Item_id", itemObject.getObjectId().toString());
		supplierItemObject.put("Supplier_Id",id);
		supplierItemObject.put("Item_Qty",supplierItemQty.getText().toString());
		//supplierItemObject.put("Item_Qty",Integer.parseInt(supplierItemQty.getText().toString()));
		try {
			supplierItemObject.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.supplier_create_item, menu);
		return true;
	}

}
