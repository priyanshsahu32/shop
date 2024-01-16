package com.pcsahu.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class fetchdataAdapter extends RecyclerView.Adapter<fetchdataAdapter.MyViewHolder> {

    ArrayList<ContactModel> arrayList;
    Context context;



    public fetchdataAdapter(Context context, ArrayList<ContactModel> arrayList) {

        this.arrayList = arrayList;
        this.context = context;


    }

    @NonNull
    @Override
    public fetchdataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate( R.layout.detailholder,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder( view );
//        int[] androidcolors = view.getResources().getIntArray( R.array.androidcolors );
//
//        int randomColor = androidcolors[new Random().nextInt(androidcolors.length)];
//
//
//        myViewHolder.accordian_title.setCardBackgroundColor( randomColor );





        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull fetchdataAdapter.MyViewHolder holder, int position) {

        ContactModel contact = arrayList.get(position);

        // Set the data to the views in the ViewHolder
        holder.productname.setText(contact.getProduname());
        holder.date.setText(contact.getdate());
        holder.custname.setText(contact.getcustname());
        holder.rate.setText(String.valueOf(contact.getRate()));
        holder.phoneno.setText(contact.getphno());
        holder.weight.setText(String.valueOf(contact.gettotal_kgs()));
        holder.items.setText(contact.getItem());
        holder.paidamt.setText(String.valueOf(contact.totalAmt()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productname,date,custname,rate,phoneno,weight,items,paidamt;
        CardView accordian_title;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            productname = (TextView) itemView.findViewById( R.id.productnm );

            date = (TextView) itemView.findViewById( R.id.date);
            custname = (TextView) itemView.findViewById( R.id.name );
            rate = (TextView) itemView.findViewById( R.id.rate );
            phoneno = (TextView) itemView.findViewById( R.id.phone );
            weight = (TextView) itemView.findViewById( R.id.totalweight );
            items = (TextView) itemView.findViewById( R.id.items );
            paidamt = (TextView) itemView.findViewById( R.id.totalamount );
            accordian_title = (CardView) itemView.findViewById( R.id.accordian_title );



        }
    }
}