package com.example.virtualmousekeyboard;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GlobalClass extends Application {
    public static int count = 0;
    //public static Socket s=null;
    public static  Socket temp = null;
    public static String ipAddress=null;
    public static boolean isConnect=false;
    public static final String MOUSE_LEFT_CLICK="left_click";
    public static final String MOUSE_RIGHT_CLICK="right_click";
  //  public static PrintWriter pw=null;


    public static boolean validate(final String ip)
    {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }


    //    public static void createConnection()
//    {
//        try {
//            s= new Socket(ipAddress,7800);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


}
