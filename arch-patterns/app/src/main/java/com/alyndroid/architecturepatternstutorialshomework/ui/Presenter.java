package com.alyndroid.architecturepatternstutorialshomework.ui;

import com.alyndroid.architecturepatternstutorialshomework.pojo.DataBase;
import com.alyndroid.architecturepatternstutorialshomework.pojo.NumberModel;

public class Presenter {

    LinkActivity linkActivity;

    public Presenter(LinkActivity link){
        linkActivity = link ;
    }

    private NumberModel getNumModel(){

        return new DataBase().getNumbers();
    }

    public void giveMe(){
        NumberModel numberModel = getNumModel();
        linkActivity.OnGetModel(numberModel.getFirstNum() / numberModel.getSecondNum());
    }
}
