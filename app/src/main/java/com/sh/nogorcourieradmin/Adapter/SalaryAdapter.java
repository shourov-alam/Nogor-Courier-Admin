package com.sh.nogorcourieradmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.Model.Driver_Model;
import com.sh.nogorcourieradmin.R;

import java.util.ArrayList;

public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.MyViewHolder> {

Activity activity;
    Context context;
    ArrayList<Driver_Model> arrayList;
    public SalaryAdapter(Context context,ArrayList <Driver_Model> arrayList,Activity activity)
    {

        this.activity=activity;
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.salary_item,parent,false));
    }



    @NonNull
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



        holder.name.setText("Name: "+arrayList.get(position).getName());
        holder.phones.setText("Phone: "+arrayList.get(position).getPhone());


        FirebaseDatabase.getInstance().getReference("Driver_Profile").child(arrayList.get(position).getUid()).child("salary_driver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String a=  dataSnapshot.child("salary").getValue(String.class);

                holder.salary.setText("BDT: "+String.valueOf(a));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(context,"done",Toast.LENGTH_LONG).show();


                FirebaseDatabase.getInstance().getReference("Driver_Profile").child(arrayList.get(position).getUid()).child("salary_driver")
                        .child("salary").setValue("0");



            }
        });
        holder.minus_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Button a;
                final EditText b;



                LayoutInflater layoutInflater = LayoutInflater.from(activity);

                final View view = layoutInflater.inflate(R.layout.sample_minus, null);




                b=view.findViewById(R.id.amount);



                new AlertDialog.Builder(activity)

                        .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FirebaseDatabase.getInstance().getReference("Driver_Profile").child(arrayList.get(position).getUid()).child("salary_driver").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                        String bk=dataSnapshot.child("salary").getValue(String.class);



                                        int f=Integer.valueOf(bk)-Integer.valueOf(b.getText().toString());

                                        FirebaseDatabase.getInstance().getReference("Driver_Profile").child(arrayList.get(position).getUid()).child("salary_driver").
                                                child("salary").setValue(String.valueOf(f));






                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        })

                        .setView(view).show();




            }
        });




    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,phones,salary,cod_received;

        ImageView refresh,minus_salary;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            phones=itemView.findViewById(R.id.phone);
            salary=itemView.findViewById(R.id.salary);
            refresh=itemView.findViewById(R.id.refresh);
            minus_salary=itemView.findViewById(R.id.salary1);












        }
    }
}
