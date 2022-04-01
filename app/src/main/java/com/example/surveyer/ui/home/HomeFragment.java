package com.example.surveyer.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.SurveyView;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new HomeAdapter(getContext(), new String[]{"1","2", "3", "4", "5", "6"}));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private static class HomeAdapter extends RecyclerView.Adapter {
        Context context;
        private String[] dataset;

        public HomeAdapter(Context context, String[] dataset) {
            this.context = context;
            this.dataset = dataset;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final Context context;
            private final String surveyID;
            private final ConstraintLayout onClick;

            public ViewHolder(Context context, View v, String surveyID) {
                super(v);
                this.context = context;
                this.surveyID = surveyID;
                onClick = v.findViewById(R.id.elementClick);
                textView = v.findViewById(R.id.textViewElement);


                onClick.setOnClickListener(view -> {
                    Intent intent = new Intent(this.context, SurveyView.class);
                    this.context.startActivity(intent);
                });
            }

            public TextView getTextView() {
                return textView;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(context, LayoutInflater.from(context).inflate(R.layout.survey_element, parent, false), "0");
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.getTextView().setText(dataset[position]);
        }

        @Override
        public int getItemCount() {
            return dataset.length;
        }
    }
}