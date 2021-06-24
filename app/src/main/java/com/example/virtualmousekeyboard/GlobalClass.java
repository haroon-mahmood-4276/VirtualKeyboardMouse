package com.example.virtualmousekeyboard;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GlobalClass extends Application {
    public static int count = 0;
    public static String ipAddress=null;
    public static boolean isConnect=false;
    public static final String MOUSE_LEFT_CLICK="left_click";
    public static final String MOUSE_RIGHT_CLICK="right_click";


    public static boolean validate(final String ip)
    {
        return ip.matches("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    }
}
