package com.pcsahu.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText cust_name,cust_phone,cust_add;
    AppCompatButton add_cust,allcust;

    TextView cp,sp,nt;

    ImageView mainImg;
    ImageButton refresh,bydate;
    SharedPreferenceClass sharedPreferenceClass;



    RecyclerView rv;

    CustomerDb cd;
    fetchdataAdapter fetchdataAdapter;

    ArrayList<ContactModel> mainlist;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //customer details


        add_cust = findViewById( R.id.add_customer );
        allcust = findViewById( R.id.allCustomer );
        cp = findViewById( R.id.costprice );
        sp = findViewById( R.id.sellingprice );
        mainImg = findViewById( R.id.nullimg );
        sharedPreferenceClass = new SharedPreferenceClass( this );
        float current_cp = sharedPreferenceClass.getValue( "costprice" );
        cp.setText("₹ "+ String.valueOf( current_cp ) );
        float current_sp = sharedPreferenceClass.getValue( "sellingprice" );
        sp.setText( "₹ "+current_sp );
        cd = new CustomerDb( this );
        refresh = findViewById( R.id.refresh );
        bydate = findViewById( R.id.date );
        nt=findViewById( R.id.notransation ) ;


        mainlist = new ArrayList<>();
        rv = findViewById( R.id.mainRecycleView );

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todaysdate = dateFormat.format( currentDate );
        mainlist = cd.fetchDataByDate(todaysdate);

        if(!mainlist.isEmpty()){
            mainImg.setVisibility( View.GONE );
            rv.setVisibility( View.VISIBLE );
            rv.setLayoutManager( new LinearLayoutManager( this ) );
            rv.setHasFixedSize( true );
            fetchdataAdapter = new fetchdataAdapter(  this,mainlist);
            rv.setAdapter( fetchdataAdapter );
        }






        add_cust.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        } );

        allcust.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,fetchdatafile.class);
                startActivity( intent );
            }
        } );

        refresh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,MainActivity.class);
                startActivity( intent );
                finish();
            }
        } );

        bydate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        } );

        sp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,sellFragment.class);
                startActivity( intent );
                finish();
            }
        } );




    }



    private void showAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate( R.layout.custom_dialog_layout,null  );

        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setView( alertLayout )
                .setTitle( "Add Customer" )
                .setPositiveButton( "Add",null )
                .setNegativeButton( "Cancel",null )
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        cust_name = alertLayout.findViewById(R.id.customer_name);

                        cust_phone = alertLayout.findViewById(R.id.phonenumber);
                        cust_add = alertLayout.findViewById( R.id.customer_address );

                        String get_phone_no = cust_phone.getText().toString();


                        String custname = cust_name.getText().toString();
                        String custadd = cust_add.getText().toString();

                        if(!custname.isEmpty() && !custadd.isEmpty()){

                            dialog.dismiss();
                            Toast.makeText( MainActivity.this, "Customer added successfully", Toast.LENGTH_SHORT ).show();
                            Intent intent = new Intent(MainActivity.this,purchase.class);
                            intent.putExtra( "customername",custname );
                            intent.putExtra( "customerAddress",custadd );
                            intent.putExtra( "phoneNumber",get_phone_no );
                            startActivity( intent );


                        }
                        else{
                            Toast.makeText( MainActivity.this, "Please Write full details", Toast.LENGTH_SHORT ).show();
                        }





                    }
                });
            }
        });



        dialog.show();






    }


    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String selectedDate = dateFormat.format(calendar.getTime());

                        mainlist.clear();
                        mainlist = cd.fetchDataByDate(selectedDate);


                        if(!mainlist.isEmpty()){

                            mainImg.setVisibility( View.GONE );
                            rv.setVisibility( View.VISIBLE );
                            rv.setLayoutManager( new LinearLayoutManager( MainActivity.this) );
                            rv.setHasFixedSize( true );
                            fetchdataAdapter = new fetchdataAdapter(  MainActivity.this,mainlist);
                            rv.setAdapter( fetchdataAdapter );
                        }else{
                            mainImg.setVisibility( View.GONE );
                            rv.setVisibility( View.GONE );
                            nt.setVisibility( View.VISIBLE );
                            nt.setText("No Transaction on "+selectedDate);
                        }
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    protected void onResume() {

        super.onResume();

        ArrayList<ContactModel> mainlist;
        mainlist = new ArrayList<>();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todaysdate = dateFormat.format( currentDate );
        mainlist = cd.fetchDataByDate(todaysdate);

        if(!mainlist.isEmpty()){

            mainImg.setVisibility( View.GONE );
            rv.setVisibility( View.VISIBLE );
            mainImg.setVisibility( View.GONE );
            rv.setVisibility( View.VISIBLE );
            rv.setLayoutManager( new LinearLayoutManager( this ) );
            rv.setHasFixedSize( true );
            fetchdataAdapter = new fetchdataAdapter(  this,mainlist);
            rv.setAdapter( fetchdataAdapter );
        }


    }


}