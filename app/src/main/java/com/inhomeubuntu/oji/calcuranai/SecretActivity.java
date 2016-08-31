package com.inhomeubuntu.oji.calcuranai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecretActivity extends AppCompatActivity {

    private Intent intent;
    private String name1_main, name2_main;
    private String result;
    private int name1Int, name2Int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
    }

    private String setResult_int(int name) {

        switch (name) {
            case 0:
                result = "家族だと";
                break;
            case 1:
                result = "友達だと";
                break;
            case 2:
                result = "赤の他人だと";
                break;
            case 3:
                result = "大好きだ";
                break;
            case 4:
                result = "大嫌いだ";
                break;
            case 5:
                result = "親だと";
                break;
            case 6:
                result = "一緒にSexしたい";
                break;
            case 7:
                result = "一緒にKissしたい";
                break;
            case 8:
                result = "一緒に手を繋ぎたいと";
                break;
            case 9:
                result = "友達になると";
                break;
        }

        return result;

    }
}
