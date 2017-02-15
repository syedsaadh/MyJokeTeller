package com.example.xeda.myjoketeller;

import android.content.Context;
import android.os.AsyncTask;

import com.example.xeda.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Xeda on 15-02-2017.
 */

public class FetchJokeTask extends AsyncTask<String, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private JokeListener mListener;
    public FetchJokeTask(JokeListener jokeListener){
        this.mListener = jokeListener;
    }
    @Override
    protected String doInBackground(String... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.0.14:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

//        context = params[0].first;
//        String name = params[0].second;

        try {
            return myApiService.getJokes().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mListener.onSuccess(result);
//            Intent i = new Intent(context, JokeActivity.class);
//            i.putExtra("jokeExtra",result);
//            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//            context.startActivity(i);
    }
}
