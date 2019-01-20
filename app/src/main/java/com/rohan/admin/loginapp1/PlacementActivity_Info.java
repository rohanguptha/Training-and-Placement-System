package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class PlacementActivity_Info extends AppCompatActivity {

    public String info_url = "http://dragon321.esy.es/placement_info.php";
    TextView text1,text2,text3,text4,text5;
    ListView list1;
    Button reg_button;
    String json;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;


    private static final String TAG_RESULTS = "server_response";
    private static final String TAG_INFO = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_activity__info);
        text1 = (TextView) findViewById(R.id.companyname);
        String message = getIntent().getStringExtra("message");
        text1.setText(message);
        text2 = (TextView) findViewById(R.id.activitydate);
        String msg = getIntent().getStringExtra("msg");
        text2.setText(msg);

        text3 = (TextView) findViewById(R.id.activitybranch);
        String message1 = getIntent().getStringExtra("message1");
        text3.setText(message1);
        text4 = (TextView) findViewById(R.id.rollno);
        String message2 = getIntent().getStringExtra("rollno");
        text4.setText(message2);

        text5 = (TextView) findViewById(R.id.rname);
        String message3 = getIntent().getStringExtra("name");
        text5.setText(message3);



        list1 = (ListView) findViewById(R.id.list1);
        personList = new ArrayList<HashMap<String, String>>();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            activityInfo v = new activityInfo(this);
            v.execute("info", text1.getText().toString());
        }
        else
        {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }




        reg_button = (Button)findViewById(R.id.reg_button);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    {
                        registerBackgroundTask registerBackgroundTask = new registerBackgroundTask(PlacementActivity_Info.this);
                        registerBackgroundTask.execute("Register",text4.getText().toString(),text5.getText().toString(), text3.getText().toString(), text1.getText().toString(), text2.getText().toString());

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
                    String companyname;
                    companyname = params[1];

                    String data = URLEncoder.encode("compname", "UTF-8") + "=" + URLEncoder.encode(companyname, "UTF-8");

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
                        PlacementActivity_Info.this, personList, R.layout.traininginformation,
                        new String[]{TAG_INFO}, new int[]{R.id.tinfo}
                );
                list1.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class registerBackgroundTask extends AsyncTask<String,Void,String> {
        String register_url = "http://dragon321.esy.es/reg_placement.php";

        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;






        public registerBackgroundTask(Context ctx)
        {

            this.ctx = ctx;
            activity = (Activity)ctx;
        }

        @Override
        protected void onPreExecute()
        {
            builder = new AlertDialog.Builder(activity);
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Connecting To Server....");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals("Register"))
            {
                try {
                    URL url = new URL(register_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,("UTF-8")));
                    String rollno = params[1];
                    String name = params[2];
                    String branch = params[3];
                    String cmpname = params[4];
                    String date = params[5];
                    String data = URLEncoder.encode("rollno","UTF-8")+"="+URLEncoder.encode(rollno,"UTF-8")+"&"+
                            URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("branch","UTF-8")+"="+URLEncoder.encode(branch,"UTF-8")+"&"+
                            URLEncoder.encode("compname","UTF-8")+"="+URLEncoder.encode(cmpname,"UTF-8")+"&"+
                            URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line=bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(line+"\n");
                    }
                    httpURLConnection.disconnect();
                    Thread.sleep(800);

                    return stringBuilder.toString().trim();




                } catch (MalformedURLException e) {
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
        protected void onPostExecute(String json)
        {
            try {
                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject JO = jsonArray.getJSONObject(0);
                String code = JO.getString("code");
                String message = JO.getString("message");
                if(code.equals("regadd_true")) {
                    showDialog("Registration Success", message, code);
                }
                else if(code.equals("regadd_false"))
                {
                    showDialog("Registration Failed", message, code);
                }


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void showDialog(String title,String  message,String code)
        {
            builder.setTitle(title);
            if(code.equals("regadd_true")||code.equals("regadd_false"))
            {
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        activity.finish();
                    }
                });



            }
            AlertDialog alertDialog = builder.create();
            alertDialog.show();



        }


    }


}