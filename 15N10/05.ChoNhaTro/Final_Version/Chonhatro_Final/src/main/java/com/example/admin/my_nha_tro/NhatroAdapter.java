package com.example.admin.my_nha_tro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class NhatroAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private List<Nhatro> nhatroList;

    public NhatroAdapter(Context context, int layout, List <Nhatro> nhatroList) {
        this.context = context;
        this.layout = layout;
        this.nhatroList = nhatroList;
    }

    @Override
    public int getCount() {
        return nhatroList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTieuDe;
        TextView txtGia;
        TextView txtDiachi;
        TextView txtMota;


    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout, null);
            viewHolder.txtTieuDe= view.findViewById(R.id.textViewTieuDeCustorm);
            viewHolder.txtGia= view.findViewById(R.id.textViewGiaCustorm);
            viewHolder.txtDiachi= view.findViewById(R.id.textViewDiaChiCustorm);
            viewHolder.txtMota= view.findViewById(R.id.textViewMotacustorm);
            view.setTag(viewHolder);
        }
        else viewHolder=(ViewHolder)view.getTag();
        Nhatro nhatro = nhatroList.get(i);
        viewHolder.txtTieuDe.setText(nhatro.getTieuDe());
        viewHolder.txtGia.setText("Giá phòng: " + nhatro.getGia()+" VNĐ");
        viewHolder.txtDiachi.setText("Địa chỉ liên hệ: "+ nhatro.getDiaChi());
        viewHolder.txtMota.setText("Mô tả: "+nhatro.getMota());
        return view;
    }
}
