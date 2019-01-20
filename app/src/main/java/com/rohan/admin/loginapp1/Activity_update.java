package com.rohan.admin.loginapp1;

import android.content.Context;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_update extends ActionBarActivity {

    private String jsonResult;
    public String url ="http://dragon321.esy.es/straining.php";
    ListView listView;
    List<Map<String,String>> activity = new ArrayList<Map<String,String>>();

    TextView ADD,delete,edit;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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



        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swiperefresh1);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });




        ADD = (TextView)findViewById(R.id.add);
        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_update.this,Training_add.class));

            }
        });
        delete = (TextView)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_update.this,Training_del.class));

            }
        });
        edit = (TextView)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_update.this,Training_edit.class));

            }
        });

    }

    private class ItemList implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ViewGroup viewg=(ViewGroup)view;
            TextView textv=(TextView)viewg.findViewById(R.id.tactivity);
            TextView textc=(TextView)viewg.findViewById(R.id.pdfname);

           Intent intent = new Intent(Activity_update.this,TrainingActivity_admin_info.class);
            intent.putExtra("activity",textv.getText().toString());
            intent.putExtra("pdfname",textc.getText().toString());

            startActivity(intent);
         //   Toast.makeText(getApplicationContext(),textv.getText().toString(), Toast.LENGTH_LONG).show();
        }
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
            JSONArray jsonMainNode = jsonResonse.optJSONArray("activity");

            final ArrayList<HashMap<String,String>> MyArrList = new ArrayList<HashMap<String, String>>();

            HashMap<String,String> map;

            for(int i=0; i<jsonMainNode.length(); i++){
                JSONObject c = jsonMainNode.getJSONObject(i);

                map = new HashMap<String,String>();

                map.put("activity", c.getString("activity"));
                map.put("time", c.getString("time"));
                map.put("date", c.getString("date"));
                map.put("document", c.getString("document"));
                MyArrList.add(map);

                SimpleAdapter sAdap;
                sAdap = new SimpleAdapter(Activity_update.this, MyArrList, R.layout.activity_column, new String[]{"activity","time","date","document"}, new int[]{R.id.tactivity, R.id.ttime, R.id.tdate,R.id.pdfname});

                listView.setAdapter(sAdap);


            }
        }catch (JSONException e){
            Toast.makeText(getApplicationContext(),"error ..." + e.toString(), Toast.LENGTH_LONG).show();
        }
    }




}