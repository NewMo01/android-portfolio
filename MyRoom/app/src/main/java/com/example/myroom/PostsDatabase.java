package com.example.myroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Post.class , version = 1)
@TypeConverters(Converters.class)

public abstract class PostsDatabase extends RoomDatabase {
    private static PostsDatabase instance;
    public abstract PostsDao postsDao();

    public static synchronized PostsDatabase getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext() , PostsDatabase.class , "MyRoom")
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }
}
