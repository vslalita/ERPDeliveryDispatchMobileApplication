package com.erp.erpautomationproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.erpautomationproject.R;
import com.parse.ParseObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class SupplierListDisplayAdapter extends BaseAdapter{
//	 StrictMode.ThreadPolicy policy = new
//			 StrictMode.ThreadPolicy.Builder().permitAll().build();
//			        StrictMode.setThreadPolicy(policy);
	Context context;
	ArrayList<ParseObject> supplierList;
	LayoutInflater supplierDisplayLayoutView;
	static ParseObject selectedSupplier;
	String request;
	SupplierListDisplayAdapter(Context context,ArrayList<ParseObject> supplierList,String request){
		this.context=context;
		this.supplierList=supplierList;
		this.request=request;
	}


	@Override
	public int getCount() {
		return supplierList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return supplierList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
    View row;
    View row1=null;
	@Override
	public View getView(final int arg0, View arg1, final ViewGroup parent) {
		// TODO Auto-generated method stub
		supplierDisplayLayoutView=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);                   
		row=supplierDisplayLayoutView.inflate(R.layout.supplierdetail_layout, parent,false);

		TextView supplierName=(TextView)row.findViewById(R.id.Suppliername);
		TextView supplierPrice=(TextView)row.findViewById(R.id.supplierItemPrice);
		TextView supplierAddress=(TextView)row.findViewById(R.id.supplierAddress);
		Button fbbutton=(Button)row.findViewById(R.id.facebookicon);

		supplierName.setText(supplierList.get(arg0).getString("Supplier_Name"));
		supplierPrice.setText(supplierList.get(arg0).getNumber("Supplier_ShippingPrice").toString());
		supplierAddress.setText(supplierList.get(arg0).getString("Supplier_Street")+","+supplierList.get(arg0).getString("Supplier_City")+","+supplierList.get(arg0).getString("Supplier_State")+","+supplierList.get(arg0).getString("Supplier_Country")+","+supplierList.get(arg0).getNumber("Supplier_ZipCode").toString());

		fbbutton.setOnClickListener(new View.OnClickListener(){
			String fbname=supplierList.get(arg0).getString("Supplier_Fbname");
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String url="https://graph.facebook.com"+"/"+fbname;
				
				
				
				Thread thread = new Thread(new Runnable(){
				    @Override
				    public void run() {
				        try {
				        	try {
				        	    URL fbgraphurl;
								fbgraphurl = new URL(url);
								HttpURLConnection fbgraphurlconnect=(HttpURLConnection)fbgraphurl.openConnection();
								fbgraphurlconnect.setRequestMethod("GET");
								//int responseCode = fbgraphurlconnect.getResponseCode();
								//System.out.println("Response Code : " + responseCode);

								BufferedReader in = new BufferedReader(
										new InputStreamReader(fbgraphurlconnect.getInputStream()));
								String inputLine;
								StringBuffer response = new StringBuffer();

								while ((inputLine = in.readLine()) != null) {
									response.append(inputLine);
								}
								in.close();

								//print result
								System.out.println(response.toString());
								JSONObject fbuserIdObject;
								String fbId=null;
								try {
									fbuserIdObject=new JSONObject(response.toString());
									fbId=fbuserIdObject.getString("id");
									try {
										context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
										Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("fb://profile/"+fbId));
										context.startActivity(intent);	
									} catch (NameNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println("FbId="+fbId);

							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
				    }
					});
				    thread.start();

			}
				    
				});//fbbutton onclick end
				
		final CheckBox supplierCheckBox=(CheckBox)row.findViewById(R.id.supplierCheckBox);
		supplierCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg, boolean arg1) {
				// TODO Auto-generated method stub
				int id = arg.getId();
				if(id==supplierCheckBox.getId()){
					if(supplierCheckBox.isChecked()){
						selectedSupplier=supplierList.get(arg0);
					}
					else{
						selectedSupplier=null;
					}
				}
			}

		});
		
//		if(request.equals("Admin")){
//			supplierCheckBox.setVisibility(View.GONE);
//			System.out.println("Hello");
			//View row1;
//			row.setOnTouchListener(new View.OnTouchListener() {
//
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					// TODO Auto-generated method stub
//					System.out.println("touched");
//
//					float endX=0,startX=0;
//
//					switch(event.getAction()){
//
//					case (MotionEvent.ACTION_DOWN) :
//						startX=event.getX();
//					System.out.println(startX);
//					Log.d("THis","Action was DOWN");
//					return true;
//					case (MotionEvent.ACTION_MOVE) :
//						Log.d("THis","Action was MOVE");
//					return true;
//					case (MotionEvent.ACTION_UP) :
//						endX=event.getX();
//					System.out.println(endX);
//					    if(startX<endX){
//						  System.out.println("Moved Right");
//						  //LayoutInflater supplierLayoutView=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);                  
//						  parent.removeAllViews();
//						  //LayoutInflater.from(context).inflate(R.layout.deletelayout,parent);
//					     
//					    }
//					    
//					Log.d("THis","Action was UP");
//					return true;
//					case (MotionEvent.ACTION_CANCEL) :
//						Log.d("THis","Action was CANCEL");
//					return true;
//					case (MotionEvent.ACTION_OUTSIDE) :
//						Log.d("THis","Movement occurred outside bounds " +
//								"of current screen element");
//					return true;      
//					}
//
//
//					return false;
//
//				}
//				
//			});
			
	//	}

		return row;
	}

}


