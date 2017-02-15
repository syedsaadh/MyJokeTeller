package com.example.xeda.myjoketeller;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;

/**
 * Created by Xeda on 15-02-2017.
 */
public class FetchJokeTaskTest implements JokeListener {
    String res;
    CountDownLatch countDownLatch;

    @Test
    public void testFetchTask() throws Throwable {
        countDownLatch = new CountDownLatch(1);
        FetchJokeTask fetchJokeTask = new FetchJokeTask(this);
        fetchJokeTask.execute("Null");
        countDownLatch.await();
        assertFalse("Non Zero String", res.length() == 0);
    }

    @Override
    public void onSuccess(String response) {
        this.res = response;
        countDownLatch.countDown();
    }
}