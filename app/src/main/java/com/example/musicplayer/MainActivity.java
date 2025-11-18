package com.example.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.databinding.ActivityMainBinding;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MusicAdapter.onMusicClickListener {
    private RecyclerView recyclerView;
    private MusicAdapter adapter;
    private List<Music> musicList = Music.getList();
    private ActivityMainBinding binding;
    private MediaPlayer mediaPlayer;
    private MusicState musicState = MusicState.STOPPED;
    private Timer timer;
    private boolean isSliderDragging;
    private int cursor;

    @Override
    public void onClick(Music music, int position) {
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        cursor = position;

        onMusicChange(musicList.get(cursor));
    }

    enum MusicState {
        PLAYING, PAUSED, STOPPED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new MusicAdapter(musicList, this);
        recyclerView.setAdapter(adapter);

        onMusicChange(musicList.get(cursor));

        binding.fabPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (musicState) {
                    case PLAYING:
                        mediaPlayer.pause();
                        musicState = MusicState.PAUSED;
                        binding.fabPlayPause.setImageResource(R.drawable.play_arrow_24px);
                        break;
                    case PAUSED:
                    case STOPPED:
                        mediaPlayer.start();
                        musicState = MusicState.PLAYING;
                        binding.fabPlayPause.setImageResource(R.drawable.pause_24px);
                        break;
                }
            }
        });

        binding.sliderMain.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                binding.tvTimeCurrent.setText(Music.convertMillisToString((long) value));
            }
        });

        binding.sliderMain.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                isSliderDragging = true;
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                isSliderDragging = false;
                mediaPlayer.seekTo((int) slider.getValue());
            }
        });

        binding.ivMainNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });

        binding.ivMainPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });
    }

    private void onMusicChange(Music music) {

        adapter.notifyMusicChange(music);
        binding.sliderMain.setValue(0);
        mediaPlayer = MediaPlayer.create(this, music.getMusicFileResId());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mediaPlayer.start();
                timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isSliderDragging) {
                                    binding.tvTimeCurrent.setText(Music.convertMillisToString(mediaPlayer.getCurrentPosition()));
                                    binding.sliderMain.setValue(mediaPlayer.getCurrentPosition());
                                }

                            }
                        });
                    }
                }, 1000, 1000);

                binding.tvTimeLength.setText(Music.convertMillisToString(mediaPlayer.getDuration()));
                binding.sliderMain.setValueTo(mediaPlayer.getDuration());
                musicState = MusicState.PLAYING;
                binding.fabPlayPause.setImageResource(R.drawable.pause_24px);
            }
        });

        binding.sdvMainMusicCover.setActualImageResource(music.getCoverResId());
        binding.sdvMainBandCover.setActualImageResource(music.getBandResId());
        binding.tvMainMusicName.setText(music.getName());
        binding.tvMainBandName.setText(music.getBand());

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                goNext();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void goNext() {
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        if (cursor < musicList.size() - 1) {
            cursor++;
        } else cursor = 0;

        onMusicChange(musicList.get(cursor));
    }

    public void goPrevious() {
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        if (cursor == 0) {
            cursor = musicList.size() - 1;
        } else cursor--;

        onMusicChange(musicList.get(cursor));
    }
}
