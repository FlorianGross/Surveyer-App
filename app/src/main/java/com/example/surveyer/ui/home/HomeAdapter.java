package com.example.surveyer.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.App;
import com.example.surveyer.R;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.ui.survey.SurveyView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final ArrayList<SurveyJSON> data;

    public HomeAdapter(ArrayList<SurveyJSON> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_element, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getDescription().setText(data.get(position).surveyDescription);
        holder.getTitle().setText(data.get(position).surveyName);
        holder.getOnClick().setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SurveyView.class);
            intent.putExtra("surveyId", data.get(position).surveyID);
            v.getContext().startActivity(intent);
        });
        if(data.get(position).participants != null) {
            if (data.get(position).participants.length > 0) {
                if (data.get(position).surveyApprove.length > data.get(position).surveyDeny.length) {
                    holder.getStatus().setForeground(App.getContext().getDrawable(R.drawable.button_style));
                } else if (data.get(position).surveyApprove.length < data.get(position).surveyDeny.length) {
                    holder.getStatus().setForeground(App.getContext().getDrawable(R.drawable.button_style_unselected));
                } else {
                    holder.getStatus().setForeground(App.getContext().getDrawable(R.drawable.button_style_neither));
                }
            } else {
                holder.getStatus().setForeground(App.getContext().getDrawable(R.drawable.button_style_neither));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, description;
        private final ConstraintLayout onClick;
        private final ImageView status;

        public ViewHolder(View itemView) {
            super(itemView);
            onClick = itemView.findViewById(R.id.elementClick);
            title = itemView.findViewById(R.id.surveyTitle);
            description = itemView.findViewById(R.id.surveyDescription);
            status = itemView.findViewById(R.id.statusIndicator);
        }

        public ImageView getStatus() {
            return status;
        }

        public ConstraintLayout getOnClick() {
            return onClick;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDescription() {
            return description;
        }
    }
}
