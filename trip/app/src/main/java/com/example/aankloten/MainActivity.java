package com.example.aankloten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    Button   loginButton;
    EditText username;
    EditText pass;
    String username1;
    String pass1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.loginB);
        username  = (EditText)findViewById(R.id.username);
        pass  = (EditText)findViewById(R.id.pass);
    }

    public void buttonOnClick(View v) {
        TextView text=(TextView)findViewById(R.id.textView2);
        username1 = this.username.getText().toString();
        pass1 = this.pass.getText().toString();

        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String unm=sp1.getString(username1, null);
        String pass = sp1.getString(username1+"pass", null);

        if(username1.equals(unm) && pass1.equals(pass))
        {
            startActivity(new Intent(MainActivity.this, Main2Activity.class));
            text.setText("");
            return;
        }else{
            text.setText("inlog klopt niet");
            return;
        }
    }

    public void regOnClick(View v) {
        TextView text=(TextView)findViewById(R.id.textView2);
        username1 = this.username.getText().toString();
        pass1 = this.pass.getText().toString();

        SharedPreferences sp=this.getSharedPreferences("Login", MODE_PRIVATE);
        String unm2=sp.getString(username1, null);



        if(unm2 == null && username1 != ""){
            SharedPreferences.Editor Ed = sp.edit();
            Ed.putString(username1,username1);
            Ed.putString(username1+"pass",pass1);
            Ed.apply();
            text.setText("");
            return;
        }else {
            text.setText("username bestaat al");
            return;
        }
    }

}
