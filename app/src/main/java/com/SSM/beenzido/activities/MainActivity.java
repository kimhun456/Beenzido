package com.SSM.beenzido.activities;

import java.util.ArrayList;
import java.util.TreeMap;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.SSM.beenzido.R;


public class MainActivity extends Activity {


	private int click = 0;
	private int saveCount = 0;

	private  final int  image_korea 					= 0;
	private  final int  image_seoul 					= 1;
	private  final int  image_gyeongido 				= 2;
	private  final int  image_gangwondo 				= 3;
	private  final int  image_chungcheongbukdo 			= 4;
	private  final int  image_chungcheongnamdo 			= 5;
	private  final int  image_jeollabukdo 				= 6;
	private  final int  image_jeollanamdo 				= 7;
	private  final int  image_gyeongsangbukdo 			= 8;
	private  final int  image_gyeongsangnamdo 			= 9;
	private  final int  image_jejudo 					= 10;

	ArrayList<ImageView>							imageViewList;
	TreeMap<Integer, Integer> 						imageViewMap;

	private final int  btn_korea 					= 30;
	private final int  btn_seoul 					= 31;
	private final int  btn_gyeongido 				= 32;
	private final int  btn_gangwondo 				= 33;
	private final int  btn_chungcheongbukdo 		= 34;
	private final int  btn_chungcheongnamdo 		= 35;
	private final int  btn_jeollabukdo 			    = 36;
	private final int  btn_jeollanamdo 			    = 37;
	private final int  btn_gyeongsangbukdo 		    = 38;
	private final int  btn_gyeongsangnamdo 		    = 39;
	private final int  btn_jejudo 				    = 40;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);


		imageViewList = new ArrayList<ImageView>();
		imageViewMap = new TreeMap<Integer, Integer>();

		AddViewImage(R.drawable.korea, image_korea, true);
		AddViewImage(R.drawable.seoul, image_seoul, true);
		AddViewImage(R.drawable.gyeonggido, image_gyeongido, true);
		AddViewImage(R.drawable.gangwondo, image_gangwondo, true);
		AddViewImage(R.drawable.chungcheongbukdo, image_chungcheongbukdo, true);
		AddViewImage(R.drawable.chungcheongnamdo, image_chungcheongnamdo, true);
		AddViewImage(R.drawable.gyeongsangbukdo, image_gyeongsangbukdo, true);
		AddViewImage(R.drawable.gyeongsangnamdo, image_gyeongsangnamdo, true);
		AddViewImage(R.drawable.jeollabukdo, image_jeollabukdo, true);
		AddViewImage(R.drawable.jeollanamdo, image_jeollanamdo, true);
		AddViewImage(R.drawable.jejudo, image_jejudo, true);


		AddButton(0, 0, btn_korea);
		AddButton(100, 0, btn_seoul);
		AddButton(200, 0, btn_gyeongido);
		AddButton(300, 0, btn_gangwondo);
		AddButton(400, 0, btn_chungcheongbukdo);
		AddButton(500, 0, btn_chungcheongnamdo);
		AddButton(600, 0, btn_jeollabukdo);

		AddButton(0, 200, btn_jeollanamdo);
		AddButton(100,200, btn_gyeongsangbukdo);
		AddButton(200,200, btn_gyeongsangnamdo);
		AddButton(300,200, btn_jejudo);


	}

	public void AddViewImage(int filepathForR, int id, boolean visivile)
	{

		RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.mainview_relativelayout);
		ImageView iv = new ImageView(this);
		iv.setId(id);
		imageViewMap.put(id, saveCount);
		iv.setImageResource(filepathForR);
		iv.setAdjustViewBounds(true);
		iv.setLayoutParams(new Gallery.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		saveCount++;
		//if(visivile)iv.setVisibility(View.VISIBLE);
		//else
		iv.setVisibility(View.INVISIBLE);
		imageViewList.add(iv);
		relativeLayout.addView(imageViewList.get(imageViewList.size() - 1));

	}

	public void AddButton(int x, int y,final int id)
	{

		RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.mainview_relativelayout);
		Button btn = new Button(this);
		btn.setId(id);
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		buttonParams.leftMargin = x;
		buttonParams.topMargin = y;
		btn.setLayoutParams(buttonParams);
		btn.setWidth(50);


		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "id" ,Toast.LENGTH_SHORT).show();


				//id - 30은 그 버튼 id랑 이미지 id랑 차이가 30 나기 때문에
				if(imageViewList.get(id - 30).getVisibility() == View.VISIBLE)
					imageViewList.get(id - 30).setVisibility(View.INVISIBLE);
				else
					imageViewList.get(id - 30).setVisibility(View.VISIBLE);

				Intent intent = new Intent(getApplicationContext(), CityListActivity.class);
				intent.putExtra("MapClickData", id);
				startActivity(intent);
				//finish();


			}

		});
		relativeLayout.addView(btn);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				exitApp();
				return true;
			case KeyEvent.KEYCODE_MENU:

			default :
				return false;
		}
	}

	/**2014.05.18
	 * add by DBK
	 * 어플리케이션을 종료하는 메소드
	 * */
	private void exitApp(){

		String alertTitle = "종료";
		new AlertDialog.Builder(MainActivity.this)
				.setTitle(alertTitle)
				.setMessage("프로그램을 완전히 종료합니다.\n종료 하시겠습니다?")
				.setPositiveButton("아니오",null)
				.setNegativeButton("예",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						finish();

					}
				})
				.show();

	}


}