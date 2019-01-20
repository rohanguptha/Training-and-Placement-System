package com.rohan.admin.loginapp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Student_verify extends AppCompatActivity {
    TextView textView,textView1;
    private TextView btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_verify);
        textView = (TextView) findViewById(R.id.student_verify);
        String message = getIntent().getStringExtra("message");
        textView.setText(message);
        textView1 = (TextView) findViewById(R.id.verify_rollno);
        String message1 = getIntent().getStringExtra("message1");
        textView1.setText(message1);



        btnLogout = (TextView)findViewById(R.id.btnLogoutstu);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    public void showGreeting(View view)
    {
        String button_text;

        button_text = ((Button) view).getText().toString();
        if (button_text.equals("REGISTER"))
        {
            Intent intent = new Intent(this,Register_scrolling.class);
            intent.putExtra("message1",textView1.getText().toString());

            startActivity(intent);

        }

    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Student_verify.this);
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

}
