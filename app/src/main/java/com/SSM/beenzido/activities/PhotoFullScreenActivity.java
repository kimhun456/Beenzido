package com.SSM.beenzido.activities;

import com.SSM.beenzido.R;
import com.SSM.beenzido.Util.Util;
import com.SSM.beenzido.adapter.FullScreenImageAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;


public class PhotoFullScreenActivity extends Activity{

    private Util utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_view);

        viewPager = (ViewPager) findViewById(R.id.pager);

        utils = new Util(getApplicationContext());

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);

        adapter = new FullScreenImageAdapter(PhotoFullScreenActivity.this,
                utils.getFilePaths(PhotoFullScreenActivity.this));

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }
}