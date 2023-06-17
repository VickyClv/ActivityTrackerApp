package com.ds.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class StatisticsRouteThread extends Thread {

    final String GPXName;
    final Handler myHandler;
    final String username;

    public StatisticsRouteThread(String GPXName, Handler myHandler, String username){
        this.GPXName = GPXName;
        this.myHandler = myHandler;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket("192.168.1.7",4321);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeInt(2);
            out.flush();

            out.writeUTF(username);
            out.flush();

            out.writeUTF(GPXName);
            out.flush();

            String[] route = (String[]) in.readObject();
            String[] user = (String[]) in.readObject();

            String[] result = concatWithCollection(route, user);

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putStringArray("Route and User results", result);
            msg.setData(bundle);
            myHandler.sendMessage(msg);

            s.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static String[] concatWithCollection(String[] array1, String[] array2) {
        List<String> resultList = new ArrayList<>(array1.length + array2.length);
        Collections.addAll(resultList, array1);
        Collections.addAll(resultList, array2);

        //the type cast is safe as the array1 has the type T[]
        String[] resultArray = (String[]) Array.newInstance(Objects.requireNonNull(array1.getClass().getComponentType()), 0);
        return resultList.toArray(resultArray);
    }
}