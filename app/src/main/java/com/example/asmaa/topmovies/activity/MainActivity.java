package com.example.asmaa.topmovies.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import com.example.asmaa.topmovies.R;
import com.example.asmaa.topmovies.demo.UserDB;
import com.example.asmaa.topmovies.model.MyApplication;
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        int logoutflag = ((MyApplication) MainActivity.this.getApplication()).getLogoutFlage();
        Log.d("logoutflag: ", "> " + logoutflag);
        Button b = (Button) findViewById(R.id.button);
        Button c = (Button) findViewById(R.id.button2);

        if (logoutflag==0) {
            ((MyApplication) MainActivity.this.getApplication()).setLogoutFlage(-2);
            finish();
        }
        else if(logoutflag==-1 ||logoutflag==-2){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                }
            });

            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, SignUp.class));
                    finish();

                }
            });
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return true;
    }
}