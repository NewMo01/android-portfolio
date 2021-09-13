package com.alyndroid.architecturepatternstutorialshomework.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alyndroid.architecturepatternstutorialshomework.R;
import com.alyndroid.architecturepatternstutorialshomework.databinding.ActivityMainBinding;
import com.alyndroid.architecturepatternstutorialshomework.pojo.DataBase;
import com.alyndroid.architecturepatternstutorialshomework.pojo.NumberModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , LinkActivity {

    ActivityMainBinding bind ;
    // for mvp
    Presenter waiter = new Presenter(this);
    // for mvvm
    MViewModel viewModel = new MViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = DataBindingUtil.setContentView(this , R.layout.activity_main);
        bind.setViewModel(viewModel);
        bind.setLifecycleOwner(this);

        bind.plusButton.setOnClickListener(this);
        bind.divButton.setOnClickListener(this);

    }

    // controller for ( mvc )
    private NumberModel getNumModel(){
        return new DataBase().getNumbers();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.plus_button:
                bind.plusResultTextView.setText(String.valueOf(getNumModel().getFirstNum()+ getNumModel().getSecondNum() )  );
                break;
            case R.id.div_button:
                waiter.giveMe() ;
                break;
        }
    }


    @Override
    public void OnGetModel(int value) {
        bind.divResultTextView.setText(String.valueOf(value));
    }
}
