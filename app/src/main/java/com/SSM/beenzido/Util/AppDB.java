package com.SSM.beenzido.Util;

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AppDB {

    private final static String DB_NAME     = "BeenzidoDatabase.db";
    private SQLiteDatabase db               = null;
    
    public AppDB(Context mContext)
    {
        if(db == null)
        {
            db = mContext.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        }
    }
    
    public void closeDB()
    {
        if(Util.isNotEmptyObj(db))
        {
            db.close();
            db  = null;
        }
    }
    
    private final String TABLE_NAME_CITY_INFO      = "CITY_INFO";
    private final String COL_INDEX          	  = "INDEX";
    private final String COL_COUNTY          	  = "COUNTY";
    private final String COL_CITY         		  = "CITY";
    private final String COL_PATH         		  = "PATH";
    
    private void createCityInfoTable()
    {
        try
        {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CITY_INFO + 
            		
                    " ( " + COL_INDEX + " TEXT" +
                    ", " + COL_COUNTY + " TEXT" +
                    ", " + COL_CITY + " TEXT" +
                    ", " + COL_PATH + " TEXT);");
        }
        catch(Exception e)
        {
            Util.Log(e);
        }
    }
    
	public void saveCityInfo(String index, String county, String city, String path)
    {
    	createCityInfoTable();
       
        db.beginTransaction();
        
        try
        {
            String where    = COL_INDEX + " = " + "'" + index + "'";
            
            db.delete(TABLE_NAME_CITY_INFO, where, null);
            
            ContentValues values = new ContentValues();

            values.put(COL_INDEX, index);
            values.put(COL_COUNTY, county);
            values.put(COL_CITY, city);
            values.put(COL_PATH, path);
            
            db.insert(TABLE_NAME_CITY_INFO, null, values);
            
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }
    }
    
    public ArrayList<CityInfoObj> getCityInfo()
    {
    	createCityInfoTable();

        ArrayList<CityInfoObj> arrList  = new ArrayList<CityInfoObj>();
        
        
        String[] columns =  { COL_INDEX, COL_COUNTY, COL_CITY, COL_PATH };
        

        Cursor cursor    = db.query(TABLE_NAME_CITY_INFO, columns, null, null, null, null, COL_INDEX);
        
        if(cursor != null)
        {
            int len = cursor.getCount();
            
            if(len > 0)
            {
            	CityInfoObj obj;
                
                for(int i=0; i<len; i++)
                {
                    cursor.moveToPosition(i);
                    
                    obj = new CityInfoObj();
                    obj.setIndex(cursor.getString(cursor.getColumnIndex(COL_INDEX)));
                    obj.setCounty(cursor.getString(cursor.getColumnIndex(COL_COUNTY)));
                    obj.setCity(cursor.getString(cursor.getColumnIndex(COL_CITY)));
                    obj.setPath(cursor.getString(cursor.getColumnIndex(COL_PATH)));
                    arrList.add(obj);
                }
            }
            
            cursor.close();
        }
        
        return arrList;
    }

    private boolean isExistCityInfoData()
    {
        boolean isExistGameInfoData;
        
        Cursor cursor         = db.query(TABLE_NAME_CITY_INFO, null, null, null, null, null, null);
        
        if(cursor != null)
        {
            if(cursor.getCount() > 0)
            {
                isExistGameInfoData = true;
            }
            else
            {
                isExistGameInfoData = false;
            }
            
            cursor.close();
        }
        else
        {
            isExistGameInfoData = false;
        }
        
        return isExistGameInfoData;
    }
    
    public class CityInfoObj
    {
        private String index        	 = "";
        private String county        	 = "";
        private String city         	 = "";
        private String path          	 = "";
        
		public String getIndex() {
			return index;
		}
		public void setIndex(String index) {
			this.index = index;
		}
		public String getCounty() {
			return county;
		}
		public void setCounty(String county) {
			this.county = county;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
    }
    
}
