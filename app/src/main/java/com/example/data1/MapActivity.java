package com.example.data1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.Layer;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap map;
    private MarkersOnMap markersOnMap;
    private Marker currentSelectedIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.projMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        currentSelectedIcon = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.toListItem)
        {
            startActivity(new Intent(getApplicationContext(), ListActivity.class));
            //overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_in);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.to_list_menu, menu);
        return true;
    }

    //when the map is ready
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
    //    map.addOnMapClickListener(this);
        map.setOnInfoWindowClickListener(new MapboxMap.OnInfoWindowClickListener() {
            @Override
            public boolean onInfoWindowClick(@NonNull Marker marker) {
                currentSelectedIcon = null;
                return false;
            }
        });
        //removing the irrelevant layers(different data on the map) from the map
        List<Layer> layers = map.getLayers();
        for (Layer la : layers) {
            if(la.getId().contains("poi-scalerank"))
            {
                System.out.println(la.getId());
                map.removeLayer(la.getId());
            }
        }


        //adding the markers to the map
        markersOnMap = new MarkersOnMap(getBaseContext());
        boolean addedSuccesfully = markersOnMap.addMarkersToMap(map);
        if(!addedSuccesfully){
            //failed to add
            Toast.makeText(getBaseContext(), "Error While Loading The Icons To The Map.",
                    Toast.LENGTH_LONG).show();
        }

        //when a marker is clicked
        map.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                MapIcon ic = markersOnMap.getIcons().get(marker.getId());
                if(ic != null)
                {
                    /*Location iconLocation = new Location("");
                    iconLocation.setLatitude(ic.getPosition().getLatitude());
                    iconLocation.setLongitude(ic.getPosition().getLongitude());
                    setCameraPosition(iconLocation);*/
                    if(currentSelectedIcon != null)
                    {
                        currentSelectedIcon.hideInfoWindow();
                        if(currentSelectedIcon.getId() != marker.getId())
                        {
                            marker.setTitle(ic.getName());
                            marker.setSnippet(ic.getDescription());
                            marker.showInfoWindow(map, mapView);
                            currentSelectedIcon = marker;
                        }

                        else
                        {
                            currentSelectedIcon = null;
                        }
                    }

                    else
                    {
                        marker.setTitle(ic.getName());
                        marker.setSnippet(ic.getDescription());
                        marker.showInfoWindow(map, mapView);
                        currentSelectedIcon = marker;
                    }
                    /*marker.setTitle(ic.getName());
                    marker.setSnippet(ic.getDescription());
                    marker.showInfoWindow(map, mapView);*/

                    //then hide it

                    /*Toast.makeText(getBaseContext(), ic.getDescription(),
                            Toast.LENGTH_LONG).show();*/

                    return true;
                }
                return false;
            }
        });

        //updates the position of the popup window by the movement of the camera
        map.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if(currentSelectedIcon != null)
                {
                    currentSelectedIcon.showInfoWindow(map, mapView);
                }
            }
        });

    }



    //setting the camera position to a location
    private void setCameraPosition(Location location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()), 16.0));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}