package com.webconsole.harshmathur.multilock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


import java.util.List;

/**
 * Created by Harsh Mathur on 18-09-2016.
 */
public class AccountInfo extends AppCompatActivity {

    private TextView namedisp;
    private TextView emaildisp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info);
        namedisp = (TextView) findViewById(R.id.user_name2);
        emaildisp = (TextView) findViewById(R.id.user_email2);

        Intent i = getIntent();
        String name, email;
        name = i.getStringExtra("p_name");   //TODO fix this null value problem
        email = i.getStringExtra("p_email");
        Log.d("AUTH","NAME ACCEPTED "+name);
        namedisp.setText(name);
        emaildisp.setText(email);
    }
}