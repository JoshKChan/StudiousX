package com.example.jkc.studiousx.StudiousCore.Questions;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Joshua on 24/12/2014.
 */
public class MultipleChoiceQuestion extends Question {

    private ArrayList<String> answerList;

    private int correctAnswer;

    public MultipleChoiceQuestion(String question, Collection<String> answerCollection, int cAns){
        this.changeQuestionText(question);
        answerList = new ArrayList<String>();
        correctAnswer = cAns;
        for(String answer: answerCollection){
            answerList.add(answer);
        }
    }

    public Collection<String> getAnswers(){
        return answerList;
    }

    public int getCorrectAnswerIndex(){
        return correctAnswer;
    }


}
