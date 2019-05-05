package com.example.aankloten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        if(username1.equals("admin") && pass1.equals("geheim"))
        {
            startActivity(new Intent(MainActivity.this, secondactivity.class));
            text.setText("");
        }else{
            text.setText("inlog klopt niet");
        }
    }
}
