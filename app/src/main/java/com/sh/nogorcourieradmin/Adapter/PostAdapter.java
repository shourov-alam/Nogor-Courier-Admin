package com.sh.nogorcourieradmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.Model.Post_Model;
import com.sh.nogorcourieradmin.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context context;
    ArrayList<Post_Model> arrayList;


    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("User_Payment");

    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Posts");

    public PostAdapter(Context context,ArrayList<Post_Model> arrayList){



        this.context=context;
        this.arrayList=arrayList;

    }

    @NonNull
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.post_item,parent,false));
    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.MyViewHolder holder, final int position) {


        holder.name.setText("Name: "+arrayList.get(position).getName());
        holder.phones.setText("Phone: "+arrayList.get(position).getPhone());

        holder.cod.setText("Cod: "+arrayList.get(position).getCondition_amount());



        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference1.child(arrayList.get(position).getKey()).child("cod_paid").setValue("true");
                databaseReference1.child(arrayList.get(position).getKey()).child("con_now").setValue("You condition amount has sent to your account");


               /* arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size()); */




            }
        });


        Query query = databaseReference1.orderByChild("post_type").equalTo("Cash on Delivery");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {



                int y = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Post_Model post_model = dataSnapshot1.getValue(Post_Model.class);

                    if (post_model.getUser_uid().equals(arrayList.get(position).getUser_uid()) && post_model.getCod_paid().equals("null") && !post_model.getDriver_check1().equals("null")) {

                        y++;
                    }

                    holder.count.setText(String.valueOf(y));

                }

            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



         databaseReference.child(arrayList.get(position).getUser_uid()).child("bk").addValueEventListener(new ValueEventListener() {
             @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // dataSnapshot.child("bKash").getValue(String.class);
                 holder.bkash.setText("bKash: "+  dataSnapshot.child("bKash").getValue(String.class));
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


        databaseReference.child(arrayList.get(position).getUser_uid()).child("acc").addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // dataSnapshot.child("bKash").getValue(String.class);
                //Payment_Model payment_model =dataSnapshot.getValue(Payment_Model.class);

                holder.acc.setText("BANK INFO : "+dataSnapshot.child("b_ac_name").getValue(String.class)+"-->"+dataSnapshot.child("bank_name").getValue(String.class)

                +"-->"+dataSnapshot.child("bank_branch").getValue(String.class)+"-->"+dataSnapshot.child("bank_acc_number").getValue(String.class)

                +"-->"+dataSnapshot.child("routing_num").getValue(String.class)+"-->"+dataSnapshot.child("type").getValue(String.class)

                );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,phones,bkash,acc,cod,count;

        LinearLayout linearLayout;
        ImageView btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phones=itemView.findViewById(R.id.phone);

            bkash=itemView.findViewById(R.id.bkash);
            acc=itemView.findViewById(R.id.acc);
            btn=itemView.findViewById(R.id.done);
            cod=itemView.findViewById(R.id.cod);
            count=itemView.findViewById(R.id.count);











        }
    }
}
