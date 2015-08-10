package com.SSM.beenzido.Util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefLib {

    private final static String N_BEENZIDO        = "n_beenzido";

    private final static String I_IS_FIRST       = "i_is_first";
    
    /**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 앱을 처음 실행시 Yes로 변경 
     * @param context, appAid
     * */
    public static void setIsFirst(Context context, String val)
    {
        Editor editor = context.getSharedPreferences(N_BEENZIDO, Context.MODE_PRIVATE).edit();
        editor.putString(I_IS_FIRST, val);
        editor.commit();
    }
    
    /**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 앱을 처음실행 여부를 확인 
     * @param context
     * */
    public static String getIsFirst(Context context)
    {
        SharedPreferences pref = context.getSharedPreferences(N_BEENZIDO, Context.MODE_PRIVATE);
        
        String appAid = pref.getString(I_IS_FIRST, Constant.NO).trim();
        
        return appAid;
    }

    private final static String I_PHOTO_IDX       = "i_photo_idx";
    
    /**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 마지막 사진 Index 저장
     * @param context, val
     * */
    public static void setPhotoIndex(Context context, int val)
    {
        Editor editor = context.getSharedPreferences(N_BEENZIDO, Context.MODE_PRIVATE).edit();
        editor.putInt(I_PHOTO_IDX, val);
        editor.commit();
    }
    
    /**
	 * @Date 2015.08.10 Add by DBK
	 * @brief 마지막 사진 Index 가져오기
     * @param context
     * */
    public static int getPhotoIndex(Context context)
    {

        SharedPreferences pref = context.getSharedPreferences(N_BEENZIDO, Context.MODE_PRIVATE);
        
        int val = pref.getInt(I_PHOTO_IDX, 3);
        
        return val;
    }

}
