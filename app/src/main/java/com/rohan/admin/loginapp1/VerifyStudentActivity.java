package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

public class VerifyStudentActivity extends AppCompatActivity {
    public String studentverify_url="http://dragon321.esy.es/search_student.php";
    TextView text;
    Button accept;
    String json;
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    private static final String TAG_RESULTS = "server_response";
    private static final String TAG_ROLLNO = "rollno";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_BRANCH = "branch";
    private static final String TAG_YEARPASSBTCH = "yearpassbtch";
    private static final String TAG_RANK = "rank";
    private static final String TAG_SURNAME = "surname";
    private static final String TAG_FULLNAME = "fullname";
    private static final String TAG_DOB = "dob";
    private static final String TAG_BIRTHPLACE = "birthplace";
    private static final String TAG_AGE = "age";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_CASTE = "caste";
    private static final String TAG_RELIGION = "religion";
    private static final String TAG_AADHARNO = "aadharno";
    private static final String TAG_SSCPASS = "sscpass";
    private static final String TAG_SSCMARKS = "sscmarks";
    private static final String TAG_SSCPERCENT = "sscpercent";
    private static final String TAG_SSCATTEMPT = "sscattempt";
    private static final String TAG_INTERYEARPASS = "interyearpass";
    private static final String TAG_INTERMARKS = "intermarks";
    private static final String TAG_INTERPERCENT = "interpercent";
    private static final String TAG_INTERATTEMPT = "interattempt";
    private static final String TAG_DIPLOMAYEARPASS = "diplomayearpass";
    private static final String TAG_DIPLOMAMARKS = "diplomamarks";
    private static final String TAG_DIPLOMAPERCENT = "diplomapercent";
    private static final String TAG_DIPLOMAATTEMPT = "diplomaattempt";
    private static final String TAG_BTECH1YEARPASS = "btech1yearpass";
    private static final String TAG_BTECH1MARKS = "btech1marks";
    private static final String TAG_BTECH1PERCENT = "btech1percent";
    private static final String TAG_BTECH1ATTEMPT= "btech1attempt";
    private static final String TAG_BTECH1BACKLOG = "btech1backlog";
    private static final String TAG_BTECHII1YEARPASS = "btechII1yearpass";
    private static final String TAG_BTECHII1MARKS = "btechII1marks";
    private static final String TAG_BTECHII1PERCENT = "btechII1percent";
    private static final String TAG_BTECHII1ATTEMPT = "btechII1attempt";
    private static final String TAG_BTECHII1BACKLOG = "btechII1backlog";
    private static final String TAG_BTECHII2YEARPASS = "btechII2yearpass";
    private static final String TAG_BTECHII2MARKS = "btechII2marks";
    private static final String TAG_BTECHII2PERCENT = "btechII2percent";
    private static final String TAG_BTECHII2ATTEMPT = "btechII2attempt";
    private static final String TAG_BTECHII2BACKLOG = "btechII2backlog";
    private static final String TAG_BTECHIII1YEARPASS = "btechIII1yearpass";
    private static final String TAG_BTECHIII1MARKS = "btechIII1marks";
    private static final String TAG_BTECHIII1PERCENT = "btechIII1percent";
    private static final String TAG_BTECHIII1ATTEMPT = "btechIII1attempt";
    private static final String TAG_BTECHIII1BACKLOG = "btechIII1backlog";
    private static final String TAG_BTECHIII2YEARPASS = "btechIII2yearpass";
    private static final String TAG_BTECHIII2MARKS = "btechIII2marks";
    private static final String TAG_BTECHIII2PERCENT = "btechIII2percent";
    private static final String TAG_BTECHIII2ATTEMPT = "btechIII2attempt";
    private static final String TAG_BTECHIII2BACKLOG = "btechIII2backlog";
    private static final String TAG_FATHERNAME = "fathername";
    private static final String TAG_FOCCUPATION = "foccupation";
    private static final String TAG_FORGANIZATION = "forganization";
    private static final String TAG_FDESIGNATION = "fdesignation";
    private static final String TAG_FCELLNO = "fcellno";
    private static final String TAG_MOTHERNAME = "mothername";
    private static final String TAG_MOCCUPATION = "moccupation";
    private static final String TAG_MORGANIZATION = "morganization";
    private static final String TAG_MDESIGNATION = "mdesignation";
    private static final String TAG_MCELLNO = "mcellno";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_STATE = "state";
    private static final String TAG_PINCODE = "pincode";
    private static final String TAG_STUDENTCELLNO = "studentcellno";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        text = (TextView) findViewById(R.id.message);
        String message = getIntent().getStringExtra("message");
        text.setText(message);
        list = (ListView) findViewById(R.id.list);
        personList = new ArrayList<HashMap<String,String>>();

