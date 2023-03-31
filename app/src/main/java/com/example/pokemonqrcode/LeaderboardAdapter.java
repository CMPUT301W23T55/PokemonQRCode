package com.example.pokemonqrcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private ArrayList<Map<String, Object>> UserArray;
    private Context context;

    public LeaderboardAdapter(ArrayList<Map<String, Object>> UserArray, Context context) {
        this.UserArray = UserArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {
        Map<String, Object> User = UserArray.get(position);
        holder.name.setText(User.get("Username").toString());
        holder.rank.setText(String.valueOf(position));
        holder.total_score.setText(User.get("Total_Score").toString());
        holder.total_codes.setText(User.get("Total_Codes").toString());

    }

    @Override
    public int getItemCount() {
        return UserArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,total_score,total_codes,rank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name);
            total_codes=itemView.findViewById(R.id.picture3);
            total_score=itemView.findViewById(R.id.picture2);
            rank = itemView.findViewById(R.id.picture);
        }
    }
}
