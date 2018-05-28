package com.example.creators.danhgiahocphan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.models.Comment;
import com.example.creators.danhgiahocphan.models.Subject_Teacher;

import java.util.List;

/**
 * Created by Thach on 28/9/2017.
 */

public class CommentAdapter extends BaseAdapter{
    Context context;
    int resource;
    List<Comment> listCmt;

    public CommentAdapter(Context context, int resource, List<Comment> objects) {

        this.context = context;
        this.resource = resource;
        this.listCmt = objects;
    }


    @Override
    public int getCount() {
        return listCmt.size();
    }

    @Override
    public Object getItem(int position) {
        return listCmt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtUser;
        TextView txtContent;
        TextView txtTime;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.txtUser = (TextView) convertView.findViewById(R.id.txtUserCmt);
            viewHolder.txtContent = (TextView) convertView.findViewById(R.id.txtContentCmt);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txtTimeCmt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtUser.setText(listCmt.get(position).getUser());
        viewHolder.txtContent.setText(listCmt.get(position).getContent());
        viewHolder.txtTime.setText(listCmt.get(position).getTime());
        return convertView;
    }
}
