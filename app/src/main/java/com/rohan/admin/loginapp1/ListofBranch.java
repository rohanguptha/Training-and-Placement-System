package com.rohan.admin.loginapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListofBranch extends AppCompatActivity {

    ListView listView ;
    TextView text1,text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_branch);
        listView = (ListView) findViewById(R.id.listView);
        String values[] = {"CSE", "ECE", "IT", "CIVIL", "MECH", "EEE", "EIE"," "};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.branch_display,R.id.branch,values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ItemList());


        text1 = (TextView) findViewById(R.id.bactivity);
        String message = getIntent().getStringExtra("activity");
        text1.setText(message);

        text2 = (TextView) findViewById(R.id.bdate);
        String message1 = getIntent().getStringExtra("date");
        text2.setText(message1);



    }

    private class ItemList implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ViewGroup viewg=(ViewGroup)view;
            TextView textv=(TextView)viewg.findViewById(R.id.branch);

            Intent intent = new Intent(ListofBranch.this,ListofStudents.class);
            intent.putExtra("activity",text1.getText().toString());
            intent.putExtra("date", text2.getText().toString());
            intent.putExtra("branch", textv.getText().toString());
            startActivity(intent);
           // Toast.makeText(getApplicationContext(),textv.getText().toString(), Toast.LENGTH_LONG).show();

        }
    }


}