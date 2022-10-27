package com.hadianoor.i190404_i190405;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView titleTv, currenttime, totaltime;
    SeekBar seekBar;
    ImageView pause, next, prev;
    ArrayList<AudioModel> songsList;
    AudioModel currentsong;
    MediaPlayer mediaPlayer=MyMediaPlayer.getInstance();
    //int x=0;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv = findViewById(R.id.song_title);
        currenttime = findViewById(R.id.currenttimetv);
        totaltime = findViewById(R.id.totaltimetv);
        seekBar = findViewById(R.id.seekbar);
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);

        titleTv.setSelected(true);
        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("List");


        setResourceswithMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null)
                {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currenttime.setText(converttoMMSS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying())
                    {
                        pause.setImageResource(R.drawable.ic_pause);
                      //  music_icon.setRotation(x++);
                    }
                    else
                    {
                        pause.setImageResource(R.drawable.ic_play);
                       // music_icon.setRotation(0);
                    }

                }

                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser)
                {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    void setResourceswithMusic() {
        currentsong = songsList.get(MyMediaPlayer.currentIndex);
        titleTv.setText(currentsong.getTitle());
        totaltime.setText(converttoMMSS(currentsong.getDuration()));

        pause.setOnClickListener(v -> pausePlay());
        next.setOnClickListener(v -> playNextSong());
        prev.setOnClickListener(v -> playPrevSong());

        playMusic();

    }

    private void playMusic() {

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentsong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playNextSong() {

        if(MyMediaPlayer.currentIndex==songsList.size()-1)
            return;
        MyMediaPlayer.currentIndex+=1;
        mediaPlayer.reset();
        setResourceswithMusic();
    }

    private void playPrevSong() {
        if(MyMediaPlayer.currentIndex==0)
            return;
        MyMediaPlayer.currentIndex-=1;
        mediaPlayer.reset();
        setResourceswithMusic();

    }

    private void pausePlay() {

        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }


    public static String converttoMMSS(String duration) {
        Long mills = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(mills) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(mills) % TimeUnit.MINUTES.toSeconds(1));
    }



}