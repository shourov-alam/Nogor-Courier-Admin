package com.sh.nogorcourieradmin.Adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.nogorcourieradmin.R;

public class ViewHolderAnalysis extends RecyclerView.ViewHolder {
    public View mview ;
    public TextView nows,weights,date2,post_type,area,address,name2,phone2,status,phone,cod_amount,post_id,instruction,name1,phone1,cost,name;
    public  ImageButton delete,edit;
    public  DatabaseReference ref;
    public  ImageButton call,call1,done;
    public LinearLayout linearLayout;


    public ViewHolderAnalysis(@NonNull View itemView) {
        super(itemView);

        date2=itemView.findViewById(R.id.date);
        name=itemView.findViewById(R.id.m_name);
        nows=itemView.findViewById(R.id.now);


        call=itemView.findViewById(R.id.image_call);
        call1=itemView.findViewById(R.id.image_call1);
        done=itemView.findViewById(R.id.image_done);



        post_id=itemView.findViewById(R.id.post_id);
        linearLayout=itemView.findViewById(R.id.lin2);




        name1=itemView.findViewById(R.id.rname);
        phone1=itemView.findViewById(R.id.rphone);
        instruction=itemView.findViewById(R.id.instruction);
        cost=itemView.findViewById(R.id.cost);

        weights=itemView.findViewById(R.id.weight);





        post_type=itemView.findViewById(R.id.post_type);
        area=itemView.findViewById(R.id.from);
        address=itemView.findViewById(R.id.to);
        name2=itemView.findViewById(R.id.fromarea);
        phone2=itemView.findViewById(R.id.toarea);
        status=itemView.findViewById(R.id.status);
        phone=itemView.findViewById(R.id.phone);
        cod_amount=itemView.findViewById(R.id.condition);





    }
    public  void setDetails(String driver_checked,String driver_checked1,String no,String type,String bdt,String weight,String date,String product_type, String areas,String addresss,String d_areas,String d_addresss,
                            String phones, String cod_amounts,String pid,String ins,String nam,String phn,String nm ) {

        // final String r=refa;





        FirebaseDatabase.getInstance().getReference("Driver_Profile").child(driver_checked)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //  dataSnapshot.child("profile").child("name").getValue(String.class);

                        name1.setText("Name: "+dataSnapshot.child("profile").child("name").getValue(String.class));
                        phone1.setText("Phone: "+dataSnapshot.child("profile").child("phone").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        FirebaseDatabase.getInstance().getReference("Driver_Profile").child(driver_checked1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //  dataSnapshot.child("profile").child("name").getValue(String.class);

                        name2.setText("Name: "+dataSnapshot.child("profile").child("name").getValue(String.class));
                        phone2.setText("Phone: "+dataSnapshot.child("profile").child("phone").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




        FirebaseDatabase.getInstance().getReference("Driver_Profile").child(driver_checked1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          dataSnapshot.child("profile").child("name").getValue(String.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





        name.setText("Name: "+nm+"-->"+nam);
        post_id.setText("PID: "+pid);
        post_type.setText(product_type);
        date2.setText(date);
        area.setText("Area: "+areas+"-->"+d_areas);
        address.setText("Address: "+addresss+"-->"+d_addresss);
     //   d_area.setText("Name: "+driver_d_name);
     //   d_address.setText("Phone: "+driver_d_phone);
        status.setText(type);
        phone.setText("Cell: "+phones+"-->"+phn);
      //  rname.setText("Name: "+driver_p_name);
      //  rphone.setText("Cell: "+driver_p_phone);
        instruction.setText("Instruction: "+ins);

        weights.setText("Weight: "+weight+"kg");
        nows.setText(no);
        cost.setText("Cost: "+bdt+" BDT");




        if(product_type.equals("Parcel Delivery")){
            cod_amount.setVisibility(View.GONE);





        }
        if(product_type.equals("Cash on Delivery")){
            cod_amount.setVisibility(View.VISIBLE);



            cod_amount.setText("Condition: "+cod_amounts+" BDT");
        }










    }




}

