package com.kikyou.mymacapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/test/two")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        gitTest1();
        gitTest2();
        gitTest3();
    }

    private void gitTest1() {
        Log.e("GIT", "git test 1");
    }

    private void gitTest2() {
        Log.e("GIT", "git test 2");
    }

    private void gitTest3() {
        Log.e("GIT", "git test 3");
    }
}
