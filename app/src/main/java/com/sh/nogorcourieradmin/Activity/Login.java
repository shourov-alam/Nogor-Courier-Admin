package com.sh.nogorcourieradmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.R;

public class Login extends AppCompatActivity {


    Button bt;
    EditText edt;
    String h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__salary);




        edt=findViewById(R.id.pass);
        bt=findViewById(R.id.btn_enter);


        FirebaseDatabase.getInstance().getReference("Password").child("pass").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                h=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edt.getText().toString().equals(h)){
                    FirebaseDatabase.getInstance().getReference("Password").child("loged").setValue("true");
                    startActivity(new Intent(getApplicationContext(),Home.class));

                    finish();


                }else {

                    Toast.makeText(getApplicationContext(),"Wrong Password Entered",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
