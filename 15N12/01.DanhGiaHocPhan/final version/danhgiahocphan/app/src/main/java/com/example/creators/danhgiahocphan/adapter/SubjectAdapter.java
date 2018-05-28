package com.example.creators.danhgiahocphan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.activity.ListTeacherActivity;
import com.example.creators.danhgiahocphan.models.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {
    private Context context;
    private List<Subject> listSubject;
    private LayoutInflater inflater;
    private ArrayList<Subject> arraylist;

    public SubjectAdapter(List<Subject> listSubject, Context context) {
        this.listSubject = listSubject;
        this.context = context;
        this.arraylist=new ArrayList<Subject>();
        this.arraylist.addAll(listSubject);
        inflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.dong_listview_subject, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Subject subject = listSubject.get(position);
        holder.txtSubject.setText(subject.getName());
        try {
            String firstLetter = subject.getName().substring(0, 1);
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getRandomColor();
            TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);
            holder.imageView.setImageDrawable(drawable);
        } catch (Exception e) {

        }

        holder.itemSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListTeacherActivity.class);
                intent.putExtra(Constant.KEY_SUBJECT, subject);
                v.getContext().startActivity(intent);
            }
        });

    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listSubject.clear();
        if (charText.length() == 0) {
            listSubject.addAll(arraylist);
        } else {
            for (Subject subject : arraylist) {
                if (subject.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                   listSubject.add(subject);
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listSubject.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemSubject;
        private TextView txtSubject;
        private ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtSubject = itemView.findViewById(R.id.txt_subject);
            imageView = itemView.findViewById(R.id.img_icon);
            itemSubject=itemView.findViewById(R.id.itemSubject);
        }


    }

}
