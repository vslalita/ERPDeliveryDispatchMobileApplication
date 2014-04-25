package com.erp.erpautomationproject;

import java.util.ArrayList;

import com.example.erpautomationproject.R;
import com.parse.ParseObject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
	LayoutInflater ordersDisplayLayoutView;
	Context context;
	ArrayList<ParseObject> orders;
	
	
	OrderListAdapter(Context context,ArrayList<ParseObject> orders){
		this.context=context;
		this.orders=orders;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orders.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return orders.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup parent) {
		// TODO Auto-generated method stub
		ordersDisplayLayoutView = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);              
		View row = ordersDisplayLayoutView.inflate(R.layout.ordersview_layout, parent,false);
 System.out.println(orders.size());
		
		TextView orderNumberInfo=(TextView)row.findViewById(R.id.OrderNumberValue);
		TextView orderItemCount=(TextView)row.findViewById(R.id.OrderItemCount);
		TextView orderDateInfo=(TextView)row.findViewById(R.id.OrderDate);
		TextView orderStatusInfo=(TextView)row.findViewById(R.id.OrderStatus);
		TextView assignWarehouse=(TextView)row.findViewById(R.id.WarehouseSelectOption);;

		//final int position=arg0;
		orderNumberInfo.setText("OrderNumber:"+orders.get(arg0).getObjectId());
		orderItemCount.setText("ItemCount:"+orders.get(arg0).getNumber("OrderItem_Count"));
		orderDateInfo.setText("Order Date:"+orders.get(arg0).getCreatedAt().toString());
		orderStatusInfo.setText("Status:"+orders.get(arg0).getString("Order_Status"));

		assignWarehouse.setVisibility(View.GONE);
		
		orderNumberInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,OrderDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("OrderNumber", orders.get(arg0).getObjectId().toString());
				context.startActivity(intent);
			}
		});
		return row;
	}

}
