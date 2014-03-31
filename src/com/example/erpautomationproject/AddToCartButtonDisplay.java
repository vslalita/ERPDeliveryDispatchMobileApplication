package com.example.erpautomationproject;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddToCartButtonDisplay extends Activity {

	static TextView allItemPrice;

	public void displaycartItems(ListView addtoCartItemsList,ArrayList<ParseObject> addToCartItems,Button BookOrderButton,final Context c,final String Username,final Button AddtoCartButton){

		//ListView addtoCartItemsList=(ListView)findViewById(R.id.addtocartListView);
		final ItemListDisplayAdapter listItemAdapter=new ItemListDisplayAdapter(addToCartItems,c,"AddtoCartItem");
		addtoCartItemsList.setAdapter(listItemAdapter);

		addtoCartItemsList.addFooterView(BookOrderButton,null,true);

		BookOrderButton.setText("BookOrder");
		BookOrderButton.setBackgroundColor(getTitleColor());
		addtoCartItemsList.addFooterView(BookOrderButton,null,true);

		BookOrderButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ArrayList<ParseObject> s=listItemAdapter.SelectedItem;

				ParseQuery<ParseObject> UserData = ParseQuery.getQuery("Users");
				UserData.whereEqualTo("User_UserName", Username);
				
				ParseQuery<ParseObject> customerIdQuery = ParseQuery.getQuery("Customers");
				try {
					customerIdQuery.whereEqualTo("User_Id",UserData.getFirst().getObjectId().toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customerId=new String();
				try {
					 customerId=customerIdQuery.getFirst().getObjectId().toString();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				ParseObject insertOrder=new ParseObject("Orders");
				insertOrder.put("Order_Amount",listItemAdapter.price);
				insertOrder.put("Order_Status","Booked");
				insertOrder.put("OrderItem_Count", s.size());
				insertOrder.put("User_Name",customerId);
				try {
					insertOrder.save();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				for(int i=0;i<s.size();i++){
					ParseObject insertOrderItem=new ParseObject("Order_Items");
					insertOrderItem.put("Order_Id", insertOrder.getObjectId().toString());
					insertOrderItem.put("Item_Id", s.get(i).getObjectId().toString());
					insertOrderItem.put("OrderItem_Status", "Booked");
					insertOrderItem.put("OrderItem_Qty", 1);
					try {
						insertOrderItem.save();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				for(int i=listItemAdapter.ItemList.size()-1;i>=0;i--){
					System.out.println("ListItemAdapter: "+listItemAdapter.ItemList.size()+" iteration"+i);
					listItemAdapter.ItemList.remove(i);
				}
				AddtoCartButton.performClick();
				Toast.makeText(c, "Items removed from cart", Toast.LENGTH_LONG).show();
			}
		});
	}

}
