package com.example.surveyer.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.json.SurveyJSON;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private final SurveyJSON[] surveys;
    private final SessionJSON[] session;

    public DashboardAdapter(SessionJSON[] sessions) {
        this.session = sessions;
        this.surveys = new SurveyJSON[1];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_element, parent, false);
        return new DashboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return session.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, description;
        private final ConstraintLayout onClick;
        private final ImageView status;
        public ViewHolder(@NonNull View itemView) {
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
