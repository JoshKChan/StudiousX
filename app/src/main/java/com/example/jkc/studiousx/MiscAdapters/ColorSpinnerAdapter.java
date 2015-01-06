package com.example.jkc.studiousx.MiscAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jkc.studiousx.R;

/**
 * Created by Joshua on 05/01/2015.
 */
public class ColorSpinnerAdapter extends ArrayAdapter<String> {

    private static final String[] COLOR_NAMES = new String[]{
            "Blue",
            "Green",
            "Yellow",
            "Red",
            "Purple",
    };
    private static final int[] COLOR_IDS = new int[]{
            R.color.holoBlue,
            R.color.holoGreen,
            R.color.holoYellow,
            R.color.holoRed,
            R.color.holoPurple,
    };

    public ColorSpinnerAdapter(Context context) {
        super(context, R.layout.rowlayout_colorspinner, COLOR_NAMES);
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    //TODO Use convertview
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mySpinner = inflater.inflate(R.layout.rowlayout_colorspinner, parent, false);

        TextView title = (TextView) mySpinner.findViewById(R.id.title);
        LinearLayout colorSpace = (LinearLayout) mySpinner.findViewById(R.id.color_sample);

        title.setText(COLOR_NAMES[position]);
        colorSpace.setBackgroundColor(getContext().getResources().getColor(COLOR_IDS[position]));

        return mySpinner;
    }
}