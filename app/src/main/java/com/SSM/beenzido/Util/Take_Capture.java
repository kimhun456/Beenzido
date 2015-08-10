package com.SSM.beenzido.Util;

/**
 * Created by HyunJae on 2015-08-11.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***************사용법 *************************
 *
 *    View 파라미터에는 Layout을 넣어주면 스크린샷이 찍인다
 *
 *    path 는 /DCIM/Beenzido/image명
 *
 *   Take_Capture.getInstance().takeScreenshot(linearLayout);
 *
 *
 *    전체 화면을 찍기 위해서는
 *    View rootView = findViewById(android.R.id.content).getRootView();
 *    Take_Capture.getInstance().takeScreenshot(rootView);
 *
 *    을 실행시킨다.
 */


public class Take_Capture {
    private static Take_Capture instance =null;
    public static Take_Capture getInstance(){
        if(instance==null){
            instance = new Take_Capture();
        }
        return instance;
    }

    private Take_Capture(){}


    public String takeScreenshot(Context context ,View view) {

        view.setDrawingCacheEnabled(true);
        return saveBitmap(context, view.getDrawingCache());

    }

    public String saveBitmap(Context context, Bitmap bitmap) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss");

        String namePostfix = format.format(new Date());

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Beenzido";
        File file = new File(path);

        if(!file.exists()){
            file.mkdirs();
        }
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Beenzido/beenzido_"+ namePostfix +".png";

        File imagePath = new File(path);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return path;

    }

}
