package com.rohan.admin.loginapp1;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class FirstActivity extends TabActivity {

    Activity activity;
    TabHost TabHostWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        TabHostWindow = (TabHost) findViewById(android.R.id.tabhost);

        //Creating tab menu.
       TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("HOME");

        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("LOGIN");

        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("ALERTS");

        //Setting up tab 1 name.

        TabMenu1.setIndicator("",getResources().getDrawable(R.drawable.icon_photos_tab));
        //Set tab 1 activity to tab 1 menu.
       TabMenu1.setContent(new Intent(this, MainActivity.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("",getResources().getDrawable(R.drawable.icon_songs_tab));
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this, LoginActivity.class));


        //Setting up tab 2 name.
        TabMenu3.setIndicator("",getResources().getDrawable(R.drawable.icon_videos_tab));
        //Set tab 3 activity to tab 1 menu.
        TabMenu3.setContent(new Intent(this, StudentsAlerts.class));

        //Setting up tab 2 name.

        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);
    }
}
