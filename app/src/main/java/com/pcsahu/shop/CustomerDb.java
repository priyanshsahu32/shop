package com.pcsahu.shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class CustomerDb extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MyShopDatabase";
        private static final String Table = "Customer";

        private static final String date = "Date";
        private static final String customer_name = "Customer_Name";
        private static final String customer_address = "Customer_Address";
        private static final String phone_no = "Phone_no";
        private static final String product_name = "product_name";
        private static final String items = "Items";
        private static final String total_kgs = "Total_weight";
        private static final String rate = "Rate";
        private static final String total_amt = "Total_Amount";

        private Context context;





    public CustomerDb(Context context) {
        super( context, DATABASE_NAME, null, 1 );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + Table + " ("
                + date + " TEXT,"
                + customer_name + " TEXT,"
                + customer_address + " TEXT,"
                + phone_no + " TEXT,"
                + product_name + " TEXT,"
                + items + " TEXT,"
                + total_kgs + " REAL,"
                + rate + " REAL,"
                + total_amt + " REAL"
                + ")";

        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS "+Table );
        onCreate( db );

    }

    public void addCustomer(String inputDate, String inputCustomerName, String inputCustomerAddress , String inputPhoneNo,
                            String inputProductName, ArrayList<String> inputItems, float inputTotalKgs,
                            float inputRate, float inputTotalAmt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(date, inputDate);

        values.put(customer_name, inputCustomerName);
        values.put(customer_address,inputCustomerAddress);
        values.put(phone_no, inputPhoneNo);
        values.put(product_name, inputProductName);
        values.put(items, new Gson().toJson(inputItems));
        values.put(total_kgs, inputTotalKgs);
        values.put(rate, inputRate);
        values.put(total_amt, inputTotalAmt);

        Log.d("VALUES : ",values.toString());
        long result = db.insert(Table, null, values);

        Log.d("CustomerDb", "Insert result: " + result);

        db.close();

        if (result != -1) {
            showToast("Customer added successfully!");
        } else {
            showToast("Error adding customer. Please try again.");
        }


    }

    private void showToast(String message) {
        // Show a toast message
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public ArrayList<ContactModel> fetchContact(){
        ArrayList<ContactModel> ans = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery( "SELECT * FROM "+Table,null  );
        while(c.moveToNext()){
            ContactModel model= new ContactModel(c.getString( 0 ),c.getString( 1 ),c.getString( 2 ),c.getString( 3 ),c.getString( 4 ),c.getString( 5 ),c.getFloat( 6 ),c.getFloat( 7 ),c.getFloat( 8 ));

            ans.add(model);



        }
        return ans;

    }

    public ArrayList<ContactModel> fetchDataByDate(String targetDate) {
        ArrayList<ContactModel> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {date, customer_name, customer_address, phone_no, product_name, items, total_kgs, rate, total_amt};
        String selection = date + "=?";
        String[] selectionArgs = {targetDate};

        Cursor cursor = db.query(Table, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            ContactModel model = new ContactModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getFloat(6),
                    cursor.getFloat(7),
                    cursor.getFloat(8)
            );
            result.add(model);
        }

        cursor.close();
        db.close();

        return result;
    }


}
