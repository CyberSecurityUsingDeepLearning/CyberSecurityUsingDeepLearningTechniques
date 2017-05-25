package com.example.shirodkarrakesh.droidscreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;
import static android.R.attr.password;
import static com.example.shirodkarrakesh.droidscreen.R.id.name_text;
import static com.example.shirodkarrakesh.droidscreen.R.id.username;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Helper = new DatabaseHelper(this);

        Button login = (Button) findViewById(R.id.button);

            login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create a new intent to open the {@link Profile page}
                    Intent nameIntent = new Intent(MainActivity.this, dashboard_profile.class);

                    //getting values entered from user input

                    EditText userName = (EditText)findViewById(R.id.name_text);
                    EditText password = (EditText)findViewById(R.id.password_text);

                    String username_entered = userName.getText().toString();
                    String password_entered = password.getText().toString();

                    // getting the Password from database for the provided username

                    String db_password = Helper.getpass(username_entered);


                    Log.d("Password for use", db_password);


                    if(password_entered.equals(db_password )) {

                        // Start the new activity
                        startActivity(nameIntent);
                        Toast.makeText(getApplicationContext(),
                                "Congratulations!! Log In successful !!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Incorrect Username or Password!!", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        Button SignUp = (Button) findViewById(R.id.button2);

        SignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@Sign up page}
                Intent nameIntent = new Intent(MainActivity.this, SignUp.class);

                    // Start the new activity
                    startActivity(nameIntent);
                }


        });


    }

}
