package com.example.aankloten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    Button   loginButton;
    Button   regB1;
    Button   regB2;
    EditText username;
    EditText pass;
    EditText pass2;
    String username1;
    String pass1;
    String pass2string;
    TextView pass2Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.loginB);
        regB2 = (Button) findViewById(R.id.registerB);
        regB1 = (Button) findViewById(R.id.loginB2);
        username  = (EditText)findViewById(R.id.username);
        pass  = (EditText)findViewById(R.id.pass);
        pass2  = (EditText)findViewById(R.id.pass2);
        pass2Text  = (TextView)findViewById(R.id.textView5);
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
            text.setBackgroundResource(R.color.red);
            return;
        }
    }

    public void regOnClick(View v) {
        TextView text=(TextView)findViewById(R.id.textView2);
        username1 = this.username.getText().toString();
        pass1 = this.pass.getText().toString();
        pass2string = this.pass2.getText().toString();

        SharedPreferences sp=this.getSharedPreferences("Login", MODE_PRIVATE);
        String unm2=sp.getString(username1, null);

        if(unm2 == null){
            if(username1 != "" && pass1.equals(pass2string)){
                SharedPreferences.Editor Ed = sp.edit();
                Ed.putString(username1,username1);
                Ed.putString(username1+"pass",pass1);
                Ed.apply();
                text.setText("Registratie gelukt");
                text.setBackgroundResource(R.color.green);
                return;
            }else{
                text.setText("registratie ging fout");
                text.setBackgroundResource(R.color.red);
                return;
            }

        }else {
            text.setText("username in gebruik");
            text.setBackgroundResource(R.color.red);
            return;
        }
    }
    public void regButton(View v){
        pass2.setVisibility(View.VISIBLE);
        pass2Text.setVisibility(View.VISIBLE);
        regB1.setVisibility(View.INVISIBLE);
        regB2.setVisibility(View.VISIBLE);

    }

}
