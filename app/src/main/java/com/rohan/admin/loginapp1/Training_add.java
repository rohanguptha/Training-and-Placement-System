package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.Calendar;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Training_add extends AppCompatActivity {
    EditText Activiy_name,time,date,details;
    Button add_button;
    Button datedialog,document;
    private int PICK_IMAGE_REQUEST = 1;
    int year_x,month_x,day_x;
    static final int DIALOG_ID=0;

    private int PICK_FILE_REQUEST = 1;
    ProgressDialog progressDialog;
    private Uri filePath;
    AlertDialog.Builder builder;

    private static final String TAG = MainActivity.class.getSimpleName();
    private String selectedFilePath;
    private String SERVER_URL = "http://dragon321.esy.es/UploadToServer.php";
    ImageView ivAttachment;

    TextView tvFileName,documentid;
    ProgressDialog dialog;
String filename="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_add);
        Activiy_name = (EditText)findViewById(R.id.add_activity);
        time = (EditText)findViewById(R.id.add_time);
        date = (EditText)findViewById(R.id.add_date);
        details = (EditText)findViewById(R.id.info_activity);
        documentid =(TextView)findViewById( R.id.documentid ) ;
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

        add_button = (Button)findViewById(R.id.add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (Activiy_name.getText().toString().equals("") || time.getText().toString().equals("") || date.getText().toString().equals(""))

                    {
                        Snackbar snack = Snackbar.make(v, "Please fill details..", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        ViewGroup group = (ViewGroup) snack.getView();
                        group.setBackgroundColor(ContextCompat.getColor(Training_add.this, R.color.btn_logut_bg));
                        snack.show();

                    } else {
                        addBackgroundTask addbackgroundTask = new addBackgroundTask(Training_add.this);
                        addbackgroundTask.execute("ADD",Activiy_name.getText().toString(), time.getText().toString(), date.getText().toString(),details.getText().toString());

                    }
                }
                else{
                    //bLogin.setVisibility(View.INVISIBLE);
                    // bLogin.setEnabled(false);
                    Snackbar snack = Snackbar.make(v, "Please connect to internet..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    ViewGroup group = (ViewGroup) snack.getView();
                    group.setBackgroundColor(ContextCompat.getColor(Training_add.this, R.color.btn_logut_bg));
                    snack.show();
                }
            }
        });

        document  = (Button)findViewById(R.id.document);
        document .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
            }
        });







    }

    public void showDialogOnButtonClick()

    {
        datedialog = (Button)findViewById(R.id.datedialog);
        datedialog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);

                    }
                }
        );

    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id== DIALOG_ID)
            return new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            date.setText(year_x + "-" + month_x + "-" + day_x);
          // Toast.makeText(Training_add.this,year_x  + "/" + month_x + "/" + day_x,Toast.LENGTH_LONG).show();
        }
    };

    public class addBackgroundTask extends AsyncTask<String,Void,String> {
        String add_url = "http://dragon321.esy.es/training_add.php";

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

                    Random r=new Random();
                    String file1=selectedFilePath.toString();
                    int index=file1.lastIndexOf("/");
                    file1=file1.substring(index+1);
                    filename=r.nextInt(1000)+""+r.nextInt(9999)+file1;

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
                    String info = params[4];
                    String data = URLEncoder.encode("activity", "UTF-8") + "=" + URLEncoder.encode(activity, "UTF-8") + "&" +
                            URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                            URLEncoder.encode("info", "UTF-8") + "=" + URLEncoder.encode(info, "UTF-8"  )+"&"+
                    URLEncoder.encode("filename", "UTF-8") + "=" + URLEncoder.encode(filename, "UTF-8"  );
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

                    //on upload button Click
                    if(selectedFilePath != null){
                        dialog = ProgressDialog.show(Training_add.this,"","Uploading File...",true);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //creating new thread to handle Http Operations


                                uploadFile(selectedFilePath);
                            }
                        }).start();
                    }else{
                        Toast.makeText(Training_add.this,"Please choose a File First",Toast.LENGTH_SHORT).show();
                    }

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

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("application/pdf");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                String filename=filePath.toString();
               // Toast.makeText(Training_add.this,"path="+filePath.toString(),Toast.LENGTH_LONG).show();
                int i=filename.lastIndexOf("/");
                filename=filename.substring(i+1);
                documentid.setText(filename);
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    tvFileName.setText(selectedFilePath);


                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //   Toast.makeText(RegisterActivity.this,"path="+filePath,Toast.LENGTH_LONG).show();

        }
    }


    public int uploadFile(final String selectedFilePath){

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        if (!selectedFile.isFile()){
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   //tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());



                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + filename + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Training_add .this,"File Not Found",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(Training_add.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Training_add.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }

    }
}






