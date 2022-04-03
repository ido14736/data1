package com.example.data1;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

public class InformationHandler {
    private static List<Information> info;
    private static DatabaseHandler myDB;
    private static String[] types;

    //Adding the information from the DB to the list
    public static boolean initializeInformation(Context c) {
        info = new ArrayList();
        myDB = new DatabaseHandler(c);

        types = new String[] {
                "All", "amphitheater", "dorms", "building", "library", "microwave", "shuttle"
                , "water", "refrigerator", "parking", "sports", "restaurant", "coffee"
        };

        Cursor cursor = myDB.getAllMarkers();
        if (cursor == null) {
            cursor = myDB.getAllMarkers();
        }

        Context initilizedContext = c;

        //INIT INFORMATION
        if (cursor != null) {
            do {
                info.add(new Information(new LatLng(cursor.getDouble(2), cursor.getDouble(3)), cursor.getString(1), cursor.getString(0), cursor.getString(4)));

            } while (cursor.moveToNext());

            return true;

        } else {
            return false;
        }
    }

    public static int getSize() {
        return info.size();
    }

    public static String[] getTypes() {
        return types;
    }


    public static Information getInfoByIndex(int index) {
        return info.get(index);
    }
}
