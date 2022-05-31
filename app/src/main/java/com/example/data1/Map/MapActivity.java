package com.example.data1.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.data1.Data.Information;
import com.example.data1.Data.InformationHandler;
import com.example.data1.List.ListActivity;
import com.example.data1.R;
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
    private Marker currentSelectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.projMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        currentSelectedMarker = null;

        boolean addedSuccesfully = InformationHandler.initializeInformation(getBaseContext());
        if(!addedSuccesfully){
            //failed to add
            Toast.makeText(getBaseContext(), "Error While Reading The Data From The Database.",
                    Toast.LENGTH_LONG).show();
        }

        Spinner spinner = findViewById(R.id.typeSP);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, InformationHandler.getServices_names());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.toListItem)
        {
            //PASS THE MAPICON LIST

            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);

            //intent.putExtra("markersOnMap", (Parcelable) markersOnMap);
           /* Bundle b = new Bundle();
            b.putSerializable("markersOnMap", markersOnMap);
            intent.putExtras(b);*/
            //intent.putExtra("markersOnMap", markersOnMap);
            //Map<Long, MapIcon> icons = markersOnMap.getIcons();

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
    //    map.addOnMapClickListener(this);
        map.setOnInfoWindowClickListener(new MapboxMap.OnInfoWindowClickListener() {
            @Override
            public boolean onInfoWindowClick(@NonNull Marker marker) {
                currentSelectedMarker = null;
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
        markersOnMap.initializeMarkersToMap(map);

        //when a marker is clicked
        map.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Information markerInfo = InformationHandler.getInfoByIndex(markersOnMap.getMarkerIndexById(marker.getId()));
                if(markerInfo != null)
                {
                    /*Location iconLocation = new Location("");
                    iconLocation.setLatitude(ic.getPosition().getLatitude());
                    iconLocation.setLongitude(ic.getPosition().getLongitude());
                    setCameraPosition(iconLocation);*/
                    if(currentSelectedMarker != null)
                    {
                        currentSelectedMarker.hideInfoWindow();
                        if(currentSelectedMarker.getId() != marker.getId())
                        {
                            marker.setTitle(markerInfo.getName());
                            marker.setSnippet(markerInfo.getDescription());
                            marker.showInfoWindow(map, mapView);
                            currentSelectedMarker = marker;
                        }

                        else
                        {
                            currentSelectedMarker = null;
                        }
                    }

                    else
                    {
                        marker.setTitle(markerInfo.getName());
                        marker.setSnippet(markerInfo.getDescription());
                        marker.showInfoWindow(map, mapView);
                        currentSelectedMarker = marker;
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
                if(currentSelectedMarker != null)
                {
                    currentSelectedMarker.showInfoWindow(map, mapView);
                }
            }
        });

        Spinner spinner = findViewById(R.id.typeSP);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = InformationHandler.getTypeByServiceName(spinner.getSelectedItem().toString());
                /*if(currentSelectedMarker != null) {
                    if(selectedType != "All" && selectedType != InformationHandler.getInfoByIndex(markersOnMap.getMarkerIndexById(currentSelectedMarker.getId())).getType()) {
                        currentSelectedMarker = null;
                    }

                }*/
                markersOnMap.MarkersSelectionToMap(map, selectedType);

                if(currentSelectedMarker != null) {
                    if(selectedType != "All" && (!selectedType.equals(InformationHandler.getInfoByIndex(markersOnMap.getMarkerIndexById(currentSelectedMarker.getId())).getType()))) {
                        currentSelectedMarker.hideInfoWindow();
                        currentSelectedMarker = null;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //markersOnMap.MarkersSelectionToMap(map, "dorms");
        //markersOnMap.MarkersSelectionToMap(map, "sports");
        //markersOnMap.MarkersSelectionToMap(map, "library");
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