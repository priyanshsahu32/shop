package com.pcsahu.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class fetchdatafile extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ContactModel> arrayList;
    CustomerDb customerDb;
    fetchdataAdapter fetchdataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fetchdatafile );
        customerDb = new CustomerDb( this );
        arrayList = new ArrayList<ContactModel>();
        arrayList = customerDb.fetchContact();

        recyclerView = findViewById( R.id.recycler_view );


        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setHasFixedSize( true );

        fetchdataAdapter = new fetchdataAdapter( this,arrayList);
        recyclerView.setAdapter( fetchdataAdapter );


    }
}