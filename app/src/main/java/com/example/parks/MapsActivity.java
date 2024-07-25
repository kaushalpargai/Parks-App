package com.example.parks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView bottomNavigationMenuView = findViewById(R.id.bottom_navigation);
        bottomNavigationMenuView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();
            if (id == R.id.maps_nav_button){
                // show the map view
                mMap.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.map , mapFragment).commit();
               mapFragment.getMapAsync(this);
                return true;

            }else if(id == R.id.parks_nav_button){
                selectedFragment = new ParksFragment();

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.map,selectedFragment).commit();

            return true;


        });
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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,5));}
                    Log.d("Parks", "processPark: " + park.getLongitude());

                }
            }
        });

        // Add a marker in Sydney and move the camera
        // Add a marker in Sydney and move the camera




    }
}