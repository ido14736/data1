package com.example.data1;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class Information {
    private LatLng position;
    private String type;
    private String name;
    private String description;
    // add image

    public Information(LatLng position, String type, String name, String description)
    {
        this.position = position;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
