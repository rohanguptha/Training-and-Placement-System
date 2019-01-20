package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class LoginActivity extends Activity {
    EditText Pass,Email;
    Button login_button;
   
  TextView signup_text,admin;
    String login_url;
    private RadioGroup radioUserGroup;
    private RadioButton radioUserButton;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = (EditText) findViewById(R.id.email);
        Pass = (EditText) findViewById(R.id.password);

        signup_text = (TextView) findViewById(R.id.sign_up);
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if(saveLogin == true)
        {
            Email.setText(loginPreferences.getString("username", ""));
            Pass.setText(loginPreferences.getString("password",""));
            saveLoginCheckBox.setChecked(true);
        }





        radioUserGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioUserGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if (checkedId == R.id.radiostudent) {

                    login_url = "http://dragon321.esy.es/login.php";

                } else if (checkedId == R.id.radioadmin) {

                    login_url = "http://dragon321.esy.es/adminlogin.php";
                } else if(checkedId == R.id.radiofaculty){

                    login_url = "http://dragon321.esy.es/facultylogin.php";
                }

            }
        });


        login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_url==null)
                {
                    Snackbar snack = Snackbar.make(v, "Please select user type..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    ViewGroup group = (ViewGroup) snack.getView();
                    group.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.btn_logut_bg));
                    snack.show();
                }
                else
                {

                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected())
                    {
                        if (Email.getText().toString().equals(""))

                        {
                           Email.setError("enter email...") ;
                        }
                        else if(Pass.getText().toString().equals("") )
                        {
                            Pass.setError("enter password...") ;
                        }
                        else
                        {
                            loginBackgroundTask loginBackgroundTask = new loginBackgroundTask(LoginActivity.this);
                            loginBackgroundTask.execute("login", Email.getText().toString(), Pass.getText().toString());
                        }
                    }
                    else
                    {

                        Snackbar snack = Snackbar.make(v, "Please connect to internet..", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        ViewGroup group = (ViewGroup) snack.getView();
                        group.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.btn_logut_bg));
                        snack.show();
                    }
                }
            }
        });



    }

    public class loginBackgroundTask extends AsyncTask<String,Void,String> {


        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;
        public loginBackgroundTask(Context ctx)
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
            if(method.equals("login"))
            {
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,("UTF-8")));
                    String email,password;
                    email = params[1];
                    password = params[2];
                    String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
        protected void onPostExecute(String json)
        {
            try {
                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject JO = jsonArray.getJSONObject(0);
                String code = JO.getString("code");
                String message = JO.getString("message");
                String message1 = JO.getString("message1");
                String message2 = JO.getString("message2");
                if(code.equals("login_true"))
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Email.getWindowToken(), 0);
                    String username = Email.getText().toString();
                    String password = Pass.getText().toString();
                    if(saveLoginCheckBox.isChecked())
                    {
                     loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    }
                    Intent intent = new Intent(activity,HomeActivity.class);
                    intent.putExtra("message",message);
                    intent.putExtra("message1",message1);
                    intent.putExtra("message2",message2);
                    activity.startActivity(intent);
                }
                else if(code.equals("adminlogin_true"))
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Email.getWindowToken(), 0);
                    String username = Email.getText().toString();
                    String password = Pass.getText().toString();
                    if(saveLoginCheckBox.isChecked())
                    {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username",username);
                        loginPrefsEditor.putString("password",password);
                        loginPrefsEditor.commit();
                    }
                    Intent intent = new Intent(activity,AdminHomeActivity.class);
                intent.putExtra("message",message);
                activity.startActivity(intent);
            }
                else if(code.equals("verify_false"))
                {
                    Intent intent = new Intent(activity,Student_verify.class);
                    intent.putExtra("message",message);
                    intent.putExtra("message1",message1);
                    activity.startActivity(intent);
                }
                else if(code.equals("facultylogin_true"))
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Email.getWindowToken(), 0);
                    String username = Email.getText().toString();
                    String password = Pass.getText().toString();
                    if(saveLoginCheckBox.isChecked())
                    {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username",username);
                        loginPrefsEditor.putString("password",password);
                        loginPrefsEditor.commit();
                    }
                    Intent intent = new Intent(activity,FacultyHomeActivity.class);
                    intent.putExtra("message",message);
                    activity.startActivity(intent);
                }
                else if(code.equals("login_false"))
                {   loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                    showDialog("Login Error...", message, code);
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public void showDialog(String title,String  message,String code)
        {
            builder.setTitle(title);
            if(code.equals("login_false"))
            {
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText email,password;
                        email = (EditText) activity.findViewById(R.id.email);
                        password = (EditText) activity.findViewById(R.id.password);
                        email.setText("");
                        password.setText("");
                        dialog.dismiss();
                    }
                });

            }
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }


    }

}
