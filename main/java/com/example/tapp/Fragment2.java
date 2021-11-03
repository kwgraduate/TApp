package com.example.tapp;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ViewGroup v;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txt_avg;
    String temperature1;
    String temperature2;
    String temperature3;
    String temperature4;
    String count;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            count = getArguments().getString("count");
            temperature1 = getArguments().getString("temperature1");
            temperature2 = getArguments().getString("temperature2");
            temperature3 = getArguments().getString("temperature3");
            temperature4 = getArguments().getString("temperature4");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = (ViewGroup)inflater.inflate(R.layout.fragment_2, container, false);
        txt_avg = (TextView)v.findViewById(R.id.txt_avg);
        float temp_avg;
        if(Integer.parseInt(count)>=4) {
            temp_avg=(Float.parseFloat(temperature1)
                    +Float.parseFloat(temperature2)
                    +Float.parseFloat(temperature3)
                    +Float.parseFloat(temperature4))/4;
            txt_avg.setText(temp_avg+"'C");
        }

        LineChart chart = (LineChart)v.findViewById(R.id.linechart);

        ArrayList<Entry> values = new ArrayList<>();

        if(temperature1!=null) values.add(new Entry(0,Float.parseFloat(temperature1)));
        if(temperature2!=null) values.add(new Entry(1,Float.parseFloat(temperature2)));
        if(temperature3!=null) values.add(new Entry(2,Float.parseFloat(temperature3)));
        if(temperature4!=null) values.add(new Entry(3,Float.parseFloat(temperature4)));

        LineDataSet set1;
        set1 = new LineDataSet(values, "DataSet 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // set data
        chart.setData(data);


        return v;
    }
}