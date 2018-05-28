package com.example.quang.tabdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class Manhinh1 extends Fragment {
    ListView lvphanloai;
    ArrayList<PhanLoaiStruct> arrayPhanloai;
    PhanLoaiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_manhinh1, container, false);

        lvphanloai =(ListView) view.findViewById(R.id.listviewphanloai);
        arrayPhanloai =new ArrayList<>();
        arrayPhanloai.add(new PhanLoaiStruct("Món Chiên",R.drawable.chien));
        arrayPhanloai.add(new PhanLoaiStruct("Món Xào",R.drawable.xao));
        arrayPhanloai.add(new PhanLoaiStruct("Món Hấp",R.drawable.hap));
        arrayPhanloai.add(new PhanLoaiStruct("Món Luộc",R.drawable.luoc));
        arrayPhanloai.add(new PhanLoaiStruct("Món Nướng",R.drawable.nuong));

        adapter=new PhanLoaiAdapter(getActivity(),R.layout.dong_phan_loai,arrayPhanloai);
        lvphanloai.setAdapter(adapter);

        lvphanloai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mh1, new Monchien())
                            .addToBackStack(null)
                            .commit();
                            break;

                }
            }
        });




        return view;
    }


}