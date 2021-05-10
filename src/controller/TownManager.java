package controller;

import model.House;
import model.Person;
import storage.FileHouse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TownManager {
    private String name;
    private List<House> houses = new ArrayList<>();
    private static TownManager INSTANCE;

    private TownManager(String name, List<House> houses) {
        this.name = name;
        this.houses = houses;
    }

    private TownManager() {
    }

    public static TownManager getInstance(String name, List<House> houses) {
        if (INSTANCE == null) {
            INSTANCE = new TownManager(name, houses);
        }
        return INSTANCE;
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

    public static void setINSTANCE(TownManager INSTANCE) {
        TownManager.INSTANCE = INSTANCE;
    }

    //    Thêm người vào nhà chưa có người!
    public void addPersonAtHome(String name, House house, int number, Person person, List<Person> personList) throws IOException {
        house.setHouseholder(name);
        house.setNumberOfPeople(number);
        personList.add(person);
        house.setPersonList(personList);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile("house.dat",houses);
    }

    //Thêm thành viên vào hộ gia đình
    public void addPersonInHouseHavePerson(Person person, House house) throws IOException {
        house.getPersonList().add(person);
        int number = house.getPersonList().size();
        house.setNumberOfPeople(number);
        System.out.println("Thêm thành công");
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile("house.dat",houses);
    }

    //    Xoá Hộ gia đình không còn thuộc diện quản lý
    public void deleteHouseHolds(House house) throws IOException {
        house.setHouseholder(null);
        house.setNumberOfPeople(0);
        house.setPersonList(null);
        System.err.println("Đã xoá");
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile("house.dat",houses);
    }

    //    Xoá thành viên trong hộ

    public void deletePersonInHouse(House house, Person person) throws IOException {
        List<Person> personList = house.getPersonList();
        int size = personList.size();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.contains(person)) {
                personList.remove(person);
                house.setPersonList(personList);
                int serial = 1 + i;
                house.setNumberOfPeople(size - serial);
                i--;
                FileHouse fileHouse = FileHouse.getInstance();
                fileHouse.writeFile("house.dat",houses);
            }
        }
        System.err.println("Đã xoá!");
    }

    //    Thêm nhà Trống cần quản lý vào danh sách Nhà
    public void addHouse(House house) throws IOException {
        houses.add(house);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile("house.dat",houses);
    }

    //    Sửa địa chỉ nhà
    public void editHouseAddress(House house, String newAddress) throws IOException {
        house.setAddress(newAddress);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile("house.dat",houses);
    }

    //    Delete house
    public void deleteHouse(House house) throws IOException {
        houses.remove(house);
        System.out.println("Đã xoá!");
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile("house.dat",houses);
    }

    //    Kiểm tra người này có ở trong nhà cho trước hay không? tìm theo tên, trả về người nếu có!
    public Person checkPersonByName(House house, String name) {
        Person person = null;
        List<Person> personList = house.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getName().equalsIgnoreCase(name)) {
                person = personList.get(i);
                return person;
            }
        }
        return null;
    }

////    Tìm người trong khu phố, bằng tên, tìm trong danh sách hộ dân đang sinh sống
    public List<Person> getInforPersonByName(List<Person>personList ,String name){
        List<Person> personList1 = new ArrayList<>();
        for (int i =0; i< personList.size();i++){
            if (personList.get(i).getName().equalsIgnoreCase(name)){
                Person person= personList.get(i);
                personList1.add(person);
            }
        }return personList1;

    }

    //    Lấy danh sách nhà có hộ dân sinh sống
    public List<House> listHousHavePeople() {
        List<House> houses1 = new ArrayList<>();
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getHouseholder() != null) {
                House house = houses.get(i);
                houses1.add(house);
            }
        }
        return houses1;
    }

//    Danh sách nhà ở không có người ở
    public List<House> listHouseNoPeople() {
        List<House> houses1 = new ArrayList<>();
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getHouseholder() == null) {
                House house = houses.get(i);
                houses1.add(house);
            }
        }
        return houses1;
    }

//    Danh sách người dân trong khu
    public List<Person> getPersonInHouse(){
        List<Person> personList = new ArrayList<>();
        for (int i =0; i< houses.size();i++){
            if (houses.get(i).getHouseholder()!=null){
                List<Person> personList1 = houses.get(i).getPersonList();
                personList.addAll(personList1);
            }
        }return personList;
    }

    //    Tìm nhà theo số nhà
    public House findHouse(String address) {
        House house = null;
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getAddress().equalsIgnoreCase(address)) {
                house = houses.get(i);
                return house;
            }
        }
        return null;
    }

//    Danh sách người có tuổi nhập vào từ bàn phím
    public List<Person> getInforPersonByAge(List<Person> personList, int age){
        List<Person> personList1 = new ArrayList<>();
        for (int i = 0; i<personList.size();i++){
            if (personList.get(i).getAge() == age){
                     Person person = personList.get(i);
                     personList1.add(person);
            }
        }
        return personList1;
    }

    //    Kiểm tra nhà có người hay không?
    public boolean checkHouse(House house) {
        boolean check = false;
        if (house != null) {
            if (house.getHouseholder() != null) {
                return check = true;
            }
        }
        return check;
    }


    //    Hiển thị danh sách các hộ ở trong khu dân cư
    public void showHouseHoollds(List<House> houses1) {
        int number = 0;
        if (houses1.size() > 0) {
            for (int i = 0; i < houses1.size(); i++) {
                number += 1;
                System.out.println("+> Hộ thứ " + number + houses1.get(i).toString());
            }
        }
    }

//    Hiển thị danh sách người dân thuộc diện quản lý
    public void showPerson(List<Person> personList){
        System.out.println("\t\t_____Danh sách người dân trong khu phố_____");
        System.out.println();
        System.out.printf("\t\t\t%-20s %-20s %-20s","Tên","Tuổi","Gới Tính");
        for (int i = 0; i< personList.size();i++){
            String name = personList.get(i).getName();
            String gender = personList.get(i).getGender();
            int age = personList.get(i).getAge();
            System.out.printf("\n\t\t\t%-20s %-20d %-20s",name,age,gender);
        }
        System.out.println();

    }

//    Hiển thị danh sách toàn bộ nhà theo địa chỉ thuộc diện quản lý
    public void showAllHouse(){
        System.out.printf("\n\t\t\t\t\t\t%-20s %-20s","STT","Địa chỉ:");
       for (int i =0; i< houses.size(); i++){
           String address = houses.get(i).getAddress();
           int sothutu = i + 1;
           System.out.printf("\n\t\t\t\t\t\t%-20s %-20s", sothutu,address);
       }
        System.out.println();
    }

    //    Hiển thị danh sách nhà chưa có người ở
    public void showHouse(List<House> list) {
        if (list.size() > 0) {
            System.out.printf("\n\t\t\t\t\t\t%-20s %-20s","STT","Địa chỉ:");
            for (int i = 0; i<list.size();i++){
                String address = list.get(i).getAddress();
                System.out.printf("\n\t\t\t\t\t\t%-20s %-20s",(i+1),address);
            }
        } else {
            System.err.println("Hiện tại không có nhà bỏ trống: ");
        }
        System.out.println();
    }
}
