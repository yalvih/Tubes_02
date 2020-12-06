package com.example.tubes_02.view;

import android.view.View;

public interface FragmentListener {
    void changePage(int page);
    void changeTheme(int theme);
    void closeApplication();
    void PlayBackgroundSound();
    void selectMusic();
}
