package com.example.tapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ViewGroup v;
    private ImageButton btn_deleteaccount;
    int id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = (ViewGroup)inflater.inflate(R.layout.fragment_3, container, false);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        btn_deleteaccount = (ImageButton) getView().findViewById(R.id.btn_deleteaccount);
        btn_deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(getActivity(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog);

                oDialog.setMessage("????????? ?????????????????????????")
                        .setTitle("?????? ??????")
                        .setPositiveButton("?????????", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Log.i("Dialog", "??????");
                                Toast.makeText(v.getContext(), "??????", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton("???", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //intent?????? ?????? ????????????, main?????? ????????????.
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("id",Integer.toString(id));
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setCancelable(false) // ??????????????? ???????????? ????????? ????????? ??????.
                        .show();
            }
        });
    }
}