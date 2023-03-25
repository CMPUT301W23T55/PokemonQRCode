package com.example.pokemonqrcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//public class CustomList extends ArrayAdapter<PlayerCode> {
////    private ArrayList<PlayerCode> playerCodes;
////    private Context context;
////
////    public CustomList(Context context, ArrayList<PlayerCode> playerCodes){
////        super(context,0, playerCodes);
////        this.playerCodes = playerCodes;
////        this.context = context;
////    }
////    public CustomList(Context context){
////        super(context,0);
//////        this.playerCodes = playerCodes;
////        this.context = context;
////    }
//////
//////    @NonNull
//////    @Override
//////    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////////        return super.getView(position, convertView, parent);
//////        View view = convertView;
//////
//////        if(view == null){
//////            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
//////        }
//////
//////        PlayerCode code = playerCodes.get(position);
//////
//////        TextView codeName = view.findViewById(R.id.codeName);
////////        TextView codeScore = view.findViewById(R.id.codeScore);
////////        TextView captureDate = view.findViewById(R.id.captureDate);
////////
//////        codeName.setText(code.getName());
////////        codeScore.setText(code.getScore());
////////        captureDate.setText(code.getDate());
////
////        return view;
//
//    }
//
//
//}
