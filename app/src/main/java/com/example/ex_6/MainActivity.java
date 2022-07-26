package com.example.ex_6;

import android.app.Dialog;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonRegister,buttonBMI;
    private TextView textViewResult;
    private ActivityResultLauncher<Intent> getData;
    private int BMICODE=100;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister = (Button) findViewById(R.id.button_register);
        buttonBMI = (Button) findViewById(R.id.button_bmi);
        textViewResult = (TextView) findViewById(R.id.textView_result);
        textViewResult.setText("");

        getData= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int code = result.getResultCode();
                if(code==BMICODE){
                    dialog.dismiss();
                    Intent intent = result.getData();
                    String bmiReport = intent.getStringExtra("BMIReport");
                    String str = "kg/m2";
                    String[] report = bmiReport.split("\\n");
                    bmiReport = bmiReport.replaceAll("kg/m2\\n.+$","");
                    SpannableString s = new SpannableString(str);
                    s.setSpan(new SuperscriptSpan(),4,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textViewResult.setText(bmiReport);
                    textViewResult.append(s);
                    textViewResult.append("\n"+report[3]);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog to check userName and userPassword
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_layout);

                EditText dialog_editTextName = (EditText) dialog.findViewById(R.id.editText_dialog_name);
                EditText dialog_editTextPassword = (EditText) dialog.findViewById(R.id.editText_dialog_password);
                Button dialog_buttonRegister = (Button) dialog.findViewById(R.id.button_dialog_register);
                Button dialog_buttonOK = (Button) dialog.findViewById(R.id.button_dialog_ok);

                dialog_buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get user information to check if user registerd
                        SharedPreferences sp = getSharedPreferences("User_RegisterInfo", MODE_PRIVATE);
                        String username = sp.getString("name", "no_name");
                        String userpassword = sp.getString("password", "password");

                        // get user input
                        String inputname = dialog_editTextName.getText().toString();
                        String inputpassword = dialog_editTextPassword.getText().toString();
                        if(inputname.equals(username) && inputpassword.equals(userpassword)){
                            Intent intent = new Intent(MainActivity.this, BMIActivity.class);
                            getData.launch(intent);
                        }else{
                            Toast.makeText(MainActivity.this,"您尚未註冊，需註冊才能使用本服務!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog_buttonRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });


    }
}