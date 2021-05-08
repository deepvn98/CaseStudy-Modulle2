package controller;

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
        fileHouse.writeFile(houses);
    }

    public void addPersonInHouseHavePerson(Person person, House house) throws IOException {
//        int number1 = house.getNumberOfPeople();
//        int number2 = number1+numberPeople;
        house.getPersonList().add(person);
        int number = house.getPersonList().size();
        house.setNumberOfPeople(number);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile(houses);
    }

    //    Xoá Hộ gia đình không còn thuộc diện quản lý
    public void deleteHouseHolds(House house) throws IOException {
        house.setHouseholder(null);
        house.setNumberOfPeople(0);
        house.setPersonList(null);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile(houses);
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
                fileHouse.writeFile(houses);
            }
        }
        System.err.println("Đã xoá!");
    }

    //    Thêm nhà Trống cần quản lý vào danh sách Nhà
    public void addHouse(House house) throws IOException {
        houses.add(house);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile(houses);
    }

    //    Sửa địa chỉ nhà
    public void editHouseAddress(House house, String newAddress) throws IOException {
        house.setAddress(newAddress);
        FileHouse fileHouse = FileHouse.getInstance();
        fileHouse.writeFile(houses);
    }
//    Xoá nhà không quản lý nữa




    //    Kiểm tra người này có ở trong nhà cho trước hay không? tìm theo tên, trả về người nếu có!
    public Person getPersonByName(House house, String name) {
        Person person = null;
        ;
        List<Person> personList = house.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getName().equalsIgnoreCase(name)) {
                person = personList.get(i);
                return person;
            }
        }
        return null;
    }

    //    Lấy danh sách nhà có hộ dân sinh sống
    public List<House> listHousHavePeople() {
        List<House> houses1 = new ArrayList<>();
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getHouseholder() != null) {
                House house = houses.get(i);
//                System.out.println("Số nhà: "+ number);
                houses1.add(house);
            }
        }
        return houses1;
    }

    //  Danh sách nhà chưa có người ở
    public List<String> listHouseNoPeople() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getHouseholder() == null) {
                String number = houses.get(i).getAddress();
//                System.out.println("Số nhà: "+ number);
                strings.add(number);
            }
        }
        return strings;
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

    //    Delete house
    public void deleteHouse(House house) throws IOException {
                houses.remove(house);
                System.out.println("Đã xoá!");
                FileHouse fileHouse = FileHouse.getInstance();
                fileHouse.writeFile(houses);
    }

    //    Hiển thị danh sách các hộ ở trong khu dân cư
    public void showHouseHoollds(List<House> houses1) {
        int number = 0;
        if (houses1.size() > 0) {
            for (int i = 0; i < houses1.size(); i++) {
                number += 1;
                System.err.println("+> Hộ thứ " + number + houses1.get(i).toString());
            }
        }
    }
//    Hiển thị danh sách toàn bộ nhà theo địa chỉ thuộc diện quản lý

    skjdfhshchssad
    ádsadsad
            ádasd
    đâsd
                    ádasdsa



    public void showAllHouse(){
        System.out.println("\t\t\tDanh sách nhà ở thuộc diện quản lý sắp xếp theo địa chỉ.");
       for (int i =0; i< houses.size(); i++){
           String address = houses.get(i).getAddress();
           System.out.println(address);
       }
    }


    //    Hiển thị danh sách nhà chưa có người ở
    public void showHouse(List<String> list) {
        if (list.size() > 0) {
            for (String s : list
            ) {
                System.out.println("Số nhà: " + s);
            }
        } else {
            System.err.println("Hiện tại không có nhà bỏ trống: ");
        }
    }
}
