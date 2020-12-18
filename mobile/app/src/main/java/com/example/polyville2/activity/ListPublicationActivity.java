package com.example.polyville2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.polyville2.R;
import com.example.polyville2.model.Publication;

import java.util.List;

public class ListPublicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_publication);
        ListView lv = findViewById(R.id.list_view_publication);
        List<Publication> publications= (List<Publication>)  getIntent().getSerializableExtra("publications");
        PublicationAdapter publicationAdapter = new PublicationAdapter(publications,this);
        lv.setAdapter(publicationAdapter);
    }
}