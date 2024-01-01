package com.example.weatherapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class timer extends Fragment {
    private int seconds = 0;
    private boolean running = false;
    private View fragmentView; // Add a member variable to hold the fragment's view


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkInstance(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkInstance(savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_timer, container, false);

        Button startbtn = fragmentView.findViewById(R.id.btnStart);
        Button resetbtn = fragmentView.findViewById(R.id.btnReset);
        Button stopbtn = fragmentView.findViewById(R.id.btnStop);

        startbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                running = true;

            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                running = false;

            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                running = false;
                seconds = 0;
            }
        });
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        runTimer();

    }

    private void checkInstance(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("SECONDS");
            running = savedInstanceState.getBoolean("RUNNING");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("SECONDS", seconds);
        outState.putBoolean("RUNNING", running);
    }

    private void runTimer(){

        final TextView txtTime = fragmentView.findViewById(R.id.txtTime);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600) / 60;
                int secs = seconds%60;
                String time = hours +" : " + minutes + " : " + secs;
                txtTime.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this, 1000);

            }
        });
    }

}