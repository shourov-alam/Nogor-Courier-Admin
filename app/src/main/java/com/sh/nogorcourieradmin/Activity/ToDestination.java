package com.sh.nogorcourieradmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.Adapter.PostAdapter;
import com.sh.nogorcourieradmin.Adapter.ViewHolderAnalysis;
import com.sh.nogorcourieradmin.Model.Post_Model;
import com.sh.nogorcourieradmin.R;
import com.sh.nogorcourieradmin.Adapter.ViewHolderPost;

import java.util.ArrayList;

public class ToDestination extends AppCompatActivity{


    RecyclerView recyclerView;
    LinearLayoutManager llm;
    public FirebaseRecyclerAdapter<Post_Model, ViewHolderPost> firebaseRecyclerAdapter;
    public FirebaseRecyclerOptions<Post_Model> options; // seraching in the profile ;

    //public FirebaseRecyclerAdapter<Driver_Model, ViewHolderAdmin> firebaseRecyclerAdapter1;
    // public FirebaseRecyclerOptions<Driver_Model> options1; // seraching in the profile ;
    DatabaseReference ref;
    androidx.appcompat.widget.SearchView searchViewe;
    LinearLayoutManager llm1;
    SwipeRefreshLayout sp;
    boolean test;
    Context context;
    RecyclerView recyclerView1;
    TextView name;
    DatabaseReference refa;
    DatabaseReference mref;
    String uid;
    ArrayList<Post_Model> arrayList;
    ArrayList<Post_Model> arrayList1;


    PostAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_destination);

        arrayList=new ArrayList<Post_Model>();
        arrayList1=new ArrayList<Post_Model>();

        sp=findViewById(R.id.swipe);





        recyclerView = findViewById(R.id.myList);

        recyclerView.setLayoutManager(new LinearLayoutManager(ToDestination.this));

        ref = FirebaseDatabase.getInstance().getReference("Posts");
        refa = FirebaseDatabase.getInstance().getReference("Driver_Profile");



     /*   final long time = System.currentTimeMillis();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final String formattedDate = df.format(time);  */


alam();




        sp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               alam();
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





        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                sp.setRefreshing(false);
                test=true;
                arrayList.clear();
                adapter.notifyDataSetChanged();
               // Toast.makeText(getApplicationContext(),"trig",Toast.LENGTH_LONG).show();
                alam();

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




    void alam(){




        Query query1 = ref.orderByChild("post_type").equalTo("Cash on Delivery");

      /*  query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                arrayList.clear();
                adapter.notifyDataSetChanged();
                Post_Model post_model = dataSnapshot.getValue(Post_Model.class);


                arrayList.add(post_model);




                adapter=new PostAdapter(getApplicationContext(),arrayList);




                adapter.notifyDataSetChanged();






                recyclerView.setAdapter(adapter);
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

*/

query1.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {





        if(dataSnapshot.exists()) {

            arrayList.clear();
            sp.setRefreshing(false);
            test = true;
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {




                Post_Model post_model = dataSnapshot1.getValue(Post_Model.class);


                if (post_model.getCod_paid().equals("null") && !post_model.getDriver_check1().equals("null")) {
                    arrayList.add(post_model);

                }


            }
        }else {

            Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
            sp.setRefreshing(false);
            test=true;

        }



        adapter=new PostAdapter(getApplicationContext(),arrayList);
     //   adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchViewe = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(item);
        searchViewe.setQueryHint("Enter Phone");



        searchViewe.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // firebaseSearch(query);

                FirebaseSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Filter as you type

                //  firebaseSearch(newText);
                FirebaseSearch(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;

            default:
                break;
        }
        // searchViewe.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private  void FirebaseSearch(String searchTExt) {

        final DatabaseReference  ref = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = ref.orderByChild("phone").startAt(searchTExt).endAt(searchTExt + "\uf8ff") ;


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {





                if(dataSnapshot.exists()) {

                    arrayList.clear();

                    sp.setRefreshing(false);
                    test = true;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {




                        Post_Model post_model = dataSnapshot1.getValue(Post_Model.class);


                        if (post_model.getPost_type().equals("Cash on Delivery") && post_model.getCod_paid().equals("null") && !post_model.getDriver_check1().equals("null")) {
                            arrayList.add(post_model);

                        }


                    }
                }else {

                    Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
                    sp.setRefreshing(false);
                    test=true;

                }



                adapter=new PostAdapter(getApplicationContext(),arrayList);
                //   adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



}
