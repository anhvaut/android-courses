package com.example.creators.danhgiahocphan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.adapter.Constant;
import com.example.creators.danhgiahocphan.adapter.TeacherAdapter;
import com.example.creators.danhgiahocphan.models.Subject;
import com.example.creators.danhgiahocphan.models.Subject_Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import static com.example.creators.danhgiahocphan.activity.Home.KEY_DATA;
import static com.example.creators.danhgiahocphan.activity.Home.KEY_NAME_SUBJECT;
import static com.example.creators.danhgiahocphan.activity.Home.KEY_TEACHER;


public class ListTeacherActivity extends AppCompatActivity {
    private Subject subject;
    private ArrayList<Subject_Teacher> listSubjectTeacher;
    private TeacherAdapter adapterTeacher;
    private DatabaseReference mDataReferencesubjectsTeachers;
    private ListView lvTeachers;
    private DatabaseReference mDatabase;
    private ProgressDialog dialog ;
    private DatabaseReference mDataReferenceAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_teacher);
        lvTeachers = (ListView) findViewById(R.id.lvTeachers);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDataReferencesubjectsTeachers = mDatabase.child("Subject_Teacher");
        mDataReferenceAvatar = mDatabase.child("Teachers");
        listSubjectTeacher = new ArrayList<Subject_Teacher>();

        Intent intent = getIntent();
        subject = (Subject) intent.getSerializableExtra(Constant.KEY_SUBJECT);
        if (subject != null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Đang tải, xin chờ...");
            dialog.setCancelable(false);
            dialog.show();
        }
        getListTeacher();
        Toast.makeText(this, "Hiện đang cập nhật lại dữ liệu", Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
      finish();
    }
    public void getListTeacher() {
        // listSubjectTeacher =new ArrayList<Subject_Teacher>();

        listSubjectTeacher.clear();
        mDataReferencesubjectsTeachers.child(subject.getPart()).child(subject.getId()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    int i = 0;
                    int count = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    Subject_Teacher subjectTeacher = child.getValue(Subject_Teacher.class);
                        count = (int) dataSnapshot.getChildrenCount();
                        String name = child.child("name").getValue().toString();
                        int cmt = Integer.parseInt(child.child("noComment").getValue().toString());
                        int like = Integer.parseInt(child.child("noLike").getValue().toString());
                        final Subject_Teacher subjectTeacher = new Subject_Teacher();
                        String key = child.getKey().toString();
                        subjectTeacher.setIdTeacher(key);
                        subjectTeacher.setNameTeacher(name);
                        subjectTeacher.setIdSubject(subject.getId());
                        subjectTeacher.setNoComment(cmt);
                        subjectTeacher.setNoLike(like);
                        mDataReferenceAvatar.child(key).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String avtTeacher=dataSnapshot.getValue().toString();
                                subjectTeacher.setAvatar(avtTeacher);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        listSubjectTeacher.add(subjectTeacher);
                        i++;

                    }
                    adapterTeacher = new TeacherAdapter(listSubjectTeacher, getApplicationContext());
                    lvTeachers.setAdapter(adapterTeacher);
                    if (i == count)
                        dialog.dismiss();
                     }
                     else {
                    dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvTeachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListTeacherActivity.this, RatingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_TEACHER, listSubjectTeacher.get(i));
                bundle.putSerializable(KEY_NAME_SUBJECT, subject);
                intent.putExtra(KEY_DATA, bundle);
                startActivity(intent);

            }
        });

    }
}