        verifyStudent v=new verifyStudent(this);
        v.execute("verifystudent", text.getText().toString());

        accept = (Button)findViewById(R.id.btnaccept);
        accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    {
                        acceptBackgroundTask acceptBackgroundTask = new acceptBackgroundTask(VerifyStudentActivity.this);
                        acceptBackgroundTask.execute("accept",text.getText().toString());

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

         public class verifyStudent extends AsyncTask<String, Void, String> {


            Context ctx;
            ProgressDialog progressDialog;
            Activity activity;
            android.support.v7.app.AlertDialog.Builder builder;

            public verifyStudent(Context ctx) {

                this.ctx = ctx;
                activity = (Activity) ctx;
            }


            @Override
            protected String doInBackground(String... params) {
                String method = params[0];
                if (method.equals("verifystudent")) {
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
                        String svrollno = c.getString(TAG_ROLLNO);
                        String svname = c.getString(TAG_NAME);
                        String svemail = c.getString(TAG_EMAIL);
                        String svbranch= c.getString(TAG_BRANCH) ;
                        String svyearpassbtch = c.getString(TAG_YEARPASSBTCH);
                        String svrank = c.getString(TAG_RANK);
                        String svsurname =c.getString(TAG_SURNAME);
                        String svfullname = c.getString(TAG_FULLNAME);
                        String svdob =c.getString(TAG_DOB);
                        String svbirthplace =c.getString(TAG_BIRTHPLACE);
                        String svage = c.getString(TAG_AGE);
                        String svgender =c.getString(TAG_GENDER);
                        String svcaste=c.getString(TAG_CASTE);
                        String svreligion=c.getString(TAG_RELIGION);
                        String  svaadharno=c.getString(TAG_AADHARNO);
                        String svsscpass=c.getString(TAG_SSCPASS) ;
                        String  svsscmarks=c.getString(TAG_SSCMARKS) ;
                        String svsscpercent=c.getString(TAG_SSCPERCENT) ;
                        String  svsscattempt=c.getString(TAG_SSCATTEMPT) ;
                        String svinteryearpass=c.getString(TAG_INTERYEARPASS) ;
                        String  svintermarks=c.getString(TAG_INTERMARKS) ;
                        String  svinterpercent=c.getString(TAG_INTERPERCENT);
                        String  svinterattempt=c.getString(TAG_INTERATTEMPT) ;
                        String  svdiplomayearpass=c.getString(TAG_DIPLOMAYEARPASS) ;
                        String  svdiplomamarks=c.getString(TAG_DIPLOMAMARKS) ;
                        String svdiplomapercent=c.getString(TAG_DIPLOMAPERCENT) ;
                        String svdiplomaattempt=c.getString(TAG_DIPLOMAATTEMPT)  ;
                        String svbtech1yearpass=c.getString(TAG_BTECH1YEARPASS)  ;
                        String svbtech1marks=c.getString(TAG_BTECH1MARKS) ;
                        String svbtech1percent= c.getString(TAG_BTECH1PERCENT) ;
                        String  svbtech1attempt=c.getString(TAG_BTECH1ATTEMPT);
                        String svbtech1backlog=c.getString(TAG_BTECH1BACKLOG);
                        String svbtech2yearpass=c.getString(TAG_BTECHII1YEARPASS) ;
                        String  svbtech2marks=c.getString(TAG_BTECHII1MARKS);
                        String svbtech2percent=c.getString(TAG_BTECHII1PERCENT);
                        String svbtech2attempt=c.getString(TAG_BTECHII1ATTEMPT)  ;
                        String  svbtech2backlog=c.getString(TAG_BTECHII1BACKLOG) ;
                        String  svbtech2iyearpass=c.getString(TAG_BTECHII2YEARPASS);
                        String svbtech2imarks=c.getString(TAG_BTECHII2MARKS) ;
                        String  svbtech2ipercent=c.getString(TAG_BTECHII2PERCENT) ;
                        String  svbtech2iattempt=c.getString(TAG_BTECHII2ATTEMPT) ;
                        String  svbtech2ibacklog=c.getString(TAG_BTECHII2BACKLOG) ;
                        String  svbtech3yearpass=c.getString(TAG_BTECHIII1YEARPASS);
                        String  svbtech3marks=c.getString(TAG_BTECHIII1MARKS);
                        String svbtech3percent=c.getString(TAG_BTECHIII1PERCENT) ;
                        String  svbtech3attempt=c.getString(TAG_BTECHIII1ATTEMPT) ;
                        String  svbtech3backlog=c.getString(TAG_BTECHIII1BACKLOG) ;
                        String  svbtech3iyearpass=c.getString(TAG_BTECHIII2YEARPASS);
                        String  svbtech3imarks=c.getString(TAG_BTECHIII2MARKS) ;
                        String  svbtech3ipercent=c.getString(TAG_BTECHIII2PERCENT) ;
                        String  svbtech3iattempt=c.getString(TAG_BTECHIII2ATTEMPT) ;
                        String  svbtech3ibacklog=c.getString(TAG_BTECHIII2BACKLOG) ;
                        String  svfathername=c.getString(TAG_FATHERNAME) ;
                        String  svfoccupation=c.getString(TAG_FOCCUPATION) ;
                        String svforganization=c.getString(TAG_FORGANIZATION);
                        String  svfdesignation=c.getString(TAG_FDESIGNATION) ;
                        String  svfcellno=c.getString(TAG_FCELLNO) ;
                        String  svmothername=c.getString(TAG_MOTHERNAME) ;
                        String  svmoccupation=c.getString(TAG_MOCCUPATION) ;
                        String  svmorganization=c.getString(TAG_MORGANIZATION);
                        String svmdesignation=c.getString(TAG_MDESIGNATION);
                        String svmcellno=c.getString(TAG_MCELLNO);
                        String svaddress=c.getString(TAG_ADDRESS);
                        String svstate=c.getString(TAG_STATE);
                        String  svpincode=c.getString(TAG_PINCODE);
                        String  svstudentcellno=c.getString(TAG_STUDENTCELLNO);



                        HashMap<String,String> persons = new HashMap<String,String>();

                        persons.put(TAG_ROLLNO,svrollno);
                        persons.put(TAG_NAME,svname);
                        persons.put(TAG_EMAIL,svemail);
                        persons.put(TAG_BRANCH,svbranch);
                        persons.put(TAG_YEARPASSBTCH,svyearpassbtch);
                        persons.put (TAG_RANK,svrank);
                        persons.put (TAG_SURNAME,svsurname);
                        persons.put(TAG_FULLNAME,svfullname);
                        persons.put(TAG_DOB,svdob);
                        persons.put (TAG_BIRTHPLACE,svbirthplace);
                        persons.put(TAG_AGE,svage);
                        persons.put(TAG_GENDER,svgender);
                        persons.put(TAG_CASTE,svcaste);
                        persons.put(TAG_RELIGION,svreligion);
                        persons.put(TAG_AADHARNO,svaadharno);
                        persons.put(TAG_SSCPASS,svsscpass);
                        persons.put(TAG_SSCMARKS,svsscmarks);
                        persons.put(TAG_SSCPERCENT,svsscpercent);
                        persons.put(TAG_SSCATTEMPT,svsscattempt );
                        persons.put(TAG_INTERYEARPASS,svinteryearpass);
                        persons.put(TAG_INTERMARKS,svintermarks);
                        persons.put(TAG_INTERPERCENT,svinterpercent);
                        persons.put(TAG_INTERATTEMPT,svinterattempt);
                        persons.put(TAG_DIPLOMAYEARPASS,svdiplomayearpass);
                        persons.put(TAG_DIPLOMAMARKS,svdiplomamarks);
                        persons.put(TAG_DIPLOMAPERCENT,svdiplomapercent);
                        persons.put(TAG_DIPLOMAATTEMPT,svdiplomaattempt);
                        persons.put(TAG_BTECH1YEARPASS,svbtech1yearpass);
                        persons.put(TAG_BTECH1MARKS,svbtech1marks);
                        persons.put(TAG_BTECH1PERCENT,svbtech1percent);
                        persons.put(TAG_BTECH1ATTEMPT,svbtech1attempt);
                        persons.put(TAG_BTECH1BACKLOG,svbtech1backlog);
                        persons.put(TAG_BTECHII1YEARPASS,svbtech2yearpass);
                        persons.put(TAG_BTECHII1MARKS,svbtech2marks);
                        persons.put(TAG_BTECHII1PERCENT,svbtech2percent);
                        persons.put(TAG_BTECHII1ATTEMPT,svbtech2attempt);
                        persons.put(TAG_BTECHII1BACKLOG,svbtech2backlog);
                        persons.put(TAG_BTECHII2YEARPASS,svbtech2iyearpass);
                        persons.put(TAG_BTECHII2MARKS,svbtech2imarks);
                        persons.put(TAG_BTECHII2PERCENT,svbtech2ipercent);
                        persons.put(TAG_BTECHII2ATTEMPT,svbtech2iattempt);
                        persons.put(TAG_BTECHII2BACKLOG,svbtech2ibacklog);
                        persons.put(TAG_BTECHIII1YEARPASS,svbtech3yearpass);
                        persons.put(TAG_BTECHIII1MARKS,svbtech3marks);
                        persons.put(TAG_BTECHIII1PERCENT,svbtech3percent);
                        persons.put(TAG_BTECHIII1ATTEMPT,svbtech3attempt);
                        persons.put(TAG_BTECHIII1BACKLOG,svbtech3backlog);
                        persons.put(TAG_BTECHIII2YEARPASS,svbtech3iyearpass);
                        persons.put(TAG_BTECHIII2MARKS,svbtech3imarks);
                        persons.put(TAG_BTECHIII2PERCENT,svbtech3ipercent);
                        persons.put(TAG_BTECHIII2ATTEMPT,svbtech3iattempt);
                        persons.put(TAG_BTECHIII2BACKLOG,svbtech3ibacklog);
                        persons.put(TAG_FATHERNAME,svfathername);
                        persons.put(TAG_FOCCUPATION,svfoccupation);
                        persons.put(TAG_FORGANIZATION,svforganization);
                        persons.put(TAG_FDESIGNATION,svfdesignation);
                        persons.put(TAG_FCELLNO,svfcellno);
                        persons.put(TAG_MOTHERNAME,svmothername);
                        persons.put(TAG_MOCCUPATION,svmoccupation);
                        persons.put(TAG_MORGANIZATION,svmorganization);
                        persons.put(TAG_MDESIGNATION,svmdesignation);
                        persons.put(TAG_MCELLNO,svmcellno);
                        persons.put(TAG_ADDRESS,svaddress);
                        persons.put(TAG_STATE,svstate);
                        persons.put(TAG_PINCODE,svpincode);
                        persons.put(TAG_STUDENTCELLNO,svstudentcellno);


                        personList.add(persons);
                    }

                    ListAdapter adapter = new SimpleAdapter(
                            VerifyStudentActivity.this, personList, R.layout.verifystudent_display,
                            new String[]{TAG_ROLLNO,TAG_NAME,TAG_EMAIL,TAG_BRANCH,TAG_YEARPASSBTCH ,TAG_RANK,TAG_SURNAME,TAG_FULLNAME ,
                                    TAG_DOB ,TAG_BIRTHPLACE,TAG_AGE ,TAG_GENDER ,TAG_CASTE,TAG_RELIGION ,TAG_AADHARNO ,TAG_SSCPASS,TAG_SSCMARKS ,
                                    TAG_SSCPERCENT ,TAG_SSCATTEMPT ,TAG_INTERYEARPASS,TAG_INTERMARKS ,TAG_INTERPERCENT ,TAG_INTERATTEMPT ,TAG_DIPLOMAYEARPASS ,
                                    TAG_DIPLOMAMARKS,TAG_DIPLOMAPERCENT ,TAG_DIPLOMAATTEMPT,TAG_BTECH1YEARPASS ,TAG_BTECH1MARKS ,TAG_BTECH1PERCENT ,
                                    TAG_BTECH1ATTEMPT,TAG_BTECH1BACKLOG ,TAG_BTECHII1YEARPASS ,TAG_BTECHII1MARKS ,TAG_BTECHII1PERCENT,TAG_BTECHII1ATTEMPT ,
                                    TAG_BTECHII1BACKLOG ,TAG_BTECHII2YEARPASS ,TAG_BTECHII2MARKS ,TAG_BTECHII2PERCENT ,TAG_BTECHII2ATTEMPT ,TAG_BTECHII2BACKLOG ,
                                    TAG_BTECHIII1YEARPASS ,TAG_BTECHIII1MARKS ,TAG_BTECHIII1PERCENT ,TAG_BTECHIII1ATTEMPT ,TAG_BTECHIII1BACKLOG ,
                                    TAG_BTECHIII2YEARPASS ,TAG_BTECHIII2MARKS ,TAG_BTECHIII2PERCENT ,TAG_BTECHIII2ATTEMPT ,TAG_BTECHIII2BACKLOG ,
                                    TAG_FATHERNAME ,TAG_FOCCUPATION ,TAG_FORGANIZATION ,TAG_FDESIGNATION ,TAG_FCELLNO , TAG_MOTHERNAME ,TAG_MOCCUPATION,
                                    TAG_MORGANIZATION, TAG_MDESIGNATION,TAG_MCELLNO,TAG_ADDRESS,TAG_STATE,TAG_PINCODE,TAG_STUDENTCELLNO},
                            new int[]{R.id.svrollno, R.id.svname, R.id.svemail,R.id.svbranch,R.id.svyearofpassbtch,R.id.svrank,R.id.svsurname,R.id.svfullname,R.id.svdob,
                                    R.id.svbirthplace,R.id.svage,R.id.svgender,R.id.svcaste,R.id.svreligion,R.id.svaadharno,R.id.svsscpassd,R.id.svsscmarks,R.id.svsscpercent,
                                    R.id.svsscattempt,R.id.svinteryearpass,R.id.svintermarks,R.id.svinterpercent,R.id.svinterattempt,R.id.svdiplomayearpass,R.id.svdiplomamarks,
                                    R.id.svdiplomapercent,R.id.svdiplomaattempt,R.id.svbtch1yearpass,R.id.svbtech1marks,R.id.svbtech1percent,R.id.svbtech1attempt,R.id.svbtech1backlog,
                                    R.id.svbtech2yearpass,R.id.svbtech2marks,R.id.svbtech2percent,R.id.svbtech2attempt,R.id.svbtech2backlog,
                                    R.id.svbtech2iyearpass,R.id.svbtech2imarks,R.id.svbtech2ipercent,R.id.svbtech2iattempt,R.id.svbtech2ibacklog,
                                    R.id.svbtech3yearpass,R.id.svbtech3marks,R.id.svbtech3percent,R.id.svbtech3attempt,R.id.svbtech3backlog,
                                    R.id.svbtech3iyearpass,R.id.svbtech3imarks,R.id.svbtech3ipercent,R.id.svbtech3iattempt,R.id.svbtech3ibacklog,
                                    R.id.svfathername,R.id.svfocc,R.id.svforg,R.id.svfdes,R.id.svfcellno,
                                    R.id.svmothername,R.id.svmocc,R.id.svmorg,R.id.svmdes,R.id.svmcellno,
                                    R.id.svaddress,R.id.svstate,R.id.svpincode,R.id.sv}
                    );

                    list.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                }
            }



    public class acceptBackgroundTask extends AsyncTask<String,Void,String> {
        String register_url = "http://dragon321.esy.es/accept_student.php";

        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;

        public acceptBackgroundTask(Context ctx)
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
            if (method.equals("accept"))
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

                    String data = URLEncoder.encode("rollno","UTF-8")+"="+URLEncoder.encode(rollno,"UTF-8");

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
                if(code.equals("accept_true")) {
                    showDialog("Student accepted", message, code);

                }
                else if(code.equals("accept_false"))
                {
                    showDialog("Not accepted", message, code);
                }


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void showDialog(String title,String  message,String code)
        {
            builder.setTitle(title);
            if(code.equals("accept_true")||code.equals("accept_false"))
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
