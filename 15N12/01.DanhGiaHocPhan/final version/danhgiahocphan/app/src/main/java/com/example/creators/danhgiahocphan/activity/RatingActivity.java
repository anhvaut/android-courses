package com.example.creators.danhgiahocphan.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.adapter.CommentAdapter;
import com.example.creators.danhgiahocphan.models.Comment;
import com.example.creators.danhgiahocphan.models.Liker;
import com.example.creators.danhgiahocphan.models.SingletonUser;
import com.example.creators.danhgiahocphan.models.Subject;
import com.example.creators.danhgiahocphan.models.Subject_Teacher;
import com.example.creators.danhgiahocphan.models.Teacher;
import com.example.creators.danhgiahocphan.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */

public class RatingActivity extends AppCompatActivity {
    private TextView txtTeacher, txtSubject, txtLevel, txtLike, txtComment, txtMail, txtPhone;
    private EditText edtComment;
    private Button btnLike;
    private ListView lvCmt;
    private ImageView imgTeacher;
    private List<Comment> listCmt;
    private CommentAdapter commentAdapter;
    private static int cmtNo;
    private static int likeNo;
    private boolean checkLike = false;
    private Subject_Teacher subjectTeacher;
    private DatabaseReference mDataReferenceTeacher;
    private DatabaseReference mDataReferenceComment;
    private DatabaseReference mDataReferenceGetComment;
    private DatabaseReference mDataReferenceLike;
    private DatabaseReference mDataReferenceFilterComment;
    private Teacher teacher;
    private String nameSubject;
    private ArrayList<String> listFilterComment;
    private Subject subject;
    private String part;
    private ProgressDialog dialog;
    private String id;
    private String userName;
    private String mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Mapping();
        listFilterComment = new ArrayList<>();
        userName = SingletonUser.Instance().getUserName();
        id = SingletonUser.Instance().getId().toString();
        mail = SingletonUser.Instance().getMail().toString();
        // preferences = getSharedPreferences("checkLike", MODE_PRIVATE);
        mDataReferenceLike = FirebaseDatabase.getInstance().getReference();
        mDataReferenceTeacher = FirebaseDatabase.getInstance().getReference();
        mDataReferenceComment = FirebaseDatabase.getInstance().getReference();
        mDataReferenceGetComment = FirebaseDatabase.getInstance().getReference();
        mDataReferenceFilterComment = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Home.KEY_DATA);
        if (bundle != null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Đang tải, xin chờ...");
            dialog.setCancelable(false);
            dialog.show();
        }


        subjectTeacher = (Subject_Teacher) bundle.getSerializable(Home.KEY_TEACHER);
        subject = (Subject) bundle.getSerializable(Home.KEY_NAME_SUBJECT);
        nameSubject = subject.getName();
        part = subject.getPart();
        listCmt = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(this, R.layout.dong_listview_comment, listCmt);
        lvCmt.setAdapter(commentAdapter);
        //  getListLiker();
        commentAdapter.notifyDataSetChanged();
        likeNo = subjectTeacher.getNoLike();


        // checkLike = preferences.getBoolean("check", false);
        getFilterComments();
        getComments();
        checkLiked();
        if ((listCmt != null)) {
            dialog.dismiss();

        }
        lvCmt.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (listCmt.get(i).getUsermail().equals(mail)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RatingActivity.this);
                builder.setTitle("Xóa bình luận");
                ///builder.setCancelable(false);
                builder.setMessage("Bạn có chắc chắn xóa bình luận không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {

                            mDataReferenceComment.child("Comments").child(subjectTeacher.getIdTeacher() + subjectTeacher.getIdSubject()).child(listCmt.get(i).getIdCmt()).removeValue();
                            listCmt.remove(i);
                            cmtNo--;
                            txtComment.setText(cmtNo+" bình luận");
                            mDataReferenceTeacher.child("Subject_Teacher").child(part).child(subjectTeacher.getIdSubject())
                                    .child(subjectTeacher.getIdTeacher()).child("noComment").setValue(cmtNo);
                            commentAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                    return true;
                }
                else return false;

            }
        });
        setInfor();
        // checkLiker();
    }

    private void checkLiked() {
        mDataReferenceLike.child("Likers").child(subjectTeacher.getIdTeacher() + subjectTeacher.getIdSubject()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id)) {
                    checkLike = true;
                    txtLike.setTextColor(getApplication().getResources().getColor(R.color.colorPrimaryDark));
                    btnLike.setBackgroundResource(R.drawable.icon_like);
                    btnLike.getLayoutParams().height = 80;
                    btnLike.getLayoutParams().width = 80;
                    txtLike.setText(likeNo + " thích");
                } else {
                    txtLike.setTextColor(getApplication().getResources().getColor(R.color.black));
                    btnLike.setBackgroundResource(R.drawable.icon_unlike);
                    btnLike.getLayoutParams().height = 70;
                    btnLike.getLayoutParams().width = 70;
                    txtLike.setText(likeNo + " thích");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getFilterComments() {
        mDataReferenceFilterComment.child("FilterComment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String filterCmt = child.getValue().toString();
                    listFilterComment.add(filterCmt);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setInfor() {
        teacher = new Teacher();
        mDataReferenceTeacher.child("Teachers").child(subjectTeacher.getIdTeacher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacher = dataSnapshot.getValue(Teacher.class);
                likeNo = subjectTeacher.getNoLike();
                cmtNo = subjectTeacher.getNoComment();

                teacher.setIdTeacher(subjectTeacher.getIdTeacher());
                teacher.setSubjectTeach(nameSubject);
                txtTeacher.setText(subjectTeacher.getNameTeacher().toUpperCase());
                txtSubject.setText("Môn: " + teacher.getSubjectTeach());
                txtLevel.setText("Học hàm: " + teacher.getLevel());
                txtComment.setText(subjectTeacher.getNoComment() + " bình luận");
                txtMail.setText(teacher.getMail());
                txtPhone.setText(teacher.getPhone());
                Picasso.with(getApplicationContext()).load(teacher.getAvatar()).into(imgTeacher);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void getComments() {
        mDataReferenceGetComment.child("Comments").child(subjectTeacher.getIdTeacher() + subjectTeacher.getIdSubject()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                listCmt.add(comment);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onBackPressed() {
        Intent intent = new Intent(RatingActivity.this, Home.class);
        startActivity(intent);
    }

    //lọc những comment không phù hợp
    private boolean filterComment(String cmt) {
        for (String filterCmt : listFilterComment) {
            if (filterCmt.toLowerCase().trim().indexOf(cmt.toLowerCase().trim()) >= 0)
                return true;
        }
        return false;
    }

    public void CmtRating̣̣̣̣̣(View view) {
        final String content = edtComment.getText().toString();
        if (content.isEmpty()) {
            return;
        } else {
            if (filterComment(content)) {
                Toast.makeText(this, "Xin lỗi! bình luận không được chấp nhận, bạn nên văn minh hơn nơi công cộng", Toast.LENGTH_SHORT).show();
            } else {
                String userNameCmt = userName;
                String userMailCmt = mail;
                Date time = new Date();
                SimpleDateFormat dinhDangDate = new SimpleDateFormat("HH:mm dd/MM/yyyy ");
                String showDate = dinhDangDate.format(time.getTime());
                Comment cmt = new Comment(id + time.getTime(), userNameCmt, userMailCmt, content, showDate);

                //listCmt.add(cmt);
                //commentAdapter.notifyDataSetChanged();
                mDataReferenceComment.child("Comments").child(subjectTeacher.getIdTeacher() + subjectTeacher.getIdSubject()).child(id + time.getTime()).setValue(cmt);
                edtComment.setText("");
                getCmtNo();
            }
        }
    }

    private void getCmtNo() {
        cmtNo = listCmt.size();
        txtComment.setText(cmtNo + " bình luận");
        mDataReferenceTeacher.child("Subject_Teacher").child(part).child(subjectTeacher.getIdSubject())
                .child(subjectTeacher.getIdTeacher()).child("noComment").setValue(cmtNo);

        cmtNo++;
        subjectTeacher.setNoComment(cmtNo);
        txtComment.setText(cmtNo + " bình luận");
        mDataReferenceTeacher.child("Subject_Teacher").child(part).child(subjectTeacher.getIdSubject())
                .child(subjectTeacher.getIdTeacher()).child("noComment").setValue(cmtNo);

    }

    public void like(View view) {
        if (checkLike) {
            //
            likeNo = subjectTeacher.getNoLike() - 1;

            txtLike.setText(likeNo + " thích");

            txtLike.setText(likeNo + " thích");

            subjectTeacher.setNoLike(likeNo);
            txtLike.setTextColor(this.getResources().getColor(R.color.black));
            checkLike = false;
            btnLike.setBackgroundResource(R.drawable.icon_unlike);
            btnLike.getLayoutParams().height = 70;
            btnLike.getLayoutParams().width = 70;
            mDataReferenceLike.child("Likers").child(subjectTeacher.getIdTeacher() + subjectTeacher.getIdSubject()).child(id).removeValue();
            mDataReferenceTeacher.child("Subject_Teacher").child(part).child(subjectTeacher.getIdSubject())
                    .child(subjectTeacher.getIdTeacher()).child("noLike").setValue(likeNo);
        } else {
            likeNo = subjectTeacher.getNoLike() + 1;

            txtLike.setText(likeNo + " thích");

            txtLike.setText(likeNo + " thích");
            subjectTeacher.setNoLike(likeNo);
            txtLike.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            checkLike = true;
            btnLike.setBackgroundResource(R.drawable.icon_like);
            btnLike.getLayoutParams().height = 80;
            btnLike.getLayoutParams().width = 80;
            mDataReferenceLike.child("Likers").child(subjectTeacher.getIdTeacher() + subjectTeacher.getIdSubject()).child(id).setValue(mail);
            mDataReferenceTeacher.child("Subject_Teacher").child(part).child(subjectTeacher.getIdSubject())
                    .child(subjectTeacher.getIdTeacher()).child("noLike").setValue(likeNo);
        }

    }

    private void Mapping() {
        btnLike = (Button) findViewById(R.id.btnLike);
        txtComment = (TextView) findViewById(R.id.txtComment);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtMail = (TextView) findViewById(R.id.txtMail);
        txtLevel = (TextView) findViewById(R.id.txtLevel);
        txtLike = (TextView) findViewById(R.id.txtLike);
        txtTeacher = (TextView) findViewById(R.id.txtTeacher);
        txtSubject = (TextView) findViewById(R.id.txtSubject);
        edtComment = (EditText) findViewById(R.id.edtComment);
        lvCmt = (ListView) findViewById(R.id.lvComment);
        imgTeacher = (ImageView) findViewById(R.id.imgTeacher);
    }
}