package controller;

import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
import model.House;
import model.Person;
import storage.FileHouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TownManager {
    private String name;
    private List<House> houses = new ArrayList<>();
    private static TownManager INSTANCE;

    public TownManager(String name, List<House> houses) {
        this.name = name;
        this.houses = houses;
    }

    public TownManager() {
    }
    public static TownManager getInstance(String name, List<House>houses){
        if (INSTANCE == null){
            INSTANCE = new TownManager(name,houses);
        }return INSTANCE;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

//    public static TownManager getINSTANCE(String sáng, List<House> houses) {
//        return INSTANCE;
//    }

    public static void setINSTANCE(TownManager INSTANCE) {
        TownManager.INSTANCE = INSTANCE;
    }
//    Thêm người vào nhà
    public void addPersonAtHome(String name, House house, int number, Person person,List<Person> personList) throws IOException {
        house.setHouseholder(name);
        house.setNumberOfPeople(number);
//    List<Person> personList = new ArrayList<>();
        personList.add(person);
        house.setPersonList(personList);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile(houses);
    }
//    Thêm nhà vào danh sách Nhà
    public void addHouse(House house) throws IOException {
        houses.add(house);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile(houses);
    }
//    Xoá nhà không quản lý nữa
    public void deleteHouse(House house) throws IOException {
        if (house!=null){
            if (house.getHouseholder()==null){
                houses.remove(house);
                System.out.println("Đã xoá!");
                FileHouse fileHouse = FileHouse.getInstance();
                fileHouse.writeFile(houses);
            }
        }else {
            System.err.println("Bạn không quản lý Ngôi nhà này");
        }
    }
//    Tìm nhà theo số nhà
    public House findHouse(String address){
        House house=null;
        for (int i = 0; i< houses.size();i++){
            if (houses.get(i).getAddress().equalsIgnoreCase(address)){
                house = houses.get(i);
                return house;
            }
        }return null;
    }
//    Hiển thị toàn bộ danh sách hộ khu dân cư
    public void showHouseHoollds(){
        for (int i = 0; i < houses.size();i++){
            if (houses.get(i).getHouseholder()!= null){
                System.out.println(houses.get(i).toString());
            }
        }
    }
//  Danh sách nhà chưa có người ở
    public List<String> listHouseNoPeople(){
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < houses.size();i++){
            if (houses.get(i).getHouseholder()== null){
                String number = houses.get(i).getAddress();
//                System.out.println("Số nhà: "+ number);
                strings.add(number);
            }
        }return strings;
    }
    public void showHouse(List<String> list){
        if (list.size()>0){
            for (String s:list
                 ) {
                System.out.println("Số nhà: "+ s);
            }
        }else {
            System.err.println("Hiện tại không có nhà bỏ trống: ");
        }
    }
}
