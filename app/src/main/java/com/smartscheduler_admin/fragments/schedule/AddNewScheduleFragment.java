package com.smartscheduler_admin.fragments.schedule;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.List;

public class AddNewScheduleFragment extends Fragment {
    EditText etStartingTime;
    EditText etEndingTime;
    Spinner DaysSpinner;
    Spinner CourseSpinner;
    Spinner FacultySpinner;
    Spinner RoomSpinner;
    Spinner SemesterSpinner;
    Spinner DepartmentSpinner;
    Spinner CreditSpinner;

    private int  mHour, mMinute;
    Button addNewSchedule;

    ArrayAdapter<String> adapterDays, adapterCourse,adapterCreditHours,adapterRoom,adapterSemester,adapterDepartment;

    DatabaseReference myRef;
    FirebaseAuth mAuth;
    ArrayList<String> DaysList = new ArrayList<>();
    ArrayList<String> CourseList = new ArrayList<>();
    ArrayList<String> FacultyList = new ArrayList<>();
    ArrayList<String> RoomList = new ArrayList<>();
    ArrayList<String> SemesterList = new ArrayList<>();
        ArrayList<String> CreditList = new ArrayList<>();
    ArrayList<String> DepartmentList = new ArrayList<>();

    List<String> faculty_name = new ArrayList<String>();
    List<String> rooms = new ArrayList<String>();
    List<String> departments = new ArrayList<String>();

    int count=0;
    boolean isCountingDone=false;

    public AddNewScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_schedule, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        DaysList.add("Monday");
        DaysList.add("Tuesday");
        DaysList.add("Wednesday");
        DaysList.add("Thursday");
        DaysList.add("Friday");
        DaysList.add("Saturday");

        CourseList.add("Course 1");
        CourseList.add("Course 2");
        CourseList.add("Course 3");
        CourseList.add("Course 4");
        CourseList.add("Course 5");
        CourseList.add("Course 6");


        SemesterList.add("1");
        SemesterList.add("2");
        SemesterList.add("3");
        SemesterList.add("4");
        SemesterList.add("5");
        SemesterList.add("6");
        SemesterList.add("7");
        SemesterList.add("8");

        CreditList.add("1");
        CreditList.add("2");
        CreditList.add("3");
        CreditList.add("4");
        CreditList.add("5");
        CreditList.add("6");

        etStartingTime = view.findViewById(R.id.editText_StartingTime);
        etEndingTime = view.findViewById(R.id.editText_EndingTime);
        DaysSpinner = view.findViewById(R.id.allDaysSpinner);
        CourseSpinner = view.findViewById(R.id.allCoursesSpinner);
        CreditSpinner = view.findViewById(R.id.creditHourSpinner);

        FacultySpinner = view.findViewById(R.id.allFacultySpinner);
        RoomSpinner = view.findViewById(R.id.allRoomSpinner);
        SemesterSpinner = view.findViewById(R.id.allSemesterSpinner);
        DepartmentSpinner = view.findViewById(R.id.allDepartmentSpinner);

        etStartingTime.setOnClickListener(v -> {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (view1, hourOfDay, minute) -> {
                        if (hourOfDay > 12) {
                            etStartingTime.setText(hourOfDay - 12 + ":" + minute + " PM");
                        } else {
                            etStartingTime.setText(hourOfDay + ":" + minute + " AM");
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();


        });
        etEndingTime.setOnClickListener(v -> {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (view12, hourOfDay, minute) -> {


                        if (hourOfDay>12) {
                            etEndingTime.setText(hourOfDay-12 + ":" + minute + " PM");
                        } else {
                            etEndingTime.setText(hourOfDay + ":" + minute + " AM");
                        }

                    }, mHour, mMinute, false);
            timePickerDialog.show();


        });

        adapterDays = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, DaysList);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DaysSpinner.setAdapter(adapterDays);

