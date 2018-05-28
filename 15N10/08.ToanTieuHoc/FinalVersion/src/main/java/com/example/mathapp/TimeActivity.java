package com.example.mathapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class TimeActivity extends AppCompatActivity {
    private Button btn_play;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        anhxa();
        batsukien();
    }
    public void anhxa(){
        btn_play = (Button) findViewById(R.id.btn_play);
        videoView = (VideoView) findViewById(R.id.video_view);
    }
    public void batsukien(){
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.xemgio));
                videoView.start();
                MediaController mediaController = new MediaController(TimeActivity.this);
                mediaController.setMediaPlayer(videoView);
                videoView.setMediaController(mediaController);
            }
        });
    }
}
