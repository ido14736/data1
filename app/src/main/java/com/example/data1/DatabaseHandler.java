package com.example.data1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NavigationBIUDB";
    private static final String TABLE_NAME = "markers";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_LOCATION_LAT = "location_lat";
    private static final String KEY_LOCATION_LON = "location_lon";
    private static final String KEY_DESCRIPTION = "description";
   /* private SQLiteDatabase writableDB;
    private SQLiteDatabase raedableDB;*/


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MARKERS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_TYPE + " TEXT,"
                + KEY_LOCATION_LAT + " REAL," + KEY_LOCATION_LON + " REAL," + KEY_DESCRIPTION + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_MARKERS_TABLE);
        //sqLiteDatabase.execSQL("CREATE TABLE markers(name TEXT, type TEXT, location_lat REAL, location_lon REAL, description TEXT)");
        //addAllMarkers();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS markers");
        //onCreate(sqLiteDatabase);

    }

    public void addAllMarkers() {
        //amphitheaters
        insertData("1501 - אמפי-תיאטרון מרכזי", "amphitheater", 32.07265, 34.848452, "האמפי-תיאטרון המרכזי בבר אילן, ממוקם בחלק הצפוני של הקמפוס. משמש לטקסים, הופעות, אירועים שונים ועוד");
        insertData("110 - אמפי-תיאטרון דרומי", "amphitheater", 32.066434, 34.842545, "האמפי-תיאטרון הדרומי בבר אילן,  משמש לטקסים, הופעות, אירועים שונים ועוד.");

    }


    public void insertData(String name, String type, double location_lat, double location_lon, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, name);
        values.put(KEY_TYPE, type);
        values.put(KEY_LOCATION_LAT, location_lat);
        values.put(KEY_LOCATION_LON, location_lon);
        values.put(KEY_DESCRIPTION, description);
        /*long result = */
        db.insert(TABLE_NAME, null, values);
        db.close();

        /*if(result == -1)
        {
            return false;
        }
        return true;*/
    }

    public Cursor getAllMarkers() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM markers", null);

        //if the markers added to the DB
        if (cursor.moveToNext()) {
            return cursor;
        }
        //if the DB is empty - adding the markers
        else {
            addAllMarkers();
            return null;
        }
    }
}
