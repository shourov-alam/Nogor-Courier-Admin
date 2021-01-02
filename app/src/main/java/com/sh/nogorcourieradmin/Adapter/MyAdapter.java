package com.sh.nogorcourieradmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.Model.Driver_Model;
import com.sh.nogorcourieradmin.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
     String a;
    int sum,sum1;
    Context context;
    ArrayList<Driver_Model> arrayList;
    String uidd,type,cod,bdt;


    DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Driver_Profile");


    public MyAdapter(Context context,ArrayList<Driver_Model> arrayList,String uidd,String type,String cod,String bdt){


        this.type=type;
        this.context=context;
        this.arrayList=arrayList;
        this.uidd=uidd;
        this.cod=cod;
        this.bdt=bdt;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.driver_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.name.setText("Name: "+arrayList.get(position).getName());
        holder.phones.setText("Phone: "+arrayList.get(position).getPhone());


        if(type.equals("unread")) {
            holder.cod_received.setVisibility(View.GONE);
            holder.receive.setVisibility(View.GONE);
            holder.refresh.setVisibility(View.GONE);
        }


        if(type.equals("to_wire")) {
            holder.cod_received.setVisibility(View.GONE);
            holder.receive.setVisibility(View.GONE);
            holder.refresh.setVisibility(View.GONE);
        }


        if(type.equals("driver_collect")) {

            sum = 0;
            sum1=0;




            databaseReference.child(arrayList.get(position).getUid()).child("amount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    holder.receive.setText("pick: "+dataSnapshot.child("pick").getValue(String.class));
                    holder.cod_received.setText("cod: "+dataSnapshot.child("cod").getValue(String.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

         /*   final long time = System.currentTimeMillis();

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            final String formattedDate = df.format(time);  */


/*
           Query query = databaseReference.orderByChild("pickup_selection").equalTo(arrayList.get(position).getUid());

           query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {




                        if(dataSnapshot1.child("pick_received").getValue(String.class).equals("null")){
                            sum = sum + Integer.parseInt(dataSnapshot1.child("bdt").getValue(String.class));
                        }










                    }


                  //  notifyDataSetChanged();
                    holder.receive.setText(String.valueOf(sum));





                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            Query query1 = databaseReference.orderByChild("delivery_selection").equalTo(arrayList.get(position).getUid());

            query1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        if(dataSnapshot1.child("deliver_received").getValue(String.class).equals("null")){
                            sum1 = sum1 + Integer.parseInt(dataSnapshot1.child("condition_amount").getValue(String.class));

                        }






                    }



                 //   notifyDataSetChanged();
                    holder.cod_received.setText(String.valueOf(sum1));




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            */




        }





holder.refresh.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(context,"done",Toast.LENGTH_LONG).show();

        //Toast.makeText(context,arrayList.get(position).getUid(),Toast.LENGTH_LONG).show();



       databaseReference.child(arrayList.get(position).getUid()).child("amount").child("pick").setValue("0");
     databaseReference.child(arrayList.get(position).getUid()).child("amount").child("cod").setValue("0");



       /* arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
        Query query2 = databaseReference.orderByChild("pickup_selection").equalTo(arrayList.get(position).getUid());

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                   databaseReference.child(dataSnapshot1.getRef().getKey()).child("pick_received").setValue("true");


                }





                //   notifyDataSetChanged();
                holder.cod_received.setText(String.valueOf(sum1));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query query3 = databaseReference.orderByChild("delivery_selection").equalTo(arrayList.get(position).getUid());

        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    databaseReference.child(dataSnapshot1.getRef().getKey()).child("deliver_received").setValue("true");





                }



                //   notifyDataSetChanged();
                holder.cod_received.setText(String.valueOf(sum1));




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


*/


    }
});

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(type.equals("unread")){



                    Toast.makeText(context,"done",Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("Posts").child(uidd).child("pickup_selection")
                            .setValue(arrayList.get(position).getUid());




                    FirebaseDatabase.getInstance().getReference("Posts").child(uidd).child("admin_checked")
                            .setValue("true");

                    FirebaseDatabase.getInstance().getReference("Posts").child(uidd).child("admin_wire_check")
                            .setValue("null");

                }else if(type.equals("to_wire")){

                    Toast.makeText(context,"done",Toast.LENGTH_LONG).show();


                    FirebaseDatabase.getInstance().getReference("Posts").child(uidd).child("delivery_selection")
                            .setValue(arrayList.get(position).getUid());

                    FirebaseDatabase.getInstance().getReference("Posts").child(uidd).child("con_now")
                            .setValue("Your Product has reached in WireHouse ");





                    FirebaseDatabase.getInstance().getReference("Posts").child(uidd).child("admin_wire_check")
                            .setValue("true");

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,phones,receive,cod_received;

        ImageView refresh;
        CardView linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.driver_name);
            phones=itemView.findViewById(R.id.driver_phone);
            linearLayout=itemView.findViewById(R.id.lin1);
            receive=itemView.findViewById(R.id.driver_receive);
            cod_received=itemView.findViewById(R.id.cod_receive);
            refresh=itemView.findViewById(R.id.refresh);











        }
    }
}
