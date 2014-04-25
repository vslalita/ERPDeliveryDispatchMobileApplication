package com.erp.erpautomationproject;

import java.util.ArrayList;

import com.example.erpautomationproject.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SupplierOrderItemsview extends BaseAdapter {

	ArrayList<ParseObject> orderItems;
	Context context;
	SupplierOrderItemsview(ArrayList<ParseObject> orderItems,Context context){
		this.orderItems=orderItems;
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return orderItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater supplierOrdersDisplayLayout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);              
		View row = supplierOrdersDisplayLayout.inflate(R.layout.ordersview_layout, parent,false);
		
		ParseQuery<ParseObject> itemsQuery=ParseQuery.getQuery("Items");
		ArrayList<ParseObject> itemsList=new ArrayList<ParseObject>();
		try {
			itemsList = (ArrayList<ParseObject>) itemsQuery.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String item_name = null;
		for(int i=0;i<itemsList.size();i++){
			if(itemsList.get(i).getObjectId().equals(orderItems.get(arg0).getString("Item_Id"))){
				item_name=itemsList.get(i).getString("Item_Name");
			}
		}
		
		
		ParseQuery<ParseObject> supplierIdQuery=ParseQuery.getQuery("Suppliers");
		try {
			supplierIdQuery.get(orderItems.get(arg0).getString("Supplier_Id"));
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ParseObject supplierIdObject = new ParseObject("Suppliers");
		try {
			supplierIdObject = supplierIdQuery.getFirst();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ParseQuery<ParseObject> supplierNameQuery=ParseQuery.getQuery("Users");
		try {
			supplierNameQuery.get(supplierIdObject.getString("User_Id"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ParseObject supplierNameObject=new ParseObject("Users");
		try {
			supplierNameObject = supplierNameQuery.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		TextView OrderNumberText=(TextView)row.findViewById(R.id.OrderNumberValue);
//		TextView ItemNameorder=(TextView)row.findViewById(R.id.ItemNameorder);
//		TextView orderSupplierName=(TextView)row.findViewById(R.id.orderSupplierName);
//		TextView OrderDate=(TextView)row.findViewById(R.id.OrderDate);
//		
//		OrderNumberText.setText(orderItems.get(arg0).getObjectId());
//		ItemNameorder.setText(item_name);
//		orderSupplierName.setText(supplierNameObject.getString("User_FirstName"));
//		OrderDate.setText(orderItems.get(arg0).getCreatedAt().toString());
//		
		//itemsQuery.whereEqualTo("id", orderItems.get(arg0).getString(""));
		
		
		return row;
	}

}
