package com.example.shubham.barbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Profile extends Fragment {
    String contact,name;
   TextView logout,contact1,name1;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        name1=view.findViewById(R.id.name1);
        contact1=view.findViewById(R.id.contact1);
        UserPref user_pref = new UserPref(getContext());
        contact=user_pref.getCont();
        name=user_pref.getName();

        contact1.setText(contact);
        name1.setText(name);
        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          new UserPref(getContext()).removeUser();
                                          Intent intent = new Intent(getActivity(), Login.class);
                                          startActivity(intent);
                                      }

                                  }
        );

        return view;
    }


}
