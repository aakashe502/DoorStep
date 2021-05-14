package com.hadIt.doorstep.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hadIt.doorstep.R;

public class SearchActivity extends AppCompatActivity {
    EditText cardSearch;
    ImageView backpresssed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        cardSearch=findViewById(R.id.cardSearch);
        cardSearch.requestFocus();
        backpresssed=findViewById(R.id.backpresssed);
        backpresssed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}