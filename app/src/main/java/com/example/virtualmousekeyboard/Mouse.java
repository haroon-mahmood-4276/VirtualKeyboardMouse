package com.example.virtualmousekeyboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Mouse extends AppCompatActivity {

    private boolean mouseMoved = false;

    TextView lblTouchPad;
    Button btnLeftClick, btnRightClick, btnOpenKeyboard;

    private float initX = 0;
    private float initY = 0;
    private float disX = 0;
    private float disY = 0;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse);

        btnLeftClick = findViewById(R.id.btnLeftClick);
        btnRightClick = findViewById(R.id.btnRightClick);
        lblTouchPad = findViewById(R.id.lblTouchPad);

        lblTouchPad.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //save X and Y positions when user touches the TextView
                    initX = event.getX();
                    initY = event.getY();

                    mouseMoved = false;

                    GlobalClass.count = 1;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    GlobalClass.count = 2;

                    disX = event.getX() - initX;
                    disY = event.getY() - initY;

                    initX = event.getX();
                    initY = event.getY();
                    if (disX != 0 || disY != 0) {

                        Sender sender = new Sender();
                        sender.execute(disX + "," + disY);

                    }
                    mouseMoved = true;
                    return true;

                case MotionEvent.ACTION_UP:
                    Log.d("::::", "onTouch: ActionUp before condition" + GlobalClass.count);
                    if (GlobalClass.count == 2 && !mouseMoved) {
                        Log.d("::::", "onTouch: ActionUp After condition");
                        new Sender().execute(GlobalClass.MOUSE_LEFT_CLICK);
                        GlobalClass.count = 0;
                    }
            }
            return true;
        });

        btnLeftClick.setOnClickListener(v -> {
            Sender sender = new Sender();
            sender.execute(GlobalClass.MOUSE_LEFT_CLICK);
        });

        btnRightClick.setOnClickListener(v -> {

            Sender sender = new Sender();
            sender.execute(GlobalClass.MOUSE_RIGHT_CLICK);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
