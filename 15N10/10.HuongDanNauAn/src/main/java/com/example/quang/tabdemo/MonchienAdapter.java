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

public class MonchienAdapter extends BaseAdapter {
    private Context  context;
    private int layout;
    private List<MonchienStruct> monchienStructList;

    public MonchienAdapter(Context context, int layout, List<MonchienStruct> monchienStructList) {
        this.context = context;
        this.layout = layout;
        this.monchienStructList = monchienStructList;
    }

    @Override
    public int getCount() {
        return monchienStructList.size();
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
        view =inflater.inflate(layout,null);
        //anh xa view
        TextView txtTen=(TextView)view.findViewById(R.id.textChien);
        ImageView imghinh=(ImageView)view.findViewById(R.id.imageChien);
        //gan gia tri
        MonchienStruct monchienStruct = monchienStructList.get(i);
        txtTen.setText(monchienStruct.getTen());
        imghinh.setImageResource(monchienStruct.getHinh());


        return view;
    }
}
