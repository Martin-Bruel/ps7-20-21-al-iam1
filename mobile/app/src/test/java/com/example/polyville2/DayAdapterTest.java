package com.example.polyville2;

import com.example.polyville2.activity.DayAdapter;
import com.example.polyville2.model.OpeningHours;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DayAdapterTest {
    OpeningHours op;
    DayAdapter dayAdapter;

    @Test
    public void getCountWithOnlyOneOpenDay(){ // but two opening times
        List<LocalTime> list = new ArrayList<>();
        list.add(LocalTime.now().withHour(8).withMinute(0));
        list.add(LocalTime.now().withHour(9).withMinute(0));
        op=new OpeningHours(null,null,null,null,list,null,null);
        dayAdapter = new DayAdapter(op, null);
        assertEquals(1,dayAdapter.getCount());
    }

    @Test
    public void getCountWithSixOpenDay(){
        List<LocalTime> list = new ArrayList<>();
        list.add(LocalTime.now().withHour(8).withMinute(0));
        list.add(LocalTime.now().withHour(9).withMinute(0));
        list.add(LocalTime.now().withHour(15).withMinute(0));
        list.add(LocalTime.now().withHour(16).withMinute(0));
        op=new OpeningHours(list,list,list,list,list,list,null);
        dayAdapter = new DayAdapter(op, null);
        assertEquals(6,dayAdapter.getCount());
    }

}