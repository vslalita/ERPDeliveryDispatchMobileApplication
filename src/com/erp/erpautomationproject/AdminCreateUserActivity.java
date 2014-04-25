package com.erp.erpautomationproject;

import com.example.erpautomationproject.R;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminCreateUserActivity extends AdminPageActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_create_user);

		Intent intent=getIntent();
		final String userType=intent.getStringExtra("usertype");

		Button submit=(Button)findViewById(R.id.insertionSubmit);
		submit.setOnClickListener(new View.OnClickListener() {

			EditText firstName=(EditText)findViewById(R.id.userCreateFirstName);
			EditText lastName=(EditText)findViewById(R.id.userCreateLastname);
			EditText userName=(EditText)findViewById(R.id.userCreateUsername);
			EditText password=(EditText)findViewById(R.id.userCreatePasword);
			EditText fbname=(EditText)findViewById(R.id.userCreateFacebookName);
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseObject userInsertion=new ParseObject("Users");
				userInsertion.put("User_FirstName",firstName.getText().toString());
				userInsertion.put("User_LastName", lastName.getText().toString());
				userInsertion.put("User_UserName", userName.getText().toString());
				userInsertion.put("User_Password", password.getText().toString());
				
				try {
					userInsertion.save();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(userType.equals("Customer")){
					ParseObject customerInsertion=new ParseObject("Customers");
					customerInsertion.put("User_Id", userInsertion.getObjectId().toString());
					
					try {
						customerInsertion.save();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Customer");
				}
				if(userType.equals("Supplier")){
					ParseObject supplierInsertion=new ParseObject("Suppliers");
					supplierInsertion.put("User_Id", userInsertion.getObjectId().toString());
					supplierInsertion.put("Supplier_Name", userInsertion.getString("User_FirstName"));
					supplierInsertion.put("Supplier_Status", "Active");
					supplierInsertion.put("Supplier_FBname", fbname.getText().toString());
					try {
						supplierInsertion.save();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Supplier");
				}
				if(userType.equals("Both")){
					ParseObject customerInsertion=new ParseObject("Customers");
					customerInsertion.put("User_Id", userInsertion.getObjectId().toString());
					try {
						customerInsertion.save();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ParseObject supplierInsertion=new ParseObject("Suppliers");
					supplierInsertion.put("User_Id", userInsertion.getObjectId().toString());
					try {
						supplierInsertion.save();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("customerSupplier");
				}
				
				if(userType.equals("Transporter")){
					ParseObject supplierInsertion=new ParseObject("Transporters");
					supplierInsertion.put("User_Id", userInsertion.getObjectId().toString());
					supplierInsertion.put("Transporter_Status", "Active");
					try {
						supplierInsertion.save();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Supplier");
				}
			}			
		});
	}
}
