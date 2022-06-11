package com.smartscheduler_admin.fragments.department;

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

import java.util.Locale;

public class AddNewDepartmentFragment extends Fragment {
    private EditText department_name;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    int count=0;
    boolean isCountingDone=false;

    public AddNewDepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_department, container, false);
        department_name = view.findViewById(R.id.departmentName);
        CardView submitData = view.findViewById(R.id.submitCard);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        submitData.setOnClickListener(v -> {
            String name = department_name.getText().toString().trim();


            if (name.equals("")) {
                Toast.makeText(getContext(), "Add Department Name", Toast.LENGTH_SHORT).show();
                return;
            }


            DatabaseReference newRef = myRef.child("Departments").child((++count)+"");

            newRef.child("ID").setValue(count);
            newRef.child("NAME").setValue(name.toUpperCase(Locale.ROOT));


            Toast.makeText(getContext(), "Department Added", Toast.LENGTH_SHORT).show();

            department_name .setText("");

        });




        getScheduleNumber();


        return view;
    }

    private void getScheduleNumber(){
        DatabaseReference newRef = myRef.getRef();
        newRef.child("Departments").addListenerForSingleValueEvent(new ValueEventListener() {
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