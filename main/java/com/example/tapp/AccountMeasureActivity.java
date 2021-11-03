package com.example.tapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;

public class AccountMeasureActivity extends AppCompatActivity {

    public static Activity accountmeasureactivity;

    private Button btn_return;
    private TextView txt_bigtemperature;
    private TextView txt_name;
    private TextView txt_age;
    private TextView txt_sex;
    private ImageView iv_sex;
    private TextView txt_temperature;
    private TextView txt_explain;

    private UserDAO mUserDao;

    int id;
    String name;
    int age;
    String sex;
    int count;
    float temperature;
    String temperature1;
    String temperature2;
    String temperature3;
    String temperature4;
    String hour;
    String min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_measure);

        accountmeasureactivity = AccountMeasureActivity.this;

        /* 전 activity인 measure activity를 finish */
        AccountActivity acactivity = (AccountActivity)AccountActivity.acactivity;
        acactivity.finish();

        Intent g_intent = getIntent();
        Bundle bundle = g_intent.getExtras(); //Extra들을 가져옴

        id = bundle.getInt("id");
        name = bundle.getString("name");
        age = Integer.parseInt(bundle.getString("age"));
        count = Integer.parseInt(bundle.getString("count"));
        temperature = bundle.getFloat("temperature");
        temperature1=bundle.getString("temperature1");
        temperature2=bundle.getString("temperature2");
        temperature3=bundle.getString("temperature3");
        temperature4=bundle.getString("temperature4");
        sex = bundle.getString("sex");

        BigDecimal f_1=new BigDecimal(String.valueOf(temperature));
        BigDecimal f_2;
        float f_result;

        //투명도 처리->체온계 사진
        Drawable alpha = ((ImageView)findViewById(R.id.iv_accounttherm)).getDrawable();
        alpha.setAlpha(70);

        txt_bigtemperature = findViewById(R.id.txt_bigtemperature);
        txt_name = findViewById(R.id.txt_accountname);
        txt_age = findViewById(R.id.txt_accountage);
        txt_sex = findViewById(R.id.txt_accountsex);
        iv_sex = findViewById(R.id.iv_accountsex);
        txt_temperature = findViewById(R.id.txt_accounttemperature);
        txt_explain = findViewById(R.id.txt_accountexplain);

        txt_bigtemperature.setText(temperature+" 'C");
        txt_name.setText(name);
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

        //User DATABASE 열기
        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TA_db")
                .fallbackToDestructiveMigration()   // 스키마 버전 변경 가능
                .allowMainThreadQueries()           // Main Thread에서 DB에 IO가능하게 함
                .build();

        mUserDao = database.userDAO();  // 인터페이스 객체 할당
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(Integer.toString(age));
        user.setSex(sex);
        user.setHour(hour);
        user.setMinute(min);
        //temperature에 측정한 값 저장 완료
        int remainder;
        remainder=count%4;

        switch(remainder){
            // temperature 1에 넣어야함.
            case 0:
                user.setTemperature1(Float.toString(temperature));
                user.setTemperature2(temperature2);
                user.setTemperature3(temperature3);
                user.setTemperature4(temperature4);
                break;
            // temperature 2에 넣어야함.
            case 1:
                user.setTemperature1(temperature1);
                user.setTemperature2(Float.toString(temperature));
                user.setTemperature3(temperature3);
                user.setTemperature4(temperature4);
                break;
            // temperature 3에 넣어야함.
            case 2:
                user.setTemperature1(temperature1);
                user.setTemperature2(temperature2);
                user.setTemperature3(Float.toString(temperature));
                user.setTemperature4(temperature4);
                break;
            // temperature 4에 넣어야함.
            case 3:
                user.setTemperature1(temperature1);
                user.setTemperature2(temperature2);
                user.setTemperature3(temperature3);
                user.setTemperature4(Float.toString(temperature));
                break;
        }
        float avg;
        float diff=0;
        //아직 평균내는 중
        if(count<4){
            count++;
            user.setCount(Integer.toString(count));
            //평균 체온을 위한 N번째 체온 정보를 수집했습니다.
            txt_explain.setText("평균 체온을 위한 "+count+"번째 체온 정보 "+temperature+" 'C 를 저장합니다.");
        }

        //평균 낸 값으로 explain하기
        else{
            count++;
            user.setCount(Integer.toString(count));

            avg = (Float.parseFloat(temperature1) + Float.parseFloat(temperature2)
                    + Float.parseFloat(temperature3) + Float.parseFloat(temperature4)) / 4;
            //측정된 체온은 평균체온(xx.xx'C)과 비슷합니다. 더 높습니다. 설정해주기.
            if(temperature<=(avg+0.4)) {
                diff=avg-temperature;
                txt_explain.setText("측정된 체온은 평균체온("+avg+")과 "+diff+" 'C 차이나므로 정상체온입니다.");
                txt_bigtemperature.setTextColor(Color.parseColor("#5F9E5B"));
            }
            else{
                diff=temperature-avg;
                txt_explain.setText("측정된 체온은 평균체온("+avg+")보다 "+diff+" 'C 만큼 높아 발열 증세를 보입니다. ");
                txt_bigtemperature.setTextColor(Color.parseColor("#A63939"));
            }

        }

        btn_return = findViewById(R.id.btn_accountreturn); //바로측정하기 버튼 연결
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDao.setUpdateUser(user);
                Intent intent = new Intent(AccountMeasureActivity.this, AccountActivity.class);
                intent.putExtra("name", name); //변수값 인텐트로 넘기기
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}