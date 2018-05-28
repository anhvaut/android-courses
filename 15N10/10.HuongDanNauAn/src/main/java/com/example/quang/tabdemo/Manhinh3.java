package com.example.quang.tabdemo;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by PC on 09/09/2017.
 */

public class Manhinh3 extends Fragment {

            Button bt3;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_manhinh3,container,false);

        return view;
    }

}
