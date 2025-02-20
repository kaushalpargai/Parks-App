package com.example.parks;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.parks.adapter.CustomInfoWindow;
import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;
import com.example.parks.model.ParkViewModel;
import com.example.parks.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.parks.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;
    private CardView cardView;
    private EditText stateCodeEt;
    private ImageButton searchButton;
    private String code = "hi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        parkViewModel = new ViewModelProvider(this)
                .get(ParkViewModel.class);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cardView = findViewById(R.id.card_view);
        stateCodeEt = findViewById(R.id.floating_state_value_et);
        searchButton = findViewById(R.id.floating_search_button);


        BottomNavigationView bottomNavigationMenuView = findViewById(R.id.bottom_navigation);
        bottomNavigationMenuView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();
            if (id == R.id.maps_nav_button){
                if (cardView.getVisibility() == View.INVISIBLE ||
                cardView.getVisibility() == View.GONE){
                    cardView.setVisibility(View.VISIBLE);
                }
                parkList.clear();
                // show the map view
                mMap.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.map , mapFragment).commit();
               mapFragment.getMapAsync(this);
                return true;

            }else if(id == R.id.parks_nav_button){
                selectedFragment = new ParksFragment();
                cardView.setVisibility(View.GONE);

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.map,selectedFragment).commit();

            return true;


        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkList.clear();
                Util.hideSoftKeyboard(view);
                String stateCode = stateCodeEt.getText().toString().trim();
                if(!TextUtils.isEmpty(stateCode)){
                    code = stateCode;
                    parkViewModel.selectCode(code);
                    onMapReady(mMap); // refresh the map
                    stateCodeEt.setText("");
                }
            }
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
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);

        parkList = new ArrayList<>();
        parkList.clear();

        populateMap();

        // Add a marker in Sydney and move the camera
        // Add a marker in Sydney and move the camera




    }

    private void populateMap() {
        mMap.clear(); // important Clears the map!!
        Repository.getParks(new AsyncResponse() {
            @Override
            public void processPark(List<Park> parks) {

                parkList = parks;
                for (Park park : parks){


                    LatLng location = new LatLng(Double.parseDouble(park.getLatitude())
                            , Double.parseDouble(park.getLongitude()));

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(location)
                            .title(park.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                            .snippet(park.getStates());
                    Marker marker = mMap.addMarker(markerOptions);
                    marker.setTag(park);

// as we have set the marker above so now no need to set it another time
//                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,5));
                    Log.d("Parks", "processPark: " + park.getLongitude());

                }
                parkViewModel.setSelectedParks(parkList);
                Log.d("Size", "populateMap: " +parkList.size());

            }
        },code);

    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        cardView.setVisibility(View.GONE);
        // go to details fragment
        goToDetailsFragment(marker);


    }

    private void goToDetailsFragment(@NonNull Marker marker) {
        parkViewModel.selectPark((Park) marker.getTag());
        getSupportFragmentManager().beginTransaction().replace(R.id.map , DetailsFragment.newInstance()).commit();
    }
}