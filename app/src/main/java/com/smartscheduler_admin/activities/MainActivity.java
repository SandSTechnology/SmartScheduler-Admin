package com.smartscheduler_admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartscheduler_admin.R;
import com.smartscheduler_admin.services.ApiServices;
import com.smartscheduler_admin.services.ClientApi;
import com.smartscheduler_admin.services.Data;
import com.smartscheduler_admin.services.MyResponse;
import com.smartscheduler_admin.services.NotificationSender;
import com.smartscheduler_admin.util.BaseUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    DatabaseReference myRef;
    ArrayList<String> token = new ArrayList<>();
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRef = FirebaseDatabase.getInstance().getReference();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String token = new BaseUtil(this).getDeviceToken();
            if (token != null && !token.equals(""))
                myRef.child("DeviceTokens").child("Teachers").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token")
                        .setValue(token);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        setContentView(R.layout.activity_main);
        getSchedule();
       // Notification();

        CardView facultiesCard = findViewById(R.id.facultiesCard);
        CardView coursesCard = findViewById(R.id.coursesCard);
        CardView programsCard = findViewById(R.id.programsCard);
        CardView departmentsCard = findViewById(R.id.departmentsCard);
        CardView roomsCard = findViewById(R.id.roomsCard);
        CardView schedulesCard = findViewById(R.id.schedulesCard);
        CardView logoutCard = findViewById(R.id.logoutCard);

        facultiesCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FacultiesActivity.class);
            startActivity(intent);
        });

        coursesCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
            startActivity(intent);
        });

        programsCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgramsActivity.class);
            startActivity(intent);
        });

        departmentsCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DepartmentsActivity.class);
            startActivity(intent);
        });

        roomsCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoomsActivity.class);
            startActivity(intent);
        });

        schedulesCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SchedulesActivity.class);
            startActivity(intent);
        });

        logoutCard.setOnClickListener(v -> {
            Logout();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_button) {
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        new BaseUtil(this).ClearPreferences();
        startActivity(new Intent(MainActivity.this, LoginActivity.class)); //Go back to login page
        finish();
    }

    private void Notification(){

        myRef.child("DeviceTokens").child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                       token.add(dataSnapshot.child("token").getValue().toString());
                       String tok = dataSnapshot.child("token").getValue().toString();
                        sendNotification(tok, "Title", "Description");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendNotification(String deviceToken, String title, String message) {
        ApiServices apiServices = ClientApi.getRetrofit("https://fcm.googleapis.com/").create(ApiServices.class);

        Data data = new Data(title, message);
        NotificationSender notificationSender = new NotificationSender(data, deviceToken);

        apiServices.sendNotification(notificationSender).enqueue(new retrofit2.Callback<MyResponse>() {
            @Override
            public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        if (response.body().success != 1) {

                            Toast.makeText(MainActivity.this,"Notification Sent",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,"Notification failed",Toast.LENGTH_SHORT).show();
                        }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyResponse> call, @NonNull Throwable t) {

            }
        });
    }
    private void getSchedule(){


        myRef.child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String starttime = dataSnapshot.child("S_TIME").getValue().toString();
                            starttime.toLowerCase();
                            calander = Calendar.getInstance();
                            simpleDateFormat = new SimpleDateFormat("hh:mm");
                            time = simpleDateFormat.format(calander.getTime());
                            // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                            Date startDate = null, enddate = null;
                            try {
                                startDate = simpleDateFormat.parse("22:00");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                enddate = simpleDateFormat.parse("7:00");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            long difference = startDate.getTime() - enddate.getTime();
                            double days = (double) (difference / (1000 * 60 * 60 * 24));
                            double hours = (double) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                            double min = (double) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                            //int min = hours/60;
                            Toast.makeText(MainActivity.this, "" + min, Toast.LENGTH_SHORT).show();
                        /*   int dateDelta = starttime.compareTo(time);
                        switch (dateDelta) {
                            case 0:
                                //startTime and endTime not **Equal**
                                Toast.makeText(MainActivity.this, "equal", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this, "greater", Toast.LENGTH_SHORT).show();

                                //endTime is **Greater** then startTime
                                break;
                            case -1:
                                Toast.makeText(MainActivity.this, "less", Toast.LENGTH_SHORT).show();

                                //startTime is **Greater** then endTime
                                break;
                        }*/
                        }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}