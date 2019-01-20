package com.rohan.admin.loginapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Training_Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training__admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    public void training(View view)
    {
        String button_text;

        button_text = ((Button) view).getText().toString();
        if (button_text.equals("Training Activity"))
        {
            Intent intent = new Intent(this, Activity_update.class);
            startActivity(intent);
        }
        else if (button_text.equals("Enrolled Student List"))
        {
            Intent intent = new Intent(this, EnrolledStudentList.class);
            startActivity(intent);
        }

        else if (button_text.equals("Training Status Update"))
        {
            Intent intent = new Intent(this, TrainingStatusUpdate.class);
            startActivity(intent);
        }
        else if (button_text.equals("Activity Attendance"))
        {
            Intent intent = new Intent(this, Activity_Attendance.class);
            startActivity(intent);
        }



    }

}
