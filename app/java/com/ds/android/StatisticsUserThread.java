package com.ds.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class StatisticsUserThread extends Thread{

    final Handler myHandler;
    final String username;

    public StatisticsUserThread(Handler myHandler, String username){
        this.myHandler = myHandler;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket("192.168.1.7",4321);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeInt(3);
            out.flush();

            out.writeUTF(username);
            out.flush();

            String[] user = (String[]) in.readObject();

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putStringArray("User results", user);
            msg.setData(bundle);

            myHandler.sendMessage(msg);

            s.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}