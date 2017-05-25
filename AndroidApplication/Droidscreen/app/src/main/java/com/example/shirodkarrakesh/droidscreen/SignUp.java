package com.example.shirodkarrakesh.droidscreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by shirodkarrakesh on 2/6/17.
 */

public class SignUp extends AppCompatActivity {

    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        MyDb = new DatabaseHelper(this);

        Button button1 = (Button) findViewById(R.id.button_signup);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting value from layout
                EditText name = (EditText)findViewById(R.id.name);
                EditText username = (EditText)findViewById(R.id.username);
                EditText email = (EditText)findViewById(R.id.email);
                EditText password = (EditText)findViewById(R.id.password);


                //inserting values to database
        boolean InsertResult = MyDb.DataInsert(name.getText().toString(),username.getText().toString(),
                email.getText().toString(),password.getText().toString());


                if (InsertResult == true)

                {  Toast.makeText(getApplicationContext(),
                            "Data inserted !!", Toast.LENGTH_SHORT).show();
                }
                    else
                {Toast.makeText(getApplicationContext(),
                            "Data insert fail!!", Toast.LENGTH_SHORT).show();
                }


                // after insert go to login page
               finish();

            }


        });


    }
}
