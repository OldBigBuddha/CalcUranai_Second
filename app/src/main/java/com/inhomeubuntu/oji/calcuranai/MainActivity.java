package com.inhomeubuntu.oji.calcuranai;

import android.content.Context;
import android.content.Intent;
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

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends AppCompatActivity {

//    変数宣言
    private LinearLayout linearLayout;
    private EditText name1_Edit, name2_Edit;
    private TextView text1, text2, textResult;
    private Button resultButton;

    private String name1_Str, name2_Str, result_Str;
    private boolean isUranai = false;

    private BigDecimal bigDecimalName1, bigDecimalName2, bigDecimalName;
    private final BigDecimal TWO = new BigDecimal("2");
    private final BigDecimal HANDRED = new BigDecimal("100");
    private final int ZERO = 0;

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

                        bigDecimalName1 = convert(name1_Str);
                        bigDecimalName2 = convert(name2_Str);
                        calc(bigDecimalName1, bigDecimalName2);

                        bigDecimalName = bigDecimalName.multiply(HANDRED);
                        textResult.setText(bigDecimalName.toString() + "%");
                        changeTextColor(Integer.parseInt(bigDecimalName.setScale(ZERO, RoundingMode.DOWN).toString()));
                        resultButton.setText("もう一回!!");

                        hideKeybord(view);

                        text1.requestFocus();
                        isUranai = true;
                    }else if (isUranai) {
                        name1_Edit.setText("");
                        name2_Edit.setText("");
                        resultButton.setText("占う！");
                        textResult.setText(result_Str);
                        changeTextColor(101);
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
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private BigDecimal convert(String name) {

        BigDecimal nameNumber;

        name = name.replaceAll("[aA(きゃ)(しゃ)(ちゃ)(にゃ)(ひゃ)(みゃ)(りゃ)あかさたなはまやらわがざだばぱ]","1");
        name = name.replaceAll("[iIいきしちにひみりぎじぢびぴ]","2");
        name = name.replaceAll("[uU(きゅ)(しゅ)(ちゅ)(にゅ)(ひゅ)(みゅ)(りゅ)うくすつぬふむゆるぐずづぶぷ]","3");
        name = name.replaceAll("[eEえけせてねへめれげぜでべぺ]","4");
        name = name.replaceAll("[oO(きょ)(しょ)(ちょ)(にょ)(ひょ)(みょ)(りょ)おこそとのほもよろをごぞどぼぽ]","5");
        name = name.replaceAll("[(nn)ん]","0");
        name = name.replaceAll("[^0-5]","");

        nameNumber = new BigDecimal(name);

        return nameNumber;
    }

    private void calc(BigDecimal name1, BigDecimal name2) {
        bigDecimalName = name1.add(name2);
        BigDecimal judge_big = bigDecimalName.setScale(0,RoundingMode.DOWN);

        long judge = Long.parseLong(judge_big.toString());

        while (judge > 0) {
            bigDecimalName = bigDecimalName.divide(TWO, 1, RoundingMode.HALF_UP);
            judge_big = bigDecimalName.setScale(ZERO,RoundingMode.DOWN);
            judge = Long.parseLong(judge_big.toString());
        }
        isUranai = true;

    }

    private void hideKeybord(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void changeTextColor(double compatibility) {

        if (compatibility < 30) {
            textResult.setTextColor(Color.parseColor("#44617B"));
        }else if (compatibility < 70) {
            textResult.setTextColor(Color.parseColor("#CD5E3C"));
        }else if (compatibility <= 100) {
            textResult.setTextColor(Color.parseColor("#D7003A"));
            //TODO:画面遷移
            Intent intent = new Intent(this,SecretActivity.class);
            intent.putExtra("name1", name1_Str);
            intent.putExtra("name2", name2_Str);
        }else {
            textResult.setTextColor(Color.parseColor("#F6E3E5"));
        }
    }



}
