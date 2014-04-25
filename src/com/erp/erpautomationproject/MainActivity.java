
package com.erp.erpautomationproject;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;

import com.example.erpautomationproject.R;
import com.parse.*;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
	String username_value;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RelativeLayout layout=(RelativeLayout)findViewById(R.id.mainActivityLayout);
		layout.setBackgroundColor(Color.WHITE);
		layout.setBackgroundResource(R.drawable.rectangle);
		
		Button Login=(Button) findViewById(R.id.Login);
		final EditText username=(EditText)findViewById(R.id.Username);
		final EditText password=(EditText)findViewById(R.id.Password);

		Parse.initialize(MainActivity.this, "oHrb9b5ygGjT8PmEYb5Cdi1xTPAczZ1GNVQhaFJ0", "oOr5dAYiJiLhT1p5Evmi3QZiI7JQihoNAleWCHkD");

		Login.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				username_value=username.getText().toString();
				String password_value=password.getText().toString();

				ParseQuery<ParseObject> uservalidationQuery = ParseQuery.getQuery("Users");
				uservalidationQuery=uservalidationQuery.whereEqualTo("User_UserName",username_value);
				uservalidationQuery=uservalidationQuery.whereEqualTo("User_Password", password_value);
				try {
					if(uservalidationQuery.count()==1){
						ArrayList<ParseObject> user=(ArrayList<ParseObject>) uservalidationQuery.find();

						ParseQuery<ParseObject> customerCheckQuery=new ParseQuery<ParseObject>("Customers");
						customerCheckQuery.whereEqualTo("User_Id", user.get(0).getObjectId().toString());

						if(user.get(0).getString("User_Role").equals("Admin")){
							Intent adminPageIntent=new Intent(MainActivity.this,AdminPageActivity.class);
						    adminPageIntent.putExtra("text",username_value);
							startActivity(adminPageIntent);
						}
						
						ParseQuery<ParseObject> supplierCheckQuery=new ParseQuery<ParseObject>("Suppliers");
						supplierCheckQuery.whereEqualTo("User_Id", user.get(0).getObjectId());

						ParseQuery<ParseObject> transporterCheckQuery=new ParseQuery<ParseObject>("Transporters");
						transporterCheckQuery.whereEqualTo("User_Id", user.get(0).getObjectId());

						int customer_flag=customerCheckQuery.count();
						int supplier_flag=supplierCheckQuery.count();
						int transporter_flag=transporterCheckQuery.count();

						if(customer_flag==1 && supplier_flag==0 && transporter_flag==1){
							setDialogView("Supplier");
						}

						if(customer_flag==1 && supplier_flag==1 && transporter_flag==0){
							setDialogView("Transporter");
						}
						if(customer_flag==1 && supplier_flag==1 && transporter_flag==1){
							setDialogView("All");
						}

						if(customer_flag==1 && supplier_flag==0 && transporter_flag==0){
							openView("Customer");
						}

						if(customer_flag==0 && supplier_flag==1 && transporter_flag==0){
							openView("Supplier");
						}
					}
					else{
						Toast.makeText(MainActivity.this,"User does not exist. Please Signup", Toast.LENGTH_LONG).show();
						username.setText(null);
						password.setText(null);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});

		Button Signup=(Button)findViewById(R.id.Signup);
		Signup.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				final Dialog d=new Dialog(MainActivity.this);
				d.setContentView(R.layout.signup_layout);
				d.setTitle("SignUp");
				d.setCancelable(true);
				d.show();

				Button signupButton= (Button)d.findViewById(R.id.SignupButton2);
				signupButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						EditText firstname=(EditText)d.findViewById(R.id.firstnamefield);
						EditText lastname=(EditText)d.findViewById(R.id.lastnamefield);
						EditText username1=(EditText)d.findViewById(R.id.usernamefield);
						EditText password1=(EditText)d.findViewById(R.id.passwordfield);
						String username_value=username1.getText().toString();
						String password_value=password1.getText().toString();					

						ParseQuery<ParseObject> uservalidationQuery = ParseQuery.getQuery("Users");
						uservalidationQuery=uservalidationQuery.whereEqualTo("User_UserName",username1.getText().toString());
						uservalidationQuery=uservalidationQuery.whereEqualTo("User_Password", password_value);

						try {
							if(uservalidationQuery.count()>0){
								Toast.makeText(MainActivity.this, "User Already Exists .Please Try Again",Toast.LENGTH_LONG).show();
								firstname.setText(null);
								lastname.setText(null);
								username1.setText(null);
								password1.setText(null);
							}
							else{
								ParseObject user = new ParseObject("Users");
								user.put("User_FirstName", firstname.getText().toString());
								user.put("User_LastName", lastname.getText().toString());
								user.put("User_UserName", username_value);
								user.put("User_Password", password_value);
								user.put("User_Role", "Customer");
								user.save();

								System.out.println(user.getObjectId().toString());
								ParseObject customerObject=new ParseObject("Customers");
								customerObject.put("User_Id", user.getObjectId().toString());
								customerObject.saveInBackground();

								username.setText(username1.getText().toString());
								password.setText(password1.getText().toString());
								d.dismiss();
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
				});	
			}		
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	public void openView(String choice){

		if(choice.equals("Customer")){
			Intent HomePageIntent=new Intent(MainActivity.this,HomePageActivity.class);
			HomePageIntent.putExtra("text",username_value);
			startActivity(HomePageIntent);
		}
		if(choice.equals("Supplier")){
			Intent SupplierHomePageIntent=new Intent(MainActivity.this,SupplierHomePageActivity.class);
			SupplierHomePageIntent.putExtra("name",username_value);
			startActivity(SupplierHomePageIntent);
		}
		if(choice.equals("Transporter")){
			Intent TransporterHomePageIntent=new Intent(MainActivity.this,TransporterHomePageActivity.class);
			TransporterHomePageIntent.putExtra("name",username_value);
			startActivity(TransporterHomePageIntent);
		}
	}


	public void setDialogView(String removeOption){

		final Dialog d=new Dialog(MainActivity.this);
		d.setContentView(R.layout.multipleaccounts_layout);
		d.setTitle("Sign in as:");
		d.setCancelable(true);
		if(removeOption.equals("Supplier")){
			RadioButton supplierOptionButton=(RadioButton)d.findViewById(R.id.SupplierRadioButton);
			supplierOptionButton.setVisibility(View.GONE);
		}
		if(removeOption.equals("Transporter")){
			RadioButton transporterOptionButton=(RadioButton)d.findViewById(R.id.TransporterRadioButton);
			transporterOptionButton.setVisibility(View.GONE);
		}
		d.show();

		Button ok=(Button)d.findViewById(R.id.userChoiceOk);
		ok.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				RadioGroup userAccountChoice=(RadioGroup)d.findViewById(R.id.userAccountChoice);
				int id=userAccountChoice.getCheckedRadioButtonId();
				if(id!=-1){
					RadioButton userChoice=(RadioButton)d.findViewById(id);

					if(userChoice.getText().toString().equals("Customer")){
						openView("Customer");
					}
					if(userChoice.getText().toString().equals("Supplier")){
						openView("Supplier");
					}
					if(userChoice.getText().toString().equals("Transporter")){
						openView("Transporter");
					}
				}
			}
		});
	}
}
