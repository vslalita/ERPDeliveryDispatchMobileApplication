package com.erp.erpautomationproject;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.erpautomationproject.R;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.NotificationCompat;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class HomePageActivity extends Activity {
	final static ArrayList<ParseObject> AddtocartList=new ArrayList<ParseObject>();
int notificationid=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		Parse.initialize(HomePageActivity.this, "oHrb9b5ygGjT8PmEYb5Cdi1xTPAczZ1GNVQhaFJ0", "oOr5dAYiJiLhT1p5Evmi3QZiI7JQihoNAleWCHkD");

		Intent HomePageIntent=getIntent();
		final String name=HomePageIntent.getStringExtra("text");
		setTitle(name);

		final ViewGroup homepageview=(ViewGroup)findViewById(R.id.item_category_view_layout);


		Button home_button=(Button)findViewById(R.id.home_button);

		home_button.setOnClickListener(new OnClickListener(){
			int j=0;
			ArrayList<ParseObject> itemCategories;
			String SelectedCategory;
			@Override
			public void onClick(View arg0) {

				((ViewGroup) homepageview).removeAllViews();
				View Itemsdisplay=LayoutInflater.from(getBaseContext()).inflate(R.layout.home_layout,homepageview);

				TextView updateProfile=(TextView)Itemsdisplay.findViewById(R.id.UpdateProfileText);
				updateProfile.setPaintFlags(updateProfile.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
				updateProfile.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						((ViewGroup) homepageview).removeAllViews();
						final View userInfoForm=LayoutInflater.from(getBaseContext()).inflate(R.layout.updateprofile_layout,homepageview);

						ParseQuery<ParseObject> userInfo=ParseQuery.getQuery("Users");
						userInfo.whereEqualTo("User_UserName", name);

						try {
							userInfo.getInBackground(userInfo.getFirst().getObjectId(), new GetCallback<ParseObject>(){
								EditText userEmail=(EditText)userInfoForm.findViewById(R.id.UserEmailId);
								EditText userPhone=(EditText) userInfoForm.findViewById(R.id.UserPhone);
								EditText userBillToStreet=(EditText)userInfoForm.findViewById(R.id.UserBillToStreet);
								EditText userBillToCity=(EditText) userInfoForm.findViewById(R.id.UserBillToCity);
								EditText userBillToState=(EditText)userInfoForm.findViewById(R.id.UserBillToState);
								EditText userBillToCountry=(EditText)userInfoForm.findViewById(R.id.UserBillToCountry);
								EditText userBillToPin=(EditText)userInfoForm.findViewById(R.id.UserBillToPin);
								EditText userShipToStreet=(EditText)userInfoForm.findViewById(R.id.UserShipToStreet);
								EditText userShipToCity=(EditText) userInfoForm.findViewById(R.id.UserShipToCity);
								EditText userShipToState=(EditText)userInfoForm.findViewById(R.id.UserShipToState);
								EditText userShipToCountry=(EditText)userInfoForm.findViewById(R.id.UserShipToCountry);
								EditText userShipToPin=(EditText)userInfoForm.findViewById(R.id.UserShipToZip);
								@Override
								public void done(final ParseObject user,
										ParseException arg1) {
									// TODO Auto-generated method stub


									Button updateButton=(Button)userInfoForm.findViewById(R.id.UpdateButton);
									updateButton.setOnClickListener(new OnClickListener(){

										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub
											user.put("User_EmailId",userEmail.getText().toString());
											user.put("User_PhoneNumber",Integer.valueOf(userPhone.getText().toString()));
											user.put("User_BillTo_Street",userBillToStreet.getText().toString());
											user.put("User_BillTo_City",userBillToCity.getText().toString());
											user.put("User_BillTo_State",userBillToState.getText().toString());
											user.put("User_BillTo_Country",userBillToCountry.getText().toString());
											user.put("User_BillTo_Pin",Integer.valueOf(userBillToPin.getText().toString()));
											user.put("User_ShipTo_Street",userShipToStreet.getText().toString());
											user.put("User_ShipTo_City",userShipToCity.getText().toString());
											user.put("User_ShipTo_State",userShipToState.getText().toString());
											user.put("User_ShipTo_Country",userShipToCountry.getText().toString());
											user.put("User_ShipTo_Pin",Integer.valueOf(userShipToPin.getText().toString()));
											try {
												user.save();
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}

									});


								}

							});
						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
				});
				final AutoCompleteTextView autoSearchItem=(AutoCompleteTextView)Itemsdisplay.findViewById(R.id.AutoSearchItem);

				ParseQuery<ParseObject> searchItems = ParseQuery.getQuery("Items");
				ArrayList<String> item=new ArrayList<String>();
				ArrayList<ParseObject> searchItemsList=new ArrayList<ParseObject>();
				try {
					searchItemsList=(ArrayList<ParseObject>) searchItems.find();
					for(int i=0;i<searchItemsList.size();i++){
						item.add(searchItemsList.get(i).getString("Item_Name"));
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,item);
				autoSearchItem.setAdapter(adapter);

				final ArrayList<ParseObject> itemsClickArray=searchItemsList;
				autoSearchItem.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						String selectedItem=autoSearchItem.getText().toString();
						for(int i=0;i<itemsClickArray.size();i++){
							if(selectedItem.equals(itemsClickArray.get(i).getString("Item_Name"))){
								itemDetailDisplay(homepageview,itemsClickArray.get(i));	
							}
						}
					}
				});

				Button rightarrow=(Button)Itemsdisplay.findViewById(R.id.Categoryrightarrow);
				Button leftarrow=(Button)Itemsdisplay.findViewById(R.id.Categoryleftarrow);
				final com.parse.ParseImageView Image=(com.parse.ParseImageView)Itemsdisplay.findViewById(R.id.Categoryimageview);
				final TextView CategoryText=(TextView)Itemsdisplay.findViewById(R.id.CategoryText);
				final LinearLayout itemListLayout=(LinearLayout)Itemsdisplay.findViewById(R.id.itemslayoutView);

				ParseQuery<ParseObject> itemCategoryData = ParseQuery.getQuery("ItemCategories");

				try {
					itemCategories = (ArrayList<ParseObject>)itemCategoryData.find();
					if(itemCategories.size()>0){
						if(itemCategories.size()==1){
							rightarrow.setVisibility(View.GONE);
							leftarrow.setVisibility(View.GONE);
						}
						Image.setParseFile(itemCategories.get(0).getParseFile("Category_Image"));//.setText(items.get(i).getString("Item_Name"));
						Image.loadInBackground();
						SelectedCategory=itemCategories.get(j).getString("Category_Name");
						CategoryText.setText(SelectedCategory);

					}					
				} catch (ParseException e) {
					e.printStackTrace();
				}

				try {
					findItems(CategoryText.getText().toString(),itemListLayout,homepageview);
				} catch (ParseException e) {
					e.printStackTrace();
				}


				rightarrow.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						if(j==itemCategories.size()-1){
							j=0;
							Image.setParseFile(itemCategories.get(j).getParseFile("Category_Image"));
							Image.loadInBackground();
							SelectedCategory=itemCategories.get(j).getString("Category_Name");
							CategoryText.setText(SelectedCategory);
							try {
								findItems(CategoryText.getText().toString(),itemListLayout,homepageview);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						else{
							j=j+1;
							Image.setParseFile(itemCategories.get(j).getParseFile("Category_Image"));
							Image.loadInBackground();
							SelectedCategory=itemCategories.get(j).getString("Category_Name");
							CategoryText.setText(SelectedCategory);
							try {
								findItems(CategoryText.getText().toString(),itemListLayout,homepageview);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

					}

				});


				leftarrow.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						if(j==0){
							j=itemCategories.size()-1;
							Image.setParseFile(itemCategories.get(j).getParseFile("Category_Image"));
							Image.loadInBackground();
							SelectedCategory=itemCategories.get(j).getString("Category_Name");
							CategoryText.setText(SelectedCategory);
							try {
								findItems(CategoryText.getText().toString(),itemListLayout,homepageview);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						else{
							j=j-1;
							Image.setParseFile(itemCategories.get(j).getParseFile("Category_Image"));
							Image.loadInBackground();
							SelectedCategory=itemCategories.get(j).getString("Category_Name");
							CategoryText.setText(SelectedCategory);
							try {
								findItems(CategoryText.getText().toString(),itemListLayout,homepageview);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}

				});


				final TextView ViewAllText=(TextView)Itemsdisplay.findViewById(R.id.ViewAllText);
				ViewAllText.setPaintFlags(ViewAllText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
				ViewAllText.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						((ViewGroup) homepageview).removeAllViews();
						View ItemDetailListLayout=LayoutInflater.from(getBaseContext()).inflate(R.layout.itemdisplaylist_layout,homepageview);					

						final ListView itemsDetailListView=(ListView)ItemDetailListLayout.findViewById(R.id.itemsDisplayListView);
						ParseQuery<ParseObject> itemDetailsData = ParseQuery.getQuery("Items");
						itemDetailsData.whereEqualTo("Item_Category", SelectedCategory);
						ArrayList<ParseObject> itemsDetailList=new ArrayList<ParseObject>();
						try {
							itemsDetailList = (ArrayList<ParseObject>)itemDetailsData.find();
						} catch (ParseException e) {
							e.printStackTrace();
						}
						itemsDetailListView.setAdapter(new ItemListDisplayAdapter(itemsDetailList,getBaseContext(),"ShowItem"));

						itemsDetailListView.setOnItemClickListener(new OnItemClickListener(){
							ParseObject eachItemDetail=new ParseObject("eachItem");
							public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
									long id) {
								eachItemDetail=(ParseObject)itemsDetailListView.getItemAtPosition(position);
								itemDetailDisplay(homepageview,eachItemDetail);
							}
						});
					}				
				});
			}
		});
		home_button.performClick();

		final Button addtocart_button=(Button)findViewById(R.id.cart_button);
		addtocart_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				((ViewGroup) homepageview).removeAllViews();

				LayoutInflater.from(getBaseContext()).inflate(R.layout.addtocart_layout,homepageview);
				ListView addtoCartItemsList=(ListView)findViewById(R.id.addtocartListView);
				Button BookOrderButton=new Button(getBaseContext());

				AddToCartButtonDisplay addtocartlistdisplay=new AddToCartButtonDisplay();
				addtocartlistdisplay.displaycartItems(addtoCartItemsList,AddtocartList,BookOrderButton,getBaseContext(),name,addtocart_button);
			}
		});

		Button showorder_button=(Button)findViewById(R.id.orders_button);
		showorder_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				((ViewGroup) homepageview).removeAllViews();
				View Itemsdisplay=LayoutInflater.from(getBaseContext()).inflate(R.layout.showorder_layout,homepageview);
				ExpandableListView orderBookedItems=(ExpandableListView)Itemsdisplay.findViewById(R.id.expandableListView1);
				ShowOrderButtonDisplay s=new ShowOrderButtonDisplay();
				try {
					s.displayOrders(orderBookedItems, name,getBaseContext());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	
	int k=0;
	@SuppressLint("NewApi")
	public void findItems(String category,LinearLayout itemsLayout,final View homepageview) throws ParseException{
		itemsLayout.removeAllViews();
		ParseQuery<ParseObject> itemData = ParseQuery.getQuery("Items");
		itemData.whereEqualTo("Item_Category", category);

		final ArrayList<ParseObject> itemValues = (ArrayList<ParseObject>)itemData.find();
		for(k=0;k<itemValues.size();k++){
			final RelativeLayout eachItemLayout=new RelativeLayout(getBaseContext());
			itemsLayout.addView(eachItemLayout);

			ViewGroup.LayoutParams params =eachItemLayout.getLayoutParams();
			params.height=300;
			params.width=300;
			//eachItemLayout.setOrientation(1);


			final com.parse.ParseImageView eachItemImage=new com.parse.ParseImageView(getBaseContext());
			eachItemLayout.addView(eachItemImage);

			final TextView eachItemText= new TextView(getBaseContext());
			eachItemText.setBottom(eachItemImage.getId());
			//eachItemImage.setPlaceholder(null);
			final ParseObject itemname=itemValues.get(k);
			eachItemImage.setParseFile(itemValues.get(k).getParseFile("Item_Image"));
			eachItemImage.loadInBackground();
			eachItemImage.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					itemDetailDisplay(homepageview,itemname);
				}
			});
			eachItemLayout.addView(eachItemText);
			eachItemText.setText(itemValues.get(k).getString("Item_Name"));
			itemsLayout.setVisibility(View.VISIBLE);
		}
	}
	NotificationManager notificationManager;
	 Notification myNotification;
	
	public void itemDetailDisplay(View homepageview,final ParseObject eachItemDetail){
		((ViewGroup) homepageview).removeAllViews();
		View ItemDetailLayout=LayoutInflater.from(getBaseContext()).inflate(R.layout.itemdetail_layout,(ViewGroup) homepageview);	

		TextView itemNameValue=(TextView)ItemDetailLayout.findViewById(R.id.itemTextName);
		TextView itemCategoryValue=(TextView)ItemDetailLayout.findViewById(R.id.itemCategoryName);
		TextView itemColorValue=(TextView)ItemDetailLayout.findViewById(R.id.itemColor);
		TextView itemPriceValue=(TextView)ItemDetailLayout.findViewById(R.id.itemPrice);
		com.parse.ParseImageView itemImageValue=(com.parse.ParseImageView)ItemDetailLayout.findViewById(R.id.ItemImageValue);
		Button addtoCart=(Button)ItemDetailLayout.findViewById(R.id.itemAddtoCart);

		itemNameValue.setText("Item Name:"+ eachItemDetail.getString("Item_Name"));
		itemCategoryValue.setText("Category:"+eachItemDetail.getString("Item_Category"));
		itemColorValue.setText("Colour:"+eachItemDetail.getString("Item_Colour"));
		itemPriceValue.setText("Price:"+eachItemDetail.getNumber("Item_Price").toString()+".00$");
		itemImageValue.setParseFile(eachItemDetail.getParseFile("Item_Image"));
		itemImageValue.loadInBackground();

		addtoCart.setOnClickListener(new OnClickListener(){
			//Notification myNotification;
			
			@Override
			public void onClick(View v) {
				AddtocartList.add(eachItemDetail);
				NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(getBaseContext())
				        .setSmallIcon(R.drawable.shopping_cart)
				        .setContentTitle("ERP Delivery Dispatch")
				        .setContentText("an item has been added to cart");
				
				NotificationManager mNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(notificationid, mBuilder.build());
				notificationid=notificationid+1;
			}
				
//				ParseInstallation p=new ParseInstallation();
//				try {
//					p.refresh();
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				 p=ParseInstallation.getCurrentInstallation();
//			
//				try {
//					//p.refresh();
//					p.save();
//					
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				try {
//					p.refresh();
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				p.put("username", "ddsakala");
//				try {
//					p.save();
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				PushService.subscribe(getBaseContext(),"lalita", HomePageActivity.class);
//				ParseQuery<ParseInstallation> pquery = ParseInstallation.getQuery();
//				pquery.whereEqualTo("username", "ddsakala");
//
//				ParsePush parsePush = new ParsePush();
//				parsePush.setQuery(pquery);
//				parsePush.setMessage("Item Added to cart");
//				parsePush.sendInBackground();
//			}
		});
		
		
		
					
					
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection

		if(item.getItemId()==R.id.SignOut){

			this.finish();
		}
		return true;
	}
}
