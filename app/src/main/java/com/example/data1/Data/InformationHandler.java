package com.example.data1.Data;

import android.content.Context;
import android.database.Cursor;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class InformationHandler {
    private static List<Information> info;
    private static DatabaseHandler myDB;
    private static String[] types;
    private static String[] services_names;

    //Adding the information from the DB to the list
    public static boolean initializeInformation(Context c) {
        info = new ArrayList();
        myDB = new DatabaseHandler(c);

        services_names = new String[] {
                "כל השירותים", "בנייני לימוד", "ספריות", "תחנות שאטל", "עמדות מים", "מקררים", "מיקרוגלים"
                , "מעונות", "מתקני ספורט", "מרכזי הופעות ואירועים", "מגרשי חנייה", "מסעדות", "בתי קפה"
        };

        types = new String[] {
                "All", "building", "library", "shuttle", "water", "refrigerator", "microwave"
                , "dorms", "sports", "amphitheater", "parking", "restaurant", "coffee"
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

    public static String[] getServices_names() {
        return services_names;
    }

    public static String getTypeByServiceName(String name) {
        int i;
        for(i = 0; i < services_names.length; i++){
            if(services_names[i].equals(name)){
                break;
            }
        }
        return types[i];
    }

    public static Information getInfoByIndex(int index) {
        return info.get(index);
    }
}
