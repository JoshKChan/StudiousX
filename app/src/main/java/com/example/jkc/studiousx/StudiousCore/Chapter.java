package com.example.jkc.studiousx.StudiousCore;

import com.example.jkc.studiousx.StudiousCore.Questions.Question;

import java.io.Serializable;

/**
 * Created by Joshua on 07/01/2015.
 */
public class Chapter implements Serializable {

    private String name;

    private Question[] questions;

    public Chapter(){

    }

    public String getName(){
        return name;
    }

    public void setName(String iName){
        name = iName;
    }



}
