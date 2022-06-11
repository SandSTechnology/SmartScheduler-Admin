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
import com.smartscheduler_admin.model.FacultyModel;
import com.smartscheduler_admin.model.FacultyModel;

import java.util.ArrayList;

public class AdapterAllFaculty extends RecyclerView.Adapter<AdapterAllFaculty.MyHolder> {
    ArrayList<FacultyModel> al;
    Context context;
    Fragment fragment;

    public AdapterAllFaculty(ArrayList<FacultyModel> al, Context c, Fragment f) {
        this.al = al;
        this.context = c;
        fragment = f;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.recycler_item_faculty, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final FacultyModel model = al.get(position);


        if (model.getNAME()!=null && !model.getNAME().equals(""))
            holder.name.setText("" .concat(model.getNAME()));
        if (model.getDEPARTMENT()!=null && !model.getDEPARTMENT().equals(""))
            holder.department.setText("Department: " .concat(model.getDEPARTMENT()));
        if (model.getSUBJECT()!=null && !model.getSUBJECT().equals(""))
            holder.subject.setText("Subject: " .concat(model.getSUBJECT()));


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

        TextView name,subject,department;
        View view;

        public MyHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.card_view);

            name = itemView.findViewById(R.id.teacherName);
            subject = itemView.findViewById(R.id.subjectName);
            department = itemView.findViewById(R.id.departmentName);


        }
    }
}
