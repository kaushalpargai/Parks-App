package com.example.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parks.adapter.OnParkClickListener;
import com.example.parks.adapter.ParkRecyclerViewAdapter;
import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;

import java.util.List;


public class ParksFragment extends Fragment implements OnParkClickListener {
    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;

    // TODO: Rename parameter arguments, choose names that match


    public ParksFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ParksFragment newInstance(String param1, String param2) {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView = view.findViewById(R.id.park_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository.getParks(new AsyncResponse() {
            @Override
            public void processPark(List<Park> parks) {
                parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parks,ParksFragment.this);
                recyclerView.setAdapter(parkRecyclerViewAdapter);
            }
        });

    }

    @Override
    public void onParkClicked(Park park) {
        Log.d("Park", "onParkClicked: " + park.getName());
        getFragmentManager().beginTransaction()
                .replace(R.id.park_fragment, DetailsFragment.newInstance())
                .commit();

    }
}