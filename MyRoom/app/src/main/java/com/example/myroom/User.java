package com.example.myroom;

public class User {

    private int userId ;
    private String userName ;

    public User(int id , String name){
        userId = id  ;
        userName = name ;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
