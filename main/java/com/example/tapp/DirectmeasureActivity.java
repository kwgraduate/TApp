package com.example.tapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class DirectmeasureActivity extends AppCompatActivity {

    public static Activity dmactivity;

    private Button btn_dmok;
    private Button btn_prev;
    Spinner spinner;
    private BluetoothSPP bt;
    float temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directmeasure);

        dmactivity = DirectmeasureActivity.this;

        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        //데이터 수신
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                temperature =  Float.parseFloat(message);

                spinner = (Spinner)findViewById(R.id.sp_dmage);
                String age = spinner.getSelectedItem().toString();
                spinner = (Spinner)findViewById(R.id.sp_sex);
                String sex = spinner.getSelectedItem().toString();

                Intent result_intent = new Intent(DirectmeasureActivity.this, ResultActivity.class);
                result_intent.putExtra("age",age);
                result_intent.putExtra("temperature",temperature);
                result_intent.putExtra("sex", sex);
                startActivity(result_intent);

            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
                public void onDeviceConnected(String name, String address) {
                    Toast.makeText(getApplicationContext()
                            , "Connected to " + name + "\n" + address
                            , Toast.LENGTH_SHORT).show();
                }

                public void onDeviceDisconnected() { //연결해제
                    Toast.makeText(getApplicationContext()
                            , "결과를 표시합니다.", Toast.LENGTH_SHORT).show();
                }

                public void onDeviceConnectionFailed() { //연결실패
                    Toast.makeText(getApplicationContext()
                            , "Unable to connect", Toast.LENGTH_SHORT).show();
                }
            });

        btn_dmok = findViewById(R.id.btn_dmok); //바로측정하기 버튼 연결
        btn_dmok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
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
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

/*Copyright (c) 2014 Akexorcist
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/