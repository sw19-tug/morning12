package com.twelve.morning.notebook;

import java.util.Random;

public class Tag {

    private static Random rand = new Random();

    private String content;
    public int id = rand.nextInt();

    public Tag(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
