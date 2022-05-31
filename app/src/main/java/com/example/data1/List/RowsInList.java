package com.example.data1.List;

import static android.view.View.GONE;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.data1.Data.Information;
import com.example.data1.Data.InformationHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* this class is in-charge for initializing the lists from the DB, and
* for the functionality of the menu-list */
public class RowsInList {

    // constructor input
    private Context c;
    ExpandableListView myList;
    Button FromServiceToMap;
    SearchManager searchManager;

    // local lists
    List<Service> services; // connection between category and his list
    List<Service> showTheseParentList;
    MainAdapter adapter; // in-charge of handling the logic behind the expandable list
    // service's lists:
    List<Information> buildings;
    List<Information> libraries;
    List<Information> shuttles;
    List<Information> gates;
    List<Information> water;
    List<Information> refrigerators;
    List<Information> microwaves;
    List<Information> dorms;
    List<Information> sports;
    List<Information> amphitheaters;
    List<Information> parking_spots;
    List<Information> restaurants;
    List<Information> coffee_shops;

    //constructor
    public RowsInList(Context c, ExpandableListView e, Button button, SearchManager manager)
    {
        /* input data */
        this.c = c;
        myList = e; // myList
        FromServiceToMap = button;
        searchManager = manager;
        services = new ArrayList<Service>();  // parentList
        showTheseParentList = new ArrayList<Service>();
        displayList();
//        expandAll();
        // the button shall appear only after a service has been selected
        FromServiceToMap.setVisibility(GONE);

    }

    public void expandAll() {
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }

    private void displayList() {
        addListToService();
        adapter = new MainAdapter(c,services);
        myList.setAdapter(adapter);
        initializeLists();
        // set adapter and expandable objects, and initialize the lists


    }

    /* this method set's listener for the children when they are clicked  */
    public void setOnServiceClickedListener() {
        this.myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition,
                                        int childPosition, long id) {
//                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild
//                        (groupPosition, childPosition));
//                parent.setItemChecked(index, true);
                for (int i = 1; i < myList.getChildCount(); i++) {
                    View child = (View) myList.getChildAt(i);
                    child.setBackgroundColor(Color.WHITE);
                }
                view.setBackgroundColor(Color.RED);

//                Information info = services_lists.get(services[groupPosition]).get(childPosition);
                FromServiceToMap.setVisibility(View.VISIBLE);
                //System.out.println(info.getDescription());
                return false;
            }
        });
    }





    /* this method loops over the data from the information handler, and adds to right list - based
    * on type */
    public void initializeLists() {
        for (int i = 0; i < InformationHandler.getSize(); i++) {
            String type = InformationHandler.getInfoByIndex(i).getType();
            // based on the current row's type - enter the name to the right list
            switch(type){
                case "building":
                    buildings.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "library":
                    libraries.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "restaurant":
                    restaurants.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "coffee":
                    coffee_shops.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "shuttle":
                    shuttles.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "gates":
                    gates.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "water":
                    water.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "refrigerator":
                    refrigerators.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "microwave":
                    microwaves.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "dorms":
                    dorms.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "sports":
                    sports.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "amphitheater":
                    amphitheaters.add(InformationHandler.getInfoByIndex(i));
                    break;
                case "parking":
                    parking_spots.add(InformationHandler.getInfoByIndex(i));

            }

        }


        adapter.notifyDataSetChanged();

    }

    /* this method set's a list of Information for every Service
    * TO-DO: as icon, pass the @drawable/correct icon */
    public void addListToService(){
        buildings = new ArrayList<Information>();
        services.add(new Service("מבנים", buildings, 1));
        libraries = new ArrayList<Information>();
        services.add(new Service("ספריות", libraries, 2));
        shuttles = new ArrayList<Information>();
        services.add(new Service("שאטלים", shuttles, 3));
        gates = new ArrayList<Information>();
        services.add(new Service("שערים", gates, 4));
        water = new ArrayList<Information>();
        services.add(new Service("מכונות מים", water, 5));
        refrigerators = new ArrayList<Information>();
        services.add(new Service("מקררים", refrigerators, 6));
        microwaves = new ArrayList<Information>();
        services.add(new Service("מיקרוגלים", microwaves, 7));
        dorms = new ArrayList<Information>();
        services.add(new Service("מעונות", dorms, 8));
        sports = new ArrayList<Information>();
        services.add(new Service("מתקני ספורט", sports, 9));
        amphitheaters = new ArrayList<Information>();
        services.add(new Service("אמפי-תיאטרונים", amphitheaters, 10));
        parking_spots = new ArrayList<Information>();
        services.add(new Service("מגרשי חניה", parking_spots, 11));
        restaurants = new ArrayList<Information>();
        services.add(new Service("מסעדות", restaurants, 12));
        coffee_shops = new ArrayList<Information>();
        services.add(new Service("בתי קפה", coffee_shops, 13));
    }

    public MainAdapter getAdapter(){
        return this.adapter;
    }
}
