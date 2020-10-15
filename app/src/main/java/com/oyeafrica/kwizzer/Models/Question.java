package com.oyeafrica.kwizzer.Models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class Question implements Serializable {
    @DocumentId
    private String question_id;
    private String answer,a,b,c,question;
    private long time;

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Question(String question_id, String answer, String a, String b, String c, String question, long time) {
        this.question_id = question_id;
        this.answer = answer;
        this.a = a;
        this.b = b;
        this.c = c;
        this.question = question;
        this.time = time;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
