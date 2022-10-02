package com.cit.videoplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.cit.videoplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private ActivityMainBinding binding;
    ArrayList<Integer> videoList;
    int currentVideo = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        videoList = new ArrayList<>();

        binding.vidView.setMediaController(new MediaController(this));
        binding.vidView.setOnCompletionListener(this);

        videoList.add(R.raw.see_you);
        videoList.add(R.raw.faded);
        videoList.add(R.raw.how_long);
        setVideo(videoList.get(0));


    }

    private void setVideo(int id) {
        String path = "android.resource://" + getPackageName() + "/" + id;
        Uri uri = Uri.parse(path);
        binding.vidView.setVideoURI(uri);
        binding.vidView.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Video Finished!");
        builder.setIcon(R.mipmap.ic_launcher_round);
        MyListener myListener = new MyListener();
        builder.setPositiveButton("Replay", myListener);
        builder.setNegativeButton("Next", myListener);
        builder.setMessage("Want to replay or play next video?");
        builder.show();
    }

    private class MyListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1){
                binding.vidView.seekTo(0);
                binding.vidView.start();
            }else {
                ++currentVideo;
                if (currentVideo == videoList.size()){
                    currentVideo = 0;
                }
                setVideo(videoList.get(currentVideo));
            }
        }
    }
}