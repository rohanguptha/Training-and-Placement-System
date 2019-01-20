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
import android.widget.EditText;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Placement_add extends AppCompatActivity {
    EditText Company_name,time,date;
    Button add_button;


    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_add);
        Company_name = (EditText)findViewById(R.id.add_company);
        time = (EditText)findViewById(R.id.p_add_time);
        date = (EditText)findViewById(R.id.p_add_date);



        add_button = (Button)findViewById(R.id.padd);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (Company_name.getText().toString().equals("") || time.getText().toString().equals("") || date.getText().toString().equals(""))

                    {
                        Snackbar.make(v, "Please fill details..", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } else {
                        addBackgroundTask addbackgroundTask = new addBackgroundTask(Placement_add.this);
                        addbackgroundTask.execute("ADD",Company_name.getText().toString(), time.getText().toString(), date.getText().toString());

                    }
                }
                else{
                    //bLogin.setVisibility(View.INVISIBLE);
                    // bLogin.setEnabled(false);
                    Snackbar.make(v, "Please connect to internet..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    public class addBackgroundTask extends AsyncTask<String,Void,String> {
        String add_url = "http://dragon321.esy.es/placement_add.php";

        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;


        public addBackgroundTask(Context ctx)
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
            progressDialog.setMessage("Updating....");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals("ADD")) {
                try {
                    URL url = new URL(add_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, ("UTF-8")));
                    String activity = params[1];
                    String time = params[2];
                    String date = params[3];
                    String data = URLEncoder.encode("compname", "UTF-8") + "=" + URLEncoder.encode(activity, "UTF-8") + "&" +
                            URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
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
                if(code.equals("add_true")) {
                    showDialog("Updation Success", message, code);
                }
                else if(code.equals("add_false"))
                {
                    showDialog("Updation Failed", message, code);
                }


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void showDialog(String title,String  message,String code)
        {
            builder.setTitle(title);
            if(code.equals("add_true")||code.equals("add_false"))
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

