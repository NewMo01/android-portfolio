package com.example.notes_test.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes_test.MyService;
import com.example.notes_test.R;
import com.example.notes_test.TimeHelper;

import java.util.Date;

public class GoalProgress extends AppCompatActivity {


    TextView name , tv_time , tasks ;

    public static long chosenTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_progress);


        // initialize components
        name = findViewById(R.id.tv_targetName_prog);
        tv_time = findViewById(R.id.tv_time_prog);
        tasks = findViewById(R.id.tv_tasks_prog);
        //get intent data
        name.setText(getIntent().getStringExtra(NewGoal.TARGET_NAME_TAG));
        chosenTime = getIntent().getLongExtra(NewGoal.TIME_TAG , 0) ;   // milli seconds

        StringBuilder data = new StringBuilder() ;
        if(getIntent().getStringArrayListExtra(NewGoal.TASKS_TAG) != null){
            for (String task : getIntent().getStringArrayListExtra(NewGoal.TASKS_TAG))
                data.append(task).append("\n");
            tasks.setText(data.toString());
        }

        // start the service
        startService(new Intent(this , MyService.class));



    }

    BroadcastReceiver timerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTv(intent);
            Log.e("Receiving data" , "successfully received data");
        }
    };


    public void updateTv(Intent intent){
        long milli = intent.getLongExtra(MyService.BROADCAST_DATA , 0);
        int hour = (int) milli/(1000*60*60) ;
        int min = (int) milli/(1000*60)%60  ;
        int sec = (int) (milli/1000)%60 ;

        tv_time.setText(
                TimeHelper.formatTime(new Date(TimeHelper.year,TimeHelper.month,TimeHelper.date,hour,min,sec) )
        );
        if(hour==0 && min==0 && sec==0)finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(timerReceiver, new IntentFilter(MyService.MY_BROADCAST) );

        Log.e("Resume:" , "on resume is on");
    }

    @Override
    protected void onPause() {
        unregisterReceiver(timerReceiver);
        super.onPause();
        Log.e("pause:" , "on pause is on");
    }

    @Override
    protected void onStop() {
        try{
            unregisterReceiver(timerReceiver);
        }catch (Exception ignored){

        }
        super.onStop();
        Log.e("Stop:" , "on stop is on");
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this , MyService.class));
        super.onDestroy();
        Log.e("Destroy:" , "on destroy is on");
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("You sure ,  you want to cancel the target ?")
                .setNegativeButton(R.string.Cancelation , null)
                .setPositiveButton("Yes, i can't finish it", (dialogInterface, i) -> finish()).create().show();
    }

    public void cancelOnClick(View view) {
        onBackPressed();
    }
}