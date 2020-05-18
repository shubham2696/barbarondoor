package com.example.shubham.barbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.common.hash.HashingOutputStream;


public class Home extends Fragment  {
    Button explore;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
       explore=view.findViewById(R.id.explore);
explore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Explore explore= new Explore();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, explore,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }
});

        return  view;
    }


}