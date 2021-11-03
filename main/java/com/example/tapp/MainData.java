package com.example.tapp;

public class MainData {

    private int iv_profile;
    private String txt_name;

    public MainData(int iv_profile, String txt_name) {
        this.iv_profile = iv_profile;
        this.txt_name = txt_name;
    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTxt_name() {
        return txt_name;
    }

    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }
}
