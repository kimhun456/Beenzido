package com.SSM.beenzido.Util;

import java.util.Arrays;
import java.util.List;

public class Constant {

    public final static boolean IS_DEBUG        = true;
    public final static String LOG_TAG          = "BEENZIDO_DEV_LOG";

    public final static String YES              = "Y";
    public final static String NO               = "N";
    
	public final static int REQ_CODE_SELECT_IMAGE = 100;
    

    public final static String TAG_COUNTY_NAME    	  		 = "COUNTY_NAME";
    public final static String TAG_CITY_NAME    	  		 = "CITY_NAME";
    
    public final static String TAG_COUNTY_SEOUL    	  		 = "01";
    public final static String TAG_COUNTY_GYEONGGIDO 	     = "02";
    public final static String TAG_COUNTY_GANGWONDO  	     = "03";
    public final static String TAG_COUNTY_CHUNGCHEONGBUKDO   = "04";
    public final static String TAG_COUNTY_CHUNGCHEONGNAMDO   = "05";
    public final static String TAG_COUNTY_GYEONGSANGBUKDO 	 = "06";
    public final static String TAG_COUNTY_GYEONGSANGNAMDO    = "07";
    public final static String TAG_COUNTY_JEOLLABUKDO   	 = "08";
    public final static String TAG_COUNTY_JEOLLANAMDO   	 = "09";
    public final static String TAG_COUNTY_JEJUDO     		 = "10";

    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    // SD card image directory
    public static final String PHOTO_ALBUM = "Beenzido";

    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
            "png");
}
