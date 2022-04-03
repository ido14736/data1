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
        insertData("1501 - אמפי-תיאטרון מרכזי", "amphitheater", 32.07265, 34.848452, "האמפי-תיאטרון המרכזי בבר אילן, ממוקם בחלק הצפוני של הקמפוס. משמש לטקסים, הופעות, אירועים שונים ועוד.");
        insertData("110 - אמפי-תיאטרון דרומי", "amphitheater", 32.066434, 34.842545, "האמפי-תיאטרון הדרומי בבר אילן,  משמש לטקסים, הופעות, אירועים שונים ועוד.");

        //dorms
        insertData("בניין 106 - מעונות סודנטים על שם מוסקוביץ' - פרשין", "dorms", 32.065939, 34.84213, "מעונות סטודנטים, בחלק הדרומי של הקמפוס");
        insertData("בניין 108 - מעונות סטודנטים על שם סטולמן", "dorms", 32.065964, 34.843286, "מעונות סטודנטים, בחלק הדרומי של הקמפוס");
        insertData("בניין 506 - מעונות סטודנטים על שם גרוז / אלברט הוברט", "dorms", 32.070765, 34.844941, "מעונות סטודנטים");
        insertData("בניין 104 - מעונות סטודנטים על שם א' וולפסון", "dorms", 32.066241, 34.840757, "מעונות סטודנטים, בחלק הדרומי של הקמפוס");
        insertData("בניין 1300 B - מעונות סודנטים", "dorms", 32.071051, 34.850034, "מעונות סטודנטים, החדשים ביותר בקמפוס. ניתן למצוא באיזור הבניין מסעדות, חנויות ועוד.");
        insertData("בניין 1300 A - מעונות סודנטים", "dorms", 32.072127, 34.850121, "מעונות סטודנטים, החדשים ביותר בקמפוס. ניתן למצוא באיזור הבניין מסעדות, חנויות ועוד.");
        insertData("בניין 103 - מעונות סטודנטים על שם נסים ד' גאון", "dorms", 32.066574, 34.840247, "מעונות סטודנטים, בחלק הדרומי של הקמפוס");
        insertData("בניין 101 - מעונות סטודנטים על שם שרמן", "dorms", 32.065889, 34.840116, "מעונות סטודנטים, בחלק הדרומי של הקמפוס");

        //buildings
        insertData("בניין 403 - בית הספר לחינוך א' על שם צ'רלס גרוסברג", "building", 32.068406, 34.843197, "בניין לימודים");
        insertData("בניין 105 - כיתות אקסודוס על שם רוברט אסרף", "building", 32.066002, 34.841735, "בניין לימודים");

        //libraries
        insertData("בניין 212 - ספרייה למדעי החיים", "library", 32.067727, 34.841814, "ספרייה למדעי החיים, ממוקמת בקומה 1, חדר 113");

        //microwaves
        insertData("בניין 505 - מיקרוגל", "microwave", 32.07044, 34.844542, "עמדת חימום בקומה 1");

        //shuttles
        insertData("תחנת שאטל מספר 5", "shuttle", 32.069962, 34.842058, "תחנת שאטל");

        //water
        insertData("בניין 905 - עמדת מים", "water", 32.073118, 34.84586, "עמדת מים בבניין 905 בקומה 4");

        //refigerators
        insertData("בניין 101 - מקרר", "refrigerator", 32.065867, 34.840166, "מקרר בבניין 101 בקומה מינוס 1");

        //parkings
        insertData("חניון מוסיקה", "parking", 32.074778, 34.846915, "חניון מוסיקה ברחוב ז'בוטינסקי מאחורי בניין 1005. החנייה בחניונים אלו על בסיס יומי - מחיר כניסה חד-פעמי: 20 שקלים, לחברי אגודה המשלמים דמי רווחה: 10 שקלים.");

        //sports
        insertData("אולם כדורסל וכדור-עף/בדמינטון", "sports", 32.069777, 34.842636, "אולם כדורסל וכדור-עף/בדמינטון. דרוש תיאום מראש לשעות הכניסה ולאחר מכן ניתן לקחת מפתח ובסוף להחזיר אותו.");

        //restaurants
        insertData("בניין 206 - מסעדת קרנף", "restaurant", 32.066954, 34.841164, "בבניין ננו (206), קפיטריה צמחונית וחלבית. שעות פעילות: ימים א'-ה' בשעות 18:00 - 7:30");

        //coffee
        insertData("קופי טיים פירמידה", "coffee", 32.068691, 34.843615, "סמוך לבניין יהדות 410");

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
