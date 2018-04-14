package com.ict.sanvenir.collector;

/**
 * Created by sanve on 2018/4/14.
 */

public class InformationContent {
    InformationContent(String name) {
        this.name = name;
        this.content = "Not available";
        this.explanation = "None";
    }

    public String toString() {
        return this.name;
    }

    private String name;
    private String content;
    private String explanation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    String getExplanation() {
        return explanation;
    }

    void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
