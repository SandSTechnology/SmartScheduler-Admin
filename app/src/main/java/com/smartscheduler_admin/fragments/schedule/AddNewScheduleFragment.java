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

public class AddNewScheduleFragment extends Fragment {
    EditText etStartingTime;
    EditText etEndingTime;
    EditText etCreditHour;
    Spinner DaysSpinner;
    Spinner CourseSpinner;
    Spinner FacultySpinner;
    Spinner RoomSpinner;
    Spinner SemesterSpinner;
    Spinner DepartmentSpinner;
    private int  mHour, mMinute;
    Button addNewSchedule;

    ArrayAdapter<String> adapterDays, adapterCourse,adapterFaculty,adapterRoom,adapterSemester,adapterDepartment;

    DatabaseReference myRef;
    FirebaseAuth mAuth;
    ArrayList<String> DaysList = new ArrayList<>();
    ArrayList<String> CourseList = new ArrayList<>();
    ArrayList<String> FacultyList = new ArrayList<>();
    ArrayList<String> RoomList = new ArrayList<>();
    ArrayList<String> SemesterList = new ArrayList<>();
    ArrayList<String> DepartmentList = new ArrayList<>();

    int count=0;
    boolean isCountingDone=false;

    public AddNewScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_schedule, container, false);



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

        FacultyList.add("Asad");
        FacultyList.add("Ali");
        FacultyList.add("Aqib");
        FacultyList.add("Bilal");
        FacultyList.add("Zain");

        RoomList.add("11");
        RoomList.add("12");
        RoomList.add("13");
        RoomList.add("14");
        RoomList.add("15");

        DepartmentList.add("IT");
        DepartmentList.add("BBA");
        DepartmentList.add("BSM");
        DepartmentList.add("LAW");
        DepartmentList.add("BBA");

        SemesterList.add("1");
        SemesterList.add("2");
        SemesterList.add("3");
        SemesterList.add("4");
        SemesterList.add("5");
        SemesterList.add("6");
        SemesterList.add("7");
        SemesterList.add("8");

        etCreditHour = view.findViewById(R.id.editText_CreditHour);
        etStartingTime = view.findViewById(R.id.editText_StartingTime);
        etEndingTime = view.findViewById(R.id.editText_EndingTime);
        DaysSpinner = view.findViewById(R.id.allDaysSpinner);
        CourseSpinner = view.findViewById(R.id.allCoursesSpinner);
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


        adapterCourse = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CourseList);
        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CourseSpinner.setAdapter(adapterCourse);

        adapterFaculty = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, FacultyList);
        adapterFaculty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultySpinner.setAdapter(adapterFaculty);

        adapterSemester = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, SemesterList);
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SemesterSpinner.setAdapter(adapterSemester);

        adapterDepartment = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, DepartmentList);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DepartmentSpinner.setAdapter(adapterDepartment);

        adapterRoom = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, RoomList);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomSpinner.setAdapter(adapterRoom);

        addNewSchedule = view.findViewById(R.id.add_Schedule_Button);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        addNewSchedule.setOnClickListener(v -> {
            if (!isCountingDone){
                return;
            }

            String day = DaysSpinner.getSelectedItem().toString();
            String creditHour = etCreditHour.getText().toString().trim();
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
            if (faculty.equals("")) {
                Toast.makeText(getContext(), "Select Faculty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (room.equals("")) {
                Toast.makeText(getContext(), "Select Room", Toast.LENGTH_SHORT).show();
                return;
            }
            if (semester.equals("")) {
                Toast.makeText(getContext(), "Select Semester", Toast.LENGTH_SHORT).show();
                return;
            }
            if (department.equals("")) {
                Toast.makeText(getContext(), "Select Department", Toast.LENGTH_SHORT).show();
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

            etCreditHour .setText("");
            etStartingTime.setText("");
            etEndingTime .setText("");

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