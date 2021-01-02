package com.sh.nogorcourieradmin.Activity;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.Model.Driver_Model;
import com.sh.nogorcourieradmin.Model.Post_Model;
import com.sh.nogorcourieradmin.R;
import com.sh.nogorcourieradmin.Adapter.ViewHolderAnalysis;

public class Analysis extends AppCompatActivity {



    RecyclerView recyclerView;
    LinearLayoutManager llm;
    public FirebaseRecyclerAdapter<Post_Model, ViewHolderAnalysis> firebaseRecyclerAdapter;
    public FirebaseRecyclerOptions<Post_Model> options; // seraching in the profile ;
    DatabaseReference ref;
    Context context;
    TextView name;
    SwipeRefreshLayout sr;
    androidx.appcompat.widget.SearchView searchViewe;

    DatabaseReference mref;
    boolean test;
    String uid;

    String a;
   Driver_Model driver_model,driver_model1;
   String aa,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);




        recyclerView = findViewById(R.id.myList);
        sr=findViewById(R.id.swipe);

        ref = FirebaseDatabase.getInstance().getReference("Posts");




        llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                llm.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);



      loadData();


        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                test=false;




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sr.setRefreshing(false);
                        if(!test) {
                            Toast.makeText(getApplicationContext(), "Check internet connection & try again", Toast.LENGTH_LONG).show();

                        }

                    }
                }, 12000);
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            firebaseRecyclerAdapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            firebaseRecyclerAdapter.startListening();
        }
    }


    public void loadData() {

        //  String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query fireBaseQusery  = ref;


        fireBaseQusery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){

                    Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();

                    sr.setRefreshing(false);
                    test=true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        options = new FirebaseRecyclerOptions.Builder<Post_Model>().setQuery(fireBaseQusery , Post_Model.class).build() ;

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post_Model, ViewHolderAnalysis>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderAnalysis viewHolderAnalysis, final  int i, @NonNull final Post_Model post_model) {


                // setThe data to the row
                //        String imageLink , String itemdes ,String name  ,String quatitys  ,String  category ;


                //     String name , String quantity , String mail , String returnDate , String stats ;




                viewHolderAnalysis.setDetails(post_model.getDriver_checked(),post_model.getDriver_check1()
                        ,post_model.getCon_now(),post_model.getType(),post_model.getBdt(),post_model.getWeight(),
                        post_model.getDate2(),
                        post_model.getPost_type() ,
                        post_model.getArea(),post_model.getAddress(),post_model.getD_area(),

                        post_model.getD_address(),post_model.getPhone(),post_model.getCondition_amount(),post_model.getPid(),
                        post_model.getInstruction(),post_model.getR_name(),post_model.getR_phone(),post_model.getName()


                );

                sr.setRefreshing(false);
                test=true;

                viewHolderAnalysis.done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       // ref.child(getRef(i).getKey()).child("driver_checked").setValue(FirebaseAuth.getInstance().getUid());
                       // ref.child(getRef(i).getKey()).child("pickup_selection").setValue("null");

                        ref.child(getRef(viewHolderAnalysis.getAdapterPosition()).getKey()).removeValue();


                    }
                });
                viewHolderAnalysis.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        String p = "tel:" + post_model.getPhone();
                        i.setData(Uri.parse(p));
                        startActivity(i);



                    }
                });
                viewHolderAnalysis.call1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Intent.ACTION_DIAL);
                        String p = "tel:" + post_model.getR_phone();
                        i.setData(Uri.parse(p));
                        startActivity(i);


                    }
                });


            }

            @NonNull
            @Override
            public ViewHolderAnalysis onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_post_analysis, parent, false);
                ViewHolderAnalysis viewHolder = new ViewHolderAnalysis(itemView);

                return viewHolder;
            }
        } ;

        recyclerView.setLayoutManager(llm);

        firebaseRecyclerAdapter.startListening();

        //setting adapter

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchViewe = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(item);
        searchViewe.setQueryHint("Enter PID");



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
        Query query = ref.orderByChild("pid").startAt(searchTExt).endAt(searchTExt + "\uf8ff") ;
        options = new FirebaseRecyclerOptions.Builder<Post_Model>().setQuery(query , Post_Model.class).build() ;

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post_Model, ViewHolderAnalysis>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderAnalysis viewHolderAnalysis, final int i, @NonNull final Post_Model post_model) {


                // setThe data to the row
                //        String imageLink , String itemdes ,String name  ,String quatitys  ,String  category ;


                //     String name , String quantity , String mail , String returnDate , String stats ;

                viewHolderAnalysis.setDetails(post_model.getDriver_checked(),post_model.getDriver_check1()
                        ,post_model.getCon_now(),post_model.getType(),post_model.getBdt(),post_model.getWeight(),
                        post_model.getDate2(),
                        post_model.getPost_type() ,
                        post_model.getArea(),post_model.getAddress(),post_model.getD_area(),

                        post_model.getD_address(),post_model.getPhone(),post_model.getCondition_amount(),post_model.getPid(),
                        post_model.getInstruction(),post_model.getR_name(),post_model.getR_phone(),post_model.getName()


                );

                sr.setRefreshing(false);
                test=true;

                viewHolderAnalysis.done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // ref.child(getRef(i).getKey()).child("driver_checked").setValue(FirebaseAuth.getInstance().getUid());
                        // ref.child(getRef(i).getKey()).child("pickup_selection").setValue("null");

                        ref.child(getRef(i).getKey()).removeValue();



                        /////////////////////////////////Driver salary///////////////////////////////////////









                        //////////////////////////////////////////////////////////////////////////





                    }
                });
                viewHolderAnalysis.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        String p = "tel:" + post_model.getPhone();
                        i.setData(Uri.parse(p));
                        startActivity(i);



                    }
                });
                viewHolderAnalysis.call1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Intent.ACTION_DIAL);
                        String p = "tel:" + post_model.getR_phone();
                        i.setData(Uri.parse(p));
                        startActivity(i);


                    }
                });
               /* viewHolderPost.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        ref.child(getRef(i).getKey()).removeValue();

                    }
                });

                viewholderForItemList.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        LayoutInflater layoutInflater = LayoutInflater.from(User_Posts.this);

                        final View view = layoutInflater.inflate(R.layout.add_post, null);
                        final EditText title=view.findViewById(R.id.title);
                        final EditText des=view.findViewById(R.id.des);

                        title.setText(post_model.getTitle());
                        des.setText(post_model.getDes());
                        new AlertDialog.Builder(User_Posts.this)


                                .setCancelable(true)
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {







                                        ref.child(getRef(i).getKey()).child("title").setValue(title.getText().toString());
                                        ref.child(getRef(i).getKey()).child("des").setValue(des.getText().toString());


                                    }
                                }).setView(view).show();




                    }
                });
*/











            }



            @NonNull
            @Override
            public ViewHolderAnalysis onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_post_analysis, parent, false);
                ViewHolderAnalysis viewHolder = new ViewHolderAnalysis(itemView);

                return viewHolder;
            }
        } ;

        recyclerView.setLayoutManager(llm);

        firebaseRecyclerAdapter.startListening();

        //setting adapter
        // recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).build());
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        // recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).build());





    }

}


/*





 */