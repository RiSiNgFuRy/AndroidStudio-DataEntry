package com.example.dataentry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class database_store {
    public static final String KEY_ROWID="_id";
    public static final String KEY_NAME="person_name";
    public static final String KEY_EMAIL="email_id";
    public static final String KEY_IMAGE="image_src";

    private final String DATABASE_NAME="database_store";
    private final String DATABASE_TABLE="DataEntry";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;

    public database_store(Context context){
        ourContext = context;
    }

    public class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String sqlcode="CREATE TABLE "+DATABASE_TABLE+" ("+
                    KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_NAME+" TEXT NOT NULL, "+
                    KEY_EMAIL+" TEXT NOT NULL, "+
                    KEY_IMAGE+" VARCHAR(1000) );";
            db.execSQL(sqlcode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }
    public database_store open() throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String name,String email_id,String image_src)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_EMAIL,email_id);
        cv.put(KEY_IMAGE,image_src);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }


    public String getKeyName(Integer Id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE, new String[]{KEY_NAME,KEY_ROWID}, null, null, null, null,KEY_ROWID);
        int Name=c.getColumnIndex(KEY_NAME);
        int pid=c.getColumnIndex(KEY_ROWID);
        String result="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(pid).equals(Id.toString())){
                result+= c.getString(Name);
                break;
            }
        }
        return result;
    }
    public String getKeyEmail(Integer Id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE, new String[]{KEY_EMAIL,KEY_ROWID}, null, null, null, null,KEY_ROWID);
        int Email=c.getColumnIndex(KEY_EMAIL);
        int pid=c.getColumnIndex(KEY_ROWID);
        String result="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(pid).equals(Id.toString())){
                result+= c.getString(Email);
                break;
            }
        }
        return result;
    }

    public String getKeyImage(Integer Id) {
        Cursor c = ourDatabase.query(DATABASE_TABLE, new String[]{KEY_IMAGE,KEY_ROWID}, null, null, null, null,KEY_ROWID);
        int Image=c.getColumnIndex(KEY_IMAGE);
        int pid=c.getColumnIndex(KEY_ROWID);
        String result="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(pid).equals(Id.toString())){
                result+= c.getString(Image);
                break;
            }
        }
        return result;
    }

    public ArrayList<Integer> uniqueIDs() {
        Cursor c = ourDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID}, null, null, null, null,KEY_ROWID);
        int pid=c.getColumnIndex(KEY_ROWID);
        ArrayList<Integer> result = new ArrayList<Integer>();

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result.add(Integer.parseInt(c.getString(pid)));
        }
        c.close();
        return result;
    }

    public long deleteEntry(String rowID)
    {
        return ourDatabase.delete(DATABASE_TABLE,KEY_ROWID+"=?",new String[]{rowID});
    }

    public boolean isEmpty()
    {
        boolean e=true;
        Cursor c=ourDatabase.rawQuery("SELECT COUNT(*) FROM "+DATABASE_TABLE,null);
        if(c!=null && c.moveToFirst())
            e=(c.getInt(0))==0;
        c.close();
        return e;
    }
}
