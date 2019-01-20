package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.util.List;
import java.util.Map;

public class FacultyHomeActivity extends AppCompatActivity {

    private String jsonResult;
    public String url ="http://dragon321.esy.es/facultypower.php";

    ListView listView;
    List<Map<String,String>> activity = new ArrayList<Map<String,String>>();
    private TextView btnLogout;
    private SwipeRefreshLayout swipeContainer;
    TextView verify,textView,accept;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });


        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new ItemList());

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            accessWebService();
            }
        else
        {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        btnLogout = (TextView)findViewById(R.id.btnLogoutfac);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }


    private class ItemList implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ViewGroup viewg=(ViewGroup)view;
            TextView textv=(TextView)viewg.findViewById(R.id.frollno);

            Intent intent = new Intent(FacultyHomeActivity.this,VerifyStudentActivity.class);
            intent.putExtra("message",textv.getText().toString());
            startActivity(intent);
           // Toast.makeText(getApplicationContext(),textv.getText().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FacultyHomeActivity.this);
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

    private class JsonReadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try{
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();

            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is){
            String rLine ="";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try{
                while((rLine = rd.readLine()) != null){
                    answer.append(rLine);
                }
            }catch(IOException e){
                Toast.makeText(getApplicationContext(),"error ..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return  answer;
        }

        @Override
        protected void onPostExecute(String result){
            ListDrwaer(); // we will create it later
        }
    }

    public void accessWebService(){
        JsonReadTask task = new JsonReadTask();
        task.execute(new String[] {url});
    }

    public void ListDrwaer (){
        try{
            JSONObject jsonResonse = new JSONObject(jsonResult.substring(jsonResult.indexOf("{"), jsonResult.lastIndexOf("}")+1));
            JSONArray jsonMainNode = jsonResonse.optJSONArray("name");

            final ArrayList<HashMap<String,String>> MyArrList = new ArrayList<HashMap<String, String>>();

            HashMap<String,String> map;

            for(int i=0; i<jsonMainNode.length(); i++){
                JSONObject c = jsonMainNode.getJSONObject(i);

                map = new HashMap<String,String>();

                map.put("rollno", c.getString("rollno"));
                map.put("name", c.getString("name"));


                MyArrList.add(map);

                SimpleAdapter sAdap;
                sAdap = new SimpleAdapter(FacultyHomeActivity.this, MyArrList, R.layout.listofstudents, new String[]{"rollno","name"}, new int[]{R.id.frollno, R.id.fname});

                listView.setAdapter(sAdap);





            }
        }catch (JSONException e){
            Toast.makeText(getApplicationContext(),"error ..." + e.toString(), Toast.LENGTH_LONG).show();
        }
    }




}