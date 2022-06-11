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
import com.smartscheduler_admin.model.RoomsModel;
import com.smartscheduler_admin.model.ScheduleModel;

import java.util.ArrayList;

public class AdapterAllRooms extends RecyclerView.Adapter<AdapterAllRooms.MyHolder> {
    ArrayList<RoomsModel> al;
    Context context;
    Fragment fragment;

    public AdapterAllRooms(ArrayList<RoomsModel> al, Context c, Fragment f) {
        this.al = al;
        this.context = c;
        fragment = f;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.recycler_item_room, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final RoomsModel model = al.get(position);


        if (model.getROOM()!=null && !model.getROOM().equals(""))
            holder.room.setText("" .concat("Room No: " + model.getROOM()));


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

        TextView room;
        View view;

        public MyHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.card_view);

            room = itemView.findViewById(R.id.roomNumber);

        }
    }
}
