package com.example.parks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ParksFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_parks, container, false);
    }
}