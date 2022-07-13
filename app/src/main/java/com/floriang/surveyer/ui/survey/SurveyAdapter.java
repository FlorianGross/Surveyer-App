package com.floriang.surveyer.ui.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.floriang.surveyer.R;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {
    final String[] users;
    final String[] names;
    final String[] approved;
    final String[] declined;
    final String[] pending;

    public SurveyAdapter(String[] users, String[] names, String[] approved, String[] declined, String[] pending) {
        this.users = users;
        this.names = names;
        this.approved = approved;
        this.declined = declined;
        this.pending = pending;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_user_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject tempObj = new JSONObject(names[position]);
            holder.getName().setText(tempObj.getString("shownName"));
        }catch (Exception e){
            holder.getName().setText(names[position]);
        }
        holder.getImage().setImageResource(R.drawable.style_nothing);
        for (String item : approved) {
            if (item.equals(users[position])) {
                holder.getImage().setImageResource(R.drawable.button_style);
            }
        }
        for (String value : declined) {
            if (value.equals(users[position])) {
                holder.getImage().setImageResource(R.drawable.button_style_unselected);
            }
        }
        for (String s : pending) {
            if (s.equals(users[position])) {
                holder.getImage().setImageResource(R.drawable.button_style_neither);
            }
        }
    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.survey_user_name);
            image = itemView.findViewById(R.id.survey_user_image);
        }

        public TextView getName() {
            return name;
        }

        public ImageView getImage() {
            return image;
        }
    }
}
