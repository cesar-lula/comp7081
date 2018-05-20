package com.example.cmanduchi.photogalleryapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.cmanduchi.photogalleryapp", appContext.getPackageName());
    }

    @Test
    public void searchTest()
    {

        //Measuring the WorstCase Scenario - Going through all the pictures and finding the last one
        MainActivity mainActivity = rule.getActivity();
        Context context = mainActivity.getApplicationContext();


        mainActivity.captionTextSearch = "cesar";

        long time = System.currentTimeMillis();
        mainActivity.getImages();

        long executionTime = System.currentTimeMillis() - time;

        Log.d("Execution Time" , String.valueOf(executionTime));
    }
}
