package com.rohan.admin.loginapp1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class TrainingActivity_admin_info extends AppCompatActivity {

    public String info_url = "http://dragon321.esy.es/training_info.php";
    TextView text1,text2,text3,text4,text5;
    ListView list1;
    Button pbutton;
    String json;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;


    private static final String TAG_RESULTS = "server_response";
    private static final String TAG_INFO = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_activity_admin_info);
        text1 = (TextView) findViewById(R.id.activityname);
        String message = getIntent().getStringExtra("activity");
        text1.setText(message);
        text2 = (TextView) findViewById(R.id.docname);
        String message1 = getIntent().getStringExtra("pdfname");
        text2.setText(message1);


        list1 = (ListView) findViewById(R.id.list1);
        personList = new ArrayList<HashMap<String, String>>();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            activityInfo v = new activityInfo(this);
            v.execute("info", text1.getText().toString());

        } else {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


         pbutton= (Button)findViewById(R.id.button);
        pbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String pdf = "http://dragon321.esy.es/documents/"+text2.getText().toString(); //YOUR URL TO PDF

                        Intent intent = new Intent(TrainingActivity_admin_info.this,pdfview.class);
                        intent.putExtra("url",pdf);
                        startActivity(intent);

                    }
        });


    }

    public class activityInfo extends AsyncTask<String, Void, String> {


        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        android.support.v7.app.AlertDialog.Builder builder;

        public activityInfo(Context ctx) {

            this.ctx = ctx;
            activity = (Activity) ctx;
        }


        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals("info")) {
                try {
                    URL url = new URL(info_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, ("UTF-8")));
                    String activity;
                    activity = params[1];

                    String data = URLEncoder.encode("activity", "UTF-8") + "=" + URLEncoder.encode(activity, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    httpURLConnection.disconnect();
                    Thread.sleep(0);
                    return stringBuilder.toString().trim();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            return null;

        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String tinfo = c.getString(TAG_INFO);


                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put(TAG_INFO, tinfo);

                    personList.add(persons);
                }

                ListAdapter adapter = new SimpleAdapter(
                        TrainingActivity_admin_info.this, personList, R.layout.traininginformation,
                        new String[]{TAG_INFO}, new int[]{R.id.tinfo}
                );
                list1.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



}