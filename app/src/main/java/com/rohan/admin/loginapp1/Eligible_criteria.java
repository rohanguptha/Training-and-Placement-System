package com.rohan.admin.loginapp1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Eligible_criteria extends AppCompatActivity{
    CheckBox c,d,e,f,g,h,i;
    Button b;
    TextView final_text, SELECT,ssc,inter,btech,rank,compname;
    ArrayList<String> selection = new ArrayList<>();
    private int STORAGE_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligible_criteria);

        final_text = (TextView)findViewById(R.id.final_result);
        ssc = (TextView)findViewById(R.id.ssc);
        inter = (TextView)findViewById(R.id.inter);
        btech = (TextView)findViewById(R.id.btech);
        rank = (TextView)findViewById(R.id.rank);
        compname = (TextView)findViewById(R.id.compname);
        final_text.setEnabled(false);

        SELECT = (TextView)findViewById(R.id.csebutton);
        SELECT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    if (ssc.getText().toString().equals("") || inter.getText().toString().equals("") || btech.getText().toString().equals(""))

                    {
                        Snackbar.make(v, "Please fill details..", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    else
                    {

                        Intent intent = new Intent(Eligible_criteria.this, LIST.class);
                        intent.putExtra("branch", final_text.getText().toString());
                        intent.putExtra("ssc", ssc.getText().toString());
                        intent.putExtra("inter", inter.getText().toString());
                        intent.putExtra("btech", btech.getText().toString());
                        intent.putExtra("rank", rank.getText().toString());
                        intent.putExtra("compname",compname.getText().toString());
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



        if(isReadStorageAllowed()){
            //If permission is already having then showing the toast
            Toast.makeText(Eligible_criteria.this,"You already have the permission",Toast.LENGTH_LONG).show();
            //Existing the method with return
            return;
        }

        //If the app has not the permission then asking for the permission
        requestStoragePermission();


    }


    ////////////////
    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
              //  Toast.makeText(this,"Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }




    /////////////////





    public void selectItem(View view)
    {         String final_branch_selection = "";


        boolean checked =  ((CheckBox) view).isChecked();
        switch (view.getId())
        {
            case R.id.csebox1:
                if(checked)
                {selection.add("CSE");}
                else {selection.remove("CSE");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);

                break;
            case R.id.ecebox1:
                if(checked)
                {selection.add("ECE");}
                else {selection.remove("ECE");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection  + " ";
                }

                final_text.setText(final_branch_selection);

                break;
            case R.id.mechbox1:
                if(checked)
                {selection.add("MECH");}
                else {selection.remove("MECH");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);

                break;
            case R.id.eiebox1:
                if(checked)
                {selection.add("EIE");}
                else {selection.remove("EIE");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);

                break;
            case R.id.eeebox1:
                if(checked)
                {selection.add("EEE");}
                else {selection.remove("EEE");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);
                final_text.setEnabled(true);

                break;
            case R.id.itbox1:
                if(checked)
                {selection.add("IT");}
                else {selection.remove("IT");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);
                final_text.setEnabled(true);

                break;
            case R.id.civilbox1:
                if(checked)
                {selection.add("CIVIL");}
                else {selection.remove("CIVIL");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);
                final_text.setEnabled(true);

                break;
            case R.id.male:
                if(checked)
                {selection.add("MALE");}
                else {selection.remove("MALE");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);
                final_text.setEnabled(true);

                break;
            case R.id.female:
                if(checked)
                {selection.add("FEMALE");}
                else {selection.remove("FEMALE");}
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection + Selection + " ";
                }

                final_text.setText(final_branch_selection);
                final_text.setEnabled(true);

                break;

        }
    }
    public void finalSelection (View view)
    {
        String final_branch_selection = "";

        for(String Selection : selection)
        {
            final_branch_selection = final_branch_selection + Selection + " ";
        }

        final_text.setText(final_branch_selection);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}