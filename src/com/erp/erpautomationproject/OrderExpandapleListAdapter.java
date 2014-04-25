package com.erp.erpautomationproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.erpautomationproject.R;
import com.parse.ParseObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class OrderExpandapleListAdapter extends BaseExpandableListAdapter{

	ArrayList<String> orderheader;
	HashMap<String,List<ParseObject>> orderlines;
	Context context;
	int listCount;
	public OrderExpandapleListAdapter(ArrayList<String> orderheader,HashMap<String,List<ParseObject>> orderlines,Context context){

		this.orderheader=orderheader;
		this.orderlines=orderlines;
		this.context=context;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		listCount=orderlines.get(this.orderheader.get(arg0)).size();
		return this.orderlines.get(this.orderheader.get(arg0)).get(arg1);	
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			final ViewGroup arg4) {

		final ParseObject orders=(ParseObject) getChild(arg0, arg1);
		if (arg3 == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg3= infalInflater.inflate(R.layout.ordersview_layout, arg4,false);

			TextView orderNumberInfo=(TextView)arg3.findViewById(R.id.OrderNumberValue);
			TextView orderItemCount=(TextView)arg3.findViewById(R.id.OrderItemCount);
			TextView orderDateInfo=(TextView)arg3.findViewById(R.id.OrderDate);
			TextView orderStatusInfo=(TextView)arg3.findViewById(R.id.OrderStatus);
            TextView assignWarehouse=(TextView)arg3.findViewById(R.id.WarehouseSelectOption);;
            
			orderNumberInfo.setText("OrderNumber:"+orders.getObjectId());
			orderItemCount.setText("ItemCount:"+orders.getNumber("OrderItem_Count"));
			orderDateInfo.setText("Order Date:"+orders.getCreatedAt().toString());
			orderStatusInfo.setText("Status:"+orders.getString("Order_Status"));
			
			if(orders.getString("Order_Status").equals("InWarehouse")||orders.getString("Order_Status").equals("Shipped")||orders.getString("Order_Status").equals("Delivered")){
				assignWarehouse.setVisibility(View.GONE);
			}

			assignWarehouse.setPaintFlags(assignWarehouse.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
			orderNumberInfo.setPaintFlags(orderNumberInfo.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

			assignWarehouse.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,CustomerSupplierDisplayActivity.class);
				    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				    intent.putExtra("OrderNumber",orders.getObjectId().toString());
					context.startActivity(intent);
				}
			});
			
			orderNumberInfo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,OrderDetailActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("OrderNumber", orders.getObjectId().toString());
					context.startActivity(intent);
				}
			});



		}
		return arg3;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return this.orderlines.get(this.orderheader.get(arg0)).size();
	}

	@Override
	public Object getGroup(int arg0) {
		return this.orderheader.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return this.orderheader.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {

		String orderHeaderValue = (String) getGroup(arg0);
		if (arg2 == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg2 = infalInflater.inflate(R.layout.orderheader_layout, arg3,false);
		}
		TextView orderListHeader = (TextView)arg2.findViewById(R.id.OrderHeader);
		orderListHeader.setTypeface(null, Typeface.BOLD);
		orderListHeader.setText(orderHeaderValue);
		TextView orderListCount=(TextView)arg2.findViewById(R.id.OrderCount);
		orderListCount.setTypeface(null, Typeface.BOLD);
		orderListCount.setText(String.valueOf(orderlines.get(orderHeaderValue).size()));
		System.out.println("ordercount"+orderlines.get(orderHeaderValue).size());
		return arg2;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

}
