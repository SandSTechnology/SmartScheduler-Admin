package com.smartscheduler_admin.fragments.schedule;

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
import java.util.Locale;
import java.util.Objects;

public class AddNewScheduleFragment extends Fragment {
    EditText etDay;
    EditText etStartingTime;
    EditText etEndingTime;
    EditText etCreditHour;

    Spinner CourseSpinner;
    Spinner FacultySpinner;
    Spinner RoomSpinner;
    Spinner SemesterSpinner;
    Spinner DepartmentSpinner;

    Button addNewSchedule;

    ArrayAdapter<String> adapterCourse, adapterFaculty, adapterRoom, adapterSemester, adapterDepartment;

    DatabaseReference myRef;
    FirebaseAuth mAuth;

    ArrayList<String> CourseList = new ArrayList<>();
    ArrayList<String> FacultyList = new ArrayList<>();
    ArrayList<String> RoomList = new ArrayList<>();
    ArrayList<String> SemesterList = new ArrayList<>();
    ArrayList<String> DepartmentList = new ArrayList<>();

    int count = 0;
    boolean isCountingDone = false;

    String day;
    String creditHour;
    String startingTime;
    String endingTime;
    String course;
    String faculty;
    String room;
    String department;
    String semester;

    public AddNewScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_schedule, container, false);

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

        etDay = view.findViewById(R.id.editText_Day);
        etCreditHour = view.findViewById(R.id.editText_CreditHour);
        etStartingTime = view.findViewById(R.id.editText_StartingTime);
        etEndingTime = view.findViewById(R.id.editText_EndingTime);

        CourseSpinner = view.findViewById(R.id.allCoursesSpinner);
        FacultySpinner = view.findViewById(R.id.allFacultySpinner);
        RoomSpinner = view.findViewById(R.id.allRoomSpinner);
        SemesterSpinner = view.findViewById(R.id.allSemesterSpinner);
        DepartmentSpinner = view.findViewById(R.id.allDepartmentSpinner);

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
            if (!isCountingDone) {
                return;
            }

            day = etDay.getText().toString().toLowerCase(Locale.ROOT).trim();
            creditHour = etCreditHour.getText().toString().toLowerCase(Locale.ROOT).trim();
            startingTime = etStartingTime.getText().toString().toLowerCase(Locale.ROOT).trim();
            endingTime = etEndingTime.getText().toString().toLowerCase(Locale.ROOT).trim();
            course = CourseSpinner.getSelectedItem().toString().toLowerCase(Locale.ROOT);
            faculty = FacultySpinner.getSelectedItem().toString().toLowerCase(Locale.ROOT);
            room = RoomSpinner.getSelectedItem().toString().toLowerCase(Locale.ROOT);
            department = DepartmentSpinner.getSelectedItem().toString().toLowerCase(Locale.ROOT);
            semester = SemesterSpinner.getSelectedItem().toString().toLowerCase(Locale.ROOT);

            if (day.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Add Day", Toast.LENGTH_SHORT).show();
                return;
            }
            if (creditHour.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Add Credit Hour", Toast.LENGTH_SHORT).show();
                return;
            }
            if (startingTime.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Add Starting Time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (endingTime.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Add Ending Time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (course.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Select Course", Toast.LENGTH_SHORT).show();
                return;
            }
            if (faculty.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Select Faculty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (room.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Select Room", Toast.LENGTH_SHORT).show();
                return;
            }
            if (semester.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Select Semester", Toast.LENGTH_SHORT).show();
                return;
            }
            if (department.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Select Department", Toast.LENGTH_SHORT).show();
                return;
            }

            CheckOldData(day, creditHour, startingTime, endingTime, course, faculty, room, department, semester);
        });

        getScheduleNumber();

        return view;
    }

    private void SaveDB() {
        DatabaseReference newRef = myRef.child("Schedule").child((++count) + "");

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

        etDay.setText("");
        etCreditHour.setText("");
        etStartingTime.setText("");
        etEndingTime.setText("");

        CourseSpinner.setSelection(0);
        FacultySpinner.setSelection(0);
        RoomSpinner.setSelection(0);
        SemesterSpinner.setSelection(0);
        DepartmentSpinner.setSelection(0);
    }

    private void CheckOldData(String day, String creditHour, String startingTime,
                              String endingTime, String course, String faculty, String room,
                              String department, String semester) {
        DatabaseReference newRef = myRef.getRef();
        newRef.child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist = false;
                for (DataSnapshot s: snapshot.getChildren()) {
                    String DAY = s.child("DAY").getValue(String.class).toLowerCase(Locale.ROOT);
                    String S_TIME = s.child("S_TIME").getValue(String.class).toLowerCase(Locale.ROOT);
                    String E_TIME = s.child("E_TIME").getValue(String.class).toLowerCase(Locale.ROOT);
                    String COURSE = s.child("COURSE").getValue(String.class).toLowerCase(Locale.ROOT);
                    String FACULTY = s.child("FACULTY").getValue(String.class).toLowerCase(Locale.ROOT);
                    String ROOM = s.child("ROOM").getValue(String.class).toLowerCase(Locale.ROOT);
                    String SEMESTER = s.child("SEMESTER").getValue(String.class).toLowerCase(Locale.ROOT);
                    String CREDIT_HOUR = s.child("CREDIT_HOUR").getValue(String.class).toLowerCase(Locale.ROOT);
                    String DEPARTMENT = s.child("DEPARTMENT").getValue(String.class).toLowerCase(Locale.ROOT);

                    if (Objects.equals(DAY, day) && Objects.equals(S_TIME, startingTime)
                            && Objects.equals(E_TIME, endingTime) && Objects.equals(COURSE, course)
                            && Objects.equals(FACULTY, faculty) && Objects.equals(ROOM, room)
                            && Objects.equals(SEMESTER, semester) && Objects.equals(CREDIT_HOUR, creditHour)
                            && Objects.equals(DEPARTMENT, department))
                    {
                        Toast.makeText(getContext(), "Already added Time Table to this Slot !", Toast.LENGTH_LONG).show();
                        exist = true ;
                        break;
                    }
                }
                if (!exist)
                    SaveDB();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getScheduleNumber() {
        DatabaseReference newRef = myRef.getRef();
        newRef.child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
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