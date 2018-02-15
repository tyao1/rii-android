package com.rii.tianyu.rii;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class GamepadActivity extends AppCompatActivity {

    Gamepad gamepad;
    InetAddress addr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamepad);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.GAMEPAD);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);


        try {
            addr = InetAddress.getByName(message);
        } catch (Exception ex) {
            new AlertDialog.Builder(this)
                    .setTitle("Address not working")
                    .setMessage(ex.getMessage())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            return;
        }
        gamepad = new Gamepad(addr);
        Button[] btns = {
            findViewById(R.id.button0),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button1),
        };
        for (int i = 0 ; i < btns.length; i ++) {
            btns[i].setText(String.valueOf(i));
            btns[i].setOnTouchListener(createListener(i));
        }

    }



    View.OnTouchListener createListener(final int id) {
        return new View.OnTouchListener() {
            public boolean onTouch( View btn , MotionEvent motion ) {
                int action = motion.getAction();
                System.out.println("Motion action:" + action);
                if (action == MotionEvent.ACTION_DOWN) {
                    gamepad.down(id);
                    gamepad.send();
                } else if (action ==  MotionEvent.ACTION_UP) {
                    gamepad.up(id);
                    gamepad.send();
                }
                return true;
            }

        };

    }


}
