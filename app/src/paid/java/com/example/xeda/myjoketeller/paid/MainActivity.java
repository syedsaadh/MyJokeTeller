package com.example.xeda.myjoketeller.paid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.joketeller.JokeActivity;
import com.example.xeda.myjoketeller.FetchJokeTask;
import com.example.xeda.myjoketeller.JokeListener;
import com.example.xeda.myjoketeller.R;

public class MainActivity extends AppCompatActivity implements JokeListener{
    private String joke;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
    }
    public void tellJoke(View view) {
        spinner.setVisibility(View.VISIBLE);
        FetchJokeTask fetchJokeTask = new FetchJokeTask(this);
        fetchJokeTask.execute("Null");
    }

    @Override
    public void onSuccess(String joke) {
        spinner.setVisibility(View.GONE);
        this.joke = joke;
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra("jokeExtra", this.joke);
        startActivity(intent);
    }
}
