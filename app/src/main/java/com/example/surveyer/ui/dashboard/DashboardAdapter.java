package com.example.surveyer.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private final ArrayList<SessionJSON> session;

    public DashboardAdapter(ArrayList<SessionJSON> sessions) {
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
        holder.getSessionName().setText(session.get(position).id);
        ArrayList<SurveyJSON> surveys = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.getRecyclerView().setLayoutManager(linearLayoutManager);
        holder.getRecyclerView().setAdapter(new HomeAdapter(surveys));
        holder.getEdit().setVisibility(View.GONE);
        holder.getLeave().setVisibility(View.GONE);
        holder.getShare().setVisibility(View.GONE);

        holder.getOnClick().setOnClickListener(view -> {
            System.out.println("Clicked");
            if (holder.getRecyclerView().getVisibility() == View.GONE) {
                holder.getRecyclerView().setVisibility(View.VISIBLE);
                holder.getEdit().setVisibility(View.VISIBLE);
                holder.getLeave().setVisibility(View.VISIBLE);
                holder.getShare().setVisibility(View.VISIBLE);
            } else {
                holder.getRecyclerView().setVisibility(View.GONE);
                holder.getEdit().setVisibility(View.GONE);
                holder.getLeave().setVisibility(View.GONE);
                holder.getShare().setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return session.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout onClick;
        private final RecyclerView recyclerView;
        private final TextView sessionName;
        private final ImageView arrow;
        private final Button edit, share, leave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            onClick = itemView.findViewById(R.id.onClick);
            sessionName = itemView.findViewById(R.id.sessionName);
            recyclerView = itemView.findViewById(R.id.sessionRecycler);
            arrow = itemView.findViewById(R.id.imageView3);
            recyclerView.setVisibility(View.GONE);
            edit = itemView.findViewById(R.id.editSessionButton);
            share = itemView.findViewById(R.id.shareSessionButton);
            leave = itemView.findViewById(R.id.leaveSessionButton);
        }

        public Button getEdit() {
            return edit;
        }

        public Button getShare() {
            return share;
        }

        public Button getLeave() {
            return leave;
        }

        public ImageView getArrow() {
            return arrow;
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
