package com.example.cmanduchi.photogalleryapp.myDb;

/**
 * Created by c.manduchi on 2018-05-02.
 */

public interface IDataStore {
    void saveState(String state);
    String getState();
}
