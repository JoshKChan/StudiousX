package com.example.jkc.studiousx.StudiousCore;

import com.example.jkc.studiousx.StudiousCore.Questions.Question;

import java.io.Serializable;

/**
 * Created by Joshua on 07/01/2015.
 *
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

    public static boolean isValid(Chapter chapter){
        boolean out = false;
        if(chapter.getName() !=null && !chapter.getName().isEmpty()
                && chapter.getNumber() >= 0){
            out = true;
        }
        return out;
    }

    public ChapterInfo produceChapterInfo(){
        ChapterInfo chapterInfo = new ChapterInfo();
        chapterInfo.setName(name);
        chapterInfo.setDescription(description);
        chapterInfo.setNumber(number);
        return chapterInfo;
    }

    public void updateInfo(ChapterInfo chapterInfo){
        setName(chapterInfo.getName());
        setDescription(chapterInfo.getDescription());
        setNumber(chapterInfo.getNumber());
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

    public void setName(String in){
        if(in!=null&&!in.isEmpty()){
            name=in;
        }
    }
    public void setDescription(String desc){
        if(desc!=null&&!desc.isEmpty()){
            description = desc;
        }
    }
    public void setNumber(int iNumber){number = iNumber;}



}
