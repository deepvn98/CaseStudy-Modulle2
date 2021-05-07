package view;

import controller.TownManager;
import model.House;
import model.Person;
import storage.FileHouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileHouse fileHouse = FileHouse .getInstance();
        List<House> houses = new ArrayList<>();
        try {
            houses = fileHouse.readFile("house.dat");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TownManager townManager =TownManager.getInstance("Sang",houses);
        menuAll(townManager);

    }
//    Menu Quản lý chính
    public static void menuAll(TownManager manager){

        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        do {
            System.out.println("Enter 1: Quản lý nhà ở ");
            System.out.println("Enter 2: Quản lý Hộ Dân trong Khu phố ");
            System.out.println("Enter 3: Thoát  ");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                {
                    menuManagerHouse(manager);
                    break;
                }
                case 2:
                {
                    menuManagerHouseHoldr(manager);
                    break;
                }
                case 3:
                {
                    check=  false;
                    break;
                }
            }

        }while (check);

    }
//    Quản lý nhà ở
    public static void menuManagerHouse(TownManager manager){
        System.out.println("Enter 1: Thêm mới nhà: ");
        System.out.println("Enter 2: Xoá nhà: ");
        System.out.println("Enter 3: Sửa địa chỉ nhà: ");
        System.out.println("Enter 4: Danh sách căn nhà chưa có người ở: ");
        System.out.println("Enter 5: Trở về Menu chính ");
        int number = 0;
        boolean check = true;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nhập vào lựa chọn của bạn: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                {
                    System.out.println("Nhập số lượng ngôi nhà bạn muốn thêm mới: ");
                    Scanner scanner1 = new Scanner(System.in);
                    number = scanner1.nextInt();
                    int [] arr = new int[number];
                    for (int i = 0; i< arr.length;i++){
                        System.out.println("Nhập địa chỉ nhà thứ "+(i+1));
                        House house = createNewHouse();
                        try {
                            manager.addHouse(house);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                  break;
                }
                case 2:
                {
                        Scanner scanner1 = new Scanner(System.in);
                        System.out.println("Nhập địa chỉ nhà muốn xoá:");
                        String address = scanner1.nextLine();
                        House house = manager.findHouse(address);
                        try {
                            manager.deleteHouse(house);
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    break;
                }
                case 3:
                {

                    break;
                }
                case 4:
                {
                    List<String> house = manager.listHouseNoPeople();
                    manager.showHouse(house);
                    break;
                }
                case 5:
                {
                    check = false;
                    break;
                }
            }
        }while (check);
    }
//    Quản lý hộ dân
    public static void menuManagerHouseHoldr(TownManager manager){
        System.out.println("Enter 1: Thêm nhà mới: ");
        System.out.println("Enter 2: Nhập thông tin của Hộ dân mới chuyển đến: ");
        System.out.println("Enter 3: Hiển thị danh sách hộ dân đang sinh sống: ");
        System.out.println("Enter 4: Add Person at Households: ");
        System.out.println("Enter 5: Add Person at Households: ");
        System.out.println("Enter 7: Trở về menu chính ");
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        boolean check = true;
        do {
            System.out.println("Nhập vào lựa chọn của bạn: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                {
//                    System.out.println("Nhập số lượng ngôi nhà bạn muốn thêm mới: ");
//                    Scanner scanner1 = new Scanner(System.in);
//                    number = scanner1.nextInt();
//                    int [] arr = new int[number];
//                    for (int i = 0; i< arr.length;i++){
//                        System.out.println("Nhập địa chỉ nhà thứ "+(i+1));
//                        House house = createNewHouse();
//                        try {
//                            manager.addHouse(house);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    break;
                }
                case 2:
                {
                    System.out.println("Nhập Địa chỉ nhà có Hộ mới chuyển đến: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String id = scanner1.nextLine();
                    House house = manager.findHouse(id);
                    if (house!=null){
                        if (house.getPersonList()==null){
                            Scanner scanner2 = new Scanner(System.in);
                            System.out.println("Nhập Tên chủ hộ: ");
                            String name1 = scanner2.nextLine();
                            Scanner scanner3 = new Scanner(System.in);
                            System.out.println("Số thành viên của hộ là: ");
                            int number1 = scanner3.nextInt();
                            List<Person> personList = new ArrayList<>();
                            for (int i =0; i<number1;i++){
                                System.out.println("người thứ "+(i+1));
                                Person person = createNewPerson();
                                try {
                                    manager.addPersonAtHome(name1,house,number1,person,personList);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            System.out.println("Nhà đã có người ở!");
                        }
                    }else {
                        System.err.println("không có căn hộ trên!");
                    }
                    break;
                }
                case 3:
                {
                    if (manager.getHouses().size()>0){
                        manager.showHouseHoollds();
                    }else {
                        System.err.println("Không có hộ dân nào được quản lý!");
                    }
                    break;
                }

            }
        }while (check);

    }
//    Tạo mới một nhà trống không người
    public static House createNewHouse(){
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Nhập địa chỉ nhà: ");
        String address = scanner.nextLine();
        House house = new House(address);
        return house;
    }
//    Tạo mới một người
    public static Person createNewPerson(){
        System.out.println("Nhập FullName:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Nhập Age:");
        Scanner scanner1 = new Scanner(System.in);
        int age = scanner1.nextInt();
        System.out.println("Nhập Gender:");
        Scanner scanner2 = new Scanner(System.in);
        String gender = scanner2.nextLine();
        Person person = new Person(name,age,gender);
        return person;
    }
}
