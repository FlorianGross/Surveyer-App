package com.example.surveyer.ui.session;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.UserJSON;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    UserJSON[] users;

    @NonNull
    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_user_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionAdapter.ViewHolder holder, int position) {
        holder.getName().setText(users[position].userName);
    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final Button edit;
        private final Button remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.session_user_name);
            edit = itemView.findViewById(R.id.edit);
            remove = itemView.findViewById(R.id.remove);
        }

        public TextView getName() {
            return name;
        }

        public Button getEdit() {
            return edit;
        }

        public Button getRemove() {
            return remove;
        }
    }
}
