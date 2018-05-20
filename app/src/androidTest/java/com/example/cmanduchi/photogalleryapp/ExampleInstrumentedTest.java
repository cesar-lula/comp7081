package com.example.cmanduchi.photogalleryapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
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

    public String testTag;
    public String testLatDown;
    public String testLatUp;
    public String testLonLeft;
    public String testLonRight;

    @Before
    public void initializeResults()
    {
        testTag = "Cesar";
        testLatDown = "30";
        testLatUp = "50";
        testLonLeft = "-130";
        testLonRight = "124";
    }


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.cmanduchi.photogalleryapp", appContext.getPackageName());
    }

    @Test
    public void testTagSearch()
    {

        MainActivity mainActivity = rule.getActivity();

        Context context = mainActivity.getApplicationContext();


        mainActivity.captionTextSearch = testTag;

        mainActivity.showPic();

        EditText editText = (EditText) mainActivity.findViewById(R.id.caption);

        if(!editText.getText().equals(""))
        {
            assertEquals(testTag.toLowerCase(), editText.getText().toString().toLowerCase());
        }

    }

    @Test
    public void testLatSearch()
    {
        MainActivity mainActivity = rule.getActivity();

        Context context = mainActivity.getApplicationContext();

        mainActivity.latDownSearch = testLatDown;
        mainActivity.latUpSearch = testLatUp;

        TextView editText = (TextView) mainActivity.findViewById(R.id.info);

        String[] tokens = editText.getText().toString().split(" ");

        if(tokens != null && tokens.length > 2)
        {
            double realLat = Double.parseDouble(tokens[2]);
            double testedLatDownValue = Double.parseDouble(testLatDown);
            double testedLatUpValue = Double.parseDouble(testLatUp);


            assertTrue((realLat >= testedLatDownValue && realLat<=testedLatUpValue));
        }
    }

    @Test
    public void testLonSearch()
    {
        MainActivity mainActivity = rule.getActivity();

        Context context = mainActivity.getApplicationContext();

        mainActivity.lonLeftSearch = testLonLeft;
        mainActivity.lonRightSearch = testLonRight;

        TextView editText = (TextView) mainActivity.findViewById(R.id.info);

        String[] tokens = editText.getText().toString().split(" ");

        if(tokens != null && tokens.length > 2)
        {
            double realLon = Double.parseDouble(tokens[1]);
            double testedLonLeftValue = Double.parseDouble(testLonLeft);
            double testedLonRightValue = Double.parseDouble(testLonRight);


            assertTrue((realLon >= testedLonLeftValue && realLon<=testedLonRightValue));
        }
    }

    @After
    public void cleanup()
    {
        //Nothing is needed here for now, since GC is taking care of everything
    }
}
