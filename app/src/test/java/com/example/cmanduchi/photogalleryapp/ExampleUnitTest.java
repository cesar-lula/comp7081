package com.example.cmanduchi.photogalleryapp;

import android.content.Context;

import com.example.cmanduchi.photogalleryapp.myDb.DataStorageImp;
import com.example.cmanduchi.photogalleryapp.myDb.IDataStore;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public String tagCesar;

    @Rule
    //public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dataStorage() throws Exception
    {
        IDataStore data = new DataStorageImp();
        data.saveState("Testing");
        assertEquals(data.getState(), "Testing");
    }


}