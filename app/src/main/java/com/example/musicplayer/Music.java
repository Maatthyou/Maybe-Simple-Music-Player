package com.example.musicplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Music {

    private int id;
    private String name;
    private String band;
    private int coverResId;
    private int bandResId;
    private int musicFileResId;

    public int getMusicFileResId() {
        return musicFileResId;
    }

    public void setMusicFileResId(int musicFileResId) {
        this.musicFileResId = musicFileResId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public int getCoverResId() {
        return coverResId;
    }

    public void setCoverResId(int coverResId) {
        this.coverResId = coverResId;
    }

    public int getBandResId() {
        return bandResId;
    }

    public void setBandResId(int bandResId) {
        this.bandResId = bandResId;
    }

    public static List<Music> getList() {

        List<Music> musicList = new ArrayList<>();

        Music music1 = new Music();
        music1.setBand("The Last Knife Fighter");
        music1.setName("I Wanna Go Home");
        music1.setBandResId(R.drawable.the_last_knife_fighter_band);
        music1.setCoverResId(R.drawable.knife);
        music1.setMusicFileResId(R.raw.i_wanna_go_home);

        Music music2 = new Music();
        music2.setBand("The Last Knife Fighter");
        music2.setName("Fundamental Elements");
        music2.setCoverResId(R.drawable.knife);
        music2.setBandResId(R.drawable.the_last_knife_fighter_band);
        music2.setMusicFileResId(R.raw.fundamental_elements);

        Music music3 = new Music();
        music3.setBand("camel");
        music3.setName("Long GoodByes");
        music3.setCoverResId(R.drawable.camel);
        music3.setBandResId(R.drawable.camel_band);
        music3.setMusicFileResId(R.raw.long_goodbyes);

        Music music4 = new Music();
        music4.setBand("Ryan Bingham");
        music4.setName("Direction of The Wind");
        music4.setBandResId(R.drawable.ryan_bingham_band);
        music4.setCoverResId(R.drawable.direction);
        music4.setMusicFileResId(R.raw.direction_of_the_wind);


        Music music5 = new Music();
        music5.setBand("The Black Heart Procession");
        music5.setName("Last Chance");
        music5.setCoverResId(R.drawable.last);
        music5.setBandResId(R.drawable.the_black_heart_procession_band);
        music5.setMusicFileResId(R.raw.last_chance);

        musicList.add(music1);
        musicList.add(music2);
        musicList.add(music3);
        musicList.add(music4);
        musicList.add(music5);
        return musicList;
    }

    public static String convertMillisToString(long durationsInMillis) {

        long second = (durationsInMillis / 1000) % 60;
        long minuet = (durationsInMillis / (1000 * 60)) % 60;

        return String.format(Locale.US, "%02d:%02d", minuet, second);
    }
}
