package cs340.game.server.DAOs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import cs340.game.shared.Serializer;

public abstract class FlatFileDAO {
    protected abstract boolean updateDB();
    protected abstract boolean loadDB();

    protected boolean writeObjectToFile(String filename, Serializable obj){
        String b64;
        try {
            b64 = Serializer.serializeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(filename, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        pw.write(b64);
        return true;
    }

    protected <T> T readObjectFromFile(String filename, Class<T> type){
        String b64;
        try {
            b64 = new Scanner(new File("filename")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            return Serializer.deserializeObject(b64, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void deleteFile(String filename){
        File file = new File(filename);
        file.delete();
    }
}
