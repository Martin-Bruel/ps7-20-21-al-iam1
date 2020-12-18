package com.example.polyville2.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("openingHours")
public class OpeningHours implements Serializable {
    @JsonIgnore
    public List<LocalTime> getMonday() {
        return monday;
    }
    @JsonIgnore
    public List<LocalTime> getTuesday() {
        return tuesday;
    }
    @JsonIgnore
    public List<LocalTime> getWednesday() {
        return wednesday;
    }
    @JsonIgnore
    public List<LocalTime> getThursday() {
        return thursday;
    }
    @JsonIgnore
    public List<LocalTime> getFriday() {
        return friday;
    }
    @JsonIgnore
    public List<LocalTime> getSaturday() {
        return saturday;
    }
    @JsonIgnore
    public List<LocalTime> getSunday() {
        return sunday;
    }

    @JsonProperty("Monday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> monday=new ArrayList<>();

    @JsonProperty("Tuesday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> tuesday=new ArrayList<>();

    @JsonProperty("Wednesday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> wednesday=new ArrayList<>();

    @JsonProperty("Thursday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> thursday=new ArrayList<>();

    @JsonProperty("Friday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> friday=new ArrayList<>();

    @JsonProperty("Saturday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> saturday=new ArrayList<>();

    @JsonProperty("Sunday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> sunday=new ArrayList<>();

    @JsonCreator
    public OpeningHours(@JsonProperty("Monday") List<LocalTime>monday, @JsonProperty("Tuesday") List<LocalTime>tuesday, 
                        @JsonProperty("Wednesday") List<LocalTime>wednesday, @JsonProperty("Thursday") List<LocalTime>thursday,
                        @JsonProperty("Friday") List<LocalTime>friday, @JsonProperty("Saturday") List<LocalTime>saturday,
                        @JsonProperty("Sunday") List<LocalTime>sunday){
        setMonday(friday);
        setTuesday(tuesday);
        setWednesday(wednesday);
        setThursday(thursday);
        setFriday(friday);
        setSaturday(saturday);
        setSunday(sunday);
    }

    void setMonday(List<LocalTime>monday){
        if(monday!=null && monday.size()%2==0)this.monday=monday;
    }
    void setTuesday(List<LocalTime>tuesday){
        if(tuesday!=null && tuesday.size()%2==0)this.tuesday=tuesday;
    }
    void setWednesday(List<LocalTime>wednesday){
        if(wednesday!=null && wednesday.size()%2==0)this.wednesday=wednesday;
    }
    void setThursday(List<LocalTime>thursday){
        if(thursday!=null && thursday.size()%2==0)this.thursday=thursday;
    }
    void setFriday(List<LocalTime>friday){
        if(friday!=null && friday.size()%2==0)this.friday=friday;
    }
    void setSaturday(List<LocalTime>saturday){
        if(saturday!=null && saturday.size()%2==0)this.saturday=saturday;
    }
    void setSunday(List<LocalTime>sunday){
        if(sunday!=null && sunday.size()%2==0)this.sunday=sunday;
    }




    boolean isOpen(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        List<LocalTime> today;
        switch(dayOfWeek){
            default:today=monday;
            case 1:today=sunday;break;
            case 2:today=monday;break;
            case 3:today=tuesday;break;
            case 4:today=wednesday;break;
            case 5:today=thursday;break;
            case 6:today=friday;break;
            case 7:today=saturday;
        }
        for(int i=0;i<today.size();i+=2){
            int start = today.get(i).getHour()*60+today.get(i).getMinute();
            int end = today.get(i+1).getHour()*60+today.get(i+1).getMinute();
            int now = LocalTime.now().getHour()*60+LocalTime.now().getMinute();
            if (start<=now && now<end){
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public String getDay(int i){
        switch(i+1){
            default:return "";
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3:return "Wednesday";
            case 4:return "Thursday";
            case 5:return "Friday";
            case 6:return "Saturday";
            case 7:return "Sunday";
        }
    }

    @JsonIgnore
    public List<LocalTime> getLocalTimeByDay(int i){
        switch(i+1){
            default:return null;
            case 1: return monday;
            case 2: return tuesday;
            case 3:return wednesday;
            case 4:return thursday;
            case 5:return friday;
            case 6:return saturday;
            case 7:return sunday;
        }
    }

}
