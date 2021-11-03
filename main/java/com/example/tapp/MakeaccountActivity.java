package com.example.tapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MakeaccountActivity extends AppCompatActivity {

    private UserDAO mUserDao;
    private Button btn_accountok;
    Spinner spinner;
    EditText et_accountname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeaccount);

        //24시 모드로 타임피커 설정
        final TimePicker tp_reservation = (TimePicker) findViewById(R.id.tp_reservation);
        tp_reservation.setIs24HourView(true);

        btn_accountok = findViewById(R.id.btn_accountok);

        btn_accountok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 타임피커에서 시간 정보 가져오기
                int hour, hour_24, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23) {
                    hour_24 = tp_reservation.getHour();
                    minute = tp_reservation.getMinute();
                } else {
                    hour_24 = tp_reservation.getCurrentHour();
                    minute = tp_reservation.getCurrentMinute();
                }
                if (hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;
                } else {
                    hour = hour_24;
                    am_pm = "AM";
                }

                // 필요한 데이터 가져오기: 1.계정이름 2.나이 3.성별 4.체온-4개다 0으로 셋팅
                et_accountname = findViewById(R.id.et_accountname);
                String name = et_accountname.getText().toString();

                spinner = (Spinner) findViewById(R.id.sp_accountage);
                String age = spinner.getSelectedItem().toString();

                spinner = (Spinner) findViewById(R.id.sp_accountsex);
                String sex = spinner.getSelectedItem().toString();

                UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TA_db")
                        .fallbackToDestructiveMigration()   // 스키마 버전 변경 가능
                        .allowMainThreadQueries()           // Main Thread에서 DB에 IO가능하게 함
                        .build();

                mUserDao = database.userDAO();  // 인터페이스 객체 할당

                // 데이터 삽입
                User user = new User();
                user.setName(name);
                user.setAge(age);
                user.setSex(sex);
                /*user.setTemperature1("0");
                user.setTemperature2("0");
                user.setTemperature3("0");
                user.setTemperature4("0");*/
                user.setCount("0");
                user.setHour(String.valueOf(hour_24));
                user.setMinute(String.valueOf(minute));

                mUserDao.setInsertUser(user);

                Intent intent = new Intent(MakeaccountActivity.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}