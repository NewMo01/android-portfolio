package com.example.notes_test.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.notes_test.BannarAd;
import com.example.notes_test.NoteData;
import com.example.notes_test.R;
import com.example.notes_test.RewardAd;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class NewNote extends AppCompatActivity  {

    EditText title , detail ;
    ImageView lock , iconColor , cancel , ok , bin ;
    String passcode = null ; int color = Color.BLACK ;
    int red , blue , green ;
    String  noteId  ;
    Boolean newN = true ;


    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static final DatabaseReference myRef = database.getReference(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("InflateParams")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note_lay);

        getWindow().setEnterTransition(null);


        // banner Ad
        AdView mAdView = findViewById(R.id.adView);
        BannarAd.loadAd(this);
        BannarAd.showAd(mAdView);

        // Reward ad
        RewardAd.loadAd(this );

        // initialize components
        title = findViewById(R.id.titleNote);
        detail = findViewById(R.id.detailNote);
        lock = findViewById(R.id.lockNote);
        iconColor = findViewById(R.id.colorNote);
        cancel = findViewById(R.id.delNote);
        ok = findViewById(R.id.addNote);
        bin = findViewById(R.id.deleteIcon);

        // onItem click intent
        ViewCompat.setTransitionName(title , "titleTransition");
        title.setText(getIntent().getStringExtra("data_title"));
        detail.setText(getIntent().getStringExtra("data_detail"));
        noteId = getIntent().getStringExtra("data_id");
        passcode = getIntent().getStringExtra("data_pass");
        color = getIntent().getIntExtra("data_color" , Color.BLACK);
        newN = getIntent().getBooleanExtra("check",true);

        // Lock implementation
        lock.setOnClickListener(view -> showPasswordDialog(getLayoutInflater().inflate(R.layout.dialog_pass_lay,null)));
        //choose color implementation
        iconColor.setOnClickListener(view -> chooseColor(getLayoutInflater().inflate(R.layout.dialog_color,null)));
        // add note implementation
        ok.setOnClickListener(view -> {
            RewardAd.counter++;
            RewardAd.showAd(this);
            saveData();
        });
        // cancel note implementation
        cancel.setOnClickListener(view -> {
            if(newN) finish() ;
            else {
                new AlertDialog.Builder(this)
                        .setTitle("You sure you want to delete it ?")
                        .setNegativeButton(R.string.Cancelation , null)
                        .setPositiveButton(R.string.Accept, (dialogInterface, i) -> {
                            RewardAd.counter++;
                            RewardAd.showAd(this);
                            finish(); myRef.child("notes").child(noteId).removeValue() ;
                        }).create().show();

            }

        } );


    }


    private void saveData() {
        if(title.getText().toString().isEmpty() || detail.getText().toString().isEmpty()){
            Toast.makeText(this , "Please fill fields" , Toast.LENGTH_SHORT).show();
        }else if(newN)
        {
            noteId = myRef.push().getKey();
            myRef.child("notes").child(noteId).setValue(new NoteData(
                    noteId,title.getText().toString() , detail.getText().toString() , getCurrentTime() , passcode,color
                    ));

            finish();
        }else{
            myRef.child("notes").child(noteId).setValue(new NoteData(
                    noteId,title.getText().toString() , detail.getText().toString() , getCurrentTime() , passcode,color ));
            finish();
        }

    }


    private void chooseColor(View v) {
        View sview = v.findViewById(R.id.viewShow);
        SeekBar sRed = v.findViewById(R.id.redSeekbar);
        SeekBar sBlue = v.findViewById(R.id.blueSeekbar);
        SeekBar sGreen = v.findViewById(R.id.greenSeekbar);

        SeekBar.OnSeekBarChangeListener slistener = new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (seekBar.getId()){
                    case R.id.redSeekbar:
                        red = i ;break;
                    case R.id.greenSeekbar:
                        green = i ; break;
                    case R.id.blueSeekbar:
                        blue = i ; break;
                }
                color = Color.rgb(red,green,blue) ;
                sview.setBackgroundColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        sBlue.setOnSeekBarChangeListener(slistener);
        sRed.setOnSeekBarChangeListener(slistener);
        sGreen.setOnSeekBarChangeListener(slistener);

        new AlertDialog.Builder(this)
                .setTitle("Choose the color")
                .setView(v)
                .setNegativeButton(getString(R.string.Cancelation), (dialogInterface, i) -> color = Color.BLACK)
                .setPositiveButton(R.string.Accept, null).create().show();

    }

    public void showPasswordDialog(View v) {
        EditText et_pass = v.findViewById(R.id.title_dialog_pass);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Enter the password : ")
                .setView(v)
                .setNegativeButton(R.string.Cancelation, null)
                .setPositiveButton(R.string.Accept, null).create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            if(et_pass.getText().toString().isEmpty())
            {
                Toast.makeText(NewNote.this , "Please enter a password",Toast.LENGTH_SHORT).show();
            }else {
                    passcode = et_pass.getText().toString().trim();
                    dialog.dismiss();
                }
        });

    }

    public String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") Format f = new SimpleDateFormat("E   dd/MM   hh:mm a");
        return f.format(calendar.getTime());
    }

}

