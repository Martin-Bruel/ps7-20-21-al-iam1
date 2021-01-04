package com.example.polyville2.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;


public class Publication implements Serializable {
    String title;
    String description; 
    List<Label> labels;

    @JsonCreator
    public Publication(@JsonProperty("title") String title,@JsonProperty("decription") String description, @JsonProperty("labels") List<Label> labels){
        this.title=title;
        this.description=description;
        this.labels=new ArrayList<Label>();
        if(labels!=null){
            for(Label label:labels)
            addLabel(label);
        }
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void addLabel(Label label){
        if(!labels.contains(label))
            labels.add(label);
    }
}
