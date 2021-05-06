package storage;


import model.House;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHouse {
    private static FileHouse INSTACE;

    private FileHouse() {
    }
    public static FileHouse getInstance(){
        if (INSTACE == null){
            INSTACE = new FileHouse();
        }return INSTACE;
    }
    public void writeFile(List<House> houses) throws IOException {
        File file = new File("house.dat");
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
}
