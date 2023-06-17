package com.ds.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UploadThread extends Thread{

    final String GPXName;
    final String readFile;
    final Handler myHandler;

    public UploadThread(Handler myHandler, String GPXName, String readFile){
        this.myHandler = myHandler;
        this.GPXName = GPXName;
        this.readFile = readFile;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket("192.168.1.7",4321);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeInt(1);
            out.flush();

            out.writeUTF(GPXName);
            out.flush();

            out.writeUTF(readFile);
            out.flush();

            String username = in.readUTF();

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            msg.setData(bundle);
            s.close();

            myHandler.sendMessage(msg);

        } catch (Exception e) {
            System.err.println("Exception");
        }
    }
}