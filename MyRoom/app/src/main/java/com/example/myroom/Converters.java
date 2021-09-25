package com.example.myroom;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class Converters {

    @TypeConverter
    public String userToGson(User user){
        return new Gson().toJson(user);
    }

    @TypeConverter
    public User gsonToUser(String string){
        return new Gson().fromJson(string , User.class);
    }
}
