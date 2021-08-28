package com.example.notes_test;

public class NoteData {

    private String id , title , detail , time , pass  ;
    int color ;
    public NoteData(){

    }


    public NoteData(String id , String title , String detail , String time  , String pass , int color){
        this.id = id ;
        this.title = title;
        this.detail = detail ;
        this.time = time ;
        this.pass = pass ;
        this.color = color ;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
