package com.example.erpautomationproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.widget.ExpandableListView;

public class ShowOrderButtonDisplay extends Activity {

	public void displayOrders(ExpandableListView OrderListDisplay,String username,Context context) throws ParseException{
		ArrayList<String> orderheader=new ArrayList<String>();
		HashMap<String,List<ParseObject>> orderlines=new HashMap<String,List<ParseObject>>();
		
		orderheader.add("Booked Orders");
		orderheader.add("Warehouse Orders");
		orderheader.add("Delivered Orders");
		
		ParseQuery<ParseObject> user = ParseQuery.getQuery("Users");
		user.whereEqualTo("User_UserName",username);
		System.out.println("Count:"+user.count());
		String userid=user.getFirst().getObjectId().toString();
		ParseQuery<ParseObject> customer=ParseQuery.getQuery("Customers");
		customer.whereEqualTo("User_Id", userid);
		String customerId=customer.getFirst().getObjectId().toString();
		System.out.println("Count:"+user.count());
		
		
		ParseQuery<ParseObject> itemDetailsBooked = ParseQuery.getQuery("Orders");
		itemDetailsBooked.whereEqualTo("User_Name", customerId);
		itemDetailsBooked.whereEqualTo("Order_Status", "Booked");
		System.out.println("showorderbuttoncount"+itemDetailsBooked.count());
	    
		orderlines.put(orderheader.get(0),itemDetailsBooked.find());
		System.out.println("Count:"+itemDetailsBooked.count());
		ParseQuery<ParseObject> itemDetailsShipped = ParseQuery.getQuery("Orders");
		itemDetailsShipped.whereEqualTo("User_Name", customerId);
		String[] s={"Shipped","InWarehouse"};
		itemDetailsShipped.whereContainedIn("Order_Status",Arrays.asList(s));//whereContainsAll("Order_Status",s[0],s[1]);
		
		orderlines.put(orderheader.get(1),itemDetailsShipped.find());
		
		ParseQuery<ParseObject> itemDetailsDelivered = ParseQuery.getQuery("Orders");
		itemDetailsDelivered.whereEqualTo("User_Name", customerId);
		itemDetailsDelivered.whereEqualTo("Order_Status", "Delivered");
		
		orderlines.put(orderheader.get(2),itemDetailsDelivered.find());
		
		OrderExpandapleListAdapter adapter=new OrderExpandapleListAdapter(orderheader, orderlines, context);
		OrderListDisplay.setAdapter(adapter);
		
	}
	
}
