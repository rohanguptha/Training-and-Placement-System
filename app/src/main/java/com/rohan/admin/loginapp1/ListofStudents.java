package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class ListofStudents extends AppCompatActivity {
TextView text1,text2,text3;
    ListView list1;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    public String info_url = "http://dragon321.esy.es/enrolledlistbranch_training.php";

    private static final String TAG_RESULTS = "server_response";
    private static final String TAG_ROLLNO = "rollno";
    private static final String TAG_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_students);

        text1 = (TextView) findViewById(R.id.ractivity);
        String message = getIntent().getStringExtra("activity");
        text1.setText(message);

        text2 = (TextView) findViewById(R.id.rdate);
        String message1 = getIntent().getStringExtra("date");
        text2.setText(message1);

        text3 = (TextView) findViewById(R.id.rbranch);
        String message2 = getIntent().getStringExtra("branch");
        text3.setText(message2);



        list1 = (ListView) findViewById(R.id.list1);
        personList = new ArrayList<HashMap<String, String>>();


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            activityInfo v = new activityInfo(this);
            v.execute("info", text1.getText().toString(),text2.getText().toString(),text3.getText().toString());

        }
        else
        {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }



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
                    String activity,date,branch;
                    activity = params[1];
                    date = params[2];
                    branch = params[3];

                    String data = URLEncoder.encode("activity", "UTF-8") + "=" + URLEncoder.encode(activity, "UTF-8")+"&"+
                                  URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                                  URLEncoder.encode("branch","UTF-8")+"="+URLEncoder.encode(branch,"UTF-8");

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
                    String rrollno = c.getString(TAG_ROLLNO);
                    String rname = c.getString(TAG_NAME);


                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put(TAG_ROLLNO, rrollno);
                    persons.put(TAG_NAME, rname);

                    personList.add(persons);
                }

                ListAdapter adapter = new SimpleAdapter(
                        ListofStudents.this, personList, R.layout.enrolledbranchwiselist,
                        new String[]{TAG_ROLLNO,TAG_NAME}, new int[]{R.id.rrollno,R.id.rname}
                );
                list1.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }




}
