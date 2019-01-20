package com.rohan.admin.loginapp1;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ListofStudents_to_place extends AppCompatActivity {
    TextView text1,text2,final_text;
    ListView list1;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    public String info_url = "http://dragon321.esy.es/updateplaced_studentdetails.php";
    ArrayList<String> selection = new ArrayList<>();
    Button placed;

    private static final String TAG_RESULTS = "server_response";
    private static final String TAG_ROLLNO = "rollno";
    private static final String TAG_NAME = "name";
    private static final String TAG_BRANCH = "branch";
    private static final String TAG_ATTENDANCE = "attendance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_students_to_place);

        text1 = (TextView) findViewById(R.id.compname);
        String message = getIntent().getStringExtra("companyname");
        text1.setText(message);

        text2 = (TextView) findViewById(R.id.date);
        String message2 = getIntent().getStringExtra("date");
        text2.setText(message2);

        final_text = (TextView)findViewById(R.id.pid);
       // list1 = (ListView) findViewById(R.id.list1);

     //   personList = new ArrayList<HashMap<String, String>>();
        list1 = (ListView) findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice);
        list1.setAdapter(adapter);
//        list1.setOnItemClickListener(new ItemList());
        list1.setItemsCanFocus(false);
        // we want multiple clicks
        list1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        personList = new ArrayList<HashMap<String, String>>();


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            activityInfo v = new activityInfo(this);
            v.execute("info", text1.getText().toString(), text2.getText().toString());

        }
        else
        {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox);

                TextView textv=(TextView)v.findViewById(R.id.rrollno);


                cb.setChecked(!cb.isChecked());
                ListView lv = (ListView) parent;
                if(cb.isChecked()) {


                    selection.add(textv.getText().toString());
                }
                else
                {

                    selection.remove(textv.getText().toString());

                }
                String final_branch_selection = "";
                for(String Selection : selection)
                {
                    final_branch_selection = final_branch_selection  + Selection + " ";
                }
                final_text.setText(final_branch_selection);
                final_text.setEnabled(true);


            }
        });

        placed = (Button)findViewById(R.id.reg_button);
        placed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    if (final_text.getText().toString().equals("empty") || text1.getText().toString().equals(""))

                    {
                        Snackbar.make(v, "selection empty", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        postattendance postattendance = new postattendance(ListofStudents_to_place.this);
                        postattendance.execute("attendance",final_text.getText().toString(),text1.getText().toString());
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
                    String compname,date;
                    compname = params[1];
                    date = params[2];

                    String data = URLEncoder.encode("compname", "UTF-8") + "=" + URLEncoder.encode(compname, "UTF-8")+ "&" +
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
                    String rbranch = c.getString(TAG_BRANCH);
                    String rattendance = c.getString(TAG_ATTENDANCE);

                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put(TAG_ROLLNO, rrollno);
                    persons.put(TAG_NAME, rname);
                    persons.put(TAG_BRANCH,rbranch);
                    persons.put(TAG_ATTENDANCE,rattendance);

                    personList.add(persons);
                }

                ListAdapter adapter = new SimpleAdapter(
                        ListofStudents_to_place.this, personList, R.layout.listofstudents_to_place_display,
                        new String[]{TAG_ROLLNO,TAG_NAME,TAG_BRANCH,TAG_ATTENDANCE}, new int[]{R.id.rrollno,R.id.rname,R.id.rbranch,R.id.rattendance}
                );
                list1.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class postattendance extends AsyncTask<String,Void,String> {
        String register_url = "http://dragon321.esy.es/placement_post.php";

        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;
        public postattendance(Context ctx)
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
            if (method.equals("attendance"))
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
                    String compname = params[2];

                    String data = URLEncoder.encode("rollno","UTF-8")+"="+URLEncoder.encode(rollno,"UTF-8")+"&"+
                            URLEncoder.encode("compname","UTF-8")+"="+URLEncoder.encode(compname,"UTF-8");
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
                if(code.equals("attendance_true")) {
                    showDialog("Posted Success", message, code);
                }
                else if(code.equals("attendance_false"))
                {
                    showDialog("post Failed", message, code);
                }


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void showDialog(String title,String  message,String code)
        {
            builder.setTitle(title);
            if(code.equals("attendance_true")||code.equals("attendance_false"))
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

