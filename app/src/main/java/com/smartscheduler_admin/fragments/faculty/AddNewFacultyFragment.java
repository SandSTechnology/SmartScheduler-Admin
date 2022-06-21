package com.smartscheduler_admin.fragments.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartscheduler_admin.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


public class AddNewFacultyFragment extends Fragment {
    private EditText teacher_subject, teacher_department;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    int count = 0;
    String uID= "";
    boolean isCountingDone = false;
    List<String> areas = new ArrayList<String>();
    List<String> teacher = new ArrayList<String>();

    public AddNewFacultyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_faculty, container, false);
        Spinner DepartmentSpinner = view.findViewById(R.id.allDepartmentSpinner);
        Spinner TeacherSpinner = view.findViewById(R.id.allTeacherSpinner);
        teacher_subject = view.findViewById(R.id.subjectName);
        CardView submitData = view.findViewById(R.id.submitCard);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DepartmentSpinner.setAdapter(areasAdapter);


        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, teacher);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TeacherSpinner.setAdapter(teacherAdapter);

        myRef.child("Departments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = "";
                    if (areaSnapshot.child("NAME").exists())
                        areaName = areaSnapshot.child("NAME").getValue(String.class);
                    areas.add(areaName);
                }

                DepartmentSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("AppUsers").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array

                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    String teacherName = "";

                    //if (teacherSnapshot.child("username").exists() && teacherSnapshot.child("usertype").exists() && teacherSnapshot.child("usertype").equals("Teacher"))
                    String usertype = teacherSnapshot.child("usertype").getValue().toString();
                    if(usertype.equals("Teacher")) {
                        teacherName = teacherSnapshot.child("username").getValue(String.class);
                        uID = teacherSnapshot.child("uid").getValue(String.class);
                        teacher.add(teacherName);
                    }
                }

                TeacherSpinner.setAdapter(teacherAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submitData.setOnClickListener(v -> {

            String name = TeacherSpinner.getSelectedItem().toString();
            String department = DepartmentSpinner.getSelectedItem().toString();
            String subject = teacher_subject.getText().toString().trim();


            if (name.equals("")) {
                Toast.makeText(getContext(), "Add Teacher Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (subject.equals("")) {
                Toast.makeText(getContext(), "Add Subject Name", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference newRef = myRef.child("Faculty").child((++count)+"");

            newRef.child("ID").setValue(count);
            newRef.child("NAME").setValue(name);
            newRef.child("DEPARTMENT").setValue(department);
            newRef.child("SUBJECT").setValue(subject.toUpperCase(Locale.ROOT));


            Toast.makeText(getContext(), "Faculty Added", Toast.LENGTH_SHORT).show();

            teacher_subject.setText("");
            TeacherSpinner.setSelection(0);
            DepartmentSpinner.setSelection(0);

        });

        getScheduleNumber();


        return view;
    }

    private void getScheduleNumber() {
        DatabaseReference newRef = myRef.getRef();
        newRef.child("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isCountingDone = true;
                if (snapshot.exists())
                    count = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}