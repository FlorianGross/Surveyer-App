package com.example.surveyer.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.ui.survey.SurveyView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private SurveyJSON[] data;

    public HomeAdapter(SurveyJSON[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.getDescription().setText(data[position].surveyDescription);
       holder.getTitle().setText(data[position].surveyName);
       holder.getOnClick().setOnClickListener(v -> {
           Intent intent = new Intent(v.getContext(), SurveyView.class);
           intent.putExtra("surveyId", data[position].surveyID);
           v.getContext().startActivity(intent);
       });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, description;
        private final ConstraintLayout onClick;
        public ViewHolder(View itemView) {
            super(itemView);
            onClick = itemView.findViewById(R.id.elementClick);
            title = itemView.findViewById(R.id.surveyTitle);
            description = itemView.findViewById(R.id.surveyDescription);
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
