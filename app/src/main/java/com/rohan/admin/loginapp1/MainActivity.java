package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    Activity activity;

    private SwipeRefreshLayout swipeContainer;
    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarData BARDATA ;
    TextView text1,text2,text3,text4,text5,text6,text7,year,total;
    //  public String main_url = "http://dragon321.esy.es/graph_count.php";
    MyClass[] obj2 ={
         //  new MyClass("2014", 2014),
          //    new MyClass("2015", 2015),
            new MyClass("2016", 2016),
            new MyClass("2011", 2011),
            new MyClass("2018", 2018),
            new MyClass("2019", 2019),
            new MyClass("2020", 2020),
            new MyClass("2021", 2021),
            new MyClass("2022", 2022)
    };
    BarDataSet Bardataset ;
    Spinner spinner1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.cse);
        text2 = (TextView) findViewById(R.id.ece);
        text3 = (TextView) findViewById(R.id.mech);
        text4 = (TextView) findViewById(R.id.civil);
        text5 = (TextView) findViewById(R.id.eee);
        text6 = (TextView) findViewById(R.id.eie);
        text7 = (TextView) findViewById(R.id.it);
        year = (TextView) findViewById(R.id.year);
        total = (TextView) findViewById(R.id.total);


        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
      //   Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        year.setText(formattedDate);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(MainActivity.this, R.layout.spinner_layout, obj2);
        adapter1.setDropDownViewResource(R.layout.spinner_layout);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);



        year = (TextView) findViewById(R.id.year);



        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            BackgroundTask BackgroundTask = new BackgroundTask(MainActivity.this);
            BackgroundTask.execute("year",year.getText().toString());


        }
        else
        {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }



        }

    AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {


                    MyClass obj = (MyClass)(parent.getItemAtPosition(position));

                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                   // ((TextView) parent.getChildAt(0)).setTextSize(20);

                    year.setText(String.valueOf(obj.getValue()));


                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            BackgroundTask BackgroundTask = new BackgroundTask(MainActivity.this);
                            BackgroundTask.execute("year", year.getText().toString());


                        }

                    }




                @Override
                public void onNothingSelected(AdapterView<?> parent) {}

            };





    public void graphchart()

    {



        HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.chart);
        BARENTRY = new ArrayList<>();
        // text1 = (TextView) findViewById(R.id.text);
        BarEntryLabels = new ArrayList<String>();
        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "DEPARTMENT");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.JOYFUL_COLORS);

        barChart.setData(BARDATA);

        barChart.animateY(3000);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





    public class MyClass{

        private String text;
        private int value;


        public MyClass(String text, int value){
            this.text = text;
            this.value = value;
        }

        public void setText(String text){
            this.text = text;
        }

        public String getText(){
            return this.text;
        }

        public void setValue(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }
    public class MySpinnerAdapter extends ArrayAdapter<MyClass>{

        private Context context;
        private MyClass[] myObjs;

        public MySpinnerAdapter(Context context, int textViewResourceId,
                                MyClass[] myObjs) {
            super(context, textViewResourceId, myObjs);
            this.context = context;
            this.myObjs = myObjs;
        }

        public int getCount(){
            return myObjs.length;
        }

        public MyClass getItem(int position){
            return myObjs[position];
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setText(myObjs[position].getText());
            return label;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = new TextView(context);
            label.setText(myObjs[position].getText());
            return label;
        }
    }
    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String main_url = "http://dragon321.esy.es/graph_count.php";

        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;

        public BackgroundTask(Context ctx)
        {

            this.ctx = ctx;
            activity = (Activity)ctx;
        }



        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals("year"))
            {
                try {
                    URL url = new URL(main_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,("UTF-8")));
                    String year = params[1];

                    String data = URLEncoder.encode("year","UTF-8")+"="+URLEncoder.encode(year,"UTF-8");
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
                    Thread.sleep(0);

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

                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject JO = jsonArray.getJSONObject(0);
                String code = JO.getString("code");
                String message = JO.getString("message");
                String message1 = JO.getString("message1");
                String message2 = JO.getString("message2");
                String message3 = JO.getString("message3");
                String message4 = JO.getString("message4");
                String message5 = JO.getString("message5");
                String message6 = JO.getString("message6");
                if(code.equals("date_true")) {

                    text2.setText(message);
                    text1.setText(message1);
                    text3.setText(message2);
                    text5.setText(message3);
                    text4.setText(message4);
                    text7.setText(message5);
                    text6.setText(message6);

                    graphchart();

                }



            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }
    public void AddValuesToBARENTRY(){




        // text1 = (TextView) findViewById(R.id.cse);
        String s1 = text1.getText().toString();
        int a = Integer.parseInt(s1);
        // text2 = (TextView) findViewById(R.id.ece);
        String s2 = text2.getText().toString();
        int b = Integer.parseInt(s2);
        // text3 = (TextView) findViewById(R.id.mech);
        String s3 = text6.getText().toString();
        int c = Integer.parseInt(s3);
        String s4 = text4.getText().toString();
        int d = Integer.parseInt(s4);
        String s5 = text5.getText().toString();
        int e = Integer.parseInt(s5);
        String s6 = text3.getText().toString();
        int f = Integer.parseInt(s6);
        String s7 = text7.getText().toString();
        int g = Integer.parseInt(s7);

        BARENTRY.add(new BarEntry(a, 0));
        BARENTRY.add(new BarEntry(b, 1));
        BARENTRY.add(new BarEntry(c, 2));
        BARENTRY.add(new BarEntry(d, 3));
        BARENTRY.add(new BarEntry(e, 4));
        BARENTRY.add(new BarEntry(f, 5));
        BARENTRY.add(new BarEntry(g, 6));

        int tot = a+b+c+d+e+f+g;
        total.setText(Integer.toString(tot));

    }


    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("CSE");
        BarEntryLabels.add("ECE");
        BarEntryLabels.add("EEE");
        BarEntryLabels.add("MECH");
        BarEntryLabels.add("IT");
        BarEntryLabels.add("CIVIL");
        BarEntryLabels.add("EIE");

    }

}
