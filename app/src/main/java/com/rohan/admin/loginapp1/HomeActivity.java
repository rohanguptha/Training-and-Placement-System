package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity {
    private TextView btnLogout,myprofile;
CircleImageView  studimg;
    EditText email,password;
Bitmap bmImg;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    Context ctx;
    TextView signup_text1,uploadstatus;
    ProgressDialog progressDialog;
    static int BUFFER_SIZE= 1024 * 2;
    private Uri filePath;
    android.support.v7.app.AlertDialog.Builder builder;
    Activity activity;
    TextView textView,textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        textView = (TextView) findViewById(R.id.welcome_txt);
        String message = getIntent().getStringExtra("message");
        textView.setText(message);

        uploadstatus=(TextView)findViewById(R.id.uploadstatus);

        textView1 = (TextView) findViewById(R.id.branch);
        String message1 = getIntent().getStringExtra("message1");
        textView1.setText(message1);


        textView2 = (TextView) findViewById(R.id.rname);
        String message2 = getIntent().getStringExtra("message2");
        textView2.setText(message2);



        myprofile = (TextView)findViewById(R.id.Myprofile);
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,Myprofile_Student.class);
                intent.putExtra("message",textView.getText().toString());
                startActivity(intent);
            }
        });


studimg=(CircleImageView)findViewById(R.id.profile_image );

        new LoadImage().execute("http://dragon321.esy.es/uploads/"+message+".png");



  //     bmImg=loadBitmap("http://dragon321.esy.es/uploads/"+message+".png");
//        studimg.setImageBitmap(bmImg);

       // Toast.makeText(HomeActivity.this,"path="+"http://dragon321.esy.es/uploads/"+message+".png",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Logout?");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to logout?");
        // Setting Icon to Dialog

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
    public void showGreeting(View view)
    {
        String button_text;

        button_text = ((Button) view).getText().toString();
        if (button_text.equals("Training"))
        {
            Intent intent = new Intent(this, TrainingActivity.class);
            intent.putExtra("message",textView.getText().toString());
            intent.putExtra("message1",textView1.getText().toString());
            intent.putExtra("message2",textView2.getText().toString());
            startActivity(intent);

        }
        else if(button_text.equals("Placement"))
        {
            Intent intent = new Intent(this, PlacementActivity.class);
            intent.putExtra("message",textView.getText().toString());
            intent.putExtra("message1",textView1.getText().toString());
            intent.putExtra("message2",textView2.getText().toString());
            startActivity(intent);
        }
        else if(button_text.equals("My Activities"))
        {
            Intent intent = new Intent(this, Myactivities_Student.class);
            intent.putExtra("message",textView.getText().toString());

            startActivity(intent);
        }
        else if(button_text.equals("Registered Companies"))
        {
            Intent intent = new Intent(this, Mycompany_Student.class);
            intent.putExtra("message",textView.getText().toString());

            startActivity(intent);
        }

    }

    public class registerBackgroundTask extends AsyncTask<String,Void,String> {
        String register_url = "http://dragon321.esy.es/uploadfile.php";

        Context ctx;

        Activity activity;
        android.support.v7.app.AlertDialog.Builder builder;



        public registerBackgroundTask(Context ctx)
        {

            this.ctx = ctx;
            activity = (Activity)ctx;
        }

        @Override
        protected void onPreExecute()
        {
            builder = new android.support.v7.app.AlertDialog.Builder(activity);
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Uploading....");
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

                    String data = URLEncoder.encode("rollno","UTF-8")+"="+URLEncoder.encode(rollno,"UTF-8")+"&"+
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
                    Thread.sleep(100);

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
                   // showDialog("Upload Success", message, code);
                    //Name.setText(getStringImage(bitmap));
                   // Toast.makeText(HomeActivity.this,"path="+"http://dragon321.esy.es/uploads/"+textView.getText().toString()  +".png",Toast.LENGTH_LONG).show();
                    new LoadImage().execute("http://dragon321.esy.es/uploads/"+textView.getText().toString()+".png");

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
            android.support.v7.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();



        }


    }






    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        private ProgressDialog pDialog = new ProgressDialog(HomeActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // pDialog = new ProgressDialog(HomeActivity.this);
           // pDialog.setMessage("Loading Image ....");
           // pDialog.show();
           // Toast.makeText(HomeActivity.this, "Uploading...", Toast.LENGTH_SHORT).show();

        }
        protected Bitmap doInBackground(String... args) {
            try {

                bmImg=BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap image) {


            if(image != null){
                studimg.setImageBitmap(image);
               // pDialog.dismiss();

            }else{

                pDialog.dismiss();

                Toast.makeText(HomeActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();



            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.upload ) {
            showFileChooser();
          //  HomeActivity.registerBackgroundTask registerBackgroundTask = new HomeActivity.registerBackgroundTask(HomeActivity .this);
          //  registerBackgroundTask.execute("Register",textView.getText().toString());

            return true;
        }
        if(id==R.id.log ){

            //onBackPressed();
            Toast.makeText(HomeActivity.this, "logging out...", Toast.LENGTH_SHORT).show();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
               // uploadstatus.setText(filename);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                HomeActivity.registerBackgroundTask registerBackgroundTask = new HomeActivity.registerBackgroundTask(HomeActivity .this);
                 registerBackgroundTask.execute("Register",textView.getText().toString());


            } catch (IOException e) {
                e.printStackTrace();
            }
            //   Toast.makeText(RegisterActivity.this,"path="+filePath,Toast.LENGTH_LONG).show();

        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
