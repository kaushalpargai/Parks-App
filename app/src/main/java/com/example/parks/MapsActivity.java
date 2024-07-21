package com.example.parks;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.parks.databinding.ActivityMapsBinding;

import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Repository.getParks(new AsyncResponse() {
            @Override
            public void processPark(List<Park> parks) {
                for (Park park : parks){
                    if(Objects.equals(park.getFullName(), "Abraham Lincoln Birthplace National Historical Park"))
                    {

                    LatLng sydney = new LatLng(Double.parseDouble(park.getLatitude())
                            , Double.parseDouble(park.getLongitude()));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(park.getFullName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));}
                    Log.d("Parks", "processPark: " + park.getLongitude());

                }
            }
        });

        // Add a marker in Sydney and move the camera
        // Add a marker in Sydney and move the camera




    }
}