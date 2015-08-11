package com.SSM.beenzido.activities;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.SSM.beenzido.R;
import com.SSM.beenzido.Util.Address_Returner;
import com.SSM.beenzido.Util.AppDB;
import com.SSM.beenzido.Util.SharedPrefLib;
import com.SSM.beenzido.Util.Util;
import com.SSM.beenzido.Util.Constant;
import com.SSM.beenzido.adapter.GridViewImageAdapter;

@SuppressLint("NewApi")
public class CityPhotosActivity extends Activity implements OnClickListener{

	private AppDB appDB;
    
	static String countyName;
	static String cityName;

	static String county;
	static String city;



	/** GRID VIEW variables*/

    private Util utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_photo_view);
		
		//getBundleValue(getIntent().getExtras());
		appDB = new AppDB(getApplicationContext());
		Button addBtn = (Button)findViewById(R.id.add_image);
		addBtn.setOnClickListener(this);


		/**
			Grid View 처리하는 부분 시작
		 */

        gridView = (GridView) findViewById(R.id.grid_view);

        utils = new Util(this);

        // Initilizing Grid 레이아웃 설정
        InitilizeGridLayout();

        // loading all image paths from SD card
		// imagePahts 에 ArrayList<String>으로 AbsolutePath들을 넘겨주면 된다.

        imagePaths = utils.getFilePaths();

        // Gridview adapter
        adapter = new GridViewImageAdapter(this, imagePaths,
                columnWidth);

        // setting grid view adapter
        gridView.setAdapter(adapter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();        
        appDB.closeDB();
    }
    
    protected void getBundleValue(Bundle extras) 
    {
        countyName    = extras.getString(Constant.TAG_COUNTY_NAME);
        cityName    = extras.getString(Constant.TAG_CITY_NAME);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.add_image:
				getImageAction();
			break;
		}
	}

	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		
		//Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();
		
		if(requestCode == Constant.REQ_CODE_SELECT_IMAGE) 
		{ 
			if(resultCode==Activity.RESULT_OK) 
			{     
				if(data != null)
			    {           
					final Uri uri = data.getData();


					String filrName = Util.getFileName(this, uri);
					final String destPath = Util.getSavePhotoPath(this)+"/"+filrName;
					final String srcPath = Util.getRealPathFromURI(uri, this);

					Util.Log("src:"+srcPath+" dest:"+destPath);

					Util.Log("srcPath: "+Util.isExistPath(srcPath));
					Util.Log("destPath: "+Util.isExistPath(destPath));
					
					boolean isExist = getGPSInfoFromImage(srcPath);
					
					if(!isExist){
						Util.showToast(this, "GPS정보가 없는 이미지입니다.");
					}else{
						AsyncTask.execute( new Runnable(){
							public void run() {
								saveSelectedPhoto(destPath, srcPath);
							}
						});
					}
			    }
				 
				
			}     
		}   
	}
	
	/**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 로컬에 저장된 이미지를 가져온다.
	 */
	private void getImageAction(){

		Intent intent = new Intent(Intent.ACTION_PICK);                
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, Constant.REQ_CODE_SELECT_IMAGE); 
		
	}

	/**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 이미지에서 GPS정보를 추출.
	 */
	private boolean getGPSInfoFromImage(String imagePath){
		
		ExifInterface exif;
		try {
			exif = new ExifInterface(imagePath);
			String lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
			String lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
			if(lat!=null && lon!=null){
				
				Util.Log("lat:"+lat+" lon:"+lon);
				
				double latitude = Util.convertToDegree(lat);
				double longitude = Util.convertToDegree(lon);
				
				Address_Returner ad = Address_Returner.getInstance();
				
				boolean isKorea = ad.isKorea(getBaseContext(), latitude, longitude);
				if(isKorea){

					String tmpCity = ad.get_city_name(getBaseContext(), latitude, longitude);
					
					
					if(tmpCity!=null && tmpCity.equals("서울특별시")){
						county = tmpCity;
						city = ad.get_gu_name(getBaseContext(), latitude, longitude);
					}else{
						county = ad.get_do_name(getBaseContext(),latitude,longitude);
						city = tmpCity;
					}

					return true;
				}else{
					return false;
				}
				
			}else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	private void saveSelectedPhoto(String destPath, String srcPath){

		boolean isSucess = true;
		try {
			
			Util.copyFile( new File(srcPath), new File(destPath) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucess = false;
		}

		if(isSucess){
			Util.Log("isSucess");
			int cnt = SharedPrefLib.getPhotoIndex(this) + 1;
			appDB.saveCityInfo(String.valueOf(cnt), county, city, destPath);
			SharedPrefLib.setPhotoIndex(this,  Integer.valueOf(cnt));
		}
		Util.Log("isFail");
	}


	/**
	 *
	 * 	Grid layout setting
	 * 	그리드 레이아웃을 사용자 폰에 맞춰서 맞춰주는 함수.
	 *
	 */
    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                Constant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((Constant.NUM_OF_COLUMNS + 1) * padding)) / Constant.NUM_OF_COLUMNS);

        gridView.setNumColumns(Constant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

}
