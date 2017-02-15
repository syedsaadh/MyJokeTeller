package com.example.xeda.myjoketeller.free;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joketeller.JokeActivity;
import com.example.xeda.myjoketeller.FetchJokeTask;
import com.example.xeda.myjoketeller.JokeListener;
import com.example.xeda.myjoketeller.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity implements JokeListener {
    private String joke;
    InterstitialAd mInterstitialAd;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        Toast.makeText(this, "Free Version", Toast.LENGTH_SHORT).show();
    }

    public void tellJoke(View view) {
        spinner.setVisibility(View.VISIBLE);
        requestNewInterstitial();
        FetchJokeTask fetchJokeTask = new FetchJokeTask(this);
        fetchJokeTask.execute("Null");
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    @Override
    public void onSuccess(String joke) {
        spinner.setVisibility(View.GONE);
        this.joke = joke;
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    Intent intent = new Intent(MainActivity.this, JokeActivity.class);
                    intent.putExtra("jokeExtra", MainActivity.this.joke);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(this, JokeActivity.class);
            intent.putExtra("jokeExtra", joke);
            startActivity(intent);
        }
    }
}