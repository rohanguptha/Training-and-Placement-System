package com.rohan.admin.loginapp1;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import java.util.Random;

public class LIST extends AppCompatActivity {
    TextView text1,text2,text3,text4,text5,text6,final_text;
    ListView list1;
    Button submit;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    public String info_url = "http://dragon321.esy.es/checkbranch.php";
    ArrayList<String> selection = new ArrayList<>();

    private static final String TAG_RESULTS = "server_response";
    private static final String TAG_ROLLNO = "rollno";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";


ArrayList<String> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        text1 = (TextView) findViewById(R.id.listbranch);
        String message = getIntent().getStringExtra("branch");
        text1.setText(message);
        text2 = (TextView) findViewById(R.id.ssc);
        String message1 = getIntent().getStringExtra("ssc");
        text2.setText(message1);
        text3 = (TextView) findViewById(R.id.inter);
        String message2 = getIntent().getStringExtra("inter");
        text3.setText(message2);
        text4 = (TextView) findViewById(R.id.btech);
        String message3 = getIntent().getStringExtra("btech");
        text4.setText(message3);
        text5 = (TextView) findViewById(R.id.rank);
        String message4 = getIntent().getStringExtra("rank");
        text5.setText(message4);

        text6 = (TextView) findViewById(R.id.compname);
        String message5 = getIntent().getStringExtra("compname");
        text6.setText(message5);

        final_text = (TextView)findViewById(R.id.pid);
        list1 = (ListView) findViewById(R.id.list1);



        personList = new ArrayList<HashMap<String, String>>();
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
        CheckBox cb=   (CheckBox)findViewById(R.id.chk_all);
        cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if (list1.getCount() > 0)
                {
                    for (int i = 0; i < list1.getCount(); i++) {
                        View view = list1.getChildAt(i);
                        CheckBox cb=   (CheckBox)findViewById(R.id.chk_all);
                        CheckBox check=   (CheckBox)view.findViewById(R.id.checkBox);
                        TextView textv=(TextView)view.findViewById(R.id.rrollno);
                       if( cb.isChecked())
                       {
                           // (!check.isChecked());
                            if(!check.isChecked())
                            {
                                check.setChecked(true);
                                if (check.isChecked())
                                {
                                    selection.add(textv.getText().toString());
                                } else if (!check.isChecked()) {


                                    selection.remove(textv.getText().toString());

                                }
                            }
                       }
                       else
                       {
                           cb.setChecked(false);
                           check.setChecked(false);
                           selection.remove(textv.getText().toString());
                       }

                        // selection.add(textv.getText().toString());
                    }
                }

                String final_branch_selection = "";
                for(String Selection : selection)
                {
                    if(final_branch_selection.equals("")) {
                        final_branch_selection = final_branch_selection  + Selection;
                    }
                    else{
                        final_branch_selection = final_branch_selection + " " + Selection;
                    }
                }
              //  final_branch_selection = final_branch_selection - "*";

                    final_text.setText(final_branch_selection);
                final_text.setEnabled(true);
            }
        });

        activityInfo v = new activityInfo(this);
        v.execute("info", text1.getText().toString(), text2.getText().toString(), text3.getText().toString(), text4.getText().toString(), text5.getText().toString());


        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    if (final_text.getText().toString().equals("empty") || final_text.getText().toString().equals(""))

                    {
                        Snackbar.make(v, "selection empty", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        eligible eligible = new eligible(LIST.this);
                        eligible.execute("eligible",final_text.getText().toString(),text6.getText().toString());
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
                    String branch,ssc,inter,btech,rank;
                    branch = params[1];
                    ssc = params[2];
                    inter = params[3];
                    btech = params[4];
                    rank = params[5];
                    String data = URLEncoder.encode("branch", "UTF-8") + "=" + URLEncoder.encode(branch, "UTF-8")+"&"+
                            URLEncoder.encode("sscpercent","UTF-8")+"="+URLEncoder.encode(ssc,"UTF-8")+"&"+
                            URLEncoder.encode("interpercent","UTF-8")+"="+URLEncoder.encode(inter,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII1percent","UTF-8")+"="+URLEncoder.encode(btech,"UTF-8")+"&"+
                    URLEncoder.encode("rank","UTF-8")+"="+URLEncoder.encode(rank,"UTF-8");


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


             //   int permissionCheck = ContextCompat.checkSelfPermission(LIST.this,
               //         Manifest.permission.WRITE_EXTERNAL_STORAGE);
              //  Toast.makeText(LIST.this,"per :"+permissionCheck,Toast.LENGTH_LONG).show();

al=new ArrayList<String>();



                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String rrollno = c.getString(TAG_ROLLNO);
                    String rname = c.getString(TAG_NAME);
                    String email = c.getString(TAG_EMAIL);
                    al.add(rrollno + "," + rname + "," + email);



                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put(TAG_ROLLNO, rrollno);
                    persons.put(TAG_NAME, rname);
                    persons.put(TAG_EMAIL, email);


                    personList.add(persons);
                }

                ListAdapter adapter = new SimpleAdapter(
                        LIST.this, personList, R.layout.activity_list_display,
                        new String[]{TAG_ROLLNO, TAG_NAME, TAG_EMAIL}, new int[]{R.id.rrollno, R.id.rname, R.id.remail}
                );
                list1.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LIST.this,"Error",Toast.LENGTH_LONG).show();
            } catch (Exception ee)
            {
                Toast.makeText(LIST.this,"Error :"+ee.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public class eligible extends AsyncTask<String,Void,String> {
        String register_url = "http://dragon321.esy.es/eligible_students.php";

        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;
        public eligible(Context ctx)
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
            if (method.equals("eligible"))
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
                if(code.equals("eligible_true")) {
                    showDialog("Success", message, code);
                }
                else if(code.equals("eligible_false"))
                {
                    showDialog("Failed", message, code);
                }


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void showDialog(String title,String  message,String code)
        {
            builder.setTitle(title);
            if(code.equals("eligible_true")||code.equals("eligible_false"))
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite ) {
try {
    Random r = new Random();
    String filename = "eligiblestud_" + r.nextInt(1000) + r.nextInt(9999) + ".csv";

    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
    // File file=new File("/sdcard/aa/a.csv");
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);
    Toast.makeText(LIST.this,"Downloading...",Toast.LENGTH_LONG).show();
    for(String line : al) {
        bw.write(line);
        bw.newLine();
    }

    bw.close();
    fw.close();
}
catch(Exception ee){

}


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_student, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
