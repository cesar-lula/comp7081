package com.example.cmanduchi.photogalleryapp.myDb;

/**
 * Created by c.manduchi on 2018-05-02.
 */

public class DataStorageImp implements IDataStore {

    public String _state = null;

    public void saveState(String state)
    {
        _state = state;
    }

    public String getState()
    {
        return _state;
    }
}