        adapterCreditHours = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CreditList);
        adapterCreditHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CreditSpinner.setAdapter(adapterCreditHours);

        adapterCourse = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CourseList);
        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CourseSpinner.setAdapter(adapterCourse);

        adapterSemester = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, SemesterList);
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SemesterSpinner.setAdapter(adapterSemester);

        ArrayAdapter<String> facultyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, faculty_name);
        facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultySpinner.setAdapter(facultyAdapter);

        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rooms);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomSpinner.setAdapter(areasAdapter);

        ArrayAdapter<String> depAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, departments);
        depAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DepartmentSpinner.setAdapter(depAdapter);

        myRef.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = "";
                    if (areaSnapshot.child("NUMBER").exists())
                        areaName = areaSnapshot.child("NUMBER").getValue(String.class);
                    rooms.add(areaName);
                }
                RoomSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("Departments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String depName = "";
                    if (areaSnapshot.child("NAME").exists())
                        depName = areaSnapshot.child("NAME").getValue(String.class);
                    departments.add(depName);
                }
                DepartmentSpinner.setAdapter(depAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("Faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String fName = "";
                    if (areaSnapshot.child("NAME").exists())
                        fName = areaSnapshot.child("NAME").getValue(String.class);
                    faculty_name.add(fName);
                }

                FacultySpinner.setAdapter(facultyAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addNewSchedule = view.findViewById(R.id.add_Schedule_Button);

        addNewSchedule.setOnClickListener(v -> {
            if (!isCountingDone){
                return;
            }

            String day = DaysSpinner.getSelectedItem().toString();
            String creditHour = CreditSpinner.getSelectedItem().toString();
            String startingTime = etStartingTime.getText().toString().trim();
            String endingTime = etEndingTime .getText().toString().trim();

            String course = CourseSpinner.getSelectedItem().toString();
            String faculty = FacultySpinner.getSelectedItem().toString();
            String room = RoomSpinner.getSelectedItem().toString();
            String department = DepartmentSpinner.getSelectedItem().toString();
            String semester = SemesterSpinner.getSelectedItem().toString();

            if (day.equals("")) {
                Toast.makeText(getContext(), "Add Day", Toast.LENGTH_SHORT).show();
                return;
            }
            if (creditHour.equals("")) {
                Toast.makeText(getContext(), "Add Credit Hour", Toast.LENGTH_SHORT).show();
                return;
            }
            if (startingTime.equals("")) {
                Toast.makeText(getContext(), "Add Starting Time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (endingTime.equals("")) {
                Toast.makeText(getContext(), "Add Ending Time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (course.equals("")) {
                Toast.makeText(getContext(), "Select Course", Toast.LENGTH_SHORT).show();
                return;
            }

            if (semester.equals("")) {
                Toast.makeText(getContext(), "Select Semester", Toast.LENGTH_SHORT).show();
                return;
            }


            DatabaseReference newRef = myRef.child("Schedule").child((++count)+"");

            newRef.child("ID").setValue(count);
            newRef.child("DAY").setValue(day);
            newRef.child("S_TIME").setValue(startingTime);
            newRef.child("E_TIME").setValue(endingTime);
            newRef.child("COURSE").setValue(course);
            newRef.child("FACULTY").setValue(faculty);
            newRef.child("ROOM").setValue(room);
            newRef.child("SEMESTER").setValue(semester);
            newRef.child("CREDIT_HOUR").setValue(creditHour);
            newRef.child("DEPARTMENT").setValue(department);

            Toast.makeText(getContext(), "Schedule Added", Toast.LENGTH_SHORT).show();

            etStartingTime.setText("");
            etEndingTime .setText("");
            CreditSpinner.setSelection(0);
            DaysSpinner.setSelection(0);
            CourseSpinner.setSelection(0);
            FacultySpinner.setSelection(0);
            RoomSpinner .setSelection(0);
            SemesterSpinner .setSelection(0);
            DepartmentSpinner.setSelection(0);
        });

        getScheduleNumber();

        return view;
    }


    private void getScheduleNumber(){
        DatabaseReference newRef = myRef.getRef();
        newRef.child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
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