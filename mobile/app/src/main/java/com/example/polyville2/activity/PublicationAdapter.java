package com.example.polyville2.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.polyville2.R;
import com.example.polyville2.model.Publication;

import java.util.List;

public class PublicationAdapter extends BaseAdapter {
    private List<Publication> publications;
    private Context context;

    public PublicationAdapter(List<Publication> publications,Context c){
        this.publications=publications;
        this.context=c;
    }
    @Override
    public int getCount() {
        return publications.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.publication_item, viewGroup, false);
        }
        TextView title_pub = view.findViewById(R.id.publication_tv);
        title_pub.setText(publications.get(i).getTitle());
        return view;
    }
}
