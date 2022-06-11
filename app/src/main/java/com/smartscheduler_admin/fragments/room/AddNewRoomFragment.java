package com.smartscheduler_admin.fragments.room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartscheduler_admin.R;


public class AddNewRoomFragment extends Fragment {
    private EditText room_number;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    int count=0;
    boolean isCountingDone=false;

    public AddNewRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_room, container, false);

        room_number = view.findViewById(R.id.roomNumber);
        CardView submitData = view.findViewById(R.id.submitCard);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        submitData.setOnClickListener(v -> {
            String name = room_number.getText().toString().trim();


            if (name.equals("")) {
                Toast.makeText(getContext(), "Add Room Number", Toast.LENGTH_SHORT).show();
                return;
            }


            DatabaseReference newRef = myRef.child("Rooms").child((++count)+"");

            newRef.child("ID").setValue(count);
            newRef.child("NUMBER").setValue(name);


            Toast.makeText(getContext(), "Rooms Added", Toast.LENGTH_SHORT).show();

            room_number .setText("");

        });




        getScheduleNumber();


        return view;
    }

    private void getScheduleNumber(){
        DatabaseReference newRef = myRef.getRef();
        newRef.child("Rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isCountingDone=true;
                if (snapshot.exists())
                    count=(int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}