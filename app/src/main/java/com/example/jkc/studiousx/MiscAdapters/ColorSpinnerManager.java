package com.example.jkc.studiousx.MiscAdapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

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
                getColor();
            }
            public void onNothingSelected(AdapterView<?> parent){
                //Do nothing
            }
        });
    }

    public int getColor(){
        int out = colorArray.getColor(colorIndex,R.color.rowDefault);
        return out;
    }

    public String getHexString(){
        int out = colorArray.getColor(colorIndex,R.color.rowDefault);
        return "#"+Integer.toHexString(out);
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

    private String getHexStringFromArray(int index){
        int number = colorArray.getColor(index,-1);
        return "#"+Integer.toHexString(number);
    }

    public void setSelection(String color){
        for(int i=0;i<colorArray.length();i++){
            if(color.equals(getHexStringFromArray(i))){
                spinner.setSelection(i);
                break;
            }
        }
    }
}
