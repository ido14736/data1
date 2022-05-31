package com.example.data1.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.data1.List.RowsInList;
import com.example.data1.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener{
    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MenuItem searchItem;
    RowsInList rows;
    ExpandableListView expendables;
    Button ShowOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // prepare views to be sent to 'rows'
        expendables = findViewById(R.id.exp_list_view);
        ShowOnMap = (Button) findViewById(R.id.Service_to_navigation);
        rows = new RowsInList(getBaseContext(), expendables, ShowOnMap, searchManager);
         // set 'rows' into action
        rows.setOnServiceClickedListener(); // service


        // Slide slide = new Slide(Gravity.LEFT);
        //this.getWindow().setEnterTransition(slide);
        // set the listener for when a child has been pressed

        ShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "pressed",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.toMapItem)
        {
            //startActivity(new Intent(getApplicationContext(), MapActivity.class));
            finish();
            //overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            //overridePendingTransition(R.anim.right_slide, R.anim.left_slide);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.to_map_menu, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return true;
    }

    @Override
    public boolean onClose() {
        rows.getAdapter().filterData("k");
        rows.expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        rows.getAdapter().filterData(s);
        rows.expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        rows.getAdapter().filterData(s);
        rows.expandAll();
        return false;
    }
}