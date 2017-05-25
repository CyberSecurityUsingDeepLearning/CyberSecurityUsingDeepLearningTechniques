package com.example.shirodkarrakesh.droidscreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shirodkarrakesh on 2/5/17.
 */

public class dashboard_profile extends AppCompatActivity{

    //public final static String id="id";
    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MyDb = new DatabaseHelper(this);

        Button NewScan = (Button) findViewById(R.id.scan_button);

        Button Refresh = (Button) findViewById(R.id.refresh_button);

        ShowList();

        NewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     // Create a new intent to open the {@link App List}
                    Intent nameIntent = new Intent(dashboard_profile.this, app_list.class);

                    // Start the new activity
                    startActivity(nameIntent);

                    Toast.makeText(getApplicationContext(),
                            "New Scan !!", Toast.LENGTH_SHORT).show();
                }


        });

        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowList();

                Toast.makeText(getApplicationContext(),
                        "Refreshing !!", Toast.LENGTH_SHORT).show();
            }


        });


        ListView listView = (ListView) findViewById(R.id.scan_list);


        //listener on listveiw
        listView.setOnItemClickListener (new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String selectedFromList =(String) (parent.getItemAtPosition(position));
                //String value = (String)parent.getItem(position);

                ViewGroup v=(ViewGroup) view;


                TextView vname=(TextView) v.findViewById(R.id.list_app_name);

                String ids= vname.getText().toString();

                // Create a new intent to open the {@Sign up page}
                Intent nameIntent = new Intent(dashboard_profile.this, ResultDetail.class);

                nameIntent.putExtra("id",ids);

                // Start the new activity
                startActivity(nameIntent);

            }
            });


    }


    public void ShowList()
    {

        try{

            Cursor cursor=MyDb.GetRows();


        if (cursor.getCount() !=0) {

            String[] ColumnName = new String[]{MyDb.col2_2, MyDb.col2_3, MyDb.col2_4};
            int[] viewId = new int[]{R.id.list_app_name, R.id.list_app_result, R.id.list_date};
            SimpleCursorAdapter MyCursor;

            MyCursor = new SimpleCursorAdapter(getBaseContext(), R.layout.custom_dashboard_list, cursor, ColumnName, viewId, 0);

            ListView MyList = (ListView) findViewById(R.id.scan_list);

            MyList.setAdapter(MyCursor);

        }

        }
        catch (Exception e)
        {
            System.out.println("Error while fetching data\n" + e.getMessage());
            e.printStackTrace();
        }

    }

}
