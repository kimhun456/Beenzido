package com.SSM.beenzido.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.SSM.beenzido.R;

import java.io.StringBufferInputStream;

public class CityListActivity extends Activity {

	private int picture = 0;


	static public int  btn_korea 					= 30;
	static public int  btn_seoul 					= 31;
	static public int  btn_gyeongido 				= 32;
	static public int  btn_gangwondo 				= 33;
	static public int  btn_chungcheongbukdo 		= 34;
	static public int  btn_chungcheongnamdo 		= 35;
	static public int  btn_jeollabukdo 			    = 36;
	static public int  btn_jeollanamdo 			    = 37;
	static public int  btn_gyeongsangbukdo 		    = 38;
	static public int  btn_gyeongsangnamdo 		    = 39;
	static public int  btn_jejudo 				    = 310;


	static public int view_CityActivity =   123;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_list_view);
		Resources res = getResources();
		Intent intent =getIntent();
		LoadImage(intent.getExtras().getInt("MapClickData"));

	}


	public void LoadImage(int _picture)
	{
		if (btn_korea == _picture)
			AddViewImage(R.drawable.korea, view_CityActivity);

		else if (btn_seoul== _picture)
			AddViewImage(R.drawable.seoul, view_CityActivity);

		else if ( btn_gyeongido == _picture)
			AddViewImage(R.drawable.gyeonggido, view_CityActivity);

		else if ( btn_gangwondo  == _picture)
			AddViewImage(R.drawable.gangwondo, view_CityActivity);

		else if ( btn_chungcheongbukdo == _picture)
			AddViewImage(R.drawable.chungcheongbukdo, view_CityActivity);

		else  if ( btn_chungcheongnamdo == _picture)
			AddViewImage(R.drawable.chungcheongnamdo, view_CityActivity);

		else if (btn_jeollabukdo  == _picture)
			AddViewImage(R.drawable.jeollabukdo, view_CityActivity);

		else if ( btn_jeollanamdo == _picture)
			AddViewImage(R.drawable.jeollanamdo, view_CityActivity);

		else if (btn_gyeongsangbukdo  == _picture)
			AddViewImage(R.drawable.gyeongsangbukdo, view_CityActivity);

		else if (btn_gyeongsangnamdo  == _picture)
			AddViewImage(R.drawable.gyeongsangnamdo, view_CityActivity);

		else if ( btn_jejudo == _picture)
			AddViewImage(R.drawable.jejudo, view_CityActivity);
	}

	public void AddViewImage(int filepathForR,int id)
	{

		RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.citylist_Relativelayout);
		ImageView iv = new ImageView(this);
		iv.setId(id);
		iv.setImageResource(filepathForR);
		iv.setAdjustViewBounds(true);
		iv.setLayoutParams(new Gallery.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		relativeLayout.addView(iv);
	}
}
