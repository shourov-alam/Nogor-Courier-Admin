package com.sh.nogorcourieradmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.Model.Driver_Model;
import com.sh.nogorcourieradmin.Model.Post_Model;
import com.sh.nogorcourieradmin.R;
import com.sh.nogorcourieradmin.Adapter.SalaryAdapter;
import com.sh.nogorcourieradmin.Adapter.ViewHolderPost;

import java.util.ArrayList;

public class Salary extends AppCompatActivity {


    RecyclerView recyclerView;
    LinearLayoutManager llm;
    public FirebaseRecyclerAdapter<Post_Model, ViewHolderPost> firebaseRecyclerAdapter;
    public FirebaseRecyclerOptions<Post_Model> options; // seraching in the profile ;

    //public FirebaseRecyclerAdapter<Driver_Model, ViewHolderAdmin> firebaseRecyclerAdapter1;
    // public FirebaseRecyclerOptions<Driver_Model> options1; // seraching in the profile ;
    DatabaseReference ref;
    LinearLayoutManager llm1;
    Context context;
    RecyclerView recyclerView1;
    TextView name;
    SwipeRefreshLayout sp;
    boolean test;

    DatabaseReference refa;
    DatabaseReference mref;

    String uid;
    ArrayList<Driver_Model> arrayList;

    SalaryAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);


        arrayList=new ArrayList<Driver_Model>();
        sp=findViewById(R.id.swipe);





        recyclerView = findViewById(R.id.myList);

        recyclerView.setLayoutManager(new LinearLayoutManager(Salary.this));

        ref = FirebaseDatabase.getInstance().getReference("Posts");
        refa = FirebaseDatabase.getInstance().getReference("Driver_Profile");



        load();

        sp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                test=false;




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sp.setRefreshing(false);
                        if(!test) {
                            Toast.makeText(getApplicationContext(), "Check internet connection & try again", Toast.LENGTH_LONG).show();

                        }

                    }
                }, 12000);
            }
        });


        refa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                sp.setRefreshing(false);
                test=true;
               //it should place out side, arrayList.clear();
               /* adapter.notifyDataSetChanged();
                Driver_Model driver_model = dataSnapshot.child("profile").getValue(Driver_Model.class);
                arrayList.add(driver_model);

                adapter=new SalaryAdapter(getApplicationContext(),arrayList,Salary.this);*/



                adapter.notifyDataSetChanged();





            //    recyclerView.setAdapter(adapter);




            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }
    void load(){
        refa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                sp.setRefreshing(false);
                test=true;

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){





                    Driver_Model driver_model = dataSnapshot1.child("profile").getValue(Driver_Model.class);
                    arrayList.add(driver_model);



                }

                adapter=new SalaryAdapter(getApplicationContext(),arrayList,Salary.this);



                //adapter.notifyDataSetChanged();





                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
