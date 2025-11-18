package com.example.musicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private int playingMusicPos = -1;
    private List<Music> musicList = Music.getList();
    private final onMusicClickListener onMusicClickListener;


    public MusicAdapter(List<Music> musicList,onMusicClickListener onMusicClickListener) {
        this.musicList = musicList;
        this.onMusicClickListener = onMusicClickListener;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.bindMusic(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView bandTv;
        private TextView musicNameTv;
        private LottieAnimationView animationView;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.sdv_item_cover);
            bandTv = itemView.findViewById(R.id.tv_item_bandName);
            musicNameTv = itemView.findViewById(R.id.tv_item_musicName);
            animationView = itemView.findViewById(R.id.animation_lottie);
        }

        public void bindMusic(Music music) {
            simpleDraweeView.setActualImageResource(music.getCoverResId());
            bandTv.setText(music.getBand());
            musicNameTv.setText(music.getName());

            if (getAbsoluteAdapterPosition() == playingMusicPos) {
                animationView.setVisibility(View.VISIBLE);

            } else{ animationView.setVisibility(View.GONE);}

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMusicClickListener.onClick(music,getAbsoluteAdapterPosition());
                }
            });

        }

    }

    public void notifyMusicChange(Music music) {
        int index = musicList.indexOf(music);
        if (playingMusicPos != -1) {
            if (index != playingMusicPos) {
                notifyItemChanged(playingMusicPos);
                playingMusicPos = index;
                notifyItemChanged(playingMusicPos);
            }
        }
    }

    public interface onMusicClickListener {
        void onClick(Music music, int position);
    }


}
