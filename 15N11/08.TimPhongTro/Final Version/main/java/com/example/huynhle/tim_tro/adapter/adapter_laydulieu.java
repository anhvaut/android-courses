package com.example.huynhle.tim_tro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhle.tim_tro.R;
import com.example.huynhle.tim_tro.classLaydulieu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Huynh Le on 11/4/2017.
 */

public class adapter_laydulieu extends BaseAdapter{

    private Context context;
    private int layout;
    private List<classLaydulieu> troList;
    private ArrayList<classLaydulieu> arrayList;

    public adapter_laydulieu(Context context, int layout, List<classLaydulieu> troList) {
        this.context = context;
        this.layout = layout;
        this.troList = troList;
        this.arrayList = new ArrayList<classLaydulieu>();
        this.arrayList.addAll(troList);
    }

    public void AddListItemAdapter(ArrayList<classLaydulieu> itemplus){
        arrayList.addAll(itemplus);
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return troList.size();
    }

    @Override
    public Object getItem(int i) {
        return troList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgnguoidung, imgtro;
        TextView txtvtennguoidung, txtvdiachi, txtvgiaphong, txtvdientich, txtvgiodang;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(layout, null);

            holder = new ViewHolder();

            //ánh xạ view
            holder.txtvtennguoidung = (TextView) view.findViewById(R.id.txtvtennguoidun);
            holder.txtvdiachi =(TextView) view.findViewById(R.id.txtvdiach);
            holder.txtvgiaphong =(TextView) view.findViewById(R.id.txtvgiaphon);
            holder.txtvdientich =(TextView) view.findViewById(R.id.txtvdientic);
            holder.txtvgiodang = (TextView) view.findViewById(R.id.txtvgiodan);
            holder.imgnguoidung = (ImageView) view.findViewById(R.id.imgnguoidun);
            holder.imgtro = (ImageView) view.findViewById(R.id.imgtr);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        //gán giá trị

        classLaydulieu listtro = (classLaydulieu) getItem(i);

        holder.txtvtennguoidung.setText(listtro.getTennguoidung());
        holder.txtvdiachi.setText(listtro.getQuanHuyen());
        holder.txtvgiaphong.setText(listtro.getGiaPhong());
        holder.txtvdientich.setText(listtro.getDienTich());
        holder.txtvgiodang.setText(listtro.getGiodang());
        //    holder.imgnguoidung.setImageResource(listtro.getAnhnguoidung());
        //    holder.imgtro.setImageResource(listtro.getAnhtro());
        Picasso.with(context).load(listtro.getAnhnguoidung() ).into(holder.imgnguoidung);
        Picasso.with( context ).load( listtro.getLinkAnhTro() ).into( holder.imgtro );

        return view;
    }

    //Filter Class
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        troList.clear();
        if(charText.length() == 0){
            troList.addAll(arrayList);
        }else {
            for(classLaydulieu v : arrayList){
                if(v.getQuanHuyen().toLowerCase(Locale.getDefault()).contains(charText)){
                    troList.add(v);
                }
            }
        }
        notifyDataSetChanged();
    }
}
