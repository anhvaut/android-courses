package com.example.quang.tabdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by PC on 13/09/2017.
 */

public class Monchien extends Fragment{
    ListView lvMonchien;
    ArrayList<MonchienStruct> arrayMonchien;
    MonchienAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_monchien,container,false);
        lvMonchien=(ListView) view.findViewById(R.id.listviewmonchien);
        arrayMonchien= new ArrayList<>();

        arrayMonchien.add(new MonchienStruct("Khoai Tây Chiên",R.drawable.khoaitaychien));
        arrayMonchien.add(new MonchienStruct("Cá Viên Chiên",R.drawable.cavienchien));
        arrayMonchien.add(new MonchienStruct("Chân Gà Chiên Giòn",R.drawable.changachiengion));

        adapter=new MonchienAdapter(getActivity(),R.layout.dong_mon_chien,arrayMonchien);
        lvMonchien.setAdapter(adapter);


        return view;
    }
}
