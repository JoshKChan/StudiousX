package com.example.jkc.studiousx.MiscAdapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.jkc.studiousx.R;

/**
 * Created by Joshua on 05/01/2015.
 */
public class ColorSpinnerManager {

    /*
        When a ColorSpinnerManager receives a Spinner, it establishes its internals such that a color can be
        extracted from it.

        //TODO? Consider internalizing the adapter
     */
    private Spinner spinner;
    private TypedArray colorArray;
    private Context context;
    private int colorIndex;

    public ColorSpinnerManager(Context context, Spinner spinner){
        this.spinner = spinner;
        colorIndex = 0;
        this.context = context;
        colorArray = context.getResources().obtainTypedArray(R.array.colors);
        ColorSpinnerAdapter colorAdapter = new ColorSpinnerAdapter(context);
        spinner.setAdapter(colorAdapter);
        //Set item select listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                colorIndex = pos;
            }
            public void onNothingSelected(AdapterView<?> parent){
                //Do nothing
            }
        });
    }

    public int getColor(){
        int out = colorArray.getColor(colorIndex,R.color.rowDefault);
        //toast("Color output = "+out);
        return out;
    }

    public void setSelectionFromColorID(int id){
        int index = 0;
        for(int i=0;i<colorArray.length();i++){
            if(id == colorArray.getColor(i,-1)){
                index = i;
            }
        }
        colorIndex = index;
        spinner.setSelection(index);
    }
}