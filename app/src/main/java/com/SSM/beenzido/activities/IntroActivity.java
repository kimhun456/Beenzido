package com.SSM.beenzido.activities;


import com.SSM.beenzido.R;
import com.SSM.beenzido.Util.Constant;
import com.SSM.beenzido.Util.SharedPrefLib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;




public class IntroActivity extends Activity{

	/**화면 전환 시간(Default 3초)*/
	private final static int TRANS_TIMMER = 3 * 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_view);

		if(Constant.NO.equals(SharedPrefLib.getIsFirst(getApplicationContext())))
		{
			//DB 초기화 
			SharedPrefLib.setIsFirst(getApplicationContext(), Constant.YES);
			SharedPrefLib.setPhotoIndex(getApplicationContext(),0);
		}
		
		//TRANS_TIMMER에 설정된 시간(초) 후에 엑티비티 전환을 한다.
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(getApplicationContext(), CityPhotosActivity.class);
				startActivity(intent);
				finish();
			}
		}, TRANS_TIMMER);
		
	}
	
}
