package com.example.creators.danhgiahocphan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.models.Subject_Teacher;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class TeacherAdapter extends BaseAdapter {
    ArrayList<Subject_Teacher> arraylistgv;
    Context context;

    public TeacherAdapter(ArrayList<Subject_Teacher> arraylistgv, Context context) {
        this.arraylistgv = arraylistgv;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistgv.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylistgv.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtTeacherName;
        TextView tvTotalLikeTC;
        TextView tvTotalCmtTC;
        ImageView imgAvatarTeacher;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_listview_teacher, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTeacherName = (TextView) convertView.findViewById(R.id.tvTenGiangVien);
            viewHolder.tvTotalLikeTC = (TextView) convertView.findViewById(R.id.tvTotalLikeGV);
            viewHolder.tvTotalCmtTC = (TextView) convertView.findViewById(R.id.tvTotalCmtGV);
            viewHolder.imgAvatarTeacher=(ImageView) convertView.findViewById(R.id.imgAvatarTeacher);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Subject_Teacher subjectTeacher = (Subject_Teacher) getItem(position);
        viewHolder.txtTeacherName.setText(subjectTeacher.getNameTeacher());
        viewHolder.tvTotalLikeTC.setText(subjectTeacher.getNoLike()+" thích");
        viewHolder.tvTotalCmtTC.setText(subjectTeacher.getNoComment()+" bình luận");

        Picasso.with(context.getApplicationContext()).load(subjectTeacher.getAvatar()).into(viewHolder.imgAvatarTeacher);

        return convertView;
    }
}