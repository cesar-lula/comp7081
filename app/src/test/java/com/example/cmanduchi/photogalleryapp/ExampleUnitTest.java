package com.example.cmanduchi.photogalleryapp;

import android.content.Context;
import android.widget.EditText;

import com.example.cmanduchi.photogalleryapp.myDb.DataStorageImp;
import com.example.cmanduchi.photogalleryapp.myDb.IDataStore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


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