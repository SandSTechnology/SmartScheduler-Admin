package com.smartscheduler_admin.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.smartscheduler_admin.fragments.department.AddNewDepartmentFragment;
import com.smartscheduler_admin.fragments.department.ViewAllDepartmentsFragment;
import com.smartscheduler_admin.fragments.program.AddNewProgramFragment;
import com.smartscheduler_admin.fragments.program.ViewAllProgramsFragment;

public class Adapter_ProgramsActivityTabs extends FragmentStateAdapter {
    int NumOfTabs;

    public Adapter_ProgramsActivityTabs(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int numOfTabs) {
        super(fragmentManager, lifecycle);
        NumOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ViewAllProgramsFragment();
            case 1:
                return new AddNewProgramFragment();
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return NumOfTabs;
    }
}
