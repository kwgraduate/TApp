package com.example.tapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;

import app.akexorcist.bluetotohspp.library.DeviceList;

public class ResultActivity extends AppCompatActivity {

    public static Activity resultactivity;

    private Button btn_return;
    private TextView txt_bigtemperature;
    private TextView txt_age;
    private TextView txt_sex;
    private ImageView iv_sex;
    private TextView txt_temperature;
    private TextView txt_explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultactivity = ResultActivity.this;

        /* 전 activity인 measure activity를 finish */
        DirectmeasureActivity dmactivity = (DirectmeasureActivity)DirectmeasureActivity.dmactivity;
        dmactivity.finish();

        Intent g_intent = getIntent();
        Bundle bundle = g_intent.getExtras(); //Extra들을 가져옴
        int age = Integer.parseInt(bundle.getString("age"));
        float temperature = bundle.getFloat("temperature");
        String sex = bundle.getString("sex");

        BigDecimal f_1=new BigDecimal(String.valueOf(temperature));
        BigDecimal f_2;
        float f_result;

        //투명도 처리->체온계 사진
        Drawable alpha = ((ImageView)findViewById(R.id.iv_resulttherm)).getDrawable();
        alpha.setAlpha(70);

        txt_bigtemperature = findViewById(R.id.txt_bigtemperature);
        txt_age = findViewById(R.id.txt_age);
        txt_sex = findViewById(R.id.txt_sex);
        iv_sex = findViewById(R.id.iv_sex);
        txt_temperature = findViewById(R.id.txt_temperature);
        txt_explain = findViewById(R.id.txt_explain);

        txt_bigtemperature.setText(temperature+" 'C");
        txt_age.setText(age + "세");
        String temp_f="여";
        String temp_m="남";
        if(temp_f.equals(sex)) {
            txt_sex.setText("여");
            iv_sex.setImageResource(R.drawable.ic_female);
        }
        if(temp_m.equals(sex)) {
            txt_sex.setText("남");
            iv_sex.setImageResource(R.drawable.ic_male);
        }
        txt_temperature.setText(temperature + " 'C");

        // 0~2세
        if(age>=0&&age<=2){
            if(temperature>=36.4&&temperature<=38){
                // 정상
                txt_explain.setText("0~2세의 정상 체온은 36.4'C ~ 39'C 으로 현재 사용자는 정상체온입니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#5F9E5B"));
            }
            else{
                // 발열
                f_2=new BigDecimal("38.0");
                f_1=f_1.subtract(f_2);
                f_result=f_1.floatValue();
                txt_explain.setText("사용자는 정상 체온보다 " + f_result + "'C 만큼 높습니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#A63939"));
            }
        }
        //3~10세
        else if(age>2&&age<=10){
            if(temperature>=36.1&&temperature<=37.8){
                // 정상
                txt_explain.setText("2~10세의 정상 체온은 36.1'C ~ 37.8'C 으로 현재 사용자는 정상체온입니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#5F9E5B"));
            }
            else{
                // 발열
                f_2=new BigDecimal("37.8");
                f_1=f_1.subtract(f_2);
                f_result=f_1.floatValue();
                txt_explain.setText("사용자는 정상 체온보다 " + f_result + "'C 만큼 높습니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#A63939"));
            }
        }
        //11~64세
        else if(age>10&&age<65){
            if(temperature>=35.9&&temperature<=37.6){
                // 정상
                txt_explain.setText("10~65세의 정상 체온은 35.9'C ~ 37.6'C 으로 현재 사용자는 정상체온입니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#5F9E5B"));
            }
            else {
                // 발열
                f_2=new BigDecimal("37.6");
                f_1=f_1.subtract(f_2);
                f_result=f_1.floatValue();
                txt_explain.setText("사용자는 정상 체온보다 " + f_result + "'C 만큼 높습니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#A63939"));
            }
        }
        //65세 이상
        else{
            if(temperature>=35.8&&temperature<=37.5){
                // 정상
                txt_explain.setText("65세 이상의 정상 체온은 35.8'C ~ 37.5'C 으로 현재 사용자는 정상체온입니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#5F9E5B"));
            }
            else{
                // 발열
                f_2=new BigDecimal("37.5");
                f_1=f_1.subtract(f_2);
                f_result=f_1.floatValue();
                txt_explain.setText("사용자는 정상 체온보다 " + f_result + "'C 만큼 높습니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#A63939"));
            }
        }
        btn_return = findViewById(R.id.btn_return); //바로측정하기 버튼 연결
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}