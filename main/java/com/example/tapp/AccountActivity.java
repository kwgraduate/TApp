package com.example.tapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AccountActivity extends AppCompatActivity {

    public static Activity acactivity;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    int id;
    String name;
    String age;
    String sex;
    String count;
    String temperature1;
    String temperature2;
    String temperature3;
    String temperature4;
    String hour;
    String min;

    Intent intent;
    private UserDAO mUserDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        acactivity = AccountActivity.this;

        intent = getIntent();
        name = intent.getStringExtra("name");

        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TA_db")
                .fallbackToDestructiveMigration()   // 스키마 버전 변경 가능
                .allowMainThreadQueries()           // Main Thread에서 DB에 IO가능하게 함
                .build();

        mUserDao = database.userDAO();  // 인터페이스 객체 할당
        List<User> user_l = mUserDao.getUserAll();
        String user_n;

        for(int i=0;i<user_l.size();i++){
            user_n=user_l.get(i).getName();
            if(name.equals(user_n)){
                id=user_l.get(i).getId();
                name=user_l.get(i).getName();
                age=user_l.get(i).getAge();
                sex=user_l.get(i).getSex();
                count=user_l.get(i).getCount();
                temperature1=user_l.get(i).getTemperature1();
                temperature2=user_l.get(i).getTemperature2();
                temperature3=user_l.get(i).getTemperature3();
                temperature4=user_l.get(i).getTemperature4();
                hour=user_l.get(i).getHour();
                min=user_l.get(i).getMinute();
                break;
            }
        }

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name", name); // Key, Value
        bundle.putString("age", age); // Key, Value
        bundle.putString("sex", sex); // Key, Value
        bundle.putString("count", count);
        bundle.putString("temperature1", temperature1); // Key, Value
        bundle.putString("temperature2", temperature2); // Key, Value
        bundle.putString("temperature3", temperature3); // Key, Value
        bundle.putString("temperature4", temperature4); // Key, Value
        bundle.putString("hour",hour);
        bundle.putString("min",min);
        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, fragment1).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, fragment1).commit();
                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, fragment2).commit();
                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, fragment3).commit();
                        return true;
                }
                return false;
            }
        });
    }
}