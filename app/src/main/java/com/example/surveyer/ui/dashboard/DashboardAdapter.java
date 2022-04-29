package com.example.surveyer.ui.dashboard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.ui.home.HomeAdapter;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private final SessionJSON[] session;

    public DashboardAdapter(SessionJSON[] sessions) {
        this.session = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getSessionName().setText(session[position].id);
        SurveyJSON[] surveys = {
                new SurveyJSON("1", "1", "Flo", "Description", true, "Name", 0, 0, 0, new String[0], 0),
                new SurveyJSON("1", "1", "Flo", "Description", true, "Name", 0, 0, 0, new String[0], 0)
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.getRecyclerView().setLayoutManager(linearLayoutManager);
        holder.getRecyclerView().setAdapter(new HomeAdapter(surveys));

        holder.getOnClick().setOnClickListener(view -> {
            System.out.println("Clicked");
            if (holder.getRecyclerView().getVisibility() == View.GONE) {
                holder.getRecyclerView().setVisibility(View.VISIBLE);

            } else {
                holder.getRecyclerView().setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return session.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout onClick;
        private final RecyclerView recyclerView;
        private final TextView sessionName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            onClick = itemView.findViewById(R.id.onClick);
            sessionName = itemView.findViewById(R.id.sessionName);
            recyclerView = itemView.findViewById(R.id.sessionRecycler);

            recyclerView.setVisibility(View.GONE);
        }

        public ConstraintLayout getOnClick() {
            return onClick;
        }

        public TextView getSessionName() {
            return sessionName;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }
    }
}
