package com.zhh.sweepview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhh.sweepview.R;
import com.zhh.sweepview.view.SweepView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SweepView mSweepView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSweepView = (SweepView) findViewById(R.id.sweepView);
        mSweepView.setValue(0);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSweepView.setValue(new Random().nextInt(2000));
            }
        });
    }
}
