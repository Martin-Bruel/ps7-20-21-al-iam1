package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PublicationTest {
    Publication p1;

    @Test
    public void addLabelInConstructorTest(){
        p1=new Publication("title","description",null);
        p1.addLabel(Label.RAIN);
        p1.addLabel(Label.FOOD);
        List<Label> labels =new ArrayList<Label>();
        labels.add(Label.SUNNY);
        p1=new Publication("title","description",labels);
        p1.addLabel(Label.RAIN);
        p1.addLabel(Label.FOOD);
    }

    @Test
    public void cantAddTheSameLabelTwice(){
        List<Label> labels =new ArrayList<Label>();
        labels.add(Label.RAIN);
        labels.add(Label.FOOD);
        p1=new Publication("title","description",labels);
        p1.addLabel(Label.RAIN);
        assertEquals(labels.size(), p1.labels.size());
        for (int i=0; i<p1.labels.size();i++)
            assertEquals(p1.labels.get(i),labels.get(i));
    }
}
