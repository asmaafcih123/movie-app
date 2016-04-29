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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmaa_ramdan on 2/10/2016.
 */
public class SignUp extends Activity {

    UserDB UserDB;
    User Userobj;
    boolean checkUser=true;
    List<User> UserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        final EditText mname = (EditText) findViewById(R.id.editText3);
        final EditText mpass = (EditText) findViewById(R.id.editText4);
        final EditText memail = (EditText) findViewById(R.id.editText5);
            Button c = (Button) findViewById(R.id.button5);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button5:
                        Editable name = mname.getText();
                        Editable pass = mpass.getText();
                        Editable email = memail.getText();

                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)
                                && !TextUtils.isEmpty(email)) {

                            if (email.toString().contains("@")) {
                                    Userobj = new User(name.toString(),
                                            email.toString(), pass.toString());
                                UserDB = new UserDB(SignUp.this);
                                List<User> lsi=UserDB.getAllUsers();
                                UserList=new ArrayList<User>();
                                UserList = UserDB.getAllUsers();
                                for (User Userobj1 : UserList) {
                                    if (Userobj1.getE_mail().equals(email.toString())) {
                                        checkUser = false;
                                        break;

                                    }
                                }
                                //true means user not exist
                                if (checkUser) {
                                    UserDB.delete_User();
                                    UserDB.create_Account(Userobj);
                                    Toast.makeText(SignUp.this, " WELCOME " + Userobj.getUser_name() , Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignUp.this, MovieListActivity.class));
                                    finish();

                                } else {
                                    Toast.makeText(SignUp.this, " this account already exist ", Toast.LENGTH_LONG).show();
                                }
                            } else {


                                Toast.makeText(SignUp.this, "insert correct mail", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(SignUp.this,"empty fields", Toast.LENGTH_LONG).show();

                        }


                        break;

                    default:
                        break;
            }
        }});
    }
}


