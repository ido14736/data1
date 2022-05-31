package com.example.data1.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.data1.Data.Information;
import com.example.data1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Service> parentRowList; // this is mutable - it's changeable depending ion the user's search
    List<Service> originalList; // this just saves the original services and their info
//  HashMap<String, List<Information>> services_lists;

    // constructor
    public MainAdapter(Context context, List<Service> originalList){
        this.context = context;
        (this.parentRowList = new ArrayList<>()).addAll(originalList);
        (this.originalList = new ArrayList<>()).addAll(originalList);
    }

    /* returns the number of services of the menu */
    @Override
    public int getGroupCount() {
        return parentRowList.size();
    }

    /* returns the number of services - for selected category */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.parentRowList.get(groupPosition).getChildList().size();
    }

    /* returns the name of a clicked service */
    @Override
    public Object getGroup(int groupPosition) {
        return this.parentRowList.get(groupPosition);
    }

    /* returns the name of a clicked description of a clicked service */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.parentRowList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
            parent) {
        Service group = (Service) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_row, null);
        }
        ImageView serviceIcon = (ImageView) convertView.findViewById(R.id.parent_icon);
        // this is an example. I need a switch-case regarding each service
        serviceIcon.setImageResource(R.drawable.coffee);
        // set text
        TextView textview = convertView.findViewById((R.id.parent_text));
        textview.setText(group.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        Information child = (Information) getChild(groupPosition, childPosition);
        //System.out.println(child.getDescription());
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row, null);
        }



        final TextView textView = convertView.findViewById(R.id.child_text);
        textView.setText(child.getName());

        final View finalConvertView = convertView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Toast.makeText(finalConvertView.getContext(), child.getDescription(),
               //        Toast.LENGTH_LONG).show();

            }
        });

        return convertView;

    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /* this method searches for each of the info's inside the services and creates a new list of
    *  services - of which whom only contains info's that holds the query in their names */
    public void filterData(String query) {
        query = query.toLowerCase();
        parentRowList.clear();
        if  (query.isEmpty()){
            parentRowList.addAll(originalList);
        } else {
            for (Service parentRow : originalList){ // go through each service
                List<Information> childList = parentRow.getChildList(); // get current info's from this service
                List<Information> newList = new ArrayList<Information>();

                for (Information childRow : childList){
                    if  (childRow.getName().toLowerCase().contains(query)){
                        newList.add(childRow);
                    }
                }
                if (newList.size() > 0){
                    Service nParentRow = new Service(parentRow.getName(), newList, 5);
                    parentRowList.add(nParentRow);
                }
            }
        }
        notifyDataSetChanged();
    }

}
