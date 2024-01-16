package com.pcsahu.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class sellFragment extends AppCompatActivity {
    EditText sellET;
    AppCompatButton sell;

    SharedPreferenceClass sharedPreferenceClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sell_fragment );

        sellET = findViewById( R.id.sell );
        sell = findViewById( R.id.sellbtn );
        sharedPreferenceClass = new SharedPreferenceClass( this );

        sell.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selltext = sellET.getText().toString();
                if(!selltext.isEmpty()){
                    float currentsp = Float.parseFloat( selltext );
                    float totalsp = sharedPreferenceClass.getValue( "sellingprice" );
                    float finalsp = totalsp + currentsp;
                    sharedPreferenceClass.setValue( "sellingprice",finalsp );
                    Intent intent = new Intent(sellFragment.this,MainActivity.class);
                    startActivity( intent );

                }
                else{
                    Toast.makeText( sellFragment.this,"Enter Selling Price" ,Toast.LENGTH_SHORT).show();

                }


            }
        } );


    }
}