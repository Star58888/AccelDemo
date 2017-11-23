package com.star.acceldemo;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sr;
    TextView txv;
    ImageView igv;
    RelativeLayout layout;
    double mx = 0 , my = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //手機不要旋轉畫面
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        txv = findViewById(R.id.textView);
        igv = findViewById(R.id.imgMove);
        layout = findViewById(R.id.layout);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mx == 0)
        {
            mx = (layout.getWidth()-igv.getWidth()) /20;
            my = (layout.getHeight()-igv.getHeight()) /20;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) igv.getLayoutParams();
        params.leftMargin = (int)((5-event.values[0] *2* mx));
        params.topMargin = (int)((5-event.values[1] *2* my ));
        igv.setLayoutParams(params);

        txv.setText(String.format("X = %1.2f , Y =%1.2f , Z = %1.2f" ,event.values[0] ,event.values[1] ,event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this ,sr ,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
}
