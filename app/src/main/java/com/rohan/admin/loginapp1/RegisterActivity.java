package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.rohan.admin.loginapp1.R.id.imageView;

public class RegisterActivity extends AppCompatActivity {
    EditText Name,Email,Pass,ConPass,Rollno;
    Button reg_button,uploadimg;
    TextView signup_text1,uploadstatus;
    Activity activity;
    AlertDialog.Builder builder;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    ProgressDialog progressDialog;
    private Uri filePath;
    String upLoadServerUri ="http://dragon321.esy.es/uploadfile.php";

    int serverResponseCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Rollno = (EditText)findViewById(R.id.rollno);
        Name = (EditText)findViewById(R.id.reg_name);
        Email = (EditText)findViewById(R.id.reg_email);
        Pass = (EditText)findViewById(R.id.reg_password);
        ConPass = (EditText)findViewById(R.id.reg_con_password);
uploadstatus=(TextView)findViewById(R.id.uploadstatus);
        uploadimg = (Button)findViewById(R.id.uploadimg);
        uploadimg.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             showFileChooser();
                                         }
                                     });



        reg_button = (Button)findViewById(R.id.reg_button);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (Rollno.getText().toString().equals("") || Name.getText().toString().equals("") || Email.getText().toString().equals("") || Pass.getText().toString().equals(""))

                    {
                        Snackbar.make(v, "Please fill details..", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else if (!(Pass.getText().toString().equals(ConPass.getText().toString())))
                    {
                        Snackbar.make(v, "Passwords are not matching..", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        registerBackgroundTask registerBackgroundTask = new registerBackgroundTask(RegisterActivity.this);
                        registerBackgroundTask.execute("Register",Rollno.getText().toString(), Name.getText().toString(), Email.getText().toString(), Pass.getText().toString());
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
        String register_url = "http://dragon321.esy.es/Register.php";

        Context ctx;

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
                    String uploadImage =getStringImage(bitmap);
                    URL url = new URL(register_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,("UTF-8")));
                    String rollno = params[1];
                    String name = params[2];
                    String email = params[3];
                    String password = params[4];
                    String data = URLEncoder.encode("rollno","UTF-8")+"="+URLEncoder.encode(rollno,"UTF-8")+"&"+
                            URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                            URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(uploadImage,"UTF-8");
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
                if(code.equals("reg_true")) {
                    showDialog("Registration Success", message, code);
                    //Name.setText(getStringImage(bitmap));

                }
                else if(code.equals("reg_false"))
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
            if(code.equals("reg_true")||code.equals("reg_false"))
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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                String filename=filePath.toString();
                int i=filename.lastIndexOf("/");
                filename=filename.substring(i+1);
                uploadstatus.setText(filename);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


            } catch (IOException e) {
                e.printStackTrace();
            }
         //   Toast.makeText(RegisterActivity.this,"path="+filePath,Toast.LENGTH_LONG).show();

        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 1000, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
