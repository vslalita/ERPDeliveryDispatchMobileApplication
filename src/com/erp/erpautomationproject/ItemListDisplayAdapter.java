package com.erp.erpautomationproject;

import java.util.ArrayList;

import com.example.erpautomationproject.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ItemListDisplayAdapter extends BaseAdapter{

	ArrayList<ParseObject> ItemList;
	ParseObject itemDetails=new ParseObject("items");
	LayoutInflater itemsDisplayLayoutView;
	//	ArrayList<ParseFile> itemsDisplayImage;
	//	ArrayList<String> itemsValues;
	Context context;
	String ShowItems;
	ArrayList<ParseObject> SelectedItem;
	int price=0;

	public ItemListDisplayAdapter(ArrayList<ParseObject> result , Context c,String ShowItems) {
		this.ItemList = result;
		this.context = c;
		this.ShowItems=ShowItems;		
	}


	@Override
	public int getCount() {
		return ItemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return ItemList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup parent) {
		View row=new View(context);
		if(ShowItems.equals("ShowItem")||ShowItems.equals("AddtoCartItem")){
			SelectedItem=new ArrayList<ParseObject>();
			itemsDisplayLayoutView  =    
					(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);                               
			row = itemsDisplayLayoutView.inflate(R.layout.allitemview_layout, parent,false);
			TextView itemprice=(TextView)row.findViewById(R.id.ItemPriceValue);


			final CheckBox itemcheck=(CheckBox)row.findViewById(R.id.ItemCheck);
			if(ShowItems.equals("ShowItem")){
				itemcheck.setVisibility(View.GONE);
				itemprice.setVisibility(View.GONE);
			}
			TextView itemValueText = (TextView)row.findViewById(R.id.ItemText);
			com.parse.ParseImageView itemImageView = (com.parse.ParseImageView) row.findViewById(R.id.ItemImage);
			itemImageView.setPlaceholder(null);
			itemValueText.setText(ItemList.get(position).getString("Item_Name"));
			System.out.println(ItemList.get(position).getString("Item_Name"));
			itemImageView.setPlaceholder(null);
			itemImageView.setParseFile(ItemList.get(position).getParseFile("Item_Image"));
			itemImageView.loadInBackground();
			System.out.println(ItemList.get(position).getParseFile("Item_Image"));
			itemprice.setText(ItemList.get(position).getNumber("Item_Price").toString()+".00$");
			itemcheck.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean arg1) {
					// TODO Auto-generated method stub
					int id = arg0.getId();
					if(id==itemcheck.getId()){
						if(itemcheck.isChecked()){
							SelectedItem.add(ItemList.get(position));
							System.out.println(SelectedItem.size());
							price=price+(Integer)ItemList.get(position).getNumber("Item_Price");
						}
						else{
							SelectedItem.remove(ItemList.get(position));
							System.out.println(SelectedItem.size());
							price=price-(Integer)ItemList.get(position).getNumber("Item_Price");
						}
					}

				}
			});

		}
		

		if(ShowItems.equals("ShowItemsSupplier")){
			itemsDisplayLayoutView  =    
					(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);                               
			row = itemsDisplayLayoutView.inflate(R.layout.itemdisplaylayout_supplierselection, parent,false);

			com.parse.ParseImageView itemImage=(com.parse.ParseImageView)row.findViewById(R.id.ItemImageSupplier);
			TextView itemName=(TextView)row.findViewById(R.id.ItemNameSupplier);
			TextView itemQty=(TextView)row.findViewById(R.id.ItemQtySupplier);
			TextView supplierCount=(TextView)row.findViewById(R.id.SupplierCount);

			ParseQuery<ParseObject> supplierQuery=new ParseQuery<ParseObject>("Supplier_Items");
			supplierQuery.whereEqualTo("Item_id", ItemList.get(position).getString("Item_Id"));
			ParseQuery<ParseObject> orderedItemDetailsQuery= new ParseQuery<ParseObject>("Items");
			try {
				ArrayList<ParseObject> items=(ArrayList<ParseObject>) orderedItemDetailsQuery.find();
				for(int i=0;i<items.size();i++){
					if(items.get(i).getObjectId().toString().equals(ItemList.get(position).getString("Item_Id"))){
						itemDetails=items.get(i);
						System.out.println("In for loop:"+items.get(i).getObjectId().toString());
					}
				}
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}


			itemImage.setParseFile(itemDetails.getParseFile("Item_Image"));
			itemImage.loadInBackground();
			itemName.setText(itemDetails.getString("Item_Name"));
			itemQty.setText("Qty:"+ItemList.get(position).getNumber("OrderItem_Qty").toString());
			try {
				supplierCount.setText("Suppliers("+String.valueOf(supplierQuery.count())+")");
				supplierCount.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(context,SupplierDisplayActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("ItemId", ItemList.get(position).getString("Item_Id"));
						intent.putExtra("OrderItemId",ItemList.get(position).getObjectId().toString());
						context.startActivity(intent);
					}
				});
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (row);

	}

}
