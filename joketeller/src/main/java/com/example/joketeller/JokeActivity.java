package com.example.joketeller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Intent intent = this.getIntent();
        String joke = intent.getStringExtra("jokeExtra");
        TextView tv = (TextView) findViewById(R.id.joke_tv);
        tv.setText(joke);
    }
}
