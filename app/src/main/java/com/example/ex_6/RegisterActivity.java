package com.example.ex_6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName,editTextPassword,editTextHeight,editTextWeight;
    private Button buttonCancel,buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");

        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextPassword  = (EditText) findViewById(R.id.editText_password);
        editTextHeight = (EditText) findViewById(R.id.editText_height);
        editTextWeight = (EditText) findViewById(R.id.editText_weight);

        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonOK = (Button) findViewById(R.id.button_register_ok);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setText("");
                editTextHeight.setText("");
                editTextPassword.setText("");
                editTextWeight.setText("");
                finish();
            }
        });

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFilledInfo=(editTextName.length()==0 || editTextPassword.length()==0||editTextHeight.length()==0||editTextWeight.length()==0);
                if(isFilledInfo){
                    Toast.makeText(RegisterActivity.this,"請完整輸入四項註冊所需資訊!!",Toast.LENGTH_SHORT).show();
                } else {
                    //Receive the content of editText------------------------------------------------
                    String name = editTextName.getText().toString();
                    String password = editTextPassword.getText().toString();
                    float height = Float.parseFloat(editTextHeight.getText().toString());
                    float weight = Float.parseFloat(editTextWeight.getText().toString());
                    Log.d("main", "name=" + name);
                    Log.d("main", "password=" + password);
                    Log.d("main", "height=" + height);
                    Log.d("main", "weight=" + weight);

                    String msg = getResources().getString(R.string.user_name) + " " + name + "\n";
                    msg += getResources().getString(R.string.user_pwd) + " " + password + "\n";
                    msg += getResources().getString(R.string.user_height) + " " + height + " cm\n";
                    msg += getResources().getString(R.string.user_weight) + " " + weight + " kg";
                    Log.d("main", "msg=" + msg);

                    //Set dialog ---------------------------------------------------------------------
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Register Information");
                    builder.setMessage(msg);
                    builder.setIcon(android.R.drawable.ic_dialog_info);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sp = getSharedPreferences("User_RegisterInfo", MODE_PRIVATE);
                            sp.edit().putString("name", name)
                                    .putString("password", password)
                                    .putFloat("height", height)
                                    .putFloat("weight", weight)
                                    .commit();
                            finish();
                        }
                    });// end-of-dialog OK button

                    builder.setNeutralButton("CanCel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });// end-of-dialog CanCel button
                    builder.show();
                    //----------------------------------------------------------------------------------
                }
            }
        });

    }
}