package com.example.notes_test.fragments;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes_test.BannarAd;
import com.example.notes_test.R;
import com.example.notes_test.RewardAd;
import com.example.notes_test.TimeHelper;
import com.google.android.gms.ads.AdView;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewGoal extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener  {

    public static final String TIME_TAG = "time_data"  ;
    public static final String TARGET_NAME_TAG = "targetName_data";
    public static final String TASKS_TAG = "arrayList_data" ;
    LinearLayout linearLayout ;
    Button addBtn , timeBtn ;
    EditText goalName ;
    private int maxTasks = 0 ;
    private final ArrayList<String> tasks = new ArrayList<>() ;
    private long time = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        linearLayout = findViewById(R.id.linearLayout);
        addBtn = findViewById(R.id.btn_addTask);
        timeBtn = findViewById(R.id.btn_chooseTime);
        goalName = findViewById(R.id.et_goalName);


        // banner Ad
        AdView mAdView = findViewById(R.id.adView);
        BannarAd.loadAd(this);
        BannarAd.showAd(mAdView);
        //Ads
        RewardAd.loadAd(this );
    }

    private void config() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setTransitionName("go");

            setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
            MaterialContainerTransform transform = new MaterialContainerTransform();
            transform.addTarget(android.R.id.content);
            transform.setDuration(300);
            getWindow().setSharedElementEnterTransition(transform);
            getWindow().setSharedElementReturnTransition(transform);
            getWindow().setEnterTransition(null);


        }
    }

    public void onClickAddTask(View view) {

        if(validRead())
        {
            if(maxTasks<6){ addView();  maxTasks++ ; }
            else  addBtn.setEnabled(false);
        }

    }

    private boolean validRead() {
        boolean result = true ;
        if(tasks.size()!=0)tasks.clear();
        for (int i=0 ; i<linearLayout.getChildCount() ; i++){
            View v = linearLayout.getChildAt(i);
            EditText et_task = v.findViewById(R.id.et_task);
            if( !(et_task.getText().toString().isEmpty())  )  {
                tasks.add(et_task.getText().toString());

            }else{
              result = false ;
              break;
            }
        }


        return result;
    }

    public void onClickTime(View view) {
        RewardAd.counter++;
        Log.e("counter",String.valueOf(RewardAd.counter));
        RewardAd.showAd(this);
        showTimeDialog();
    }

    public void onClickStart(View view) {
        if(time !=0 && !goalName.getText().toString().isEmpty()  && validRead()  ){
            if(time < Calendar.getInstance().getTimeInMillis())
                Toast.makeText(this , "please enter a valid time through the day " , Toast.LENGTH_SHORT).show();
            else {
                startActivity(new Intent(NewGoal.this, GoalProgress.class)
                        .putExtra(TARGET_NAME_TAG, goalName.getText().toString())
                        .putExtra(TIME_TAG, time)
                        .putStringArrayListExtra(TASKS_TAG, tasks)
                );
                finish();
            }
        }else Toast.makeText(this , "Be sure you fill all empty fields and data validation " , Toast.LENGTH_SHORT).show();


    }


    private void showTimeDialog() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(NewGoal.this , this , hour , minute , false ).show();

    }

    private void addView(){
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.task , null);
        v.findViewById(R.id.iv_closeTask).setOnClickListener(view -> {
            if(maxTasks==6) addBtn.setEnabled(true);
            removeView(v) ; maxTasks--;
        } );
        linearLayout.addView(v);
    }
    private void removeView(View v){
        linearLayout.removeView(v);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        // check if time is smaller than current time // still we have problem //
        if(time < Calendar.getInstance().getTimeInMillis()){
            try {
                time = TimeHelper.convertIntoMilli( TimeHelper.formatTime(new Date(TimeHelper.year,TimeHelper.month,TimeHelper.date,i+24,i1,0) ) )  ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {

            try {
                time = TimeHelper.convertIntoMilli(TimeHelper.formatTime(new Date(TimeHelper.year, TimeHelper.month, TimeHelper.date, i, i1, 0)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String amPm ;
        if(i>=12){amPm = "PM" ; i-=12;}
        else amPm = "AM";
        timeBtn.setText(i +":"+i1 + " "+amPm);



    }

}