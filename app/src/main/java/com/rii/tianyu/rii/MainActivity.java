package com.rii.tianyu.rii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.net.DatagramSocket;


public class MainActivity extends AppCompatActivity {
    public static final String GAMEPAD = "com.rii.tianyu.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startGamepad(View view) {
        Intent intent = new Intent(this, GamepadActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(GAMEPAD, message);
        startActivity(intent);

    }
}
