package com.example.polyville2.activity;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.OpeningHours;
import com.example.polyville2.model.Store;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class DayAdapter extends BaseAdapter {

    private OpeningHours op;
    private Context context;
    public DayAdapter(OpeningHours op, Context context){
        this.context=context;
        this.op=op;
    }
    @Override
    public int getCount() {
        int count =0;
        if (op.getMonday()!=null && op.getMonday().size()>0)count++;
        if (op.getTuesday()!=null && op.getTuesday().size()>0)count++;
        if (op.getWednesday()!=null && op.getWednesday().size()>0)count++;
        if (op.getThursday()!=null && op.getThursday().size()>0)count++;
        if (op.getFriday()!=null && op.getFriday().size()>0)count++;
        if (op.getSaturday()!=null && op.getSaturday().size()>0)count++;
        if (op.getSunday()!=null && op.getSunday().size()>0)count++;
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.day_adapter, parent, false);
        }
        List<LocalTime> day=op.getLocalTimeByDay(i);
        if(day==null || day.size()==0) return view;
        TextView tvOpeningHour = view.findViewById(R.id.tv_OpeningHour);
        String dayName = new String();
        dayName+=op.getDay(i)+"\n";

        for(int j=0;j<day.size();j+=2){
            dayName+="\t"+day.get(j)+" - "+day.get(j+1)+"\n";
        }
        tvOpeningHour.setText(dayName);

        return view;
    }
}