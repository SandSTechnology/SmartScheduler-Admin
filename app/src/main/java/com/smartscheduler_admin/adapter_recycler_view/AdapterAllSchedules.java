package com.smartscheduler_admin.adapter_recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.smartscheduler_admin.R;
import com.smartscheduler_admin.model.ScheduleModel;

import java.util.ArrayList;

public class AdapterAllSchedules extends RecyclerView.Adapter<AdapterAllSchedules.MyHolder> {
    ArrayList<ScheduleModel> al;
    Context context;
    Fragment fragment;

    public AdapterAllSchedules(ArrayList<ScheduleModel> al, Context c, Fragment f) {
        this.al = al;
        this.context = c;
        fragment = f;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.recycler_item_schelue, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final ScheduleModel model = al.get(position);

        if (model.getDAY()!=null && !model.getDAY().equals(""))
            holder.day.setText(model.getDAY());

        if (model.getS_TIME()!=null && !model.getS_TIME().equals("") && model.getE_TIME() !=null && !model.getE_TIME().equals(""))
            holder.starting_ending_time.setText(model.getS_TIME() .concat("-").concat(model.getE_TIME()));

        if (model.getCOURSE()!=null && !model.getCOURSE().equals(""))
            holder.course.setText(model.getCOURSE());

        if (model.getFACULTY()!=null && !model.getFACULTY().equals(""))
            holder.faculty.setText("" .concat("Prof. " + model.getFACULTY()));

        if (model.getROOM()!=null && !model.getROOM().equals(""))
            holder.room.setText("" .concat("Room No. " + model.getROOM()));

        if (model.getSEMESTER()!=null && !model.getSEMESTER().equals(""))
            holder.semester.setText(model.getSEMESTER().concat(" Semester"));

        if (model.getCREDIT_HOUR()!=null && !model.getCREDIT_HOUR().equals(""))
            holder.credit_hour.setText(model.getCREDIT_HOUR().concat(" Credit Hour"));

        if (model.getDEPARTMENT()!=null && !model.getDEPARTMENT().equals(""))
            holder.department.setText(model.getDEPARTMENT());

        holder.view.setOnClickListener(v -> {
            //OrderViewDialogFragment dialogFragment = new OrderViewDialogFragment(model_recruiterSide);
            //dialogFragment.show(fragment.getChildFragmentManager(), "Show");
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView starting_ending_time;
        TextView course;
        TextView faculty;
        TextView room;
        TextView semester;
        TextView credit_hour;
        TextView department;
        View view;

        public MyHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.card_view);
            day = itemView.findViewById(R.id.day);
            starting_ending_time = itemView.findViewById(R.id.starting_ending_time);
            course = itemView.findViewById(R.id.course);
            faculty = itemView.findViewById(R.id.faculty);
            room = itemView.findViewById(R.id.room);
            semester = itemView.findViewById(R.id.semester);
            credit_hour = itemView.findViewById(R.id.credit_hour);
            department = itemView.findViewById(R.id.department);
        }
    }
}
