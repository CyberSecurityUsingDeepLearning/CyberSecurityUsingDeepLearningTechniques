package com.example.shirodkarrakesh.droidscreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static android.R.attr.id;
import static android.R.attr.path;

/**
 * Created by shirodkarrakesh on 4/24/17.
 */

public class ResultDetail extends AppCompatActivity {

    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MyDb = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_details);


        String column4=null;
        String ids = getIntent().getStringExtra("id");
        TextView TextName = (TextView) findViewById(R.id.detail_name);
        TextView TextStatus = (TextView) findViewById(R.id.detail_status);
        TextView TextDate= (TextView) findViewById(R.id.detail_date);
        TextView TextDetails= (TextView) findViewById(R.id.detail);

        //setting all details:
        TextName.setText("App Name: "+ids);

        try{
            Cursor cursor=MyDb.GetDetailRows(ids);

      if (cursor.getCount()!=0) {

              String column2 = cursor.getString(cursor.getColumnIndex("scan_status"));
              String column3 = cursor.getString(cursor.getColumnIndex("scan_date"));
              column4 = cursor.getString(cursor.getColumnIndex("scan_log"));


              TextStatus.setText("Scan Status: "+column2);
              TextDate.setText("Scan Date: "+column3);

               // System.out.println(column4);

        }

        }
        catch (Exception e)
        {
        System.out.println("Error while fetching scan detail data\n" + e.getMessage());
        e.printStackTrace();
        }

                //calling function to extract details:
                String val = ExtractDetail(column4);

                //setting log to detail screen
                TextDetails.setText(val);



    }

    public String ExtractDetail(String val) {

        String Total=null;
        final String str = val;
        final Pattern pattern = Pattern.compile("[(',\\s\\[\\]\")]");

        if (str.length() != 0) {

            int n = str.indexOf(":");

            if (n != 0) {
                n = n + 2;

            }

            if (str.charAt(n) == '0') {
                Total=null;

            }
            else if (str.charAt(n) == '1'){

                String[] strarr = str.split(":" , 4);
                String stringa = strarr[2];

                String rp=stringa.replace(" ","");
                rp=rp.replace("[","");
                rp=rp.replace("]",",");
                rp=rp.replace("(","");
                rp=rp.replace(")","");
                rp=rp.replace("'","");


                final String[] result = pattern.split(rp);

                List<String> list = new ArrayList<String>(Arrays.asList(result));

                for (int index=0; index < list.size(); index++)
                {
                    String temp=list.get(index);
                    if (temp.contains(".apk") || temp.contains("output"))
                    {
                        list.remove(index);
                    }



                }

                for (int index=0; index < list.size(); index++)
                {
                    try
                    { //int i = Integer.parseInt(list.get(index));
                        double i= Double.parseDouble(list.get(index));

                        double roundOff = Math.round(i * 100.0);


                        list.set(index,Double.toString(roundOff)+"% -");

                    }

                    catch(NumberFormatException er)
                    {
                        list.set(index,list.get(index)+"\n ");}
                }

                String str_array = Arrays.toString(list.toArray());

                str_array=str_array.replace(",","");
                str_array=str_array.replace("[","");
                str_array=str_array.replace("]","");
                str_array=" "+str_array;
                str_array="Please find below Malware family Prediction:\n"+str_array;
                System.out.println(str_array);
                Total=str_array;

            }
        }

        return Total;


    }
}
