package com.example.dataentry;

import android.app.Application;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationClass extends Application {
    public static ArrayList<data_items> items;
    @Override
    public void onCreate() {
        super.onCreate();
        items = new ArrayList<data_items>();
        try {
            database_store db=new database_store(this);
            db.open();
            if(!db.isEmpty()) {
                for(int j=0;j<db.uniqueIDs().size();j++){
                    int i = db.uniqueIDs().get(j);
                    items.add(new data_items(db.getKeyName(i),db.getKeyEmail(i),db.getKeyImage(i)));
                }
            }
            db.close();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
