package com.inhomeubuntu.oji.calcuranai;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

//    変数宣言
    private EditText name1_Edit, name2_Edit;
    private TextView text1, text2, textResult;
    private Button resultButton;

    private String name1_Str, name2_Str, result_Str;
    private int name1_int, name2_int, nameTotal;
    private double name1_double, name2_double, nameTotal_double;
    private long nameTotal_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        関連付け
        name1_Edit      = (EditText)findViewById(R.id.name1_edit);
        name2_Edit      = (EditText)findViewById(R.id.name2_edit);
        resultButton    = (Button)findViewById(R.id.diagnosisButton);
        text1           = (TextView)findViewById(R.id.text1);
        text2           = (TextView)findViewById(R.id.text2);
        textResult      = (TextView) findViewById(R.id.viewResult);

        result_Str = "???%";
        textResult.setText(result_Str);

        name1_Edit.setText("");
        name2_Edit.setText("");
    }

    public void onCalc(View view) {
//        EditTextに入力された名前を取得
        name1_Str = name1_Edit.getText().toString();
        name2_Str = name2_Edit.getText().toString();

        name1_double = convert(name1_Str);
        name2_double = convert(name2_Str);

        calc(name1_double, name2_double);
        nameTotal_long = Math.round(nameTotal_double * 100);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        textResult.setTextColor(Color.BLACK);
        textResult.setText(nameTotal_long + "%");

        name1_Edit.setText("");
        name2_Edit.setText("");

//        Toast.makeText(this,nameTotal_double + "",Toast.LENGTH_LONG);

    }

    public double convert(String name) {

        double nameNumber;

        name = name.replaceAll("[aAあかさたなはまやらわ]","1");
        name = name.replaceAll("[iIいきしちにひみり]","2");
        name = name.replaceAll("[uUうくすつぬふむゆる]","3");
        name = name.replaceAll("[eEえけせてねへめれ]","4");
        name = name.replaceAll("[oOおこそとのほもよろを]","5");
        name = name.replaceAll("[(nn)ん]","0");
        name = name.replaceAll("[^0-5]","");

        nameNumber = Integer.parseInt(name);

//        Toast.makeText(this, name,Toast.LENGTH_SHORT).show();

        return nameNumber;
    }

    private double calc(double name1_double, double name2_double) {

     nameTotal_double = name1_double + name2_double;

        while ((int)nameTotal_double > 0) {
            nameTotal_double /= 2;
        }

        return  nameTotal_double;

    }

}
