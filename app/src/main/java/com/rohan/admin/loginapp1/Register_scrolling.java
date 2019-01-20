package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class Register_scrolling extends AppCompatActivity {
    EditText BRANCH,YEARPASS,RANK,SURNAME,FULLNAME,DOB,BIRTHPLACE,AGE,GENDER,CASTE,RELIGION,AADHARNO,SSCYEARPASS, SSCMARKS, SSCAGGREGATE,
            SSCATTEMPT, INTERYEARPASS, INTERMARKS,INTERAGGREGATE, INTERATTEMPT, DIPLOMAYEARPASS, DIPLOMAMARKS, DIPLOMAAGGREGATE, DIPLOMAATTEMPT, IYEARPASS, IMARKS,
            IAGGREGATE, IATTEMPT, IBACKLOG, II1YEARPASS, II1MARKS, II1AGGREGATE, II1ATTEMPT, II1BACKLOG, II2YEARPASS, II2MARKS, II2AGGREGATE,
            II2ATTEMPT, II2BACKLOG,        III1YEARPASS, III1MARKS, III1AGGREGATE,        III1ATTEMPT, III1BACKLOG, III2YEARPASS, III2MARKS, III2AGGREGATE,
            III2ATTEMPT, III2BACKLOG, FNAME, OCC,        ORG, DES, FPNO,
        MNAME, OCC2, ORG2, DES2, MPNO, ADDRESS, STATE, PIN, SPNO;
    ;
    Button reg_button;
    TextView ROLLNO;
    Activity activity;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_scrolling);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ROLLNO = (TextView)findViewById(R.id.pro_rollno);

        String message1 = getIntent().getStringExtra("message1");
        ROLLNO.setText(message1);

        BRANCH = (EditText)findViewById(R.id.pro_branch);
        YEARPASS = (EditText)findViewById(R.id.pro_yearpass);
        RANK = (EditText)findViewById(R.id.pro_rank);
        SURNAME = (EditText)findViewById(R.id.pro_surname);
        FULLNAME = (EditText)findViewById(R.id.pro_fullname);
        DOB = (EditText)findViewById(R.id.pro_dob);
        BIRTHPLACE = (EditText)findViewById(R.id.pro_birthplace);
        AGE = (EditText)findViewById(R.id.pro_age);
        GENDER = (EditText)findViewById(R.id.pro_gender);
        CASTE = (EditText)findViewById(R.id.pro_caste);
        RELIGION = (EditText)findViewById(R.id.pro_religion);
        AADHARNO = (EditText)findViewById(R.id.pro_aadharno);
        SSCYEARPASS = (EditText)findViewById(R.id.pro_sscyearpass);
        SSCMARKS = (EditText)findViewById(R.id.pro_sscmarks);
        SSCAGGREGATE = (EditText)findViewById(R.id.pro_sscaggregate);
        SSCATTEMPT = (EditText)findViewById(R.id.pro_sscattempt);
        INTERYEARPASS = (EditText)findViewById(R.id.pro_interyearpass);
        INTERMARKS = (EditText)findViewById(R.id.pro_intermarks);
        INTERAGGREGATE = (EditText)findViewById(R.id.pro_interaggregate);
        INTERATTEMPT = (EditText)findViewById(R.id.pro_interattempt);
        DIPLOMAYEARPASS = (EditText)findViewById(R.id.pro_diplomayearpass);
        DIPLOMAMARKS = (EditText)findViewById(R.id.pro_diplomamarks);
        DIPLOMAAGGREGATE = (EditText)findViewById(R.id.pro_diplomaaggregate);
        DIPLOMAATTEMPT = (EditText)findViewById(R.id.pro_diplomaattempt);
        IYEARPASS = (EditText)findViewById(R.id.pro_1yearpass);
        IMARKS = (EditText)findViewById(R.id.pro_1marks);
        IAGGREGATE = (EditText)findViewById(R.id.pro_1aggregate);
        IATTEMPT = (EditText)findViewById(R.id.pro_1attempt);
        IBACKLOG = (EditText)findViewById(R.id.pro_1backlog);
        II1YEARPASS = (EditText)findViewById(R.id.pro_II1yearpass);
        II1MARKS = (EditText)findViewById(R.id.pro_II1marks);
        II1AGGREGATE = (EditText)findViewById(R.id.pro_II1aggregate);
        II1ATTEMPT = (EditText)findViewById(R.id.pro_II1attempt);
        II1BACKLOG = (EditText)findViewById(R.id.pro_II1backlog);
        II2YEARPASS = (EditText)findViewById(R.id.pro_II2yearpass);
        II2MARKS = (EditText)findViewById(R.id.pro_II2marks);
        II2AGGREGATE = (EditText)findViewById(R.id.pro_II2aggregate);
        II2ATTEMPT = (EditText)findViewById(R.id.pro_II2attempt);
        II2BACKLOG = (EditText)findViewById(R.id.pro_II2backlog);
        III1YEARPASS = (EditText)findViewById(R.id.pro_III1yearpass);
        III1MARKS = (EditText)findViewById(R.id.pro_III1marks);
        III1AGGREGATE = (EditText)findViewById(R.id.pro_III1aggregate);
        III1ATTEMPT = (EditText)findViewById(R.id.pro_III1attempt);
        III1BACKLOG = (EditText)findViewById(R.id.pro_III1backlog);
        III2YEARPASS = (EditText)findViewById(R.id.pro_III2yearpass);
        III2MARKS = (EditText)findViewById(R.id.pro_III2marks);
        III2AGGREGATE = (EditText)findViewById(R.id.pro_III2aggregate);
        III2ATTEMPT = (EditText)findViewById(R.id.pro_III2attempt);
        III2BACKLOG = (EditText)findViewById(R.id.pro_III2backlog);
        FNAME = (EditText)findViewById(R.id.fname);
        OCC = (EditText)findViewById(R.id.occ);
        ORG = (EditText)findViewById(R.id.org);
        DES = (EditText)findViewById(R.id.des);
        FPNO = (EditText)findViewById(R.id.fpno);
        MNAME = (EditText)findViewById(R.id.mname);
        OCC2 = (EditText)findViewById(R.id.occ2);
        ORG2 = (EditText)findViewById(R.id.org2);
        DES2 = (EditText)findViewById(R.id.des2);
        MPNO = (EditText)findViewById(R.id.mpno);
        ADDRESS = (EditText)findViewById(R.id.address);
        STATE = (EditText)findViewById(R.id.state);
        PIN = (EditText)findViewById(R.id.pin);
        SPNO = (EditText)findViewById(R.id.spno);
        reg_button = (Button)findViewById(R.id.pro_reg_button);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (ROLLNO.getText().toString().equals("") || BRANCH.getText().toString().equals("") || YEARPASS.getText().toString().equals("") || RANK.getText().toString().equals("") ||
                    SURNAME.getText().toString().equals("") || FULLNAME.getText().toString().equals("") || DOB.getText().toString().equals("") || BIRTHPLACE.getText().toString().equals("") ||
                            AGE.getText().toString().equals("") || GENDER.getText().toString().equals("") || CASTE.getText().toString().equals("") || RELIGION.getText().toString().equals("")||
                    AADHARNO.getText().toString().equals("") || SSCYEARPASS.getText().toString().equals("") || SSCMARKS.getText().toString().equals("") ||SSCAGGREGATE.getText().toString().equals("") ||
                            SSCATTEMPT.getText().toString().equals("") || IYEARPASS.getText().toString().equals("") || IMARKS.getText().toString().equals("") || IAGGREGATE.getText().toString().equals("")||
                            IATTEMPT.getText().toString().equals("") || IBACKLOG.getText().toString().equals("") || II1YEARPASS.getText().toString().equals("") || II1MARKS.getText().toString().equals("")||
                            II1AGGREGATE.getText().toString().equals("") || II1ATTEMPT.getText().toString().equals("") || II1BACKLOG.getText().toString().equals("") || II2YEARPASS.getText().toString().equals("")||
                            II2MARKS.getText().toString().equals("") || II2AGGREGATE.getText().toString().equals("") || II2ATTEMPT.getText().toString().equals("") || II2BACKLOG.getText().toString().equals("")||
                            III1YEARPASS.getText().toString().equals("") || III1MARKS.getText().toString().equals("") || III1AGGREGATE.getText().toString().equals("") || III1ATTEMPT.getText().toString().equals("")||
                            III1BACKLOG.getText().toString().equals("") || III2YEARPASS.getText().toString().equals("") || III2MARKS.getText().toString().equals("") || III2AGGREGATE.getText().toString().equals("")||
                            III2ATTEMPT.getText().toString().equals("") || III2BACKLOG.getText().toString().equals("") || FNAME.getText().toString().equals("") || OCC.getText().toString().equals("")||
                            ORG.getText().toString().equals("") || DES.getText().toString().equals("") || FPNO.getText().toString().equals("") || MNAME.getText().toString().equals("")||
                            OCC2.getText().toString().equals("")|| MPNO.getText().toString().equals("")||
                            ADDRESS.getText().toString().equals("") || STATE.getText().toString().equals("") || PIN.getText().toString().equals("") || SPNO.getText().toString().equals("")

                            )

                    {
                        Toast.makeText(Register_scrolling.this, "Please fill details", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        registerBackgroundTask registerBackgroundTask = new registerBackgroundTask(Register_scrolling.this);
                        registerBackgroundTask.execute("Register",ROLLNO.getText().toString(), BRANCH.getText().toString(),YEARPASS.getText().toString(), RANK.getText().toString(),SURNAME.getText().toString(),FULLNAME.getText().toString(),DOB.getText().toString(),BIRTHPLACE.getText().toString(),
                                AGE.getText().toString(),GENDER.getText().toString(),CASTE.getText().toString(),RELIGION.getText().toString(),AADHARNO.getText().toString(),SSCYEARPASS.getText().toString(),SSCMARKS.getText().toString(),SSCAGGREGATE.getText().toString(),
                                SSCATTEMPT.getText().toString(),INTERYEARPASS.getText().toString(),INTERMARKS.getText().toString(),INTERAGGREGATE.getText().toString(),
                                INTERATTEMPT.getText().toString(),DIPLOMAYEARPASS.getText().toString(),DIPLOMAMARKS.getText().toString(),DIPLOMAAGGREGATE.getText().toString(),
                                DIPLOMAATTEMPT.getText().toString(),IYEARPASS.getText().toString(),IMARKS.getText().toString(),IAGGREGATE.getText().toString(),
                                IATTEMPT.getText().toString(),IBACKLOG.getText().toString(),II1YEARPASS.getText().toString(),II1MARKS.getText().toString(),
                                II1AGGREGATE.getText().toString(),II1ATTEMPT.getText().toString(),II1BACKLOG.getText().toString(),II2YEARPASS.getText().toString(),
                                II2MARKS.getText().toString(),II2AGGREGATE.getText().toString(),II2ATTEMPT.getText().toString(),II2BACKLOG.getText().toString(),
                                III1YEARPASS.getText().toString(),III1MARKS.getText().toString(),III1AGGREGATE.getText().toString(),III1ATTEMPT.getText().toString(),
                                III1BACKLOG.getText().toString(),III2YEARPASS.getText().toString(),III2MARKS.getText().toString(),III2AGGREGATE.getText().toString(),
                                III2ATTEMPT.getText().toString(),III2BACKLOG.getText().toString(),FNAME.getText().toString(),OCC.getText().toString(),
                                ORG.getText().toString(),DES.getText().toString(),FPNO.getText().toString(),MNAME.getText().toString(),
                                OCC2.getText().toString(),ORG2.getText().toString(),DES2.getText().toString(),MPNO.getText().toString(),
                                ADDRESS.getText().toString(),STATE.getText().toString(),PIN.getText().toString(),SPNO.getText().toString()
                                );

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
    public class registerBackgroundTask extends AsyncTask<String,Void,String> {
        String register_url = "http://dragon321.esy.es/myprofile_register.php";

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
                    String branch = params[2];
                    String yearpassbtch = params[3];
                    String rank = params[4];
                    String sname = params[5];
                    String fullname = params[6];
                    String dob = params[7];
                    String birthplace = params[8];
                    String grow = params[9];
                    String gender = params[10];
                    String caste = params[11];
                    String religion = params[12];
                    String aadar = params[13];
                    String sscpass = params[14];
                    String sscmarks = params[15];
                    String sscpercent = params[16];
                    String ssca = params[17];
                    String interyearpass = params[18];
                    String intermarks = params[19];
                    String interpercent = params[20];
                    String intera = params[21];
                    String diplomayearpass = params[22];
                    String diplomamarks = params[23];
                    String dp = params[24];
                    String da = params[25];
                    String btech1yearpass = params[26];
                    String btech1marks = params[27];
                    String b1p = params[28];
                    String b1a = params[29];
                    String btech1backlog = params[30];
                    String btechII1yearpass = params[31];
                    String btechII1marks = params[32];
                    String bII1P = params[33];
                    String btechII1attempt = params[34];
                    String btechII1backlog = params[35];
                    String btechII2yearpass = params[36];
                    String btechII2marks = params[37];
                    String bII2P = params[38];
                    String btechII2attempt = params[39];
                    String btechII2backlog = params[40];
                    String btechIII1yearpass = params[41];
                    String btechIII1marks = params[42];
                    String bIII1P = params[43];
                    String btechIII1attempt = params[44];
                    String btechIII1backlog = params[45];
                    String btechIII2yearpass = params[46];
                    String btechIII2marks = params[47];
                    String bIII2P= params[48];
                    String btechIII2attempt = params[49];
                    String btechIII2backlog = params[50];
                    String fn = params[51];
                    String foc = params[52];
                    String forganization = params[53];
                    String fdesignation = params[54];
                    String fcellno = params[55];
                    String mn = params[56];
                    String moc = params[57];
                    String morganization = params[58];
                    String mdesignation = params[59];
                    String mcellno = params[60];
                    String location = params[61];
                    String state = params[62];
                    String pincode = params[63];
                    String studentcellno = params[64];
                    String data = URLEncoder.encode("rollno","UTF-8")+"="+URLEncoder.encode(rollno,"UTF-8")+"&"+
                            URLEncoder.encode("branch","UTF-8")+"="+URLEncoder.encode(branch,"UTF-8")+"&"+
                            URLEncoder.encode("yearpassbtch","UTF-8")+"="+URLEncoder.encode(yearpassbtch,"UTF-8")+"&"+
                            URLEncoder.encode("rank","UTF-8")+"="+URLEncoder.encode(rank,"UTF-8")+"&"+
                    URLEncoder.encode("surname","UTF-8")+"="+URLEncoder.encode(sname,"UTF-8")+"&"+
                            URLEncoder.encode("fullname","UTF-8")+"="+URLEncoder.encode(fullname,"UTF-8")+"&"+
                            URLEncoder.encode("dob","UTF-8")+"="+URLEncoder.encode(dob,"UTF-8")+"&"+
                            URLEncoder.encode("birthplace","UTF-8")+"="+URLEncoder.encode(birthplace,"UTF-8")+"&"+
                    URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(grow,"UTF-8")+"&"+
                            URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8")+"&"+
                            URLEncoder.encode("caste","UTF-8")+"="+URLEncoder.encode(caste,"UTF-8")+"&"+
                            URLEncoder.encode("religion","UTF-8")+"="+URLEncoder.encode(religion,"UTF-8")+"&"+
                    URLEncoder.encode("aadharno","UTF-8")+"="+URLEncoder.encode(aadar,"UTF-8")+"&"+
                            URLEncoder.encode("sscpass","UTF-8")+"="+URLEncoder.encode(sscpass,"UTF-8")+"&"+
                            URLEncoder.encode("sscmarks","UTF-8")+"="+URLEncoder.encode(sscmarks,"UTF-8")+"&"+
                            URLEncoder.encode("sscpercent","UTF-8")+"="+URLEncoder.encode(sscpercent,"UTF-8")+"&"+
                    URLEncoder.encode("sscattempt","UTF-8")+"="+URLEncoder.encode(ssca,"UTF-8")+"&"+
                            URLEncoder.encode("interyearpass","UTF-8")+"="+URLEncoder.encode(interyearpass,"UTF-8")+"&"+
                            URLEncoder.encode("intermarks","UTF-8")+"="+URLEncoder.encode(intermarks,"UTF-8")+"&"+
                            URLEncoder.encode("interpercent","UTF-8")+"="+URLEncoder.encode(interpercent,"UTF-8")+"&"+
                    URLEncoder.encode("interattempt","UTF-8")+"="+URLEncoder.encode(intera,"UTF-8")+"&"+
                            URLEncoder.encode("diplomayearpass","UTF-8")+"="+URLEncoder.encode(diplomayearpass,"UTF-8")+"&"+
                            URLEncoder.encode("diplomamarks","UTF-8")+"="+URLEncoder.encode(diplomamarks,"UTF-8")+"&"+
                            URLEncoder.encode("diplomapercent","UTF-8")+"="+URLEncoder.encode(dp,"UTF-8")+"&"+
                    URLEncoder.encode("diplomaattempt","UTF-8")+"="+URLEncoder.encode(da,"UTF-8")+"&"+
                            URLEncoder.encode("btech1yearpass","UTF-8")+"="+URLEncoder.encode(btech1yearpass,"UTF-8")+"&"+
                            URLEncoder.encode("btech1marks","UTF-8")+"="+URLEncoder.encode(btech1marks,"UTF-8")+"&"+
                            URLEncoder.encode("btech1percent","UTF-8")+"="+URLEncoder.encode(b1p,"UTF-8")+"&"+
                    URLEncoder.encode("btech1attempt","UTF-8")+"="+URLEncoder.encode(b1a,"UTF-8")+"&"+
                            URLEncoder.encode("btech1backlog","UTF-8")+"="+URLEncoder.encode(btech1backlog,"UTF-8")+"&"+
                            URLEncoder.encode("btechII1yearpass","UTF-8")+"="+URLEncoder.encode(btechII1yearpass,"UTF-8")+"&"+
                            URLEncoder.encode("btechII1marks","UTF-8")+"="+URLEncoder.encode(btechII1marks,"UTF-8")+"&"+
                    URLEncoder.encode("btechII1percent","UTF-8")+"="+URLEncoder.encode(bII1P,"UTF-8")+"&"+
                            URLEncoder.encode("btechII1attempt","UTF-8")+"="+URLEncoder.encode(btechII1attempt,"UTF-8")+"&"+
                            URLEncoder.encode("btechII1backlog","UTF-8")+"="+URLEncoder.encode(btechII1backlog,"UTF-8")+"&"+
                            URLEncoder.encode("btechII2yearpass","UTF-8")+"="+URLEncoder.encode(btechII2yearpass,"UTF-8")+"&"+
                            URLEncoder.encode("btechII2marks","UTF-8")+"="+URLEncoder.encode(btechII2marks,"UTF-8")+"&"+
                            URLEncoder.encode("btechII2percent","UTF-8")+"="+URLEncoder.encode(bII2P,"UTF-8")+"&"+
                            URLEncoder.encode("btechII2attempt","UTF-8")+"="+URLEncoder.encode(btechII2attempt,"UTF-8")+"&"+
                            URLEncoder.encode("btechII2backlog","UTF-8")+"="+URLEncoder.encode(btechII2backlog,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII1yearpass","UTF-8")+"="+URLEncoder.encode(btechIII1yearpass,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII1marks","UTF-8")+"="+URLEncoder.encode(btechIII1marks,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII1percent","UTF-8")+"="+URLEncoder.encode(bIII1P,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII1attempt","UTF-8")+"="+URLEncoder.encode(btechIII1attempt,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII1backlog","UTF-8")+"="+URLEncoder.encode(btechIII1backlog,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII2yearpass","UTF-8")+"="+URLEncoder.encode(btechIII2yearpass,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII2marks","UTF-8")+"="+URLEncoder.encode(btechIII2marks,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII2percent","UTF-8")+"="+URLEncoder.encode(bIII2P,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII2attempt","UTF-8")+"="+URLEncoder.encode(btechIII2attempt,"UTF-8")+"&"+
                            URLEncoder.encode("btechIII2backlog","UTF-8")+"="+URLEncoder.encode(btechIII2backlog,"UTF-8")+"&"+
                            URLEncoder.encode("fathername","UTF-8")+"="+URLEncoder.encode(fn,"UTF-8")+"&"+
                            URLEncoder.encode("foccupation","UTF-8")+"="+URLEncoder.encode(foc,"UTF-8")+"&"+
                            URLEncoder.encode("forganization","UTF-8")+"="+URLEncoder.encode(forganization,"UTF-8")+"&"+
                            URLEncoder.encode("fdesignation","UTF-8")+"="+URLEncoder.encode(fdesignation,"UTF-8")+"&"+
                            URLEncoder.encode("fcellno","UTF-8")+"="+URLEncoder.encode(fcellno,"UTF-8")+"&"+
                            URLEncoder.encode("mothername","UTF-8")+"="+URLEncoder.encode(mn,"UTF-8")+"&"+
                            URLEncoder.encode("moccupation","UTF-8")+"="+URLEncoder.encode(moc,"UTF-8")+"&"+
                            URLEncoder.encode("morganization","UTF-8")+"="+URLEncoder.encode(morganization,"UTF-8")+"&"+
                            URLEncoder.encode("mdesignation","UTF-8")+"="+URLEncoder.encode(mdesignation,"UTF-8")+"&"+
                            URLEncoder.encode("mcellno","UTF-8")+"="+URLEncoder.encode(mcellno,"UTF-8")+"&"+
                            URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(location,"UTF-8")+"&"+
                            URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(state,"UTF-8")+"&"+"&"+
                            URLEncoder.encode("pincode","UTF-8")+"="+URLEncoder.encode(pincode,"UTF-8")+"&"+
                            URLEncoder.encode("studentcellno","UTF-8")+"="+URLEncoder.encode(studentcellno,"UTF-8")

                            ;
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
                if(code.equals("myprofilereg_true")) {
                    showDialog("Registration Success", message, code);
                }
                else if(code.equals("myprofilereg_false"))
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
            if(code.equals("myprofilereg_true")||code.equals("myprofilereg_false"))
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
