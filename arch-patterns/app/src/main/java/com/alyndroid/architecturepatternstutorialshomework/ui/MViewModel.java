package com.alyndroid.architecturepatternstutorialshomework.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alyndroid.architecturepatternstutorialshomework.pojo.DataBase;
import com.alyndroid.architecturepatternstutorialshomework.pojo.NumberModel;


public class MViewModel extends ViewModel {

    public MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>() ;

    public void catchValue(){
        NumberModel numberModel = getNumModel();
        mutableLiveData.setValue( numberModel.getFirstNum() * numberModel.getSecondNum()  );

    }

    private NumberModel getNumModel(){
        return new DataBase().getNumbers();
    }


}
