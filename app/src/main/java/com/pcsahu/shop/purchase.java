package com.pcsahu.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class purchase extends AppCompatActivity {
    RelativeLayout NameRateLayout;
    RelativeLayout AddItemLayout;
    Intent intent;

    EditText product_name_ET,rate_ET,individual_wt_ET;

    AppCompatButton fixNameRate,additem,makebill,fetch;
    ListView lv;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<ContactModel> fetchedArray;
    fetchdataAdapter fetchdataAdapter;

    int p;

    String product_name,RATE;
    CustomerDb customerdb;
    ContactModel contactModel;
    Gson gson = new Gson();



    RecyclerView rv;


    AppCompatButton home;
    RelativeLayout homelayout;
    SharedPreferenceClass sharedPreferenceClass;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_purchase );


        intent = getIntent();
        product_name_ET = findViewById(R.id.productname);
        rate_ET = findViewById(R.id.productrate);
        fixNameRate = findViewById(R.id.addbtn);
        NameRateLayout = findViewById( R.id.NamePriceLayout );
        individual_wt_ET = findViewById( R.id.individualweight );
        lv = findViewById( R.id.listview );
        additem = findViewById( R.id.add_item );
        individual_wt_ET = findViewById( R.id.individualweight );
        AddItemLayout = findViewById( R.id.AddItemLayout );
        makebill = findViewById( R.id.makeBill );
        sharedPreferenceClass = new SharedPreferenceClass( this );

//        homelayout  =findViewById( R.id.homelayout );
//        rv  = findViewById( R.id.recycler_v);
//        home = findViewById( R.id.homeBtn);












        //datas





        fixNameRate.setOnClickListener( new View.OnClickListener() {




            @Override
            public void onClick(View v) {






                product_name = product_name_ET.getText().toString();
                RATE = rate_ET.getText().toString();
                if(!product_name.isEmpty() && !RATE.isEmpty()){
                    Toast.makeText( purchase.this, "NOW YOU CAN ADD ITEMS", Toast.LENGTH_SHORT ).show();
                    NameRateLayout.setVisibility(View.GONE );


                    AddItemLayout.setVisibility( View.VISIBLE );
                    hidekeyboard( v,purchase.this );






                }
                else{
                    Toast.makeText( purchase.this, "ENTER THE FIELD DETAILS", Toast.LENGTH_SHORT ).show();
                }
            }



        } );

        additem.setOnClickListener( new View.OnClickListener() {




            @Override


            public void onClick(View v) {

                String currweight = individual_wt_ET.getText().toString();
                individual_wt_ET.setText( "" );
                if(!currweight.isEmpty()){
                    arrayList.add( currweight );
                    setInListvew();
                }
                else{
                    Toast.makeText( purchase.this, "Enter valid Weight", Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        makebill.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerdb = new CustomerDb( purchase.this);
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String todaysdate = dateFormat.format(currentDate);
                String cust_name = intent.getStringExtra( "customername" );
                String cust_add = intent.getStringExtra( "customerAddress" );
                String get_phone_no = intent.getStringExtra( "phoneNumber" );

                float rate = Float.parseFloat( RATE );
                float totalkgs = 0;
                float finalCost = 0;
                for(int i = 0;i<arrayList.size();i++){
                    String temp = arrayList.get( i );
                    float tmp = Float.parseFloat( temp );
                    totalkgs = totalkgs + tmp;


                }

                finalCost = totalkgs*rate;

                float current_price = sharedPreferenceClass.getValue( "costprice" );
                current_price+=finalCost;
                sharedPreferenceClass.setValue( "costprice",current_price );

                customerdb.addCustomer(todaysdate,cust_name,cust_add,get_phone_no,product_name,arrayList,totalkgs,rate,finalCost);

                AddItemLayout.setVisibility( View.GONE );



                String jsonString = gson.toJson(arrayList);

                contactModel  = new ContactModel(  todaysdate,cust_name,cust_add,get_phone_no,product_name,jsonString,totalkgs,rate,finalCost);

                ArrayList<ContactModel> contactList = new ArrayList<>();
                contactList.add( contactModel );
                homelayout = findViewById( R.id.homelayout );
// Populate the contactList with data

//                Intent intent = new Intent(purchase.this, individualBill.class);
//                intent.putExtra("contactList", contactList);
//                startActivity(intent);
                homelayout.setVisibility( View.VISIBLE );

                rv =  findViewById( R.id.recycler_v );

                rv.setLayoutManager( new LinearLayoutManager( purchase.this ) );
                rv.setHasFixedSize( true );

                fetchdataAdapter = new fetchdataAdapter(purchase.this,contactList);
                rv.setAdapter( fetchdataAdapter );
                arrayList.clear();















            }
        } );

        home = findViewById( R.id.homeBtn );


        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(purchase.this,MainActivity.class);
                finish();
            }
        } );


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                


                showDeleteDialog(position);





                
                
            }
        });















    }

    private void showDeleteDialog(int position) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate( R.layout.custom_dialog_layout,null  );




        final AlertDialog dialog = new AlertDialog.Builder(purchase.this)
                .setTitle( "Want To Delete this Item ?" )
                .setPositiveButton( "Yes",null )
                .setNegativeButton( "No",null )
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        arrayList.remove(position);
                        setInListvew();
                        dialog.dismiss();

                    }
                });
            }
        });

        dialog.show();

    }

    private void setInListvew() {

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);

        lv.setAdapter( adapter2 );




    }

    private void hidekeyboard(View view,Activity activity){
        try{
            InputMethodManager imm = (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}