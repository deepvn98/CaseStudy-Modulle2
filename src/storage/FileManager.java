package storage;


import model.House;
import model.Login;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static FileManager INSTACE;

    private FileManager() {
    }
    public static FileManager getInstance(){
        if (INSTACE == null){
            INSTACE = new FileManager();
        }return INSTACE;
    }
    public void writeFile(String pathname,List<House> houses) throws IOException {
        File file = new File(pathname);
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(houses);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public List<House> readFile(String f) throws IOException, ClassNotFoundException {
        File file = new File(f);
        if (!file.exists()){
            file.createNewFile();
        }
        if (file.length()>0){
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object obj = objectInputStream.readObject();
            List<House> houses = (List<House>) obj;
            return houses;
        }else {
            return new ArrayList<>();
        }
    }
    public File createNewFile() throws IOException {
        File file = new File("house.dat");
        if (!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    public File createNewFileLogin() throws IOException {
        File file = new File("login.dat");
        if (!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    public void writeFileLogin(String pathname,List<Login> list) throws IOException {
        File file = new File(pathname);
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public List<Login> readFileLogin(String f) throws IOException, ClassNotFoundException {
        File file = new File(f);
        if (!file.exists()){
            file.createNewFile();
        }
        if (file.length()>0){
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object obj = objectInputStream.readObject();
            List<Login> list = (List<Login>) obj;
            return list;
        }else {
            return new ArrayList<>();
        }
    }

}
