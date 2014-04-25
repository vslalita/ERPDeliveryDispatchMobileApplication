package com.erp.erpautomationproject;

import java.util.ArrayList;

import com.example.erpautomationproject.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class OrderDetailActivity extends Activity {
	ParseObject order=new ParseObject("Orders");
	String orderdata;
	ArrayList<ParseObject> orderItems=new ArrayList<ParseObject>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);

		Intent intent=getIntent();
		String ordernumber=intent.getStringExtra("OrderNumber");

		ParseQuery<ParseObject> orderDetails=new ParseQuery<ParseObject>("Orders");
		try {
			orderDetails.get(ordernumber);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		//ArrayList<ParseObject> orders=(ArrayList<ParseObject>) orderDetails.find();
		
		try {
			order = orderDetails.getFirst();
		} catch (ParseException e) {
			e.printStackTrace();
		}


		TextView orderDetailNumber =(TextView)findViewById(R.id.OrderDetailsNumber);
		TextView  orderDetailDate=(TextView)findViewById(R.id.OrderDetailsDate);
		TextView  orderDetailItemCount=(TextView)findViewById(R.id.OrderDetailsItemCount);
		TextView  orderDetailStatus=(TextView)findViewById(R.id.OrderDetailsStatus);

		orderDetailNumber.setText("Order Number:"+order.getObjectId());
		orderDetailDate.setText("Date:"+order.getCreatedAt());
		orderDetailItemCount.setText("OrderItemCount:"+order.getNumber("OrderItem_Count").toString());
		orderDetailStatus.setText("Status:"+order.getString("Order_Status"));

		ParseQuery<ParseObject> orderItemDetails=new ParseQuery<ParseObject>("Order_Items");
		orderItemDetails.whereEqualTo("Order_Id", ordernumber);
		
		try {
			orderItems=(ArrayList<ParseObject>)orderItemDetails.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		TableLayout itemsDetails=(TableLayout)findViewById(R.id.ItemDetailsTable);

		for(int i=0;i<orderItems.size();i++){
			TableRow newitemrow=new TableRow(this);
			TextView itemname=new TextView(this);
			TextView itemqty=new TextView(this);
			TextView itemstatus=new TextView(this);
			TextView itemprice=new TextView(this);
           
			ParseQuery<ParseObject> orderItem=new ParseQuery<ParseObject>("Items");
			try {
				orderItem.get(orderItems.get(i).getString("Item_Id"));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			try {
				itemname.setText(orderItem.getFirst().getString("Item_Name"));
				itemqty.setText(orderItems.get(i).getNumber("OrderItem_Qty").toString());
				itemstatus.setText(orderItems.get(i).getString("OrderItem_Status"));
				itemprice.setText(orderItem.getFirst().getNumber("Item_Price").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			newitemrow.addView(itemname);
			newitemrow.addView(itemqty);
			newitemrow.addView(itemstatus);
			newitemrow.addView(itemprice);

			itemsDetails.addView(newitemrow);
			
			
		}
    
		
		Button b=(Button)findViewById(R.id.mail);
		b.setText("E-mail");

		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent openEmailIntent = new Intent(android.content.Intent.ACTION_SEND);
				//openEmailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				openEmailIntent.setType("plain/text");
				openEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[]{"twinklesiva05@gmail.com"});
				for (int i=0;i<orderItems.size();i++){
					ParseQuery<ParseObject> item=ParseQuery.getQuery("Items");
					//item.get(orderItems.get(i).getString("Item_Id"));
					try {item.get(orderItems.get(i).getString("Item_Id"));
						orderdata=orderdata+"itemName:"+item.getFirst().getString("Item_Name")+"("+orderItems.get(i).getString("OrderItem_Status")+")"+"/n";
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				openEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"OrderNumber:"+order.getObjectId()+" Confrimation");
				openEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Order For Following items: \n"+orderdata);
				startActivity(Intent.createChooser(openEmailIntent,"Sharing via Email"));
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_detail, menu);
		return true;
	}

}
