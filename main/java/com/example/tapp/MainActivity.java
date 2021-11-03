package com.example.tapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btn_dm;
    private ImageButton btn_generate;

    private UserDAO mUserDao;

    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;
    private NotificationManager notificationManager;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent fr_intent = getIntent();
        if(!TextUtils.isEmpty(fr_intent.getStringExtra("id"))){
            Bundle bundle = fr_intent.getExtras(); //Extra들을 가져옴
            id = Integer.parseInt(bundle.getString("id"));
            Toast.makeText(getApplicationContext()
                    , "id: "+id
                    , Toast.LENGTH_SHORT).show();
            UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TA_db")
                    .fallbackToDestructiveMigration()   // 스키마 버전 변경 가능
                    .allowMainThreadQueries()           // Main Thread에서 DB에 IO가능하게 함
                    .build();

            mUserDao = database.userDAO();  // 인터페이스 객체 할당

            User user_del = new User();
            user_del.setId(id);
            mUserDao.setDeleteUser(user_del);
        }



        /* 알람 설정 */
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mCalender = new GregorianCalendar();
        Log.v("HelloAlarmActivity", mCalender.getTime().toString());

        setContentView(R.layout.activity_main);

        /* 리사이클러 뷰 설정 */
        recyclerView=(RecyclerView)findViewById(R.id.rv_main);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList=new ArrayList<>();

        mainAdapter=new MainAdapter(arrayList, MainActivity.this);
        recyclerView.setAdapter(mainAdapter);

        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TA_db")
                .fallbackToDestructiveMigration()   // 스키마 버전 변경 가능
                .allowMainThreadQueries()           // Main Thread에서 DB에 IO가능하게 함
                .build();

        mUserDao = database.userDAO();  // 인터페이스 객체 할당
        List<User> user_l = mUserDao.getUserAll();

        String user_n;
        MainData mainD;
        for(int i=0;i<user_l.size();i++){
            user_n=user_l.get(i).getName();
            mainD=new MainData(R.drawable.icon_account,user_n);
            arrayList.add(mainD);
            mainAdapter.notifyDataSetChanged();
        }

        /* 바로 측정하기 Button */
        btn_dm = findViewById(R.id.btn_dm); //바로측정하기 버튼 연결

        btn_dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DirectmeasureActivity.class);//인자(현재activity,이동activity)
                startActivity(intent); //activity이동
            }
        });

        /* 계정 생성하기 Button */
        btn_generate = findViewById(R.id.btn_generate);

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generate_intent = new Intent(MainActivity.this, MakeaccountActivity.class);
                startActivityForResult(generate_intent, 0);
            }
        });

    }

    private void setAlarm() {
        //AlarmReceiver에 값 전달
        Intent receiverIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, receiverIntent, 0);

        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TA_db")
                .fallbackToDestructiveMigration()   // 스키마 버전 변경 가능
                .allowMainThreadQueries()           // Main Thread에서 DB에 IO가능하게 함
                .build();
        mUserDao = database.userDAO();  // 인터페이스 객체 할당
        List<User> user_a = mUserDao.getUserAll();

        String user_hour=user_a.get(user_a.size()-1).getHour();
        String user_minute=user_a.get(user_a.size()-1).getMinute();
        // 현재 지정된 시간으로 알람 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(user_hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(user_minute));
        calendar.set(Calendar.SECOND, 0);

        // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Date currentDateTime = calendar.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
        Toast.makeText(getApplicationContext(), date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),pendingIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        /*MakeAccount Activity에서 보내온 result*/
        if(requestCode==0){
            if (resultCode==RESULT_OK) {
                // 여기에 받아온 id값을 넣어서 listview생성하기
               UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TA_db")
                        .fallbackToDestructiveMigration()   // 스키마 버전 변경 가능
                        .allowMainThreadQueries()           // Main Thread에서 DB에 IO가능하게 함
                        .build();

               mUserDao = database.userDAO();  // 인터페이스 객체 할당

                List<User> userList = mUserDao.getUserAll();

                int id_size=userList.size()-1;
                String user_name=userList.get(id_size).getName();
                //System.out.println("유저 이름은: "+user_name+"\n");

                MainData mainData=new MainData(R.drawable.icon_account,user_name);
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();

                setAlarm();
            }else{

            }
        }
    }
}
    //<div>아이콘 제작자 <a href="https://www.flaticon.com/kr/authors/slidicon" title="Slidicon">Slidicon</a>
// from <a href="https://www.flaticon.com/kr/" title="Flaticon">www.flaticon.com</a></div>

