package com.example.pokemonqrcode;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.myViewHolder>
{
    private final RecyclerViewInterface recyclerViewInterface;
    ArrayList<Users> userList;

    public SearchAdapter(ArrayList<Users> userList, RecyclerViewInterface recyclerViewInterface) {
        this.userList = userList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setFilteredList(List<Users> filteredList) {
        this.userList = (ArrayList<Users>) filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Log.d("SearchAdapter", " " + userList.get(position).getUsername());
        holder.name.setText(userList.get(position).getUsername());
//        if (userList.get(position).getTotal_Codes()==0) {
//            holder.total_code.setText("0");
//        }
//        if (userList.get(position).getTotal_Score()==0) {
//            holder.total_score.setText("0");
//        }

        holder.total_code.setText(String.valueOf(userList.get(position).getTotal_Codes()));
//        holder.total_score.setText(userList.get(position).getTotal_Score());
        holder.total_score.setText(Integer.toString(userList.get(position).getTotal_Score()) );
//        holder.picture.setText(userList.get(position).getPicture());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView name,total_score,total_code;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name);
            total_code=itemView.findViewById(R.id.picture3);
            total_score=itemView.findViewById(R.id.picture2);
//            picture=itemView.findViewById(R.id.picture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}