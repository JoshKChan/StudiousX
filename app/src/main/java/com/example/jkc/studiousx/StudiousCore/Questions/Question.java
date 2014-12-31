package com.example.jkc.studiousx.StudiousCore.Questions;

/**
 * Created by Joshua on 24/12/2014.
 */
public abstract class Question {

    private String questionText;

    public void changeQuestionText(String newText){
        questionText = newText;
    }

    public String isValidQuestionText(String candText){
        String result = null;
        //TODO text validation. Return a string to rep an error message. Null indicates no issue.
        return result;
    }

    public String getQuestionText(){
        return questionText;
    }

}
