package com.example.virtualmousekeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class PCKeyboard extends KeyboardView implements android.view.View.OnKeyListener,  OnKeyboardActionListener {

    private static final String TAG = PCKeyboard.class.getName();

    Keyboard keyboardQwertySmall, keyboardQwertyCapital, keyboard123First, keyboard123Second, keyboardFunc;
    boolean isShiftOn = false;


    public PCKeyboard(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        init(arg0);
    }

    public PCKeyboard(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        init(arg0);
    }

    public void init(Context arg0){
        keyboardQwertySmall = new Keyboard(arg0, R.xml.qwerty_small);
        keyboardQwertyCapital = new Keyboard(arg0, R.xml.qwerty_capital);
        keyboard123First = new Keyboard(arg0, R.xml.keyboard_123_first);
        keyboard123Second = new Keyboard(arg0, R.xml.keyboard_123_second);
        keyboardFunc = new Keyboard(arg0, R.xml.keyboard_func);


        isShiftOn = true;

        setKeyboard(keyboardQwertySmall);
        setPreviewEnabled(true);
        setOnKeyListener(this);
        setOnKeyboardActionListener(this);
    }

    public void changeShiftState(){
        if(!isShiftOn){
            isShiftOn = true;
            setKeyboard(keyboardQwertyCapital);
        }else{
            isShiftOn = false;
            setKeyboard(keyboardQwertySmall);
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG, "onKey? simple keyCode=" + keyCode);
        return false;
    }

    public void swipeUp() {
        Log.d(TAG, "swipeUp");
    }

    public void swipeRight() {
        Log.d(TAG, "swipeRight");
    }

    public void swipeLeft() {
        Log.d(TAG, "swipeLeft");
    }

    public void swipeDown() {
        Log.d(TAG, "swipeDown");
    }

    public void onText(CharSequence text) {
        Log.d(TAG, "onText? \"" + text + "\"");
    }

    public void onRelease(int primaryCode) { Log.d(TAG, "onRelease? primaryCode=" + (char)primaryCode); }

    public void onPress(int primaryCode) {
        Log.d(TAG, "onPress? primaryCode=" + primaryCode);
    }

    public void onKey(int primaryCode, int[] keyCodes) {
        Log.d(TAG, "onKey multi? primaryCode=" + primaryCode);
        Sender sender = new Sender();

        switch(primaryCode){
            case -5: // 1/2 is pressed
            case -1: //123 is pressed
                setKeyboard(keyboard123First);
                break;
            case -2: //Func is pressed
                setKeyboard(keyboardFunc);
                break;
            case -4: // 2/2 is pressed
                setKeyboard(keyboard123Second);
                break;
            case -3: // 2/2 is pressed
                setKeyboard(keyboardQwertyCapital);
                isShiftOn = true;
                break;
            case -16: //Shift is pressed
                changeShiftState();
                break;
            default:
                sender.execute(String.valueOf(primaryCode));
                Log.i("key pressed"," here");
                break;
        }

        int n1 = 0;
        for (int keyCode : keyCodes) {
            Log.d(TAG, "keyCode=" + keyCode);
            if (keyCode == -1) {
                n1++;
            }
        }
        Log.d(TAG, "keyCode=-1 *" + n1);
    }
}