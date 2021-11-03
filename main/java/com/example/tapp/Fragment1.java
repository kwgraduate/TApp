package com.example.tapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewGroup v;

    private BluetoothSPP bt;

    int id;
    String name; // 전달한 key 값
    String age;
    String sex;
    String count;
    float temperature;
    String temperature1;
    String temperature2;
    String temperature3;
    String temperature4;
    String hour;
    String min;

    private TextView txt_fragment1;
    private TextView txt_homename;
    private TextView txt_homeage;
    private TextView txt_homesex;
    private ImageView iv_homesex;
    private TextView txt_homeavg;
    private Button btn_measure;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            id = getArguments().getInt("id");
            name = getArguments().getString("name");
            age = getArguments().getString("age");
            sex = getArguments().getString("sex");
            count = getArguments().getString("count");
            temperature1 = getArguments().getString("temperature1");
            temperature2 = getArguments().getString("temperature2");
            temperature3 = getArguments().getString("temperature3");
            temperature4 = getArguments().getString("temperature4");
            hour = getArguments().getString("hour");
            min=getArguments().getString("min");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = (ViewGroup)inflater.inflate(R.layout.fragment_1, container, false);

        txt_homename = (TextView)v.findViewById(R.id.txt_homename);
        txt_homeage = (TextView)v.findViewById(R.id.txt_homeage);
        txt_homesex = (TextView)v.findViewById(R.id.txt_homesex);
        iv_homesex = (ImageView) v.findViewById(R.id.iv_homesex);
        txt_homeavg = (TextView)v.findViewById(R.id.txt_homeavg);

        txt_homename.setText(name);
        txt_homeage.setText(age);
        String temp_f="여";
        String temp_m="남";
        if(temp_f.equals(sex)) {
            txt_homesex.setText("여");
            iv_homesex.setImageResource(R.drawable.ic_female);
        }
        if(temp_m.equals(sex)) {
            txt_homesex.setText("남");
            iv_homesex.setImageResource(R.drawable.ic_male);
        }
        float temp_avg;
        if(Integer.parseInt(count)>=4) {
            temp_avg=(Float.parseFloat(temperature1)
                    +Float.parseFloat(temperature2)
                    +Float.parseFloat(temperature3)
                    +Float.parseFloat(temperature4))/4;
            txt_homeavg.setText(temp_avg+"'C");
        }
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        bt = new BluetoothSPP(getActivity()); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(v.getContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } //데이터 수신
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                temperature =  Float.parseFloat(message);

                Intent result_intent = new Intent(getActivity(), AccountMeasureActivity.class);
                result_intent.putExtra("id",id);
                result_intent.putExtra("name",name);
                result_intent.putExtra("age",age);
                result_intent.putExtra("count", count);
                result_intent.putExtra("temperature",temperature);
                result_intent.putExtra("temperature1",temperature1);
                result_intent.putExtra("temperature2",temperature2);
                result_intent.putExtra("temperature3",temperature3);
                result_intent.putExtra("temperature4",temperature4);
                result_intent.putExtra("sex", sex);
                result_intent.putExtra("hour",hour);
                result_intent.putExtra("min",min);
                startActivity(result_intent);
                getActivity().finish();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(v.getContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(v.getContext()
                        , "결과를 표시합니다.", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(v.getContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        btn_measure = (Button) getView().findViewById(R.id.btn_measure);
        btn_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getActivity(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }

    public void setup() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(v.getContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}