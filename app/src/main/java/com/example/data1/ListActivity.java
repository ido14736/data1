package com.example.data1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

       // Slide slide = new Slide(Gravity.LEFT);
        //this.getWindow().setEnterTransition(slide);
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
        return true;
    }
}