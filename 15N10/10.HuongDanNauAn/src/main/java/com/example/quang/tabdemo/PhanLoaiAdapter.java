package com.example.quang.tabdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC on 13/09/2017.
 */

public class PhanLoaiAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<PhanLoaiStruct> phanLoaiStructList;

    public PhanLoaiAdapter(Context context, int layout, List<PhanLoaiStruct> phanLoaiStructList) {
        this.context = context;
        this.layout = layout;
        this.phanLoaiStructList = phanLoaiStructList;
    }

    @Override
    public int getCount() {
        return phanLoaiStructList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
//anh xa
        TextView txtTen= (TextView) view.findViewById(R.id.textviewTen);
        ImageView imgHinh=(ImageView) view.findViewById(R.id.imageviewHinh);
//gan gia tri
        PhanLoaiStruct phanLoaiStruct = phanLoaiStructList.get(i);
        txtTen.setText(phanLoaiStruct.getTen());
        imgHinh.setImageResource(phanLoaiStruct.getHinh());

        return view;
    }
}
