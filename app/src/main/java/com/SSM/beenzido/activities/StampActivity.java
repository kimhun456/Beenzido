package com.SSM.beenzido.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.SSM.beenzido.R;
import com.SSM.beenzido.Util.GPStracker;
import com.SSM.beenzido.Util.Util;

public class StampActivity extends Activity {

    private GPStracker gps;
    private Button get_location_btn;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);

        get_location_btn = (Button)findViewById(R.id.get_location_btn);

        get_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_location();



            }
        });

    }


    private void get_location(){

        gps = new GPStracker(getApplicationContext());
        gps.getLocation();
        latitude =gps.getLatitude();
        longitude = gps.getLongitude();
        Util.Log("latitude : "  +  latitude + "longitude : " + longitude);
        Util.showToast(getApplicationContext(),"latitude : "  +  latitude + "longitude : " + longitude);

    }





}
