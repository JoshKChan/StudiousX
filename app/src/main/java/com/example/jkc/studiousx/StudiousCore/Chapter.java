package com.example.jkc.studiousx.StudiousCore;

import com.example.jkc.studiousx.StudiousCore.Questions.Question;

import java.io.Serializable;

/**
 * Created by Joshua on 07/01/2015.
 */
public class Chapter implements Serializable {

    private String name;

    private String description;

    private int number;

    private Question[] questions;

    public Chapter(){
        name = null;
        number = -1;
        questions = null;
    }

    /*
        Name can be anything, just not empty.
        Number can be anything above 0. 1 is the default for the first new chapter, but 0 can be set
            explicitly to represent something like an introductory chapter.
     */
    public static boolean isValid(Chapter chapter){
        boolean out = false;
        if(chapter.getName() !=null && !chapter.getName().isEmpty()
                && chapter.getNumber() >= 0){
            out = true;
        }
        return out;
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        String out = description;
        if(description == null){
            out = "";
        }
        return out;
    }
    public int getNumber(){return number;}
    public int getQuestionCount(){return questions.length;}

    public void setName(String iName){
        name = iName;
    }
    public void setDescription(String desc){
        description = desc;
    }
    public void setNumber(int iNumber){number = iNumber;}



}
