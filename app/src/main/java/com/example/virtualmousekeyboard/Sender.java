package com.example.virtualmousekeyboard;

import android.os.AsyncTask;
import android.util.Log;

import java.io.PrintWriter;
import java.net.Socket;

public class Sender extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];
        try {
                Socket s= new Socket(GlobalClass.ipAddress,4276);
                GlobalClass.isConnect = true;
                PrintWriter pw = new PrintWriter(s.getOutputStream());
                pw.write(message);
                pw.flush();
                pw.close();
                s.close();

            Log.i("doInBack","Socket created & closed");
            //Log.d("checking",String.valueOf(GlobalClass.isConnect));
        }
        catch (Exception e)
        {

            GlobalClass.isConnect = false;
            Log.d("27wali",String.valueOf(GlobalClass.isConnect));
            Log.d("27wali",String.valueOf(e));
        }

        return null;
    }




}
