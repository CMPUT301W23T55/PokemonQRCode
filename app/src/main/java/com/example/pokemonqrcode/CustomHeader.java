package com.example.pokemonqrcode;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * CustomHeader is a class used to create a header which can be displayed in activities
 * This header contains a back button, a label describing which activity the back button will take
 * the user to, and a label describing the current activity
 */
public class CustomHeader extends RelativeLayout {

    ImageButton back_button;
    TextView back_to_activity;
    TextView head_title;

    public CustomHeader(Context context) {
        super(context);
    }

    public CustomHeader(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public CustomHeader(Context context, AttributeSet attrs, int style) {
        super(context,attrs,style);
    }

    /**
     * Inflates the header (and initializes it)
     * Sets the values of the class parameters back_button, back_to_activity, and head_title
     * Code adapted from https://stackoverflow.com/a/7421863
     */
    public void inflateHead() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_header,this);

        this.back_button = findViewById(R.id.back_button_head);
        this.back_to_activity = findViewById(R.id.back_button_label);
        this.head_title = findViewById(R.id.head_title);

    }

    /**
     * Initialize the header, set the text in the title and the back button description
     * @param title the title, displayed in the left side of the header
     * @param backText the text displayed next to the back button
     */
    public void initializeHead(String title, String backText) {
        inflateHead();
        setTitleText(title);
        setBackText(backText);
    }

    public void setBackText(String text) {
        back_to_activity.setText(text);
    }
    public void setTitleText(String text) { head_title.setText(text); }

}
