package com.example.pokemonqrcode;

import android.annotation.SuppressLint;
import android.content.Context;
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

    final private ArrayList<Map<String, Object>> UserArray;
    final private Context context;

    private String displayStyle;

    public LeaderboardAdapter(ArrayList<Map<String, Object>> UserArray, Context context) {
        this.UserArray = UserArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {
        Map<String, Object> User = UserArray.get(position);
        holder.name.setText(Objects.requireNonNull(User.get("Username")).toString());

        holder.rank.setText(position+1 + ".");
        switch (displayStyle) {
            case "Total Codes":
                holder.score.setText((Objects.requireNonNull(User.get("Total_Codes"))) + " codes");
                holder.score_title.setText("Total Codes");
                break;
            case "Total Score":
                holder.score.setText(Objects.requireNonNull(User.get("Total_Score")) + " pts");
                holder.score_title.setText("Total Score");
                break;
            case "Highest Score":
                holder.score.setText((User.get("Highest")) + " pts");
                holder.score_title.setText("Highest Code");
                break;
            default:
                break;
        }

//        holder.rank.setText(String.valueOf(User.get("rank")) + ".");
//        holder.total_codes.setText("-" + Objects.requireNonNull(User.get("Total_Codes")).toString() + " Codes");
//        holder.total_score.setText("-" + Objects.requireNonNull(User.get("Total_Score")).toString() + " Pts");


        // set top 3 color
        int white, gold, silver, bronze;
        white = ContextCompat.getColor(context, R.color.white);
        gold = ContextCompat.getColor(context, R.color.gold);
        silver = ContextCompat.getColor(context, R.color.silver);
        bronze = ContextCompat.getColor(context, R.color.bronze);

        switch ((int) User.get("rank")) {
            case 1:
                holder.bg.setBackgroundColor(gold);
                break;
            case 2:
                holder.bg.setBackgroundColor(silver);
                break;
            case 3:
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
        public TextView name,score,score_title,rank;
        public RelativeLayout bg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            score_title = itemView.findViewById(R.id.score);
            name=itemView.findViewById(R.id.user_name);
            score=itemView.findViewById(R.id.user_score);
            rank = itemView.findViewById(R.id.user_rank);
            bg = itemView.findViewById(R.id.box);
        }
    }
    public void setDisplayStyle(String displayStyle){
        this.displayStyle = displayStyle;

    }
}
/*
        switch ((int) User.get("rank")) {
            case 1:
                holder.bg.setBackgroundColor(gold);
                break;
            case 2:
                holder.bg.setBackgroundColor(silver);
                break;
            case 3:
                holder.bg.setBackgroundColor(bronze);
                break;
            default:
                holder.bg.setBackgroundColor(white);
                break;
        }
 */

/*
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
 */