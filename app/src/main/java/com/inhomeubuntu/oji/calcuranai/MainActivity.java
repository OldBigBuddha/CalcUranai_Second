package com.inhomeubuntu.oji.calcuranai;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

//    変数宣言
    private LinearLayout linearLayout;
    private EditText name1_Edit, name2_Edit;
    private TextView text1, text2, textResult;
    private Button resultButton;

    private String name1_Str, name2_Str, result_Str;
    private double name1_double, name2_double, nameTotal_double;
    private long nameTotal_long;
    private boolean isUranai = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        関連付け
        linearLayout    = (LinearLayout)findViewById(R.id.linerLayout);
        name1_Edit      = (EditText)findViewById(R.id.name1_edit);
        name2_Edit      = (EditText)findViewById(R.id.name2_edit);
        resultButton    = (Button)findViewById(R.id.diagnosisButton);
        text1           = (TextView)findViewById(R.id.text1);
        text2           = (TextView)findViewById(R.id.text2);
        textResult      = (TextView) findViewById(R.id.viewResult);

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1_Str = name1_Edit.getText().toString();
                name2_Str = name2_Edit.getText().toString();

                if (!name1_Str.equals("") && !name2_Str.equals("")) {

                    if (!isUranai) {

                        name1_double = convert(name1_Str);
                        name2_double = convert(name2_Str);

                        nameTotal_double = calc(name1_double, name2_double);
                        nameTotal_long   = Math.round(nameTotal_double * 100);
                        textResult.setText(nameTotal_long + "%");
                        resultButton.setText("もう一回!!");

                        hideKeybord(view);

                        text1.requestFocus();
                        isUranai = true;
                    }else if (isUranai) {
                        name1_Edit.setText("");
                        name2_Edit.setText("");
                        resultButton.setText("占う！");
                        textResult.setText(result_Str);
                        name1_Edit.requestFocus();

                        isUranai = false;
                    }
                }else if ( name1_Str.equals("") && !name2_Str.equals("")) {
                    hideKeybord(view);
                    Snackbar.make(name2_Edit, "一人目の名前を入力してないよ", Snackbar.LENGTH_SHORT).show();
                    name1_Edit.requestFocus();
                }else if (!name1_Str.equals("") &&  name2_Str.equals("")) {
                    hideKeybord(view);
                    Snackbar.make(name2_Edit, "二人目の名前を入力してないよ", Snackbar.LENGTH_SHORT).show();
                    name2_Edit.requestFocus();
                }else {
                    hideKeybord(view);
                    Snackbar.make(name2_Edit, "名前を入力してないよ", Snackbar.LENGTH_SHORT).show();
                    name1_Edit.requestFocus();
                }
            }
        });

        result_Str = "???%";
        textResult.setText(result_Str);
        resultButton.setText("占う！");

        name1_Edit.setText("");
        name2_Edit.setText("");

    }

//    public void onCalc(View view) {
//
//        //        EditTextに入力された名前を取得
//        name1_Str = name1_Edit.getText().toString();
//        name2_Str = name2_Edit.getText().toString();
//
//        if (name1_Str != null && name2_Str != null) {
//
//            if (!isUranai) {
//
//                name1_double = convert(name1_Str);
//                name2_double = convert(name2_Str);
//
//                calc(name1_double, name2_double);
//                nameTotal_long = Math.round(nameTotal_double * 100);
//
//                textResult.setTextColor(Color.parseColor("#DD8B91"));
//                textResult.setText(nameTotal_long + "%");
//
//                isUranai = true;
//
//                linearLayout.requestFocus();
//                resultButton.setText("もう一回!");
//
//                name1_Edit.setText("");
//                name2_Edit.setText("");
//            } else if (isUranai) {
//                name1_Edit.requestFocus();
//                textResult.setText(result_Str);
//                resultButton.setText("占う！");
//                isUranai = false;
//            }
//        }else if (name1_Str == null) {
////            name1_Edit.requestFocus();
////            Snackbar.make(linearLayout,"一人目の名前を入れてないよ", Snackbar.LENGTH_SHORT).show();
//        }else if (name2_Str == null) {
////            name2_Edit.requestFocus();
////            Snackbar.make(linearLayout,"ニ人目の名前を入れてないよ", Snackbar.LENGTH_SHORT).show();
//        }else {
////            name1_Edit.requestFocus();
////            Snackbar.make(linearLayout,"どっちも入力してないよ",Snackbar.LENGTH_SHORT).show();
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int ID = item.getItemId();

        if (ID == R.id.actionFinish) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public double convert(String name) {

        double nameNumber;

        name = name.replaceAll("[aA(きゃ)(しゃ)(ちゃ)(にゃ)(ひゃ)(みゃ)(りゃ)あかさたなはまやらわがざだばぱ]","1");
        name = name.replaceAll("[iIいきしちにひみりぎじぢびぴ]","2");
        name = name.replaceAll("[uU(きゅ)(しゅ)(ちゅ)(にゅ)(ひゅ)(みゅ)(りゅ)うくすつぬふむゆるぐずづぶぷ]","3");
        name = name.replaceAll("[eEえけせてねへめれげぜでべぺ]","4");
        name = name.replaceAll("[oO(きょ)(しょ)(ちょ)(にょ)(ひょ)(みょ)(りょ)おこそとのほもよろをごぞどぼぽ]","5");
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
        isUranai = true;

        return  nameTotal_double;

    }

    private void hideKeybord(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
