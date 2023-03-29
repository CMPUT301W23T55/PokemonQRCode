package com.example.pokemonqrcode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fragment Class used to display a list of players who have caught a QRCode
 */
public class OtherPlayersCaughtFragment extends DialogFragment {

    private ListView playerList;
    private Button closeBtn;

    private ArrayList<String> players;

    /**
     * Empty constructor used to just instantiate a OtherPLayersCaughtFragment
     */
    public OtherPlayersCaughtFragment () {

    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return A view of the fragment
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.other_players_caught_fragment, container, false);
    }

    /**
     * On creating the view gets a list of players who have caught the same code as
     * Populates a list with the names of the players and dislplays them
     * Sets onclicklistener for closing the fragment
     * @param view
     * @param savedInstance
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        assert getArguments() != null;
        ArrayList<String> players = getArguments().getStringArrayList("key");
        playerList = view.findViewById(R.id.other_players_caught_list);
        closeBtn = view.findViewById(R.id.close_others_button);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, players);
        playerList.setAdapter(adapter);

        closeBtn.setOnClickListener(v -> {
            this.dismiss();
        });
    }

}
