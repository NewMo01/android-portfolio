package com.example.notes_test.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.notes_test.fragments.Goals;
import com.example.notes_test.fragments.Notes;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private final int fragsNo;

    public MyPagerAdapter(@NonNull FragmentManager fm , int fragsNo) {
        super(fm);
        this.fragsNo = fragsNo ;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Notes();
            case 1: return new Goals();
            default:return null ;
        }

    }

    @Override
    public int getCount() {
        return fragsNo;
    }
}
