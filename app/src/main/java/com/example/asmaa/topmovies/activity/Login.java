package com.example.asmaa.topmovies.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.asmaa.topmovies.R;
import com.example.asmaa.topmovies.demo.UserDB;
import com.example.asmaa.topmovies.model.MyApplication;
import com.example.asmaa.topmovies.model.User;

import java.util.List;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_pop);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        final EditText email = (EditText) findViewById(R.id.editText);
        final EditText pass = (EditText) findViewById(R.id.editText2);
        Button c = (Button) findViewById(R.id.button2);


        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button2:
                        Editable mmemail = email.getText();
                        Editable mpass = pass.getText();
                        if (!TextUtils.isEmpty(mmemail) && !TextUtils.isEmpty(mpass)) {// add the trainee to database
                            try {
                                UserDB UserDB1=new UserDB(Login.this);
                               int checkUser= UserDB1.login(mmemail.toString(), mpass.toString());
                                if(checkUser==1) {
                                    Toast.makeText(Login.this, "welcome  ", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Login.this, MovieListActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(Login.this,"this account not Exist  ", Toast.LENGTH_LONG).show();

                                }
                            }catch(Exception E){
                                Toast.makeText(Login.this,"in correct pass or mail  ", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(Login.this,"empty fields", Toast.LENGTH_LONG).show();
                        }
                        break;

                    default:
                        break;
                }
            }
        });




    }
}
