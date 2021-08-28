package xd.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv ;
     Counter timer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.et);
    }

    public void startOnClick(View view) {
        startTime();
    }

    public void stoptOnClick(View view) {
        timer.cancel();
    }
    public void startTime(){
        timer = new Counter(60000 , 3);
        timer.start();
    }

    private class Counter extends CountDownTimer {

        Counter(long millisInFuture, long countDownInterval){
            super( millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long l) {
            long min = (l/1000)/60;
            long sec = (l/1000);
            tv.setText(String.valueOf(min)+ ":"+String.valueOf(sec) );
        }

        @Override
        public void onFinish() {
            tv.setText("DONE");
        }
    }
}