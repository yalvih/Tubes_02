package com.example.tubes_02.presenter;

import com.example.tubes_02.DBHandler;

public class SubmitHSPresenter {
    DBHandler db;

    public SubmitHSPresenter(DBHandler db) {
        this.db = db;
    }

    public void updateName(String name) {
        this.db.updateLastRecord(name);
    }
}
