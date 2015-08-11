package com.SSM.beenzido.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;


public class Util {

    private Context _context;

    // constructor
    public Util(Context context) {
        this._context = context;
    }
    
	/**
	 * @Date 2015.08.10 Add by DBK
	 * @brief Log 메소드
	 */
    public static void Log(Object... obj)
    {
        if(Constant.IS_DEBUG)
        {
            if(obj != null)
            {
                if(obj.length <= 1)
                {
                    Log.d(Constant.LOG_TAG, String.valueOf(obj[0]));
                }
                else
                {
                    for(int i=0; i<obj.length; i++)
                    {
                        Log.d(Constant.LOG_TAG, String.valueOf(obj[i]));
                    }
                }
            }
            else
            {
                Log.d(Constant.LOG_TAG, null);
            }
        }
    }
    

    public static void Log(String indentity, Object obj)
    {
        if(Constant.IS_DEBUG)
        {
            Log.d(Constant.LOG_TAG, indentity + " : " + String.valueOf(obj));
        }
    }
    
    public static void Log(Exception e)
    {
        if(Constant.IS_DEBUG)
        {
            e.printStackTrace();
        }
    }

	/**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 이미지 Uri로 실제 path를 가져온다.
	 */
    
	@SuppressLint("NewApi")
	public static String getRealPathFromURI(Uri contentUri, Context context) {
	    String[] proj = { MediaStore.Images.Media.DATA };
	    CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
	    Cursor cursor = loader.loadInBackground();
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}

	/**
	 * @Date 2015.08.10 Add by DBK
	 * @brief GPS 시분초 정보를 Degree로 변경한다.
	 */
	public static Double convertToDegree(String stringDMS)
	{
		   Log.d("GPSManager ","stringDMS :" + stringDMS);
		   Double result = null;
		  try
		  {
		   stringDMS = stringDMS.replace("/1,", ":");
		   stringDMS = stringDMS.replace("/1,", ":");
		   if(stringDMS.contains("/10000")){
			   stringDMS = stringDMS.replace("/10000", "");
			   String del = stringDMS.substring(stringDMS.length()-4);
			   stringDMS = stringDMS.replace(del, "");
		   }else{
			   stringDMS = stringDMS.replace("/1,", "");
		   }
		   stringDMS = stringDMS.replace("/1", "");
		   Double FloatS = Location.convert(stringDMS);
		   
		   result = FloatS;
		  }
		  catch (Exception e) 
		  {
		   // TODO: handle exception
		   Util.Log(e.getMessage());
		  }
		  
		  return result;
	 } 

	/**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 현재 날자를 가져온다.
	 */
    public static String getDate(){
 	   Calendar calendar = Calendar.getInstance();

 	   	String today = "";
 	
 	   	today += calendar.get(calendar.YEAR);
 	
 	   	int month = calendar.get(calendar.MONTH)+1;
 	
 	   	int day = calendar.get(calendar.DAY_OF_MONTH);
 	
 	   	today += month < 10 ? "0"+month : month;
 	
 	   	today += day < 10 ? "0"+day : day;
    		return today;
    }

	/**토스트 띄우기*/
	public static void showToast(Context context, String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**객체가 존재하는지 확인*/
    public static boolean isNotEmptyObj(Object obj) 
    {
        boolean isNotEmpty = true;
        
        if(obj == null)
        {
            isNotEmpty  = false;
        }
        
        return isNotEmpty;
    }

	/**사진을 저장할 경로를 가져온다.*/
    public static String getSavePhotoPath(Context context){
    	return context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
    }

	/**사진을 저장한다.*/
    public static Boolean saveBitmap(Context context, Bitmap bitmap, String path) {

        File imagePath = new File(path);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();


            return true;
        } catch (FileNotFoundException e) {
        	return false;
        } catch (IOException e) {
        	return false;
        }

    }
    
    /**파일명을 추출한다.*/
    public static String getFileName(Context context, Uri uri) {
    	  String result = null;
    	  if (uri.getScheme().equals("content")) {
    	    Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
    	    try {
    	      if (cursor != null && cursor.moveToFirst()) {
    	        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
    	      }
    	    } finally {
    	      cursor.close();
    	    }
    	  }
    	  if (result == null) {
    	    result = uri.getPath();
    	    int cut = result.lastIndexOf('/');
    	    if (cut != -1) {
    	      result = result.substring(cut + 1);
    	    }
    	  }
    	  return result;
    }
    
    /**폴더 생성*/
    public static void createFolder(){

        try{
            //check sdcard mount state
            String str = Environment.getExternalStorageState();
            if ( str.equals(Environment.MEDIA_MOUNTED)) {

                Log.d("Sd_card_path", "sdcard mounted");
                String mTargetDirPath = getScreenShootFilePath();

                File file = new File(mTargetDirPath);
                if(!file.exists()){
                    file.mkdirs();
                    Log.e("Sd_card_path", mTargetDirPath + " folder created");
                }else{
                  //  Log.d(TAG, mTargetDirPath + " is exist");
                }
            }else{
                Log.d("Sd_card_path", "sdcard unmount, use default image.");
            }
        }catch(Exception e){
            Log.d("Sd_card_path", "fail");
        }
    }
    
    /**파일 삭제*/
    public static boolean delete(String path,String file_Name){
        File file = new File(path+file_Name);

        return file.delete();
    }

    /**스크린샷 파일 경로를 가져온다.*/
    public static String getScreenShootFilePath(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Beenzido";
    }

    /**파일을 카피한다.*/
    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**파일 존재 유무 확인*/
    public static boolean isExistPath(String path){
		File file = new File(path);
		// test.txt 파일이 있는지 확인
		if(file.isFile()){
			return true;
		}else{
			return false;
		}
	}
    

    /**
     * @Date 2015.08.10 Add by Hyun_Jae
     * @brief Constant.PHOTO_ALBUM 에 있는 사진파일의 경로를 가지고 온다.
     */
    public static  ArrayList<String> getFilePaths(Context context) {
        ArrayList<String> filePaths = new ArrayList<String>();
        File directory = new File(
                android.os.Environment.getExternalStorageDirectory()
                        + File.separator + Constant.PHOTO_ALBUM);
        Log.e("file","file");
        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles;

            listFiles =  directory.listFiles();

            // Check for count
            if (listFiles.length > 0) {
                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // check for supported file extension
                    if (IsSupportedFile(filePath,context)) {
                        // Add image path to array list
                        filePaths.add(filePath);
                    }
                }
            } else {
                // image directory is empty
                Toast.makeText(
                        context,
                        Constant.PHOTO_ALBUM
                                + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Error!");
            alert.setMessage(Constant.PHOTO_ALBUM
                    + " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    // Check supported file extensions
    private static boolean IsSupportedFile(String filePath,Context context) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (Constant.FILE_EXTN
                .contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;

    }

    /*
     * getting screen width
     */
    @SuppressLint("NewApi")
	public static int getScreenWidth(Context context) {
        int columnWidth;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }


    public static double get_current_latitude(Context context){

        GPStracker gps = new GPStracker(context);
        gps.getLocation();
        double latitude =gps.getLatitude();


        Util.Log("latitude : "  +  latitude);
        //Util.showToast(getApplicationContext(),"latitude : "  +  latitude + "longitude : " + longitude);
        return latitude;
    }

    public static double get_current_longitude(Context context){

        GPStracker gps = new GPStracker(context);
        gps.getLocation();
        double longitude = gps.getLongitude();
        Util.Log("longitude : " + longitude);

       // Util.showToast(getApplicationContext(),"latitude : "  +  latitude + "longitude : " + longitude);
        return longitude;
    }


}
