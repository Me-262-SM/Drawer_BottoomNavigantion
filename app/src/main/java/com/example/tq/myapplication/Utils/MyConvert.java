package com.example.tq.myapplication.Utils;

import com.example.tq.myapplication.Model.Prisoner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyConvert {

    public static byte[] ObjectToByte(Prisoner prisoner){
        try {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(prisoner);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Prisoner ByteToObject(byte[] data){
        try {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
            ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
            Prisoner prisoner = (Prisoner) inputStream.readObject();
            inputStream.close();
            arrayInputStream.close();
            return prisoner;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
