package com.example.ex_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    private String bmiName, bmiPwd;
    private float bmiHeight,bmiWeight;
    private TextView textViewBMI;
    private Button buttonBMIOK;
    private int BMICODE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);
        SharedPreferences sp = getSharedPreferences("User_RegisterInfo", MODE_PRIVATE);
        bmiName = sp.getString("name", "no_name");
        bmiPwd = sp.getString("password","password");
        bmiHeight=sp.getFloat("height",0.0f);
        bmiWeight=sp.getFloat("weight",0.0f);
        setTitle(bmiName);
        float valueBMI = bmiWeight / ((bmiHeight / 100.0f) * (bmiHeight / 100.0f));
        String bmiMsg="";
        if(valueBMI>=25){//        between 25 and 29.9 – you're in the overweight range
            bmiMsg="過重";
        }else if(valueBMI>=18.5 ){//        between 18.5 and 24.9 – you're in the healthy weight range
            bmiMsg="正常";
        }else{
            bmiMsg="過輕";//        below 18.5 – you're in the underweight range
        }

        String msg = getResources().getString(R.string.user_height) + " " + bmiHeight + " cm\n";
        msg+=getResources().getString(R.string.user_weight) + " " + bmiWeight + " kg\n";
        msg+="BMI : "+(Math.round(valueBMI*100.0f)/100.0f)+" ";
        String str = "kg/m2";
        SpannableString s = new SpannableString(str);
        s.setSpan(new SuperscriptSpan(),4,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewBMI = (TextView) findViewById(R.id.textView_bmi);
        buttonBMIOK = (Button) findViewById(R.id.button_bmi_ok);
        textViewBMI.setText(msg);
        textViewBMI.append(s);
        textViewBMI.append("\n"+bmiMsg);

        buttonBMIOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BMIActivity.this, MainActivity.class);
                String bmireport = textViewBMI.getText().toString();
                Log.d("main","bmireport="+bmireport);
                intent.putExtra("BMIReport",bmireport);
                setResult(BMICODE,intent);
                finish();
            }
        });

    }
}