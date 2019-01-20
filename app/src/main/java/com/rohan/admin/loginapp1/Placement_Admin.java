package com.rohan.admin.loginapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Placement_Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement__admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void placement(View view)
    {
        String button_text;

        button_text = ((Button) view).getText().toString();
        if (button_text.equals("company information"))
        {
            Intent intent = new Intent(this, Company_update.class);
            startActivity(intent);
        }
        else if(button_text.equals("placed student details"))
        {
            Intent intent = new Intent(this, Placed_student_details.class);
            startActivity(intent);
        }
        else if(button_text.equals("eligible criteria"))
        {
            Intent intent = new Intent(this, Eligible_criteria.class);
            startActivity(intent);
        }
        else if(button_text.equals("Placement attendence status"))
        {
            Intent intent = new Intent(this, PlacementStatusUpdate.class);
            startActivity(intent);

        }

        else if(button_text.equals("Placement attendence"))
        {
            Intent intent = new Intent(this, Placement_Attendance.class);
            startActivity(intent);

        }
        else if(button_text.equals("update placed student details"))
        {
            Intent intent = new Intent(this, Update_Placed_Student_Details.class);
            startActivity(intent);
        }

    }
}
