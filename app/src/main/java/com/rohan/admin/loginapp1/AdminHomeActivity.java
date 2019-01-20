package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdminHomeActivity extends AppCompatActivity {
    TextView textView;
    EditText Email,password;
    Activity activity;
   TextView logout1;
    Button notifyid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        textView = (TextView)findViewById(R.id.adminwelcome_txt);
        String message = getIntent().getStringExtra("message");
        textView.setText(message);


        logout1 = (TextView)findViewById(R.id.logout_admin);
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        notifyid = (Button) findViewById(R.id.notifyid);
        notifyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
Intent i=new Intent(AdminHomeActivity.this,AdminAddNotifications.class);
                startActivity(i);

            }
        });


    }





    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminHomeActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Logout?");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to logout?");
        // Setting Icon to Dialog

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

    public void admin(View view)
    {
        String button_text;

        button_text = ((Button) view).getText().toString();
        if (button_text.equals("Student Profile"))
        {
            Intent intent = new Intent(this, Student_Profile_Admin.class);
            startActivity(intent);
        }
        else if(button_text.equals("Training"))
        {
            Intent intent = new Intent(this, Training_Admin.class);
            startActivity(intent);
        }
        else if(button_text.equals("Placement"))
        {
            Intent intent = new Intent(this,Placement_Admin.class);
            startActivity(intent);
        }


    }
}
