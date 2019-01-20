package com.rohan.admin.loginapp1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Placed_student_details extends AppCompatActivity {

    TextView SEARCH,text1,compname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_student_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        compname = (TextView) findViewById(R.id.compname);


        text1 = (TextView) findViewById(R.id.date);



        SEARCH = (TextView)findViewById(R.id.search);
        SEARCH.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (text1.getText().toString().equals("") ||compname.getText().toString().equals(""))

                    {
                        Snackbar.make(v, "Please fill details", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    else
                    {
                        Intent intent = new Intent(Placed_student_details.this, ListofStudentsPlaced.class);
                        intent.putExtra("companyname",compname.getText().toString());
                        intent.putExtra("date",text1.getText().toString());
                        startActivity(intent);

                    }
                }
                else
                {

                    Snackbar.make(v, "Please connect to internet..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });



    }
}
