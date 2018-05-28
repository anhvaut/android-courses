package com.example.creators.danhgiahocphan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.models.FeedbackContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    private DatabaseReference mDataReference;
    private EditText edtFeedback;
    private Button btnFeedback;
    private FeedbackContent feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mDataReference = FirebaseDatabase.getInstance().getReference();
        edtFeedback = (EditText) findViewById(R.id.edtFeedback);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        feedback = new FeedbackContent();
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedBack();
            }
        });
    }

    private void feedBack() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email;
        final String key = mDataReference.child("FeedBack").push().getKey();


        if (user != null) {

            email = user.getEmail();
            String content = edtFeedback.getText().toString();
            feedback = new FeedbackContent(key, email, content);
            mDataReference.child("Feedback").child(key).setValue(feedback);
            edtFeedback.setText("");
            Toast.makeText(this, "Cảm ơn bạn đã góp ý để cho ứng dụng đươc phát triển và hoàn thiện hơn", Toast.LENGTH_SHORT).show();

        }
    }
}