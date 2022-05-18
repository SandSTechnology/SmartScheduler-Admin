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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartscheduler_admin.R;
import com.smartscheduler_admin.util.BaseUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String token = new BaseUtil(this).getDeviceToken();
            if (token != null && !token.equals(""))
                myRef.child("DeviceTokens").child("AppAdmins").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token")
                        .setValue(token);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        setContentView(R.layout.activity_main);

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
}