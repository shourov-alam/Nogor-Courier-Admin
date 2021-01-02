package com.sh.nogorcourieradmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.R;

public class Splash extends AppCompatActivity {

    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(getSupportActionBar() != null){

            getSupportActionBar().hide();
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Handler handler = new Handler();


        FirebaseDatabase.getInstance().getReference("Password").child("loged").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                a=dataSnapshot.getValue(String.class);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        if(a.equals("true")){


                            startActivity(new Intent(getApplicationContext(),Home.class));
                            finish();


                        }else {

                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }

                    }
                }, 1500) ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
