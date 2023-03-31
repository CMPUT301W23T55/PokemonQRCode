package com.example.pokemonqrcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        holder.name.setText(Objects.requireNonNull(User.get("Username")).toString());
        holder.rank.setText(String.valueOf(position+1));
        holder.total_score.setText(Objects.requireNonNull(User.get("Total_Score")).toString());
        holder.total_codes.setText(Objects.requireNonNull(User.get("Total_Codes")).toString());

        // set top 3 color
        int white, gold, silver, bronze;
        white = ContextCompat.getColor(context, R.color.white);
        gold = ContextCompat.getColor(context, R.color.gold);
        silver = ContextCompat.getColor(context, R.color.silver);
        bronze = ContextCompat.getColor(context, R.color.bronze);

        switch (position) {
            case 0:
                holder.bg.setBackgroundColor(gold);
                break;
            case 1:
                holder.bg.setBackgroundColor(silver);
                break;
            case 2:
                holder.bg.setBackgroundColor(bronze);
                break;
            default:
                holder.bg.setBackgroundColor(white);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return UserArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,total_score,total_codes,rank;
        public RelativeLayout bg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name);
            total_codes=itemView.findViewById(R.id.picture3);
            total_score=itemView.findViewById(R.id.picture2);
            rank = itemView.findViewById(R.id.picture);
            bg = itemView.findViewById(R.id.box);
        }
    }
}
