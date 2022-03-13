package com.example.data1;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.HashMap;
import java.util.Map;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class MarkersOnMap {
    private Context c;
    private Map<Long, MapIcon> icons;
    private DatabaseHandler myDB;

    //constructor
    public MarkersOnMap(Context c)
    {
        //the context of the activity
        this.c = c;

        //a map of the icons on the map(icon id(long) paired with the icon (MapIcon))
        icons = new HashMap<Long, MapIcon>();

        myDB = new DatabaseHandler(c);
        //myDB.addAllMarkers();
        //amphitheaters
        //myDB.insertData("1501 – אמפי-תיאטרון מרכזי", "amphitheater", 32.07265, 34.848452, "האמפי-תיאטרון המרכזי בבר אילן, ממוקם בחלק הצפוני של הקמפוס. משמש לטקסים, הופעות אירועים שונים ועוד");
       // myDB.insertData("110 – אמפי-תיאטרון דרומי", "amphitheater", 32.066434, 34.842545, "האמפי-תיאטרון הדרומי בבר אילן,  משמש לטקסים, הופעות אירועים שונים ועוד.");

      //  Cursor cur = myDB.getAllMarkers();
    }


    //displaying the icons from the DB on the given map, and adding them to the icons dictionary
    public boolean addMarkersToMap(MapboxMap map)
    {
        Cursor cursor = this.myDB.getAllMarkers();

        if(cursor == null){
            cursor = this.myDB.getAllMarkers();
        }

        Context initilizedContext = c;
        int drawableID = initilizedContext.getResources().getIdentifier("amphitheater", "drawable", initilizedContext.getPackageName());
        Bitmap mBitmap = getBitmapFromVectorDrawable(initilizedContext, drawableID);
        Icon ic = IconFactory.getInstance(initilizedContext).fromBitmap(mBitmap);

        //INIT ICONS, change addMarker, put params
        Marker m;
        if(cursor != null) {
            do{
                m = map.addMarker(new MarkerOptions().position(new LatLng(cursor.getDouble(2),cursor.getDouble(3))).icon(ic));
                icons.put(m.getId(), new MapIcon(m.getId(), new LatLng(cursor.getDouble(2),cursor.getDouble(3)), cursor.getString(1), cursor.getString(0), cursor.getString(4)));

            }while (cursor.moveToNext());

            return true;

        }

        else
        {
            return false;
        }
        //initializing the icons dictionary

       /* Context initilizedContext = c;
        int drawableID = initilizedContext.getResources().getIdentifier("amphitheater", "drawable", initilizedContext.getPackageName());
        Bitmap mBitmap = getBitmapFromVectorDrawable(initilizedContext, drawableID);
        Icon ic = IconFactory.getInstance(initilizedContext).fromBitmap(mBitmap);
        Marker m = map.addMarker(new MarkerOptions().position(new LatLng(32.07265, 34.848452)).icon(ic));
        Marker m1 = map.addMarker(new MarkerOptions().position(new LatLng(32.066434, 34.842545)).icon(ic));
        icons.put(m.getId(), new MapIcon(m.getId(), new LatLng(32.07265, 34.848452), "amphitheater", "1501 – אמפי-תיאטרון מרכזי", "האמפי-תיאטרון המרכזי בבר אילן, ממוקם בחלק הצפוני של הקמפוס. משמש לטקסים, הופעות אירועים שונים ועוד"));
        icons.put(m1.getId(), new MapIcon(m1.getId(), new LatLng(32.066434, 34.842545), "amphitheater", "110 – אמפי-תיאטרון דרומי", "האמפי-תיאטרון הדרומי בבר אילן,  משמש לטקסים, הופעות אירועים שונים ועוד."));
        return true;*/

        /*Context initilizedContext = c;
        int drawableID = initilizedContext.getResources().getIdentifier("ic_airport", "drawable", initilizedContext.getPackageName());
        Bitmap mBitmap = getBitmapFromVectorDrawable(initilizedContext, drawableID);
        Icon ic = IconFactory.getInstance(initilizedContext).fromBitmap(mBitmap);
        Marker m = map.addMarker(new MarkerOptions().position(new LatLng(32.068,34.845)).icon(ic));
        Marker m1 = map.addMarker(new MarkerOptions().position(new LatLng(32.067,34.843)).icon(ic));
        icons.put(m.getId(), new MapIcon(m.getId(), new LatLng(32.068,34.845), "ic_airport", "airplain1", "first airplain"));
        icons.put(m1.getId(), new MapIcon(m1.getId(), new LatLng(32.067,34.843), "ic_airport", "airplain2", "second airplain"));*/
    }

    //converting vector to bitmap(for the icons)
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    //returnes the icons that are on the map
    public Map<Long, MapIcon> getIcons() {
        return icons;
    }
}
