package com.rohan.admin.loginapp1;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Mycompany_Student extends AppCompatActivity {

    public String studentverify_url="http://dragon321.esy.es/mycompany_placement.php";
    TextView text;

    String json;
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    private static final String TAG_RESULTS = "server_response";
    private static final String TAG_COMPANYNAME = "compname";

    private static final String TAG_DATE = "date";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycompany__student);

        text = (TextView) findViewById(R.id.message);
        String message = getIntent().getStringExtra("message");
        text.setText(message);
        list = (ListView) findViewById(R.id.list);
        personList = new ArrayList<HashMap<String,String>>();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            Studentacompanies v=new Studentacompanies(this);
            v.execute("studentactivity", text.getText().toString());


        }
        else
        {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }




    }
    public class Studentacompanies extends AsyncTask<String, Void, String> {


        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        android.support.v7.app.AlertDialog.Builder builder;

        public Studentacompanies(Context ctx) {

            this.ctx = ctx;
            activity = (Activity) ctx;
        }


        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals("studentactivity")) {
                try {
                    URL url = new URL(studentverify_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, ("UTF-8")));
                    String rollno;
                    rollno = params[1];

                    String data = URLEncoder.encode("rollno", "UTF-8") + "=" + URLEncoder.encode(rollno, "UTF-8");

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

                for(int i=0;i<peoples.length();i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String scompname = c.getString(TAG_COMPANYNAME);
                    String scdate = c.getString(TAG_DATE);


                    HashMap<String,String> persons = new HashMap<String,String>();

                    persons.put(TAG_COMPANYNAME,scompname);
                    persons.put(TAG_DATE,scdate);

                    personList.add(persons);

                }

                ListAdapter adapter = new SimpleAdapter(
                        Mycompany_Student.this, personList, R.layout.mycompany_student_display,
                        new String[]{TAG_COMPANYNAME,TAG_DATE},
                        new int[]{R.id.scompname,R.id.scdate}
                );

                list.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }








}